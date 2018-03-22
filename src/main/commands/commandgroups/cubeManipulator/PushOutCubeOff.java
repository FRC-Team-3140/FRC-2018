package main.commands.commandgroups.cubeManipulator;

import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.intake.SpinOff;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.tilt.TiltUp;

public class PushOutCubeOff extends ImprovedCommandGroup {
	public PushOutCubeOff() {
    	addSequential(new SpinOff());
    	addSequential(new TiltUp());
    }
}
