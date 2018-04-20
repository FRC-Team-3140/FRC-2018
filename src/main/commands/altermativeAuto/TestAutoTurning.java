package main.commands.altermativeAuto;

import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.drivetrain.TurnToAngle;

public class TestAutoTurning extends ImprovedCommandGroup {
	public TestAutoTurning() {
		addSequential(new TurnToAngle(90));
		//addSequential(new WaitCommand(0.5));
		addSequential(new TurnToAngle(-45));
		//addSequential(new WaitCommand(0.5));
		addSequential(new TurnToAngle(-45));
		//addSequential(new WaitCommand(0.5));
	}
}
