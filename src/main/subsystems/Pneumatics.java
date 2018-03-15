package main.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import interfacesAndAbstracts.ImprovedSubsystem;

public class Pneumatics extends ImprovedSubsystem {

	public Pneumatics() {
		shifter.set(EXT);
		shifter.set(OFF);
		tilter.set(EXT);
		tilter.set(OFF);
		intakeArm.set(EXT);
		intakeArm.set(OFF);
	}
	
	//Default Robot State at start of match.
	static boolean armClose = true;
	static boolean tiltUp = true;

	/*******************
	 * COMMAND METHODS *
	 *******************/

	/**
	 * Shifts the gearbox from the different gears
	 * 
	 * @param v - Desired shifting value (Uses default shifting values)
	 */
	public void shift(DoubleSolenoid.Value v) {
		shifter.set(v);
	}

	// Toggles Arm to open or closed
	public void toggleArm(DoubleSolenoid.Value v) {
		if (v == EXT) armClose = true;
		else if (v == RET) armClose = false;
		intakeArm.set(v);
	}
		
	// Changes the tilter to up or down
	public void tilt(DoubleSolenoid.Value v) {
		if (v == EXT) tiltUp = true;
		else if (v == RET) tiltUp = false;
		tilter.set(v);
	}
	
	public boolean isArmClose() {
		return armClose;
	}
	
	public boolean isTiltUp() {
		return tiltUp;
	}

	/*******************
	 * DEFAULT METHODS *
	 *******************/
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(null);
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
