package main.java.flagship3140.commands.auto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import main.java.flagship3140.commands.commandGroups.cubeManipulator.DropCube;
import main.java.flagship3140.commands.commandGroups.cubeManipulator.DropCubeOff;
import main.java.flagship3140.commands.drivetrain.TimedDrive;
import main.java.flagship3140.commands.drivetrain.TimedTurn;
import main.java.flagship3140.commands.elevator.MoveToTop;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommandGroup;

public class RightToRightSwitch extends ImprovedCommandGroup {
	//x & y will be the number of inches that the robot needs to drive/ alex will fill this in a couple of hours
	public RightToRightSwitch() {
		addSequential(new TimedDrive(timedDrivePercent, 148.75/timedDistanceMultiplier));
		addSequential(new TimedTurn(TurnMode.Left, timedTurnPercent, timedTurn90degTime));
		addSequential(new WaitCommand(0.25));
		//addSequential(new TimedLift(timedLiftPercent, timedLiftTime));
		addSequential(new MoveToTop(3));

		//addSequential(new MoveToSwitch(5));
		addSequential(new TimedDrive(timedDrivePercent, 43.05/timedDistanceMultiplier));
		addSequential(new WaitCommand(0.1));
		addSequential(new DropCube());
		addSequential(new WaitCommand(1));
		addSequential(new DropCubeOff());
	}
}
