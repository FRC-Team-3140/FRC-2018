package main.commands.auto;

import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.intake.SpinOff;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.tilt.TiltUp;

public class ResetForTeleop extends ImprovedCommandGroup {
	public ResetForTeleop() {
		addSequential(new TiltUp());
		addSequential(new ArmClose());
		addSequential(new SpinOff());
	}
}
