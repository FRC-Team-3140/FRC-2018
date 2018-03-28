package main.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import Util.ChezyMath;
import Util.DriveHelper;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import interfacesAndAbstracts.ImprovedSubsystem;
import main.commands.drivetrain.Drive;

public class Drivetrain extends ImprovedSubsystem  {
	private static DifferentialDrive driveTrain = new DifferentialDrive(leftDriveMaster, rightDriveMaster);
	
	//TELEOP DRIVING
	private DriveHelper helper = new DriveHelper(7.5);
	
	//SENSORS
	//Declare Sensors
	
	//ProfilePIDVariables
	private static double lastLeftEncPosError = 0.0;
	private static double lastRightEncPosError = 0.0;
	//ProfilePIDIntegralAccumulators
	private static double leftIntegralAccum = 0.0;
	private static double rightIntegralAccum = 0.0;

	public Drivetrain() {
		//Instantiate Sensors
		setTalonDefaults();
	}
	
	// DRIVE FOR TELEOP
	public void driveVelocity(double throttle, double heading) {
		if (isCompetitionRobot) {
			driveTrain.arcadeDrive(helper.handleOverPower(helper.handleDeadband(throttle, throttleDeadband)),
					helper.handleOverPower(helper.handleDeadband(-heading, headingDeadband)));
		}
		else {
			driveTrain.arcadeDrive(helper.handleOverPower(helper.handleDeadband(-throttle, throttleDeadband)),
					helper.handleOverPower(helper.handleDeadband(-heading, headingDeadband)));
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
			double leftValue = ((Math.abs(leftVoltage) > voltageCompensationVoltage) ? Math.signum(leftVoltage) : leftVoltage/voltageCompensationVoltage);
			// Negate one side so that the robot won't drive in circles
			double rightValue = -((Math.abs(rightVoltage)  > voltageCompensationVoltage) ? Math.signum(rightVoltage) : rightVoltage/voltageCompensationVoltage);	
	
		driveTrain.tankDrive(leftValue, rightValue, false);// Don't square inputs as this will affect accuracy
	}
	
	//Drive for testing the drivetrain so that the needed constants to compute the bias voltages may be derived
	public void driveVoltageTankTest(double leftVoltage, double rightVoltage) {
		driveTrain.tankDrive(leftVoltage/12, rightVoltage/12, false);
	}
	
	//Drive for Playback utilizing sensors for real-time path correction
	public void driveProfileWithPid(double leftEncTargetPos, double rightEncTargetPos, double targetHeading) {	
        //Calculate left driveTrain side error with PID
        double leftError = leftEncTargetPos - leftEncTargetPos;
        leftIntegralAccum += (leftError*kLooperDt);
        double leftDerivative = (leftError - lastLeftEncPosError)/kLooperDt;
        double leftPIDOutput = kDrivePositionKp*leftError + kDrivePositionKi*leftIntegralAccum + kDrivePositionKd*leftDerivative;        	    
        lastLeftEncPosError = leftError;
        
        //Calculate right driveTrain side error with PID
        double rightError = rightEncTargetPos - rightEncTargetPos;
        rightIntegralAccum += (rightError*kLooperDt);
        double rightDerivative = (rightError - lastRightEncPosError)/kLooperDt;
        double rightPIDOutput = kDrivePositionKp*rightError + kDrivePositionKi*rightIntegralAccum + kDrivePositionKd*rightDerivative;        	    
        lastRightEncPosError = rightError;
        
        //Post values to SmartDashboard so that variable tuning can be better understood/visualized
        SmartDashboard.putNumber("Left: FollowerSensor", getLeftEncDist());
        SmartDashboard.putNumber("Left: FollowerGoal", leftEncTargetPos);
        SmartDashboard.putNumber("Left: FollowerError", leftError);
        SmartDashboard.putNumber("Right: FollowerSensor", getRightEncDist());
        SmartDashboard.putNumber("Right: FollowerGoal", rightEncTargetPos);
        SmartDashboard.putNumber("Right: FollowerError", rightError);
        
        //Compute Turning, Well Built FRC Robots drive in virtually a straight line so no need
        //For PID just a simple proportional value to keep it on track
        double angleDiff = ChezyMath.getDifferenceInAngleDegrees(getHeading(), targetHeading);
        double turn = kDrivePathHeadingFollowKp * angleDiff;        
        
        //Drive!!!
		driveTrain.tankDrive(leftPIDOutput + turn, rightPIDOutput - turn);
	}
	public void timedTurn(TurnMode mode, double throttle) {
		if (mode == TurnMode.Left) driveTrain.tankDrive(-throttle, throttle, false);
		if (mode == TurnMode.Right) driveTrain.tankDrive(throttle, -throttle, false);
	}
	
	public void turnOff() {
		driveTrain.tankDrive(0.0, 0.0);
	}
	/***********************
	 * PLAY/RECORD METHODS *
	 ***********************/
	public double getLeftMasterVoltage() {
		return (leftDriveMaster.getMotorOutputVoltage()); //+ leftDriveSlave1.getMotorOutputVoltage())/2;
	}
	
	public double getRightMasterVoltage() {
		return (rightDriveMaster.getMotorOutputVoltage()); //+ rightDriveSlave1.getMotorOutputVoltage())/2;
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
		//leftDriveSlave2.setInverted(!isInverted);
		rightDriveSlave1.setInverted(isInverted);
		//rightDriveSlave2.setInverted(!isInverted);
	}

	private void setBrakeMode(NeutralMode mode) {
		leftDriveMaster.setNeutralMode(mode);
		leftDriveSlave1.setNeutralMode(mode);
		//leftDriveSlave2.setNeutralMode(mode);
		rightDriveMaster.setNeutralMode(mode);
		rightDriveSlave1.setNeutralMode(mode);
		//rightDriveSlave2.setNeutralMode(mode);
	}

	private void setCtrlMode() {
		leftDriveSlave1.follow(leftDriveMaster);
		rightDriveSlave1.follow(rightDriveMaster);
		//leftDriveSlave2.follow(leftDriveMaster);
		//rightDriveSlave2.follow(rightDriveMaster);
	}
	
	private void setVoltageComp(boolean set, double voltage, int timeout) {
		//Voltage Compensation
		leftDriveMaster.enableVoltageCompensation(set);
		leftDriveSlave1.enableVoltageCompensation(set);
//		leftDriveSlave2.enableVoltageCompensation(set);
		rightDriveMaster.enableVoltageCompensation(set);
		rightDriveSlave1.enableVoltageCompensation(set);
//		rightDriveSlave2.enableVoltageCompensation(set);
		leftDriveMaster.configVoltageCompSaturation(voltage, timeout);
		leftDriveSlave1.configVoltageCompSaturation(voltage, timeout);
//		leftDriveSlave2.configVoltageCompSaturation(voltage, timeout);
		rightDriveMaster.configVoltageCompSaturation(voltage, timeout);
		rightDriveSlave1.configVoltageCompSaturation(voltage, timeout);
//		rightDriveSlave2.configVoltageCompSaturation(voltage, timeout);
		//Nominal and peak outputs
		leftDriveMaster.configPeakOutputForward(1.0, timeout);
		leftDriveSlave1.configPeakOutputForward(1.0, timeout);
//		leftDriveSlave2.configPeakOutputForward(1.0, timeout);
		rightDriveMaster.configPeakOutputForward(1.0, timeout);
		rightDriveSlave1.configPeakOutputForward(1.0, timeout);
//		rightDriveSlave2.configPeakOutputForward(1.0, timeout);
		leftDriveMaster.configPeakOutputReverse(-1.0, timeout);
		leftDriveSlave1.configPeakOutputReverse(-1.0, timeout);
//		leftDriveSlave2.configPeakOutputReverse(-1.0, timeout);
		rightDriveMaster.configPeakOutputReverse(-1.0, timeout);
		rightDriveSlave1.configPeakOutputReverse(-1.0, timeout);
//		rightDriveSlave2.configPeakOutputReverse(-1.0, timeout);
		leftDriveMaster.configNominalOutputForward(0.0, timeout);
		leftDriveSlave1.configNominalOutputForward(0.0, timeout);
//		leftDriveSlave2.configNominalOutputForward(0.0, timeout);
		rightDriveMaster.configNominalOutputForward(0.0, timeout);
		rightDriveSlave1.configNominalOutputForward(0.0, timeout);
//		rightDriveSlave2.configNominalOutputForward(0.0, timeout);
		leftDriveMaster.configNominalOutputReverse(0.0, timeout);
		leftDriveSlave1.configNominalOutputReverse(0.0, timeout);
//		leftDriveSlave2.configNominalOutputReverse(0.0, timeout);
		rightDriveMaster.configNominalOutputReverse(0.0, timeout);
		rightDriveSlave1.configNominalOutputReverse(0.0, timeout);
//		rightDriveSlave2.configNominalOutputReverse(0.0, timeout);
	}
	
	public void setTalonDefaults() {
		reverseTalons(false);
		setBrakeMode(BRAKE_MODE);
		setCtrlMode();
		setVoltageComp(false, voltageCompensationVoltage, 10);
	}
	
	public void enableVoltageComp(boolean enable) {
		if(enable) {
			setVoltageComp(true, voltageCompensationVoltage, 10);
			System.out.println("Set Voltage Compensation To: " + voltageCompensationVoltage + " Volts.");
		}
		else {
			setVoltageComp(false, voltageCompensationVoltage, 10);
			System.out.println("Turned Voltage Compensation Off.");
		}


	}
	
	public double getHeading() {
		return 0;
	}
	
	public double getLeftEncDist() {
		return 0;
	}
	
	public double getRightEncDist() {
		return 0;
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
		//Zero LeftEnc
		//Zero RightEnc
		//Zero NavX
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

