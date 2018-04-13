package main.commands.altermativeAuto;

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

public class AltLeftToLeftSwitch extends ImprovedCommandGroup {
	//x & y will be the number of inches that the robot needs to drive/ alex will fill this in a couple of hours
	public AltLeftToLeftSwitch() {
		addSequential(new TimedDrive(timedDrivePercent, 148.75/timedDistanceMultiplier));
		addSequential(new TimedTurn(TurnMode.Right, timedTurnPercent, timedTurn90degTime));
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