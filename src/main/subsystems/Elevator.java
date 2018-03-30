package main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

import Util.DriveHelper;
import Util.EncoderHelper;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import interfacesAndAbstracts.ImprovedSubsystem;
import main.commands.elevator.MoveWithJoystick;

public class Elevator extends ImprovedSubsystem {
	
	private EncoderHelper encoderHelper = new EncoderHelper();
	private DriveHelper driveHelper = new DriveHelper(7.5);
	
	public Elevator() {
		setElevatorDefaults();
		configSensors();
	}
	
	/*************************
	 * TALON SUPPORT METHODS *
	 ************************/
	private void configSensors() {
		elevatorMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, pidIdx, timeout);
		elevatorMaster.setSensorPhase(true);
		zeroSensors();
	}
	
	private void setBrakeMode() {
		elevatorMaster.setNeutralMode(BRAKE_MODE);
		elevatorSlave.setNeutralMode(BRAKE_MODE);
	}
	
	private void setCtrlMode() {
		elevatorSlave.follow(elevatorMaster);
	}
	
	public void setVoltageMode(boolean set, double voltage, int timeout) {
		elevatorMaster.enableVoltageCompensation(set);
		elevatorSlave.enableVoltageCompensation(set);
		elevatorMaster.configVoltageCompSaturation(voltage, timeout);
		elevatorSlave.configVoltageCompSaturation(voltage, timeout);

		elevatorMaster.configPeakOutputForward(1.0, timeout);
		elevatorMaster.configNominalOutputForward(0, timeout);
		elevatorMaster.configPeakOutputReverse(-1.0, timeout);
		elevatorMaster.configNominalOutputReverse(0, timeout);
		elevatorSlave.configPeakOutputForward(1.0, timeout);
		elevatorSlave.configNominalOutputForward(0, timeout);
		elevatorSlave.configPeakOutputReverse(-1, timeout);
		elevatorSlave.configNominalOutputReverse(0, timeout);
	}
	
	private void setElevatorDefaults() {
		setCtrlMode();
		setBrakeMode();
		setVoltageMode(true, 12, 10);
	}

	/**************************
	 * SENSOR SUPPORT METHODS *
	 **************************/
	
	public void zeroSensors() {
		elevatorMaster.getSensorCollection().setQuadraturePosition(0, 10);
	}
	
	// Checks if the intake is at bottom
	public boolean isArmAtBottom() {
		return !stage1BottomSwitch.get() && !stage2BottomSwitch.get();
	}
	
	// Checks if intake is at the top
	public boolean isArmAtTop() {
		return !stage1TopSwitch.get() && !stage2TopSwitch.get();
	}
	
	// Checks to see if the intake is at the height needed to dump into the switch
	public boolean isArmAtSwitch() {
		return switchHeightSwitch.get();
	}
	
	// Sets encoders to 0 if the arm is at the bottom (this helps to avoid offset)
	public void check() {
		if (isArmAtBottom())
			zeroSensors();
	}
	
	// Returns whether or not the intake has reached the set position. Pos is in inches
	public boolean isIntakeAtPos(double pos) {
		if (Math.abs(getDistanceFromPos(pos)) < elevatorTolerance) {
			return true;
		}
		else return false;
	}
	
	/**********************
	 * ENC OUTPUT METHODS *
	 **********************/
	
	public double getElevatorVelocity() {
		return elevatorMaster.getSensorCollection().getQuadratureVelocity();
	}
	
	// Gets the number of revolutions of the encoder
	private double getElevatorRevs() {
		return elevatorMaster.getSensorCollection().getQuadraturePosition() / countsPerRev;
	}
	
	public double getTicksTravelled() {
		return elevatorMaster.getSensorCollection().getQuadraturePosition();
	}
	
	// Get the distance the elevator has travelled in inches
	public double getDistanceTravelled() {
		return getElevatorRevs() * spindleCircum;
	}
	
	// Returns distance from a set position
	private double getDistanceFromPos(double pos) {
		return pos - getDistanceTravelled();
	}
	
	/**********************
	 * CONVERSION METHODS *
	 **********************/
	
	private double inchesToElevatorEncoderTicks(double inches) {
		return encoderHelper.inchesToEncoderTicks(inches, spindleCircum, countsPerRev);
	}
	
	/***************
	 * RECORD/PLAY *
	 ***************/
	public double getElevatorVoltage() {
		return (elevatorMaster.getMotorOutputVoltage()); //+ elevatorSlave.getMotorOutputVoltage())/2;
	}
	
	/********************
	 * MOVEMENT METHODS *
	 ********************/
	/*
	public void moveFromPlay(double voltage) {
		//if(voltage == 0 || (voltage > 0 && !isArmAtTop()) || (voltage < 0 && !isArmAtBottom()))
			//elevatorMaster.set(voltage/12);
		if((isArmAtTop() && voltage/12 < 0) || (isArmAtBottom() && voltage/12 > 0))
			voltage = 0.0;
		else
			elevatorMaster.set(voltage/12);
	}*/
	
	public void moveWithJoystick(double throttle) {
		if((isArmAtTop() && throttle < 0) || (isArmAtBottom() && throttle > 0))
			throttle = 0.0;
			if (isCompetitionRobot)
				elevatorMaster.set(driveHelper.handleOverPower(driveHelper.handleDeadband(-throttle, elevatorDeadband)));
			else
				elevatorMaster.set(driveHelper.handleOverPower(driveHelper.handleDeadband(throttle, elevatorDeadband)));
	}
	
	public void move(double throttle) {
		if((isArmAtTop() && throttle < 0) || (isArmAtBottom() && throttle > 0))
			throttle = 0.0;
		if (isCompetitionRobot)
			elevatorMaster.set(-throttle);
		else
			elevatorMaster.set(throttle);
	}
	
	/*****************
	 * PID MOVEMENTS *
	 *****************/
	public void pushPIDGainsToSDB() {
		SmartDashboard.putNumber("Elevator Pos: P", elevator_kP);
		SmartDashboard.putNumber("Elevator Pos: I", elevator_kI);
		SmartDashboard.putNumber("Elevator Pos: D", elevator_kD);
	}
	
	public void moveWithPID(double targetPos) {
		double lastIntegral = 0;
		double lastError = 0;
		
		double kP = SmartDashboard.getNumber("Elevator Pos: P", elevator_kP);
		double kI = SmartDashboard.getNumber("Elevator Pos: I", elevator_kI);
		double kD = SmartDashboard.getNumber("Elevator Pos: D", elevator_kD);
		
		double error = targetPos - getDistanceTravelled();
		double integral = lastIntegral + error*kLooperDt;
		double derivative = (error - lastError) / kLooperDt;
		double PIDOutput = kP*error + kI*integral + kD*derivative;
		
		move(PIDOutput);
		
		lastError = error;
		lastIntegral = integral;
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new MoveWithJoystick());
	}

}
