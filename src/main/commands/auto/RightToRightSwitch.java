package main.commands.auto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.commandgroups.cubeManipulator.DropCube;
import main.commands.drivetrain.TimedDrive;
import main.commands.drivetrain.TimedTurn;
import main.commands.elevator.TimedMove;

public class RightToRightSwitch extends ImprovedCommandGroup {
	public RightToRightSwitch() {
		addSequential(new TimedDrive(0.75, 2));
		addSequential(new TimedMove(0.5, 0.5));
		addSequential(new TimedTurn(TurnMode.Left, 0.5, 0.75));
		addSequential(new TimedDrive(0.5, 1));
		addSequential(new WaitCommand(0.1));
		addSequential(new DropCube());
		addSequential(new WaitCommand(0.3));
	}

}
