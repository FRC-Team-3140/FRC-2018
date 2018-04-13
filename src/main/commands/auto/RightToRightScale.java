package main.commands.auto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.Constants.TurnMode;
import main.commands.commandGroups.cubeManipulator.DropCube;
import main.commands.commandGroups.cubeManipulator.DropCubeOff;
import main.commands.drivetrain.TimedDrive;
import main.commands.drivetrain.TimedTurn;
import main.commands.elevator.MoveToSwitch;
import main.commands.elevator.MoveToTop;
import main.commands.elevator.TimedLift;
import main.commands.pneumatics.tilt.TiltDown;

public class RightToRightScale extends ImprovedCommandGroup {
	public RightToRightScale() {
		// distance from start to scale  minus robot length
		addSequential(new TimedDrive(timedDrivePercent, (260-robotLength/2)/timedDistanceMultiplier));
		addSequential(new TimedTurn(TurnMode.Left, timedTurnPercent, timedTurn45degTime));
		//addSequential(new TiltDown());
		addSequential(new WaitCommand(0.25));
		//addSequential(new TimedLift(timedLiftPercent, timedLiftFullHeightTime));
		addSequential(new MoveToTop(3));
		// adjusted distance to scale
		addSequential(new TimedDrive(timedDrivePercent, (60.5 - safetyFactor)/timedDistanceMultiplier));
		addSequential(new WaitCommand(0.1));
		addSequential(new DropCube());
		addSequential(new WaitCommand(1));
		addSequential(new DropCubeOff());		
	}
}
