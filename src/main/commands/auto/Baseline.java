package main.commands.auto;

import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.drivetrain.TimedDrive;

public class Baseline extends ImprovedCommandGroup {
	public Baseline() {
		addSequential(new TimedDrive(0.75, 2));
	}

}
