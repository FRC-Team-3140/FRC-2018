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
		pushNormallyUnusedToSmartDashboard();
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
	public void driveVoltageTank(double leftVoltage, double rightVoltage, double voltageCompensationVoltage) {
		double leftValue = ((Math.abs(leftVoltage) > voltageCompensationVoltage) ? Math.signum(leftVoltage) : leftVoltage/voltageCompensationVoltage);
			// Negate one side so that the robot won't drive in circles
			double rightValue = -((Math.abs(rightVoltage)  > voltageCompensationVoltage) ? Math.signum(rightVoltage) : rightVoltage/voltageCompensationVoltage);	
	
		tankDrive(leftValue, rightValue, false);// Don't square inputs as this will affect accuracy
	}
	
	//Drive for testing the drivetrain so that the needed constants to compute the bias voltages may be derived
	public void driveVoltageTankTest(double leftVoltage, double rightVoltage, double voltageCompensationVoltage) {
		tankDrive(leftVoltage/voltageCompensationVoltage, rightVoltage/voltageCompensationVoltage, false);
	}
	
	//Push Default Gains To SmartDashboard
	private void pushPIDGainsToSmartDashboard() {
		SmartDashboard.putNumber("Heading: PGain", straightDriveKp);
	}
	
	//Post All Other Normally Empty Strings To SmarDashboard
	private void pushNormallyUnusedToSmartDashboard() {   
        SmartDashboard.putNumber("Heading: Target", 0.0);
        SmartDashboard.putNumber("Heading: Error", 0.0);
        SmartDashboard.putNumber("Heading PID Correction to Left Drive", 0.0);
        SmartDashboard.putNumber("Heading PID Correction To Right Drive", 0.0);
	}
	
	public void driveWithGyroCorrection(double throttleLeft, double throttleRight, double targetHeading) {
		double headingGain = SmartDashboard.getNumber("Heading: PGain", straightDriveKp);
		
        //Compute Turning, Well Built FRC Robots drive in virtually a straight line so no need
        //For PID just a simple proportional value to keep it on track
        double angleDiff = ChezyMath.getDifferenceInAngleDegrees(getHeading(), targetHeading);
        double turn = headingGain * angleDiff;
        
        SmartDashboard.putNumber("Heading: Target", targetHeading);
        SmartDashboard.putNumber("Heading: Error", angleDiff);
                
        if(!invertPIDHeadingCorrection) {
        	SmartDashboard.putNumber("Heading PID Correction to Left Drive", turn);
        	SmartDashboard.putNumber("Heading PID Correction To Right Drive", -turn);
        
        	//Drive!!!
        	tankDrive(driveHelper.handleOverPower(throttleLeft + turn),
        			driveHelper.handleOverPower(throttleRight - turn), false);
        }
        else {
        	SmartDashboard.putNumber("Heading PID Correction to Left Drive", -turn);
        	SmartDashboard.putNumber("Heading PID Correction To Right Drive", turn);
        
        	//Drive!!!
        	tankDrive(driveHelper.handleOverPower(throttleLeft - turn),
        			driveHelper.handleOverPower(throttleRight + turn), false);
        }
	}
	
	public void tankDrive(double left, double right, boolean squaredInput) {
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
	
	private void configTalonEncoders() {
		leftDriveMaster.setSensorPhase(false);
		leftDriveMaster.configSelectedFeedbackSensor(magEncoder, pidIdx, timeout);
		rightDriveMaster.setSensorPhase(true);
		rightDriveMaster.configSelectedFeedbackSensor(magEncoder, pidIdx, timeout);
	}
	
	public void setTalonDefaults() {
		reverseTalons(false);
		setBrakeMode(BRAKE_MODE);
		setCtrlMode();
		setVoltageComp(true, voltageCompensationVoltage, timeout);
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
	
	public boolean isDriveTrainAtDistance(double distance) {
		double distanceTravelled = (getLeftEncoderDistanceTravelled() + getRightEncoderDistanceTravelled()) / 2;
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
	
	public double getDistancePercentError(double distance) {
		double currentDistance = (getLeftEncoderDistanceTravelled() + getRightEncoderDistanceTravelled())/2;
		return Math.abs(distance - currentDistance) / distance;
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
	
	public double getAnglePercentError(double angle) {
		double currentAngle = getHeading();
		return Math.abs(angle - currentAngle) / angle;
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}

	@Override
	public void check() {
	}

	@Override
	public void zeroSensors() {
		zeroEncoders();
		zeroGyro();
	}	
}
