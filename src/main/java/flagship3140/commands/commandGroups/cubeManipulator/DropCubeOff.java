package main.java.flagship3140.commands.commandGroups.cubeManipulator;

import main.java.flagship3140.commands.pneumatics.arm.ArmClose;
import main.java.flagship3140.commands.pneumatics.tilt.TiltUp;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommandGroup;

public class DropCubeOff extends ImprovedCommandGroup {
	public DropCubeOff() {
		addSequential(new ArmClose());
    	addSequential(new TiltUp());
    }
}