package main.commands.commandgroups.cubeManipulator;

import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.intake.SpinOut;
import main.commands.pneumatics.tilt.TiltDown;

//TODO use this class
public class PushOutCube extends ImprovedCommandGroup { // NO_UCD (unused code)
	public PushOutCube() {
    	addSequential(new SpinOut());
    	addSequential(new TiltDown());
    }
}
