package main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;

import Util.DriveHelper;
import Util.EncoderHelper;
import interfacesAndAbstracts.ImprovedSubsystem;
import main.commands.elevator.MoveWithJoystick;

public class Elevator extends ImprovedSubsystem {
	
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
		elevatorMaster.configMotionCruiseVelocity(cruiseVelocity, timeout);
		elevatorMaster.configMotionAcceleration(acceleration, timeout);
	}
	
	private void setPIDValues() {
		elevatorMaster.selectProfileSlot(elevatorIdx, pidIdx);
		elevatorMaster.config_kF(elevatorIdx, fGain, timeout);
		elevatorMaster.config_kP(elevatorIdx, elevator_kP, timeout);
		elevatorMaster.config_kI(elevatorIdx, elevator_kI, timeout);
		elevatorMaster.config_kD(elevatorIdx, elevator_kD, timeout);
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
		
	public boolean isArmAtSwitch() {
		return isIntakeAtPos(switchHeight);
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
	
	// Returns the distance travelled in native encoder units
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
		if((isArmAtTop() && throttle < 0) || (isArmAtBottom() && throttle > 0))
			throttle = 0.0;
		if (isCompetitionRobot)
			elevatorMaster.set(-throttle);
		else
			elevatorMaster.set(throttle);
	}
	
	public void moveToPosDumb(double pos) {
		double posTicks = inchesToElevatorEncoderTicks(pos);
		if(posTicks - getTicksTravelled() > 0) move(-0.8);
		else if(posTicks - getTicksTravelled() < 0) move(0.8);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new MoveWithJoystick());
	}

}
