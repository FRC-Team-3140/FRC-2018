package main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.kauailabs.navx.frc.AHRS;//NavX import
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import util.ChezyMath;
import util.DriveHelper;
import util.EncoderHelper;
import util.motion.TrajectoryPath;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import interfacesAndAbstracts.ImprovedSubsystem;
import main.Robot;
import main.commands.drivetrain.Drive;
import main.subsystems.subsystemConstants.DrivetrainConstants;

public class Drivetrain extends ImprovedSubsystem implements DrivetrainConstants {
	public static double kPHeading = kPHeadingDefault;
	public static double kIHeading = kIHeadingDefault;
	public static double kDHeading = kDHeadingDefault; //push to sdb
	
	private static double lastHeadingIntegral = 0;
	private static double lastHeadingError = 0;
	private static double lastTime = 0;
			
	//TELEOP DRIVING
	private DriveHelper driveHelper = new DriveHelper(7.5);
	
	//Timer for PID
	private Timer timer = new Timer();
	
	//SENSORS
	private static AHRS NavX;	

	public Drivetrain() {
		try {
			/* Communicate w/navX-MXP via the MXP SPI Bus.                                     */
	        /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
	        /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
	        NavX = new AHRS(SPI.Port.kMXP); 
		} catch (RuntimeException ex) {
	          DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
	    }
		
		setTalonDefaults();
		configTalonEncoders();
		zeroSensors();
		pushPIDGainsToSmartDashboard();
		pushNormallyUnusedToSmartDashboard();
		setPIDDefaults();
	}
	
	/*****************
	 * DRIVE METHODS *
	 *****************/
	
	// DRIVE FOR TELEOP
	public void driveVelocity(double throttle, double heading) {
		if (isCompetitionRobot) {
			arcadeDrive(driveHelper.handleOverPower(driveHelper.handleDeadband(-throttle, throttleDeadband)),
					driveHelper.handleOverPower(driveHelper.handleDeadband(-heading, headingDeadband)), true);
		}
		else {
			arcadeDrive(driveHelper.handleOverPower(driveHelper.handleDeadband(-throttle, throttleDeadband)),
					driveHelper.handleOverPower(driveHelper.handleDeadband(-heading, headingDeadband)), true);
		}
	}
	
	//Drive for testing the drivetrain so that the needed constants to compute the bias voltages may be derived
	public void driveVoltageTankTest(double leftVoltage, double rightVoltage, double voltageCompensationVoltage) {
		tankDrive(leftVoltage/voltageCompensationVoltage, rightVoltage/voltageCompensationVoltage, false);
	}
	
	/**
	 * Uses cascaded loop to drive straight to a certain target position. So far, angle can only be 0
	 * @param inches inches forward to drive
	 * @param angle  target angle to end up at
	 */
	public void driveWithAnglePID(double inches, double angle) {
		int ticks = distanceToTicks(inches);

		double t = timer.get();
		double dt = t - lastTime;

		double heading = getHeading();
		double headingError = ChezyMath.getDifferenceInAngleDegrees(heading, angle);
		double derivative = ChezyMath.getDifferenceInAngleDegrees(lastHeadingError, headingError) / dt;
		double integral = lastHeadingIntegral + (headingError * dt);
		double gyroCorrection = headingError * kPHeading + integral * kIHeading + derivative * kDHeading;

		leftDriveMaster.set(ControlMode.Position, ticks, DemandType.ArbitraryFeedForward, gyroCorrection);
		rightDriveMaster.set(ControlMode.Position, ticks, DemandType.ArbitraryFeedForward, -gyroCorrection);

		lastHeadingError = headingError;
		lastHeadingIntegral = integral;
		lastTime = t;
	}
	
	/*
	 *  Drives each side with closed-loop position PID independently
	 *  @param leftInches   Inches for the left side to drive, in inches
	 *  @param rightInches  Inches for the right side to drive, in inches
	 */
	public void drivePID(double leftInches, double rightInches) {
		int leftTicks = distanceToTicks(leftInches);
		int rightTicks = distanceToTicks(rightInches);
		leftDriveMaster.set(ControlMode.Position, leftTicks);
		rightDriveMaster.set(ControlMode.Position, rightTicks);
	}

