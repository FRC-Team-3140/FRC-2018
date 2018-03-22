package main.commands.commandgroups.cubeManipulator;

import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.intake.SpinOut;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.tilt.TiltDown;

public class PushOutCube extends ImprovedCommandGroup {
	public PushOutCube() {
    	addSequential(new SpinOut());
    	addSequential(new TiltDown());
    }
}
