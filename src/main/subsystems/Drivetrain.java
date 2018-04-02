package main.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.kauailabs.navx.frc.AHRS;//NavX import
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import Util.ChezyMath;
import Util.DriveHelper;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import interfacesAndAbstracts.ImprovedSubsystem;
import main.Robot;
import main.commands.drivetrain.Drive;

public class Drivetrain extends ImprovedSubsystem  {
	private static DifferentialDrive driveTrain = new DifferentialDrive(leftDriveMaster, rightDriveMaster);
	
	//TELEOP DRIVING
	private DriveHelper driveHelper = new DriveHelper(7.5);
	
	//SENSORS
	private static AHRS NavX;	
	
	//ProfilePIDVariables
	private static double lastLeftEncPosError = 0.0;
	private static double lastRightEncPosError = 0.0;
	//ProfilePIDIntegralAccumulators
	private static double leftIntegralAccum = 0.0;
	private static double rightIntegralAccum = 0.0;

	public Drivetrain() {
		try {
			/* Communicate w/navX-MXP via the MXP SPI Bus.                                     */
	        /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
	        /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
	        NavX = new AHRS(SPI.Port.kMXP); 
		} catch (RuntimeException ex ) {
	          DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
	    }
		
		setTalonDefaults();
		configTalonEncoders();
		zeroSensors();
		pushPIDGainsToSmartDashboard();
	}
	
	// DRIVE FOR TELEOP
	public void driveVelocity(double throttle, double heading) {
		if (isCompetitionRobot) {
			driveTrain.arcadeDrive(driveHelper.handleOverPower(driveHelper.handleDeadband(throttle, throttleDeadband)),
					driveHelper.handleOverPower(driveHelper.handleDeadband(-heading, headingDeadband)));
		}
		else {
			driveTrain.arcadeDrive(driveHelper.handleOverPower(driveHelper.handleDeadband(-throttle, throttleDeadband)),
					driveHelper.handleOverPower(driveHelper.handleDeadband(-heading, headingDeadband)));
		}
	}

	//Drive for playing back
	public void driveVoltageTank(double leftVoltage, double rightVoltage) {
		/*
		//ROBOT BIAS CONSTANTS COMPUTATION
			double competitionBiasLeft = ((Math.abs(leftVoltage)*practiceBotLeftFreeRPMAtTestVoltage*competitionBotWeight*competitonBotLeftWheelRadius) /
					(practiceBotLeftWheelRadius*competitonBotLeftFreeRPMAtTestVoltage*practiceBotWeight)) - Math.abs(leftVoltage);
		
			double competitionBiasRight = ((Math.abs(rightVoltage)*practiceBotRightFreeRPMAtTestVoltage*competitionBotWeight*competitonBotRightWheelRadius) /
					(practiceBotRightWheelRadius*competitonBotRightFreeRPMAtTestVoltage*practiceBotWeight)) - Math.abs(rightVoltage);
		
		//IMPLEMENT BIAS VOLTAGES FOR DIFFERENCE BETWEEN ROBOTS COMPENSATION?
		//If it is the competition robot then implement, otherwise don't
			double leftVoltageBias =(isCompetitionRobot? competitionBiasLeft : 0.0);// compBot:practiceBot // Units, (V), Volts
			double rightVoltageBias = (isCompetitionRobot? competitionBiasRight : 0.0);// compBot:practiceBot // Units, (V), Volts
		
		//APPLY BIASES
			leftVoltage = Math.signum(leftVoltage) * (Math.abs(leftVoltage) + leftVoltageBias);
			rightVoltage = Math.signum(rightVoltage) * (Math.abs(rightVoltage) + rightVoltageBias);
		*/
		//APPLY {-1:1} DOMAIN TO COMPUTED VALUES BEFORE PASSING TO DRIVETRAIN
			double leftValue = ((Math.abs(leftVoltage) > voltageCompensationVoltageDriveTrain) ? Math.signum(leftVoltage) : leftVoltage/voltageCompensationVoltageDriveTrain);
			// Negate one side so that the robot won't drive in circles
			double rightValue = -((Math.abs(rightVoltage)  > voltageCompensationVoltageDriveTrain) ? Math.signum(rightVoltage) : rightVoltage/voltageCompensationVoltageDriveTrain);	
	
		tankDrive(leftValue, rightValue, false);// Don't square inputs as this will affect accuracy
	}
	
