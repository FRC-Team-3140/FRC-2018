  package main.java.flagship3140.commands.auto;

import main.java.flagship3140.commands.drivetrain.TimedDrive;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommandGroup;

public class Baseline extends ImprovedCommandGroup {
	//Baseline is 10ft away so the robot should drive 11ft
	public Baseline() {
		addSequential(new TimedDrive(timedDrivePercent, 78/38.58));
	}
}
