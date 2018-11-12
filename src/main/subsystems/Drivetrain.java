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
import util.motion.TrapezoidalProfileFactory;
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
	private boolean okayToPID = false;
	public double inchesToTurn = 0;
	
	double[][] prof;
		
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

	//Drive for voltage playing back
	public void driveVoltageTank(double leftVoltage, double rightVoltage, double voltageCompensationVoltage) {
		double leftValue = ((Math.abs(leftVoltage) > voltageCompensationVoltage) ? Math.signum(leftVoltage) : leftVoltage/voltageCompensationVoltage);
			// Negate one side so that the robot won't drive in circles
			double rightValue = ((Math.abs(rightVoltage)  > voltageCompensationVoltage) ? Math.signum(rightVoltage) : rightVoltage/voltageCompensationVoltage);	
	
		tankDrive(leftValue, rightValue, false);// Don't square inputs as this will affect accuracy
	}
	
	//Drive for testing the drivetrain so that the needed constants to compute the bias voltages may be derived
	public void driveVoltageTankTest(double leftVoltage, double rightVoltage, double voltageCompensationVoltage) {
		tankDrive(leftVoltage/voltageCompensationVoltage, rightVoltage/voltageCompensationVoltage, false);
	}
	public void turnOff() {
		tankDrive(0.0, 0.0, false);
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
	
	public void driveTankVeloFF(double veloTicks100Ms) {
		//System.out.println(veloTicks100Ms);
		double t = timer.get();
		int i =(int) ( t/0.01);
		
		veloTicks100Ms = prof[i][1];
		double leftThrottle = veloTicks100Ms * kLeftVeloFeedForward;
		double rightThrottle = veloTicks100Ms * kRightVeloFeedForward;
		if(leftThrottle > 1.0) leftThrottle = 1.0;
		if(rightThrottle > 1.0) rightThrottle = 1.0;
		System.out.println("throttle " + leftThrottle);
		tankDrive(leftThrottle, rightThrottle, false);
	}
	
	// TODO add something to stop the command before the array ends so it won't crash code
	public void initDriveFF(double inches) {
		int distanceUnits = (int) EncoderHelper.inchesToEncoderTicks(inches, wheelCircum, quadConversionFactor);
		System.out.println(distanceUnits);
		prof = TrapezoidalProfileFactory.getProfile(distanceUnits, cruiseSpeed, RAMP_RATE_UNITS_100MS_S);
		initPID();
	}
	
	// Drives with a velocity profile
	public void driveVeloFF() {
		double t = timer.get();
		int i =(int) ( t/0.01);
		
		double veloTicks100Ms = prof[i][1];
		double leftThrottle = veloTicks100Ms * kLeftVeloFeedForward;
		double rightThrottle = veloTicks100Ms * kRightVeloFeedForward;
		if(leftThrottle > 1.0) leftThrottle = 1.0;
		if(rightThrottle > 1.0) rightThrottle = 1.0;
		System.out.println("throttle " + leftThrottle + "\tSpeed: " + getLeftEncoderVelocity());
		tankDrive(leftThrottle, rightThrottle, false);
	}
	
	// Drives with closed loop position control w/o gyro correction
	public void drivePID(double inches) {
		int ticks = distanceToTicks(inches);
		leftDriveMaster.set(ControlMode.Position, ticks);
		rightDriveMaster.set(ControlMode.Position, ticks);
	}
	
	// Drives one side of the drivetrain with position closed loop control
	public void drivePID(double inches, String side) {
		int ticks = distanceToTicks(inches);
		if(side.toLowerCase().equals("left")) leftDriveMaster.set(ControlMode.Position, ticks);
		else if(side.toLowerCase().equals("right")) rightDriveMaster.set(ControlMode.Position, ticks);
	}
	
	public void drivePID(double leftInches, double rightInches) {
		int leftTicks = distanceToTicks(leftInches);
		int rightTicks = distanceToTicks(rightInches);
		leftDriveMaster.set(ControlMode.Position, leftTicks);
		rightDriveMaster.set(ControlMode.Position, rightTicks);
	}

	// Turns to angle w/ closed loop control using the gyro's heading
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
	
	// future use
	public void driveFromPlayPID(double leftTicks, double rightTicks, double leftVeloTicks100Ms, double rightVeloTicks100Ms, double headingTarget) {
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
	
	// Future use- velocity closed loop control
	public void driveAngleVeloPID(double velocityInchesPerSec, double targetHeading) {
		double veloTicks100Ms = distanceToTicks(velocityInchesPerSec) * 10;
		//TODO gyro correction and switching constants for velocity/position modes
		leftDriveMaster.set(ControlMode.Velocity, veloTicks100Ms);
		rightDriveMaster.set(ControlMode.Velocity, veloTicks100Ms);
	}
	
	public void driveTimeStraight(double throttle) {
		double heading = getHeading();
		double headingError = 0 - heading;
		double t = timer.get();
		double dt = t - lastTime;
		//System.out.println(headingError);
		
		double headingDerivative = (headingError - lastHeadingError) / dt;
		double headingIntegral = lastHeadingIntegral + headingError * dt;
		double gyroCorrection = kPHeading * headingError + kIHeading * headingIntegral + kDHeading * headingDerivative;
		if(Math.abs(gyroCorrection) > kMaxTurnRate) gyroCorrection = Math.signum(gyroCorrection) * kMaxTurnRate;
		
		//System.out.println(output);
		arcadeDrive(throttle, gyroCorrection, false);
		
		lastHeadingError = headingError;
		lastHeadingIntegral = headingIntegral;
		lastTime = t;
	}
	
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
	
	public void resetForPID() {
		lastHeadingError = 0;
		lastHeadingIntegral = 0;
		lastTime = 0;
	}
	
	public void okayToPID(boolean okayToPID) {
		this.okayToPID = okayToPID;
	}
	
	public void startTimer() {
		timer.reset();
		timer.start();
	}
	
	public void stopTimer() {
		timer.stop();
		timer.reset();
	}
	
	public void initPID() {
		updateHeadingGains();
		zeroSensors();
		resetForPID();
		okayToPID(true);
		startTimer();
	}
	
	public void endPID() {
		resetForPID();
		okayToPID(false);
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
	
	/*
	 * CURRENT METHODS
	 */
	
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
		// TODO CHECK THIS
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
	
	public boolean isDriveAtDistanceGreaterThan(double distance) {
		double currentDistance = (getLeftEncoderDistanceTravelled() + getRightEncoderDistanceTravelled())/2;
		return Math.abs(currentDistance) > Math.abs(distance); 
	}
	
	public boolean isDriveAtDistanceLessThan(double distance) {
		double currentDistance = (getLeftEncoderDistanceTravelled() + getRightEncoderDistanceTravelled())/2;
		return Math.abs(currentDistance) < Math.abs(distance); 
	}
	
	public boolean isDriveTrainAtAngle(double angle) {
		double currentAngle = getHeading();
		return Math.abs(angle - currentAngle) < driveTrainAngleTolerance;
	}
	
	public boolean isDriveAtAngleGreaterThan(double angle) {
		double currentAngle = getHeading();
		return Math.abs(currentAngle) > Math.abs(angle); 
	}
	
	public boolean isDriveAtAngleLessThan(double angle) {
		double currentAngle = getHeading();
		return Math.abs(currentAngle) < Math.abs(angle); 
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}

	@Override
	public void check() {
	}
}