	/*
	 * Turns to the specified angle with closed loop heading PID
	 * @param angle  The number of degrees to turn
	 */
	public void turnToAngleGyro(double angle) {
		double heading = getHeading();
		double headingError = angle - heading;
		double t = timer.get();
		double dt = t - lastTime;
		//System.out.println(headingError);
		
		double headingDerivative = (headingError - lastHeadingError) / dt;
		double headingIntegral = lastHeadingIntegral + headingError * dt;
		double output = kPHeading * headingError + kIHeading * headingIntegral + kDHeading * headingDerivative;
		if(Math.abs(output) > kMaxTurnRate) output = Math.signum(output) * kMaxTurnRate;
		
		//System.out.println(output);
		arcadeDrive(0, output, false);
		lastHeadingError = headingError;
		lastHeadingIntegral = headingIntegral;
		lastTime = t;
	}
	
	/*
	 * Drives to a profile
	 * @param path  The TrajectoryPath object that contains the profile to follow
	 */
	public void driveWithProfile(TrajectoryPath path) {
		initPID();
		System.out.println("drive With profile method");
		
		for(int i =0; i < path.getLeftPosList().size(); i++) {
			double leftTicks = path.getLeftPosList().get(i);
			double rightTicks = path.getRightPosList().get(i);
			double leftVelo = path.getLeftVeloList().get(i);
			double rightVelo = path.getRightVeloList().get(i);
			double heading = path.getHeadingList().get(i);
			System.out.println(leftTicks);
		
			boolean reached = false;
			
			driveToWaypoint(leftTicks, rightTicks, leftVelo, rightVelo, heading);

			while(!reached) {
				boolean leftReached = Math.abs(getLeftEncoderTicksTravelled() - leftTicks) < ALLOWABLE_ERR_UNITS;
				boolean rightReached = Math.abs(getRightEncoderTicksTravelled() - rightTicks) < ALLOWABLE_ERR_UNITS;
				boolean headingReached = Math.abs(getHeading() - heading) < ALLOWABLE_ERR_DEG;
				System.out.println("driving to waypoint... left ticks: " + getLeftEncoderTicksTravelled() +
						"heading error: " + heading);
				
				if(leftReached && rightReached && headingReached) {
					reached = true;
					System.out.println("reached!!!");
				}
			}

			
			resetBetweenWaypoints();
		}
		
		System.out.println("end of profile method");
		endPID();
		arcadeDrive(0,0, false);
	}
	
	/*
	 * Drives to a single waypoint in a motion profile. Uses cascaded-closed loop. 
	 * 
	 * Position and heading are controlled with closed loop PID. The target velocity is scaled and added to the closed loop values
	 * @param leftTicks            Target number of ticks for left side to drive
	 * @param rightTicks           Target number of ticks for left side to drive
	 * @param leftVeloTicks100Ms   Target velocity in ticks per 100ms for the left side
	 * @param rightVeloTicks100Ms  Target velocity in ticks per 100ms for the left side
	 * @param headingTarget        Target heading in degrees
	 */
	public void driveToWaypoint(double leftTicks, double rightTicks, double leftVeloTicks100Ms, double rightVeloTicks100Ms, double headingTarget) {
		
		double heading = getHeading();
		double headingError = headingTarget - heading;
		double t = timer.get();
		double dt = t - lastTime;
		
		double leftVeloFeedForward = kLeftVeloFeedForward * leftVeloTicks100Ms;
		double rightVeloFeedForward = kRightVeloFeedForward * rightVeloTicks100Ms;
		
		double headingDerivative = ChezyMath.getDifferenceInAngleDegrees(lastHeadingError, headingError);
		double headingIntegral = lastHeadingIntegral + headingError * dt;
		double gyroCorrection = kPHeading * headingError + kIHeading * headingIntegral + kDHeading * headingDerivative;
		
		double leftFeedForward = leftVeloFeedForward + gyroCorrection;
		double rightFeedForward = rightVeloFeedForward - gyroCorrection;
		
		leftDriveMaster.set(POSITION_MODE, leftTicks, ARB_FEED_FORWARD, leftFeedForward);
		rightDriveMaster.set(POSITION_MODE, rightTicks, ARB_FEED_FORWARD, rightFeedForward);
		
		lastHeadingIntegral = headingIntegral;
		lastHeadingError = headingError;
		lastTime = t;
	}
	
