package main.commands.auto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.commandgroups.cubeManipulator.DropCube;
import main.commands.commandgroups.cubeManipulator.DropCubeOff;
import main.commands.drivetrain.TimedDrive;
import main.commands.drivetrain.TimedTurn;
import main.commands.elevator.MoveToSwitch;
import main.commands.elevator.MoveToTop;

public class RightToRightSwitch extends ImprovedCommandGroup {
	//x & y will be the number of inches that the robot needs to drive/ alex will fill this in a couple of hours
	public RightToRightSwitch() {
		addSequential(new TimedDrive(TIMED_DRIVE_PERCENT, 148.75/TIMED_DISTANCE_MULTIPLIER));
		addSequential(new TimedTurn(TurnMode.LEFT, TIMED_TURN_PERCENT, TIMED_TURN_90_DEG_TIME));
		addSequential(new WaitCommand(0.25));
		addSequential(new MoveToTop(3));
		addSequential(new TimedDrive(TIMED_DRIVE_PERCENT, 43.05/TIMED_DISTANCE_MULTIPLIER));
		addSequential(new WaitCommand(0.1));
		addSequential(new DropCube());
		addSequential(new WaitCommand(1));
		addSequential(new DropCubeOff());
	}
}
