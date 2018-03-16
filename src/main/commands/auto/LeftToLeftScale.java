package main.commands.auto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.commandgroups.cubeManipulator.DropCube;
import main.commands.commandgroups.cubeManipulator.DropCubeOff;
import main.commands.drivetrain.TimedDrive;
import main.commands.drivetrain.TimedTurn;
import main.commands.elevator.MoveToTop;
import main.commands.pneumatics.tilt.TiltDown;

// TODO use or remove
public class LeftToLeftScale extends ImprovedCommandGroup { // NO_UCD (unused code)
	public LeftToLeftScale() {
		addSequential(new TimedDrive(TIMED_DRIVE_PERCENT, 238.66/TIMED_DISTANCE_MULTIPLIER));
		addSequential(new TimedTurn(TurnMode.RIGHT, TIMED_TURN_PERCENT, TIMED_TURN_45_DEG_TIME));
		addSequential(new TiltDown());
		addSequential(new WaitCommand(1));
		//addSequential(new TimedLift(timedLiftPercent, timedLiftFullHeightTime));
		addSequential(new MoveToTop(5));
		addSequential(new TimedDrive(TIMED_DRIVE_PERCENT, 67.3/TIMED_DISTANCE_MULTIPLIER));
		addSequential(new WaitCommand(0.1));
		addSequential(new DropCube());
		addSequential(new WaitCommand(1));
		addSequential(new DropCubeOff());	
	}
}