	//Drive for testing the drivetrain so that the needed constants to compute the bias voltages may be derived
	public void driveVoltageTankTest(double leftVoltage, double rightVoltage) {
		tankDrive(leftVoltage/voltageCompensationVoltageDriveTrain, rightVoltage/voltageCompensationVoltageDriveTrain, false);
	}
	
	//Push Default Gains To SmartDashboard
	private void pushPIDGainsToSmartDashboard() {
        SmartDashboard.putNumber("Pos: PGain", kDrivePositionKp);
        SmartDashboard.putNumber("Pos: IGain", kDrivePositionKi);
        SmartDashboard.putNumber("Pos: DGain", kDrivePositionKd);
        SmartDashboard.putNumber("Heading: PGain", kDrivePathHeadingFollowKp);
	}
	
	//Drive for Playback utilizing sensors for real-time path correction
	public void driveProfileWithPid(double leftEncTargetPos, double rightEncTargetPos, double targetHeading) {	
		//Grab PID Vars
		double PGain = SmartDashboard.getNumber("Pos: PGain", kDrivePositionKp);
		double IGain = SmartDashboard.getNumber("Pos: IGain", kDrivePositionKi);
		double DGain = SmartDashboard.getNumber("Pos: DGain", kDrivePositionKd);
		double headingGain = SmartDashboard.getNumber("Heading: PGain", kDrivePathHeadingFollowKp);
		
        //Calculate left driveTrain side error with PID
        double leftError = leftEncTargetPos - getLeftEncoderDistanceTravelled();
        leftIntegralAccum += (leftError*kLooperDt);
        double leftDerivative = (leftError - lastLeftEncPosError)/kLooperDt;
        double leftPIDOutput = PGain*leftError + IGain*leftIntegralAccum + DGain*leftDerivative;        	    
        lastLeftEncPosError = leftError;
        
        //Calculate right driveTrain side error with PID
        double rightError = rightEncTargetPos - getRightEncoderDistanceTravelled();
        rightIntegralAccum += (rightError*kLooperDt);
        double rightDerivative = (rightError - lastRightEncPosError)/kLooperDt;
        double rightPIDOutput = PGain*rightError + IGain*rightIntegralAccum + DGain*rightDerivative;        	    
        lastRightEncPosError = rightError;
        
        //Post values to SmartDashboard so that variable tuning can be better understood/visualized
        SmartDashboard.putNumber("Left: FollowerSensor", getLeftEncoderDistanceTravelled());
        SmartDashboard.putNumber("Left: FollowerGoal", leftEncTargetPos);
        SmartDashboard.putNumber("Left: FollowerError", leftError);
        SmartDashboard.putNumber("Right: FollowerSensor", getRightEncoderDistanceTravelled());
        SmartDashboard.putNumber("Right: FollowerGoal", rightEncTargetPos);
        SmartDashboard.putNumber("Right: FollowerError", rightError);
        
        //Compute Turning, Well Built FRC Robots drive in virtually a straight line so no need
        //For PID just a simple proportional value to keep it on track
        double angleDiff = ChezyMath.getDifferenceInAngleDegrees(getHeading(), targetHeading);
        double turn = headingGain * angleDiff;
        
        SmartDashboard.putNumber("Heading: Sensor", getHeading());
        SmartDashboard.putNumber("Heading: Target", targetHeading);
        SmartDashboard.putNumber("Heading: Error", angleDiff);
        
        boolean invertPIDHeadingCorrection = false;
        
        if(!invertPIDHeadingCorrection) {
        	SmartDashboard.putNumber("PID Correction to Left Drive", turn);
        	SmartDashboard.putNumber("PID Correction To Right Drive", -turn);
        
        	//Drive!!!
        	tankDrive(driveHelper.handleOverPower(leftPIDOutput + turn),
        			driveHelper.handleOverPower(rightPIDOutput - turn), false);
        }
        else {
        	SmartDashboard.putNumber("PID Correction to Left Drive", -turn);
        	SmartDashboard.putNumber("PID Correction To Right Drive", turn);
        
        	//Drive!!!
        	tankDrive(driveHelper.handleOverPower(leftPIDOutput - turn),
        			driveHelper.handleOverPower(rightPIDOutput + turn), false);
        }
	}
	
