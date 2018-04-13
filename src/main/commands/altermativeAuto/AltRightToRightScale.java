package main.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.commandGroups.cubeManipulator.DropCube;
import main.commands.commandGroups.cubeManipulator.DropCubeOff;
import main.commands.drivetrain.DistanceDriveStraight;
import main.commands.drivetrain.TurnToAngle;
import main.commands.elevator.MoveToSwitch;
import main.commands.elevator.MoveToTop;

public class AltRightToRightScale extends ImprovedCommandGroup {
	public AltRightToRightScale() {
		// distance from start to scale  minus robot length
		addSequential(new DistanceDriveStraight((260-robotLength/2)));
		addSequential(new TurnToAngle(-45));
		addSequential(new WaitCommand(0.25));
		addSequential(new MoveToTop(3));
		addSequential(new DistanceDriveStraight((60.5 - safetyFactor)));
		addSequential(new WaitCommand(0.1));
		addSequential(new DropCube());
		addSequential(new WaitCommand(1));
		addSequential(new DropCubeOff());		
	}
}
