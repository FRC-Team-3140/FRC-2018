  package main.commands.altermativeAuto;

import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.drivetrain.DistanceDriveStraight;

public class AltBaseline extends ImprovedCommandGroup {
	//Baseline is 10ft away so the robot should drive 12ft
	public AltBaseline() {
		addSequential(new DistanceDriveStraight(144));
	}
}
