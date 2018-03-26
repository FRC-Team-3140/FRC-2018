package main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

import Util.DriveHelper;
import Util.EncoderHelper;
import interfacesAndAbstracts.ImprovedSubsystem;
import main.commands.elevator.MoveWithJoystick;

public class Elevator extends ImprovedSubsystem {
	
	// GET F-GAIN
	// TEST ERROR AND CALCULATE P
	// TEST FOR COASTING- BRAKE MODE WORKS GREAT

			
	private EncoderHelper encoderHelper = new EncoderHelper();
	private DriveHelper driveHelper = new DriveHelper(7.5);
	
	public Elevator() {
		setElevatorDefaults();
		configSensors();
		setMotionMagicDefaults();
	}
	
	/************************
	 * MOTION MAGIC METHODS *
	 ************************
	 * This is to make a trapezoidal motion profile for the elevator... Hopefully it will work.
	 */
	
	private void setStatusFrames() {
		elevatorMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, timeout);
		elevatorMaster.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, timeout);
	}
		
	private void setAccelAndVeloDefaults() {
		elevatorMaster.configMotionCruiseVelocity(cruiseVelocity, 10);
		elevatorMaster.configMotionAcceleration(acceleration, 10);
	}
	
	private void setPIDValues() {
		elevatorMaster.selectProfileSlot(elevatorIdx, pidIdx);
		elevatorMaster.config_kF(elevatorIdx, fGain, 10);
		elevatorMaster.config_kP(elevatorIdx, elevator_kP, 10);
		elevatorMaster.config_kI(elevatorIdx, elevator_kI, 10);
		elevatorMaster.config_kD(elevatorIdx, elevator_kD, 10);
	}
	
	private void setMotionMagicDefaults() {
		setStatusFrames();
		setAccelAndVeloDefaults();
		setPIDValues();
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
	
	// Returns whether or not the elevator is close to set position
	private boolean isIntakeNearPos(double pos, double near) {
		if (getDistanceFromPos(pos) < near && getDistanceFromPos(pos) > -1* near) {
			return true;
		}
		else return false;
	}
	
	// Returns if the intake is currently below the desired position or not
	private boolean isIntakeBelowPos(double pos) {
		if (getDistanceFromPos(pos) > 0) return true;
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
	
	public void moveToPosPID(double pos) {
		setMotionMagicDefaults();
		elevatorMaster.set(ControlMode.MotionMagic, inchesToElevatorEncoderTicks(pos));
	}
	
	public void moveWithJoystick(double throttle) {
		if((isArmAtTop() && throttle < 0) || (isArmAtBottom() && throttle > 0))
			throttle = 0.0;
			if (isCompetitionRobot)
				elevatorMaster.set(driveHelper.handleOverPower(driveHelper.handleDeadband(-throttle, elevatorDeadband)));
			else
				elevatorMaster.set(driveHelper.handleOverPower(driveHelper.handleDeadband(throttle, elevatorDeadband)));
	}
	
	public void move(double throttle) {
		//if(throttle == 0 || (throttle > 0 && !isArmAtTop()) || (throttle < 0 && !isArmAtBottom()))
			//elevatorMaster.set(throttle);
		if((isArmAtTop() && throttle < 0) || (isArmAtBottom() && throttle > 0))
			throttle = 0.0;
		if (isCompetitionRobot)
			elevatorMaster.set(-throttle);
		else
			elevatorMaster.set(throttle);
	}
	
	// Moves fast to a position if far away, slows down when it gets closer, and stops when it reaches
	// the position within a tolerance.
	public void moveToPosDumb(double pos) {
		double posTicks = inchesToElevatorEncoderTicks(pos);
		if(posTicks - getTicksTravelled() > 0) move(-0.8);
		else if(posTicks - getTicksTravelled() < 0) move(0.8);
	}

	//public void moveDown() {
		/*if (isArmAtBottom()) {
			elevatorMaster.set(PERCENT_VBUS_MODE, 0);
		}
		else if (isIntakeNearPos(0, nearSetpointDown)) {
			elevatorMaster.set(getDistanceTravelled() * (-1/36));
		}
		else {
			elevatorMaster.set(-1 * defaultElevatorSpeed);
		}
	}
	
	public void moveUp() {
		/*if (isArmAtTop()) {
			elevatorMaster.set(0);
		}
		else if(isIntakeNearPos(elevatorHeight, nearSetpoint)) {
			elevatorMaster.set(slowElevatorSpeed);
		}
		else {
			elevatorMaster.set(defaultElevatorSpeed);
		}
	}*/
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new MoveWithJoystick());
	}

}
