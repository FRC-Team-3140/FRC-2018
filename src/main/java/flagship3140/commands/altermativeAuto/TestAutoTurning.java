package main.java.flagship3140.commands.altermativeAuto;

import main.java.flagship3140.commands.drivetrain.TurnToAngle;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommandGroup;

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
