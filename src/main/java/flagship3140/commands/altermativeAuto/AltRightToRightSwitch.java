package main.java.flagship3140.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import main.java.flagship3140.commands.commandGroups.cubeManipulator.DropCube;
import main.java.flagship3140.commands.commandGroups.cubeManipulator.DropCubeOff;
import main.java.flagship3140.commands.drivetrain.DistanceDriveStraight;
import main.java.flagship3140.commands.drivetrain.TurnToAngle;
import main.java.flagship3140.commands.elevator.MoveToSwitch;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommandGroup;

public class AltRightToRightSwitch extends ImprovedCommandGroup {
	public AltRightToRightSwitch() {
		addSequential(new DistanceDriveStraight(148.75));
		addSequential(new TurnToAngle(-90));
		addSequential(new WaitCommand(0.25));
		addSequential(new MoveToSwitch(1.5));
		addSequential(new DistanceDriveStraight(43.05));
		addSequential(new WaitCommand(0.1));
		addSequential(new DropCube());
		addSequential(new WaitCommand(1));
		addSequential(new DropCubeOff());
	}
}
