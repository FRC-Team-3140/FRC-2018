package main.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.commandGroups.cubeManipulator.DropCube;
import main.commands.commandGroups.cubeManipulator.DropCubeOff;
import main.commands.drivetrain.DistanceDriveStraight;
import main.commands.drivetrain.TurnToAngle;
import main.commands.elevator.MoveToSwitch;
import main.commands.pneumatics.arm.ArmOpen;

public class AltCenterToLeftSwitch extends ImprovedCommandGroup {
	public AltCenterToLeftSwitch() {
		addSequential(new DistanceDriveStraight(30.375)); //(Break away from wall so there is no resistance on the first turn)
		addSequential(new TurnToAngle(-90));
		addSequential(new DistanceDriveStraight(69.5));
		addSequential(new TurnToAngle(90));
		addSequential(new WaitCommand(0.25));
		addSequential(new MoveToSwitch(1.5));
		addSequential(new DistanceDriveStraight(90.375));
		addSequential(new WaitCommand(0.1));
		addSequential(new DropCube());
		addSequential(new WaitCommand(1));
		addSequential(new DropCubeOff());
		addSequential(new WaitCommand(0.2));
		addSequential(new DistanceDriveStraight(-60));
		addSequential(new TurnToAngle(90));
		addSequential(new DistanceDriveStraight(48));
		addSequential(new TurnToAngle(-90));
		addSequential(new ArmOpen());
	}
}
