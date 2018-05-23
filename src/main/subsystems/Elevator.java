package main.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;

import Util.DriveHelper;
import Util.EncoderHelper;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import interfacesAndAbstracts.ImprovedSubsystem;
import main.commands.elevator.MoveWithJoystick;

public class Elevator extends ImprovedSubsystem {
	private DriveHelper driveHelper = new DriveHelper(7.5);
	private int toleranceTicks = 30;
	
	private int slotIdx = 0;
	private double kP = 0.7;
	private double kI = 0;
	private double kD = 3;
	private double veloFeedForward = 0;
	private boolean playFinished = false;
	
	public Elevator() {
		setElevatorDefaults();
		configSensors();
		zeroSensors();
		setPIDDefaults();
		SmartDashboard.putNumber("Target elevator velocity", 0);
	}
	
	/*************************
	 * TALON SUPPORT METHODS *
	 ************************/
	private void configSensors() {
		elevatorMaster.configSelectedFeedbackSensor(magEncoder, pidIdx, timeout);
		elevatorMaster.setSensorPhase(true);
	}
	
	private void setBrakeMode() {
		elevatorMaster.setNeutralMode(BRAKE_MODE);
		elevatorSlave.setNeutralMode(BRAKE_MODE);
	}
	
	private void setCtrlMode() {
		elevatorSlave.follow(elevatorMaster);
	}
	
	private void setPIDDefaults() {
		//elevatorMaster.configClosedloopRamp(0.2, timeout); use this??
		
		elevatorMaster.configAllowableClosedloopError(slotIdx, 0, timeout);
		elevatorMaster.config_kF(slotIdx, 0, timeout);
		elevatorMaster.config_kP(slotIdx, kP, timeout);
		elevatorMaster.config_kI(slotIdx, kI, timeout);
		elevatorMaster.config_kD(slotIdx, kD, timeout);
		elevatorMaster.selectProfileSlot(slotIdx, pidIdx);
	}
	
	public void setVoltageComp(boolean set, double voltage, int timeout) {
		if(set)
			System.out.println("Elevator Changed Voltage Compensation To: " + voltage);
		else
			System.out.println("Elevator Turned Voltage Compensation Off");	
		
		//Voltage Compensation On/Off
		elevatorMaster.enableVoltageCompensation(set);
		elevatorSlave.enableVoltageCompensation(set);
		//Config Voltage Compensation
		elevatorMaster.configVoltageCompSaturation(voltage, timeout);
		elevatorSlave.configVoltageCompSaturation(voltage, timeout);
		//Nominal and peak outputs
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
		setVoltageComp(true, voltageCompensationVoltage, timeout);
		elevatorMaster.setInverted(true);
		elevatorSlave.setInverted(true);
		}

	/**************************
	 * SENSOR SUPPORT METHODS *
	 **************************/
	
	public void zeroSensors() {
		elevatorMaster.getSensorCollection().setQuadraturePosition(0, 10);
	}
	
	// Checks if the intake is at bottom
	public boolean isArmAtBottom() {
		return !stage1BottomSwitch.get();
	}
	
	// Checks if intake is at the top
	public boolean isArmAtTop() {
		return !stage1TopSwitch.get();
	}
	
	// Sets encoders to 0 if the arm is at the bottom (this helps to avoid offset)
	public void check() {
		if (isArmAtBottom())
			zeroSensors();
	}
	
	// Returns whether or not the intake has reached the set position. Pos is in inches
	public boolean isIntakeAtPos(double pos) {
		return Math.abs(getDistanceFromPos(pos)) < elevatorTolerance;
	}
	
	public boolean isIntakeAbovePosition(double pos) {
		return getDistanceFromPos(pos) < 0;
	}
	/**********************
	 * ENC OUTPUT METHODS *
	 **********************/
	
	public double getElevatorVelocity() {
		return elevatorMaster.getSelectedSensorVelocity(pidIdx);
	}
	
	// Gets the number of revolutions of the encoder
	private double getElevatorRevs() {
		return elevatorMaster.getSelectedSensorPosition(pidIdx) / quadConversionFactor;
	}
	
	public double getTicksTravelled() {
		return elevatorMaster.getSelectedSensorPosition(pidIdx);
	}
	
	// Get the distance the elevator has traveled in inches
	public double getDistanceTravelled() {
		return 2 * getElevatorRevs() * spindleCircum / elevatorGearRatio;
	}
	
	// Returns distance from a set position
	private double getDistanceFromPos(double pos) {
		return pos - getDistanceTravelled();
	}
	
	public double getElevatorMasterVoltage() {
		return (elevatorMaster.getMotorOutputVoltage());
	}
	
	public double getElevatorSlaveVoltage() {
		return (elevatorSlave.getMotorOutputVoltage());
	}
		
	public int distanceToTicks(double distanceInches) {
		return (int) (EncoderHelper.inchesToEncoderTicks(distanceInches, spindleCircum, quadConversionFactor) * elevatorGearRatio / 2);
	}
	
	public boolean isPlayFinished() {
		return playFinished;
	}
	
	public void setPlayFinished(boolean finished) {
		playFinished = finished;
	}
	/********************
	 * MOVEMENT METHODS *
	 ********************/
	// Simple move to closed loop position PID
	public void movePID(double distanceInches) {
		elevatorMaster.set(ControlMode.Position, distanceToTicks(distanceInches));
	}
	
	/***
	 * Uses PID to hit a certain target velocity and maintains that velocity until stopped by another command/cancelled.
	 * @param velocityInchesPerSecond The target velocity in inches per second to hit
	 */
	public void moveVelocityPID(double velocityInchesPerSecond) {
		double velocityTicksPer100Ms = distanceToTicks(velocityInchesPerSecond) * 10;
		SmartDashboard.putNumber("Target elevator velocity", velocityTicksPer100Ms);
		elevatorMaster.set(ControlMode.Velocity, velocityTicksPer100Ms);
	}
	
	/**
	 * Uses a cascaded loop with position closed loop and velocity feed-forward. Used in motion profiles. Velocity
	 * feed forward helps "skip over" any slowing down before hitting a trajectory point that isn't near the end of
	 * the motion.
	 * @param velocityTicks100Ms Velocity in ticks per 100 ms of the trajectory point
	 * @param distanceTicks      Distance in ticks of the trajectory point.
	 */
	public void playbackPID(double velocityTicks100Ms, double distanceTicks) {
		double feedForward = veloFeedForward * velocityTicks100Ms;
		elevatorMaster.set(ControlMode.Position, distanceTicks, DemandType.ArbitraryFeedForward, feedForward);
		// if this doesnt work i will cry
		if(Math.abs(getTicksTravelled() - distanceTicks) < toleranceTicks) playFinished = true;
	}
	
	public void moveTrapezoidalProfile(double distance) {
		
	}
			
	public void moveWithJoystick(double throttle) {
		move(driveHelper.handleDeadband(throttle, elevatorDeadband));
	}
	
	public void move(double throttle) {
		SmartDashboard.putNumber("Elevator Input", throttle);
		if((isArmAtTop() && throttle > 0) || (isArmAtBottom() && throttle < 0))
			throttle = 0.0;
		if (isCompetitionRobot)
			elevatorMaster.set(driveHelper.handleOverPower(throttle));
		else
			elevatorMaster.set(driveHelper.handleOverPower(throttle));
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new MoveWithJoystick());
	}
}