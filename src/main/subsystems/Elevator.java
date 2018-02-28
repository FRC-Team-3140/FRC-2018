package main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import Util.DriveHelper;
import Util.EncoderHelper;
import interfacesAndAbstracts.ImprovedSubsystem;
import main.commands.elevator.MoveWithJoystick;

public class Elevator extends ImprovedSubsystem {
	// GET F-GAIN
	// TEST ERROR AND CALCULATE P
	// TEST FOR COASTING- BRAKE MODE WORKS GREAT
	
	// ELEVATOR LENGTHS
	public final double spindleDiameter = 2; //placeholder
	public final double spindleCircum = Math.PI * spindleDiameter;
	public final double elevatorHeight = 86;  
	public final double elevatorTolerance = 2;
	public final double switchHeight = 24; //set this in encoder units today...
	public final double scaleHeight = 70; 
	public final double nearSetpoint = 12;
	public final double nearSetpointDown = 36;
		
	// ELEVATOR SPEEDS
	public final double defaultElevatorSpeed = 0.8;
	public final double slowElevatorSpeed = 0.2;
	public final int maxVelocity = 100523; //GET THIS VALUE PLEASE
	public final int cruiseVelocity = maxVelocity * 3/4; 
	public final int acceleration = 6250; //native units of encoder per 100 ms per second- PLACEHOLDER
	
	// MOTION MAGIC ELEVATOR STUFF
	public final int elevatorIdx = 0;
	public final int pidIdx = 0;
	public final double fGain = 1023 / maxVelocity ;// 1023/max speed
	public final double elevator_kP = 0;
	public final double elevator_kI = 0;
	public final double elevator_kD = 0;
			
	private EncoderHelper encoderHelper = new EncoderHelper();
	private DriveHelper driveHelper = new DriveHelper(7.5);
	
	//max velocity was 100523u/100ms	
	public Elevator() {
		setElevatorDefaults();
	}
	
	/************************
	 * MOTION MAGIC METHODS *
	 ************************
	 * This is to make a trapezoidal motion profile for the elevator... Hopefully it will work.
	 */
	private void setStatusFrames() {
		//something goes here but idk what
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
	/*public boolean isArmAtBottom() {
		if (stage1BottomSwitch.get()) return true;
		else return false;
	}*/
	
	// Checks if intake is at the top
	/*public boolean isArmAtTop() {
		if (stage1TopSwitch.get())
			return true;
		else return false;
	}*/
	
	// Sets encoders to 0 if the arm is at the bottom (this helps to avoid offset)
	public void check() {
		/*if (isArmAtBottom())
			zeroSensors();*/
	}
	
	// Returns whether or not the intake has reached the set position. Pos is in inches
	public boolean isIntakeAtPos(double pos) {
		if (getDistanceFromPos(pos) < elevatorTolerance && getDistanceFromPos(pos) > -1 * elevatorTolerance) {
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
	
	// Get the distance the elevator has travelled
	private double getDistanceTravelled() {
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
		return (elevatorMaster.getMotorOutputVoltage() + elevatorSlave.getMotorOutputVoltage())/2;
	}
	
	/********************
	 * MOVEMENT METHODS *
	 ********************/
	
	public void moveFromPlay(double voltage) {
		elevatorMaster.set(voltage);
	}
	
	public void moveToPosPID(double pos) {
		setMotionMagicDefaults();
		elevatorMaster.set(ControlMode.MotionMagic, inchesToElevatorEncoderTicks(pos));
	}
	
	public void moveWithJoystick(double throttle) {
		elevatorMaster.set(driveHelper.handleOverPower(driveHelper.handleDeadband(throttle, elevatorDeadband)));
	}
	
	public void moveTimed(double throttle) {
		elevatorMaster.set(throttle);
	}
	
	// Moves fast to a position if far away, slows down when it gets closer, and stops when it reaches
	// the position within a tolerance.
	public void moveToPos(double pos) {
//		if(isIntakeAtPos(pos)) {
//			elevatorMaster.set(0);
//		}
		if (isIntakeNearPos(pos, nearSetpoint)) {
			if (isIntakeBelowPos(pos)) elevatorMaster.set(slowElevatorSpeed);
			else elevatorMaster.set(-1 * slowElevatorSpeed);
		}
		else {
			if (isIntakeBelowPos(pos)) elevatorMaster.set(defaultElevatorSpeed);
			else elevatorMaster.set(defaultElevatorSpeed * -1); 
		}
	}

	public void moveDown() {
		/*if (isArmAtBottom()) {
			elevatorMaster.set(PERCENT_VBUS_MODE, 0);
		}
		else if (isIntakeNearPos(0, nearSetpointDown)) {
			elevatorMaster.set(getDistanceTravelled() * (-1/36));
		}
		else {
			elevatorMaster.set(-1 * defaultElevatorSpeed);
		}*/
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
		}*/
	}
	
	/*****************
	 * DUMMY METHODS *
	 *****************/
	
	public void up() {
		elevatorMaster.set(-1.0);
	}
	
	public void down() {
		elevatorMaster.set(1.0);
	}
	
	public void stop() {
		elevatorMaster.set(0);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new MoveWithJoystick());
	}

}
