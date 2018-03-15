package main.commands.commandgroups.cubeManipulator;

import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.intake.SpinOff;
import main.commands.pneumatics.tilt.TiltUp;

// TODO use this class
public class PushOutCubeOff extends ImprovedCommandGroup { // NO_UCD
	public PushOutCubeOff() {
    	addSequential(new SpinOff());
    	addSequential(new TiltUp());
    }
}
