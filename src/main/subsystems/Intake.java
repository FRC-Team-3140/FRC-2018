package main.subsystems;

import interfacesAndAbstracts.ImprovedSubsystem;

public class Intake extends ImprovedSubsystem {
	public static enum WheelStates {
		In, Out, Off
	}
	
	// NOT SURE IF WE SHOULD USE AN ENUM- TESTING SWITCH METHODS FIRST
//	public static enum CubeInIntake {
//		HasCube, NoCube
//	}
	
	public static WheelStates wheelStates = WheelStates.Off;
	//public static CubeInIntake cubeInIntake = CubeInIntake.NoCube;
	
	public void spinIn() {
		leftIntakeMotor.set(1.0);
    	rightIntakeMotor.set(-1.0);
    	wheelStates = WheelStates.In;
	}
	
	public void spinOut() {
		leftIntakeMotor.set(-1.0);
    	rightIntakeMotor.set(1.0);
    	wheelStates = WheelStates.Out;
	}
	
	public void spinOff() {
    	leftIntakeMotor.set(0.0);
    	rightIntakeMotor.set(0.0);
    	wheelStates = WheelStates.Off;
	}
	
//	public boolean isCubeInIntake() {
//		return intakeSwitch.get();
//	}
//	
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
