package main.commands.auto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.commandgroups.cubeManipulator.DropCube;
import main.commands.commandgroups.cubeManipulator.DropCubeOff;
import main.commands.drivetrain.TimedDrive;
import main.commands.drivetrain.TimedTurn;
import main.commands.elevator.MoveToTop;

// TODO use or remove
public class RightToRightScale extends ImprovedCommandGroup { // NO_UCD (unused code)
	public RightToRightScale() {
		// distance from start to scale  minus robot length
		addSequential(new TimedDrive(TIMED_DRIVE_PERCENT, (260-ROBOT_LENGTH/2)/TIMED_DISTANCE_MULTIPLIER));
		addSequential(new TimedTurn(TurnMode.LEFT, TIMED_TURN_PERCENT, TIMED_TURN_45_DEG_TIME));
		addSequential(new WaitCommand(0.25));
		addSequential(new MoveToTop(3));
		// adjusted distance to scale
		addSequential(new TimedDrive(TIMED_DRIVE_PERCENT, (60.5 - SAFETY_FACTOR)/TIMED_DISTANCE_MULTIPLIER));
		addSequential(new WaitCommand(0.1));
		addSequential(new DropCube());
		addSequential(new WaitCommand(1));
		addSequential(new DropCubeOff());
	}
}
