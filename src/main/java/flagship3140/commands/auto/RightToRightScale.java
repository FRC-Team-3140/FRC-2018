package main.java.flagship3140.commands.auto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import main.java.flagship3140.commands.commandGroups.cubeManipulator.DropCube;
import main.java.flagship3140.commands.commandGroups.cubeManipulator.DropCubeOff;
import main.java.flagship3140.commands.drivetrain.TimedDrive;
import main.java.flagship3140.commands.drivetrain.TimedTurn;
import main.java.flagship3140.commands.elevator.MoveToTop;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommandGroup;

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
