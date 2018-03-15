package main.commands.commandgroups.cubeManipulator;

import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.intake.SpinOff;

// TODO use this class
public class DanceOff extends ImprovedCommandGroup { // NO_UCD
	public DanceOff() {
		addSequential(new SpinOff());
	}
}
