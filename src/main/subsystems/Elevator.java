package main.subsystems;

import Util.DriveHelper;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import interfacesAndAbstracts.ImprovedSubsystem;
import main.commands.elevator.MoveWithJoystick;

public class Elevator extends ImprovedSubsystem {
	private DriveHelper driveHelper = new DriveHelper(7.5);
	
	//ProfilePIDVariables
	private static double lastEncPosError = 0.0;
	//ProfilePIDIntegralAccumulators
	private static double integralAccum = 0.0;
	
	public Elevator() {
		setElevatorDefaults();
		configSensors();
		zeroSensors();
		pushPIDGainsToSDB();
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
	
	private void setVoltageComp(boolean set, double voltage, int timeout) {
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
		setVoltageComp(false, voltageCompensationVoltageElevator, 10);
	}
	
	public void enableVoltageComp(boolean enable) {
		if(enable) {
			setVoltageComp(true, voltageCompensationVoltageElevator, 10);
			System.out.println("Elevator:");
			System.out.println("Set Voltage Compensation To: " + voltageCompensationVoltageElevator + " Volts.");
		}
		else {
			setVoltageComp(false, voltageCompensationVoltageElevator, 10);
			System.out.println("Elevator:");
			System.out.println("Turned Voltage Compensation Off.");
		}
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
		
	/********************
	 * MOVEMENT METHODS *
	 ********************/
		
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
			elevatorMaster.set(-driveHelper.handleOverPower(throttle));
		else
			elevatorMaster.set(driveHelper.handleOverPower(throttle));
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
		//Grab PID Vars
		double kP = SmartDashboard.getNumber("Elevator Pos: P", elevator_kP);
		double kI = SmartDashboard.getNumber("Elevator Pos: I", elevator_kI);
		double kD = SmartDashboard.getNumber("Elevator Pos: D", elevator_kD);
		//Calculate Error With PID
		double error = targetPos - getDistanceTravelled();
		integralAccum += (error*kLooperDt);
		double derivative = (error - lastEncPosError)/kLooperDt;
		double PIDOutput = kP*error + kI*integralAccum + kD*derivative;
		lastEncPosError = error;
		//Move Based on PID Output
		move(PIDOutput);		
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new MoveWithJoystick());
	}
	
	public void zeroPIDVariables() {
		//Clear Previous Errors
		lastEncPosError = 0.0;
		//Clear Integral Accumulators
	    integralAccum = 0.0;
	}
}