	/*
	 * Drives forward with gyro correction
	 * @param throttle  The throttle to apply to the motors
	 */
	public void driveTimeStraight(double throttle) {
		double heading = getHeading();
		double headingError = 0 - heading;
		double t = timer.get();
		double dt = t - lastTime;
		
		double headingDerivative = (headingError - lastHeadingError) / dt;
		double headingIntegral = lastHeadingIntegral + headingError * dt;
		double gyroCorrection = kPHeading * headingError + kIHeading * headingIntegral + kDHeading * headingDerivative;
		if(Math.abs(gyroCorrection) > kMaxTurnRate) gyroCorrection = Math.signum(gyroCorrection) * kMaxTurnRate;
		
		arcadeDrive(throttle, gyroCorrection, false);
		
		lastHeadingError = headingError;
		lastHeadingIntegral = headingIntegral;
		lastTime = t;
	}
	
	// Turns the drivetrain by raw throttle
	public void timedTurn(TurnMode mode, double throttle) {
		if (mode == TurnMode.Left) tankDrive(-throttle, throttle, false);
		if (mode == TurnMode.Right) tankDrive(throttle, -throttle, false);
	}
	
	// SIMPLE ARCADE DRIVE
	public void arcadeDrive(double throttle, double heading, boolean squared) {
		if(squared) {
			throttle = Math.signum(throttle) * throttle * throttle;
			heading = Math.signum(heading) * heading * heading;
		}
		tankDrive(throttle + heading, throttle - heading, false);
	}
		
	// SIMPLE TANK DRIVE
	public void tankDrive(double left, double right, boolean squaredInput) {
		leftDriveMaster.set(left);
		rightDriveMaster.set(right);
	}
	
	/******************
	 * SMARTDASHBOARD *
	 ******************/
	//Push Default Gains To SmartDashboard
	private void pushPIDGainsToSmartDashboard() {
		SmartDashboard.putNumber("Heading: kP", kPHeading);
		SmartDashboard.putNumber("Heading: kI", kIHeading);
		SmartDashboard.putNumber("Heading: kD", kDHeading);
	}
	
	//Post All Other Normally Empty Strings To SmarDashboard
	private void pushNormallyUnusedToSmartDashboard() {   
        SmartDashboard.putNumber("Heading: Target", 0.0);
        SmartDashboard.putNumber("Heading: Error", 0.0);
	}
	
	public void updateHeadingGains() {
		kPHeading = SmartDashboard.getNumber("Heading: kP", kPHeading);
		kIHeading = SmartDashboard.getNumber("Heading: kI", kIHeading);
		kDHeading = SmartDashboard.getNumber("Heading: kD", kDHeading);
	}
	
	/***************
	 * PID SUPPORT *
	 ***************/
	private void setPIDDefaults() {
		leftDriveMaster.config_kP(slotIdx, kPLeft, 10);		
		leftDriveMaster.config_kI(slotIdx, kILeft, 10);
		leftDriveMaster.config_kD(slotIdx, kDLeft, 10);
		leftDriveMaster.config_kF(slotIdx, 0, 10);
		leftDriveMaster.selectProfileSlot(slotIdx, 0);
		
		rightDriveMaster.config_kP(slotIdx, kPRight, 10);
		rightDriveMaster.config_kI(slotIdx, kIRight, 10);
		rightDriveMaster.config_kD(slotIdx, kDRight, 10);
		rightDriveMaster.config_kF(slotIdx, 0, 10);
		rightDriveMaster.selectProfileSlot(slotIdx, 0);
		
		leftDriveMaster.configAllowableClosedloopError(slotIdx, 0, timeout);
		rightDriveMaster.configAllowableClosedloopError(slotIdx, 0, timeout);
	}
	
	// Resets PID variables
	public void resetForPID() {
		lastHeadingError = 0;
		lastHeadingIntegral = 0;
		lastTime = 0;
	}
	
	// Resets some PID variables when changing the target waypoint
	public void resetBetweenWaypoints() {
		lastHeadingError = 0;
		lastHeadingIntegral = 0;
	}

	public void startTimer() {
		timer.reset();
		timer.start();
	}
	
	public void stopTimer() {
		timer.stop();
		timer.reset();
	}
	
	// Updates all variables/values for PID
	public void initPID() {
		updateHeadingGains();
		zeroSensors();
		resetForPID();
		startTimer();
	}
	
	// Stops and resets any values needed for when PID ends
	public void endPID() {
		resetForPID();
		stopTimer();
	}
	
	/*******************
	 * VOLTAGE METHODS *
	 *******************/
	public double getLeftMasterVoltage() {
		return (leftDriveMaster.getMotorOutputVoltage());
	}
	
