package main.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.commandGroups.cubeManipulator.DropCube;
import main.commands.commandGroups.cubeManipulator.DropCubeOff;
import main.commands.drivetrain.DistanceDriveStraight;
import main.commands.drivetrain.TurnToAngle;
import main.commands.drivetrain.TurnToAngleGyro;
import main.commands.elevator.MovePID;
import main.commands.elevator.MoveToSwitch;

public class AltLeftToLeftSwitch extends ImprovedCommandGroup {
	public AltLeftToLeftSwitch() {
		addSequential(new DistanceDriveStraight(148.75));
		addSequential(new TurnToAngleGyro(90, 2.5));
		addSequential(new WaitCommand(0.25));
		addSequential(new MovePID(switchHeight, 2));
		addSequential(new DistanceDriveStraight(43.05));
		addSequential(new WaitCommand(0.1));
		addSequential(new DropCube());
		addSequential(new WaitCommand(1));
		addSequential(new DropCubeOff());
	}
}
