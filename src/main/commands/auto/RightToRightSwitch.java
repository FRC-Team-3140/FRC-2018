package main.commands.auto;

import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.drivetrain.TimedDrive;
import main.commands.elevator.TimedMove;

public class RightToRightSwitch extends ImprovedCommandGroup {
	public RightToRightSwitch() {
		addSequential(new TimedDrive(0.75, 2));
		addSequential(new TimedMove(0.5, 0.5));
		//TODO FINISH THIS
	}

}
