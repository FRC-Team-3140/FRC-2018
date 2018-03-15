package main.commands.commandgroups.cubeManipulator;

import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.intake.SpinIn;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.tilt.*;

// TODO use this class
public class IntakeCube extends ImprovedCommandGroup { // NO_UCD
	public IntakeCube() {
    	addSequential(new TiltDown());
    	addSequential(new ArmOpen());
    	addSequential(new SpinIn());
    }
}
