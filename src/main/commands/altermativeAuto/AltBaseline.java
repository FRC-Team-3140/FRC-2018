  package main.commands.altermativeAuto;

import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.drivetrain.TimedDrive;

public class AltBaseline extends ImprovedCommandGroup {
	//Baseline is 10ft away so the robot should drive 11ft
	public AltBaseline() {
		addSequential(new TimedDrive(timedDrivePercent, 144/38.58));
	}
}