	public double getRightMasterVoltage() {
		return (rightDriveMaster.getMotorOutputVoltage());
	}
	
	public double getLeftSlaveVoltage() {
		return (leftDriveSlave1.getMotorOutputVoltage());
	}
	
	public double getRightSlaveVoltage() {
		return (rightDriveSlave1.getMotorOutputVoltage());
	}
	
	/*************************
	 * DRIVE SUPPORT METHODS *
	 *************************/
	private void reverseTalons(boolean isInverted) {
		leftDriveMaster.setInverted(isInverted);
		rightDriveMaster.setInverted(isInverted);
		leftDriveSlave1.setInverted(isInverted);
		rightDriveSlave1.setInverted(isInverted);
	}

	private void setBrakeMode(NeutralMode mode) {
		leftDriveMaster.setNeutralMode(mode);
		leftDriveSlave1.setNeutralMode(mode);
		rightDriveMaster.setNeutralMode(mode);
		rightDriveSlave1.setNeutralMode(mode);
	}

	private void setCtrlMode() {
		leftDriveSlave1.follow(leftDriveMaster);
		rightDriveSlave1.follow(rightDriveMaster);
	}
	
	public void setVoltageComp(boolean set, double voltage, int timeout) {		
		if(set)
			System.out.println("DriveTrain Changed Voltage Compensation To: " + voltage);
		else
			System.out.println("DriveTrain Turned Voltage Compensation Off");	
		
		//Voltage Compensation On/Off
		leftDriveMaster.enableVoltageCompensation(set);
		leftDriveSlave1.enableVoltageCompensation(set);
		rightDriveMaster.enableVoltageCompensation(set);
		rightDriveSlave1.enableVoltageCompensation(set);
		//Config Voltage Compensation
		leftDriveMaster.configVoltageCompSaturation(voltage, timeout);
		leftDriveSlave1.configVoltageCompSaturation(voltage, timeout);
		rightDriveMaster.configVoltageCompSaturation(voltage, timeout);
		rightDriveSlave1.configVoltageCompSaturation(voltage, timeout);
		//Nominal and peak outputs
		leftDriveMaster.configPeakOutputForward(1.0, timeout);
		leftDriveSlave1.configPeakOutputForward(1.0, timeout);
		rightDriveMaster.configPeakOutputForward(1.0, timeout);
		rightDriveSlave1.configPeakOutputForward(1.0, timeout);
		leftDriveMaster.configPeakOutputReverse(-1.0, timeout);
		leftDriveSlave1.configPeakOutputReverse(-1.0, timeout);
		rightDriveMaster.configPeakOutputReverse(-1.0, timeout);
		rightDriveSlave1.configPeakOutputReverse(-1.0, timeout);
		leftDriveMaster.configNominalOutputForward(0.0, timeout);
		leftDriveSlave1.configNominalOutputForward(0.0, timeout);
		rightDriveMaster.configNominalOutputForward(0.0, timeout);
		rightDriveSlave1.configNominalOutputForward(0.0, timeout);
		leftDriveMaster.configNominalOutputReverse(0.0, timeout);
		leftDriveSlave1.configNominalOutputReverse(0.0, timeout);
		rightDriveMaster.configNominalOutputReverse(0.0, timeout);
		rightDriveSlave1.configNominalOutputReverse(0.0, timeout);
	}

	public void setTalonDefaults() {
		reverseTalons(false);
		setBrakeMode(BRAKE_MODE);
		setCtrlMode();
		setVoltageComp(true, voltageCompensationVoltage, timeout);
		rightDriveMaster.setInverted(true);
		rightDriveSlave1.setInverted(true); //TODO move this somewhere else pls
	}
	
	/***********
	 * SENSORS *
	 ***********/
	private void configTalonEncoders() {
		leftDriveMaster.setSensorPhase(false);
		leftDriveMaster.configSelectedFeedbackSensor(magEncoder, pidIdx, timeout);
		rightDriveMaster.setSensorPhase(false);
		rightDriveMaster.configSelectedFeedbackSensor(magEncoder, pidIdx, timeout);
	}
	
	@Override
	public void zeroSensors() {
		zeroEncoders();
		zeroGyro();
	}	
	
	public AHRS getGyro(){
		return NavX;
	}
	
	public void zeroGyro() {
		NavX.reset();
	}
	
	public double getHeading() {
		return NavX.getYaw();
	}
	
