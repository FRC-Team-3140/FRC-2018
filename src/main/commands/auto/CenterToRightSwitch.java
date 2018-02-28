package main.commands.auto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.Constants.TurnMode;
import main.commands.commandgroups.cubeManipulator.DropCube;
import main.commands.commandgroups.cubeManipulator.DropCubeOff;
import main.commands.drivetrain.TimedDrive;
import main.commands.drivetrain.TimedTurn;
import main.commands.elevator.TimedLift;

public class CenterToRightSwitch extends ImprovedCommandGroup {
	//x, y & z will be the number of feet that the robot needs to drive/ alex will fill this in a couple of hours
	//also need to check that 45deg is a sufficient angle
	public CenterToRightSwitch() {
		addSequential(new TimedDrive(timedDrivePercent, x/timedDistanceMultiplier)); //(Break away from wall so there is no resistance on the first turn)
		addSequential(new TimedTurn(TurnMode.Right, timedTurnPercent, timedTurn45degTime));
		addSequential(new TimedLift(timedLiftPercent, timedLiftTime));
		addSequential(new TimedDrive(timedDrivePercent, y/timedDistanceMultiplier));
		addSequential(new TimedTurn(TurnMode.Left, timedTurnPercent, timedTurn45degTime));
		addSequential(new TimedDrive(timedDrivePercent, z/timedDistanceMultiplier));
		addSequential(new WaitCommand(0.1));
		addSequential(new DropCube());
		addSequential(new WaitCommand(0.5));
		addSequential(new DropCubeOff());
	}
}
