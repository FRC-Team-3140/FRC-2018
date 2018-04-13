package main.commands.commandGroups.cubeManipulator;

import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.tilt.TiltUp;

public class DropCubeOff extends ImprovedCommandGroup {
	public DropCubeOff() {
		addSequential(new ArmClose());
    	addSequential(new TiltUp());
    }
}