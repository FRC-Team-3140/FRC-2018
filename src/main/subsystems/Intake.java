package main.subsystems;

import interfacesAndAbstracts.ImprovedSubsystem;
import Util.DriveHelper;
import main.commands.intake.ShootWithJoystick;

public class Intake extends ImprovedSubsystem {
	private final DriveHelper driveHelper = new DriveHelper(7.5);

	public void spinIn() {
		leftIntakeMotor.set(1.0);
    	rightIntakeMotor.set(-1.0);
	}
	
	public void spinOut() {
		leftIntakeMotor.set(-1.0);
    	rightIntakeMotor.set(1.0);
	}
	
	public void spinOff() {
    	leftIntakeMotor.set(0.0);
    	rightIntakeMotor.set(0.0);
	}
	
	@Override
	protected void initDefaultCommand() {
		setDefaultCommand(new ShootWithJoystick());
	}

	@Override
	public void check() {
		// TODO implement
	}

	@Override
	public void zeroSensors() {
		// TODO implement
	}
	public void moveWithJoystick(double throttle) {
		leftIntakeMotor.set(driveHelper.handleOverPower(driveHelper.handleDeadband(throttle, elevatorDeadband)));
		rightIntakeMotor.set(-driveHelper.handleOverPower(driveHelper.handleDeadband(throttle, elevatorDeadband)));
	}
}
