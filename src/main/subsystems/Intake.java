package main.subsystems;

import interfacesAndAbstracts.ImprovedSubsystem;

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
