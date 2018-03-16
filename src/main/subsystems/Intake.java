package main.subsystems;

import interfacesAndAbstracts.ImprovedSubsystem;
import Util.DriveHelper;
import main.commands.intake.ShootWithJoystick;

public class Intake extends ImprovedSubsystem {
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
		leftIntakeMotor.set(DriveHelper.handleDrive(throttle, ELEVATOR_DEADBAND));
		rightIntakeMotor.set(DriveHelper.handleDrive(throttle, ELEVATOR_DEADBAND));
	}
	
	public double getLeftIntakeWheelValue() {
		return leftIntakeMotor.get();
	}
	
	public double getRightIntakeWheelValue() {
		return leftIntakeMotor.get();
	}
	
	public void setIntakeValues(double leftIntakeWheelValue, double rightIntakeWheelValue) {
		leftIntakeMotor.set(leftIntakeWheelValue);
		rightIntakeMotor.set(rightIntakeWheelValue);
	}
}
