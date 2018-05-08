package main.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.commandGroups.cubeManipulator.DropCube;
import main.commands.commandGroups.cubeManipulator.DropCubeOff;
import main.commands.drivetrain.TurnToAngle;
import main.commands.elevator.MoveToSwitch;

public class AltRightToRightSwitch extends ImprovedCommandGroup {
	public AltRightToRightSwitch() {
//		addSequential(new DistanceDriveStraight(148.75));
		addSequential(new TurnToAngle(-90));
		addSequential(new WaitCommand(0.25));
		addSequential(new MoveToSwitch(1.5));
//		addSequential(new DistanceDriveStraight(43.05));
		addSequential(new WaitCommand(0.1));
		addSequential(new DropCube());
		addSequential(new WaitCommand(1));
		addSequential(new DropCubeOff());
	}
}
