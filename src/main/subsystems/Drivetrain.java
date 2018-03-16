package main.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import Util.DriveHelper;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import interfacesAndAbstracts.ImprovedSubsystem;
import main.commands.drivetrain.Drive;

public class Drivetrain extends ImprovedSubsystem  {
	private final static DifferentialDrive driveTrain = new DifferentialDrive(leftDriveMaster, rightDriveMaster);
	
	public Drivetrain() {
		setTalonDefaults();
	}
	
	// DRIVE FOR TELEOP
	public void driveVelocity(double throttle, double heading) {
		if (IS_COMPETITION_ROBOT) {
			driveTrain.arcadeDrive(DriveHelper.handleDrive(throttle, THROTTLE_DEADBAND),
					DriveHelper.handleDrive(-heading, HEADING_DEADBAND));
		}
		else {
			driveTrain.arcadeDrive(DriveHelper.handleDrive(-throttle, THROTTLE_DEADBAND),
					DriveHelper.handleDrive(-heading, HEADING_DEADBAND));
		}
	}

	//Drive for playing back
	// TODO use or remove
	public void driveVoltageTank(double leftVoltage, double rightVoltage) { // NO_UCD
		leftVoltage = (Math.abs(leftVoltage) > 12.0) ? Math.signum(leftVoltage) : leftVoltage/12;
		rightVoltage = -((Math.abs(rightVoltage)  > 12.0) ? Math.signum(rightVoltage) : rightVoltage/12);
		
		leftVoltage = Math.signum(leftVoltage) * (Math.abs(leftVoltage) + LEFT_VOLTAGE_BIAS);
		rightVoltage = Math.signum(rightVoltage) * (Math.abs(rightVoltage) + RIGHT_VOLTAGE_BIAS);
		
		driveTrain.tankDrive(leftVoltage, rightVoltage, false);	
	}
	
	//Drive for testing the drivetrain so that the needed constants to compute the bias voltages may be derived
	public void driveVoltageTankTest(double leftVoltage, double rightVoltage) {
		driveTrain.tankDrive(leftVoltage/12, -rightVoltage/12, false);
	}
	
	public void timedTurn(TurnMode mode, double throttle) {
		if (mode == TurnMode.LEFT) driveTrain.tankDrive(-throttle, throttle, false);
		if (mode == TurnMode.RIGHT) driveTrain.tankDrive(throttle, -throttle, false);
	}
	
	/***********************
	 * PLAY/RECORD METHODS *
	 ***********************/
	public double getLeftVoltage() {
		return (leftDriveMaster.getMotorOutputVoltage());
	}
	
	public double getRightVoltage() {
		return (rightDriveMaster.getMotorOutputVoltage());
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
	
	private void setTalonDefaults() {
		reverseTalons(false);
		setBrakeMode(BRAKE_MODE);
		setCtrlMode();
		setVoltageComp(true, VOLTAGE_COMPENSATION_VOLTAGE, 10);
	}

	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new Drive());
	}

	@Override
	public void check() {
		// TODO implement
	}

	@Override
	public void zeroSensors() {
		// TODO implement
	}	
}