	public double getLeftEncoderVelocity() {
		return leftDriveMaster.getSelectedSensorVelocity(pidIdx);
	}
	
	// Gets the number of revolutions of the encoder
	private double getLeftEncoderRevs() {
		return leftDriveMaster.getSelectedSensorPosition(pidIdx) / quadConversionFactor;
	}
	
	// Returns the distance traveled in native encoder units
	public double getLeftEncoderTicksTravelled() {
		return leftDriveMaster.getSelectedSensorPosition(pidIdx);
	}
	
	// Get the distance the elevator has traveled in inches
	public double getLeftEncoderDistanceTravelled() {
		if(Robot.pn.isLowGear())
			if(isCompetitionRobot)
				return getLeftEncoderRevs() * 2 * Math.PI * competitonBotLeftWheelRadius  / lowGearDriveTrainGearRatio;
			else
				return getLeftEncoderRevs() * 2 * Math.PI * practiceBotLeftWheelRadius / lowGearDriveTrainGearRatio;
		else
			if(isCompetitionRobot)
				return getLeftEncoderRevs() * 2 * Math.PI * competitonBotLeftWheelRadius  / highGearDriveTrainGearRatio;
			else
				return getLeftEncoderRevs() * 2 * Math.PI * practiceBotLeftWheelRadius / highGearDriveTrainGearRatio;
	}
	
	public double getRightEncoderVelocity() {
		return rightDriveMaster.getSelectedSensorVelocity(pidIdx);
	}
	
	// Gets the number of revolutions of the encoder
	private double getRightEncoderRevs() {
		return rightDriveMaster.getSelectedSensorPosition(pidIdx) / quadConversionFactor;
	}
	
	// Returns the distance traveled in native encoder units
	public double getRightEncoderTicksTravelled() {
		return rightDriveMaster.getSelectedSensorPosition(pidIdx);
	}
	
	public double getDistanceTravelled() {
		return (getLeftEncoderDistanceTravelled() + getRightEncoderDistanceTravelled()) / 2;
	}
		
	// Get the distance the elevator has traveled in inches
	public double getRightEncoderDistanceTravelled() {
		if(Robot.pn.isLowGear())
			if(isCompetitionRobot)
				return getRightEncoderRevs() * 2 * Math.PI * competitonBotRightWheelRadius / lowGearDriveTrainGearRatio;
			else
				return getRightEncoderRevs() * 2 * Math.PI * practiceBotRightWheelRadius / lowGearDriveTrainGearRatio;
		else
			if(isCompetitionRobot)
				return getRightEncoderRevs() * 2 * Math.PI * competitonBotRightWheelRadius / highGearDriveTrainGearRatio;
			else
				return getRightEncoderRevs() * 2 * Math.PI * practiceBotRightWheelRadius / highGearDriveTrainGearRatio;
	}
	
	public void zeroEncoders() {
		leftDriveMaster.getSensorCollection().setQuadraturePosition(0, 0);
		rightDriveMaster.getSensorCollection().setQuadraturePosition(0, 0);
	}
	
	/***************
	 * CONVERSIONS *
	 ***************/
	private int distanceToTicks(double distanceInches) {
		return (int) Math.round(EncoderHelper.inchesToEncoderTicks(distanceInches, wheelCircum, quadConversionFactor) * lowGearDriveTrainGearRatio);
	}
	
	/*****************
	 * CHECK METHODS *
	 *****************/
	public boolean isDriveTrainAtDistance(double distance) {
		double distanceTravelled = (getLeftEncoderDistanceTravelled() + getRightEncoderDistanceTravelled()) / 2;
		return Math.abs(distance - distanceTravelled) < driveTrainDistanceTolerance;
	}
	
	public boolean isRightDriveAtDistance(double distance) {
		double distanceTravelled = getRightEncoderDistanceTravelled();
		return Math.abs(distance - distanceTravelled) < driveTrainDistanceTolerance;
	}
	
	public boolean isLeftDriveAtDistance(double distance) {
		double distanceTravelled = getLeftEncoderDistanceTravelled();
		System.out.println(Math.abs(distance - distanceTravelled) < driveTrainDistanceTolerance);
		return Math.abs(distance - distanceTravelled) < driveTrainDistanceTolerance;
	}
	
	public boolean isDriveTrainAtAngle(double angle) {
		double currentAngle = getHeading();
		return Math.abs(angle - currentAngle) < driveTrainAngleTolerance;
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}

	@Override
	public void check() {
	}
}
