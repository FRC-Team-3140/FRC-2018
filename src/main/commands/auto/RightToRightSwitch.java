package main.commands.auto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.commandgroups.cubeManipulator.DropCube;
import main.commands.commandgroups.cubeManipulator.DropCubeOff;
import main.commands.drivetrain.TimedDrive;
import main.commands.drivetrain.TimedTurn;
import main.commands.elevator.TimedLift;

public class RightToRightSwitch extends ImprovedCommandGroup {
	//x & y will be the number of inches that the robot needs to drive/ alex will fill this in a couple of hours
	public RightToRightSwitch() {
		addSequential(new TimedDrive(timedDrivePercent, x/timedDistanceMultiplier));
		addSequential(new TimedLift(timedLiftPercent, timedLiftTime));
		addSequential(new TimedTurn(TurnMode.Left, timedTurnPercent, timedTurn90degTime));
		addSequential(new TimedDrive(timedDrivePercent, y/timedDistanceMultiplier));
		addSequential(new WaitCommand(0.1));
		addSequential(new DropCube());
		addSequential(new WaitCommand(0.5));
		addSequential(new DropCubeOff());
	}
}
