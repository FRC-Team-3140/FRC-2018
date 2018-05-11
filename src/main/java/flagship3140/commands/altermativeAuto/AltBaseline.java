  package main.java.flagship3140.commands.altermativeAuto;

import main.java.flagship3140.commands.drivetrain.DistanceDriveStraight;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommandGroup;

public class AltBaseline extends ImprovedCommandGroup {
	//Baseline is 10ft away so the robot should drive 11ft
	public AltBaseline() {
		addSequential(new DistanceDriveStraight(144));
	}
}