	public void tankDrive(double left, double right, boolean squaredInput) {
		SmartDashboard.putNumber("TankDrive Left Input", left);
		SmartDashboard.putNumber("TankDrive Right Input", right);
		driveTrain.tankDrive(left, right, squaredInput);
	}
	public void timedTurn(TurnMode mode, double throttle) {
		if (mode == TurnMode.Left) tankDrive(-throttle, throttle, false);
		if (mode == TurnMode.Right) tankDrive(throttle, -throttle, false);
	}
	
	public void turnOff() {
		tankDrive(0.0, 0.0, false);
	}
	
	/***********************
	 * PLAY/RECORD METHODS *
	 ***********************/
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
	
	private void setVoltageComp(boolean set, double voltage, int timeout) {
		//Voltage Compensation
		leftDriveMaster.enableVoltageCompensation(set);
		leftDriveSlave1.enableVoltageCompensation(set);
		rightDriveMaster.enableVoltageCompensation(set);
		rightDriveSlave1.enableVoltageCompensation(set);
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
	
	private void configTalonEncoders() {
		leftDriveMaster.setSensorPhase(true);
		leftDriveMaster.configSelectedFeedbackSensor(magEncoder, pidIdx, timeout);
		rightDriveMaster.setSensorPhase(true);
		rightDriveMaster.configSelectedFeedbackSensor(magEncoder, pidIdx, timeout);
	}
	
	public void setTalonDefaults() {
		reverseTalons(false);
		setBrakeMode(BRAKE_MODE);
		setCtrlMode();
		setVoltageComp(false, voltageCompensationVoltageDriveTrain, 10);
	}
	
	public void enableVoltageComp(boolean enable) {
		if(enable) {
			setVoltageComp(true, voltageCompensationVoltageDriveTrain, 10);
			System.out.println("DriveTrain:");
			System.out.println("Set Voltage Compensation To: " + voltageCompensationVoltageDriveTrain + " Volts.");
		}
		else {
			setVoltageComp(false, voltageCompensationVoltageDriveTrain, 10);
			System.out.println("DriveTrain:");
			System.out.println("Turned Voltage Compensation Off.");
		}
	}	

	public AHRS getGyro(){
		return NavX;
	}
	
	public void zeroGyro() {
		NavX.reset();
		NavX.zeroYaw();
	}
	
	public double getHeading() {
		return NavX.getYaw();
	}
	
	public double getLeftEncoderVelocity() {
		return leftDriveMaster.getSensorCollection().getQuadratureVelocity();
	}
	
	// Gets the number of revolutions of the encoder
	private double getLeftEncoderRevs() {
		return leftDriveMaster.getSensorCollection().getQuadraturePosition() / countsPerRev;
	}
	
	// Returns the distance traveled in native encoder units
	public double getLeftEncoderTicksTravelled() {
		return leftDriveMaster.getSensorCollection().getQuadraturePosition();
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
		return rightDriveMaster.getSensorCollection().getQuadratureVelocity();
	}
	
	// Gets the number of revolutions of the encoder
	private double getRightEncoderRevs() {
		return rightDriveMaster.getSensorCollection().getQuadraturePosition() / countsPerRev;
	}
	
	// Returns the distance traveled in native encoder units
	public double getRightEncoderTicksTravelled() {
		return rightDriveMaster.getSensorCollection().getQuadraturePosition();
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

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}

	@Override
	public void check() {
		// TODO Auto-generated method stub
	}

	@Override
	public void zeroSensors() {
		zeroEncoders();
		zeroGyro();
	}	
	
	public void zeroPIDVariables() {
		//Clear Previous Errors
		lastLeftEncPosError = 0.0;
	    lastRightEncPosError = 0.0;
		//Clear Integral Accumulators
		leftIntegralAccum = 0.0;
		rightIntegralAccum = 0.0;
	}
}

