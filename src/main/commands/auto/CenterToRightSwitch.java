package main.commands.auto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.commandgroups.cubeManipulator.DropCube;
import main.commands.commandgroups.cubeManipulator.DropCubeOff;
import main.commands.drivetrain.TimedDrive;
import main.commands.drivetrain.TimedTurn;
import main.commands.elevator.MoveToSwitch;

public class CenterToRightSwitch extends ImprovedCommandGroup {
	//x, y & z will be the number of inches that the robot needs to drive/ alex will fill this in a couple of hours
	public CenterToRightSwitch() {
		addSequential(new TimedDrive(timedDrivePercent, 60.375/timedDistanceMultiplier)); //(Break away from wall so there is no resistance on the first turn)
		addSequential(new TimedTurn(TurnMode.Right, timedTurnPercent, timedTurn90degTime));
		addSequential(new TimedDrive(timedDrivePercent, 60.5/timedDistanceMultiplier));
		addSequential(new TimedTurn(TurnMode.Left, timedTurnPercent, timedTurn90degTime));
		addSequential(new WaitCommand(1));
		//addSequential(new TimedLift(timedLiftPercent, timedLiftTime));
		addSequential(new MoveToSwitch(5));
		addSequential(new TimedDrive(timedDrivePercent, 60.375/timedDistanceMultiplier));
		addSequential(new WaitCommand(0.1));
		addSequential(new DropCube());
		addSequential(new WaitCommand(1));
		addSequential(new DropCubeOff());
	}
}
