package main.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.drivetrain.DistanceDriveStraight;
import main.commands.drivetrain.TurnToAngle;
import main.commands.elevator.MoveToBottom;
import main.commands.elevator.MoveToTop;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.tilt.TiltDown;
import main.commands.pneumatics.tilt.TiltUp;

public class AltRightToRightScale extends ImprovedCommandGroup {
	public AltRightToRightScale() {
		// distance from start to scale  minus robot length
		addSequential(new DistanceDriveStraight(((296-robotLength/2) - 6)));
		addSequential(new TurnToAngle(-35));
		addSequential(new WaitCommand(0.25));
		addSequential(new DistanceDriveStraight(-3));
		addSequential(new MoveToTop(3));
		addSequential(new DistanceDriveStraight(4));
		//addSequential(new TiltDown());
		addSequential(new WaitCommand(0.3));
		addSequential(new TiltDown());
		addSequential(new ArmOpen());
		addSequential(new WaitCommand(0.5));
		addSequential(new TiltUp());
		addSequential(new ArmClose());
		addSequential(new WaitCommand(0.3));
		addSequential(new DistanceDriveStraight(-4));
		addSequential(new MoveToBottom(3));
		//addSequential(new DropCube());
		//addSequential(new WaitCommand(1));
		//addSequential(new DropCubeOff());		
	}
}
