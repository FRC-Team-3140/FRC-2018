package main.java.flagship3140.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import main.java.flagship3140.commands.drivetrain.DistanceDriveStraight;
import main.java.flagship3140.commands.drivetrain.TurnToAngle;
import main.java.flagship3140.commands.elevator.MoveToBottom;
import main.java.flagship3140.commands.elevator.MoveToSwitch;
import main.java.flagship3140.commands.pneumatics.arm.ArmClose;
import main.java.flagship3140.commands.pneumatics.arm.ArmOpen;
import main.java.flagship3140.commands.pneumatics.tilt.TiltDown;
import main.java.flagship3140.commands.pneumatics.tilt.TiltUp;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommandGroup;

public class AltRightToLeftSwitch extends ImprovedCommandGroup {
	public AltRightToLeftSwitch() {
		// distance from start to scale  minus robot length
		addSequential(new DistanceDriveStraight(219));
		addSequential(new TurnToAngle(-90));
		addSequential(new WaitCommand(0.25));
		addSequential(new DistanceDriveStraight(175));
		addSequential(new MoveToSwitch(1.5));
		addSequential(new TurnToAngle(-90), 2);
		addSequential(new DistanceDriveStraight(18), 2);
		//addSequential(new TiltDown());
		addSequential(new WaitCommand(0.3));
		addSequential(new TiltDown());
		addSequential(new ArmOpen());
		addSequential(new WaitCommand(0.5));
		addSequential(new TiltUp());
		addSequential(new ArmClose());
		addSequential(new WaitCommand(0.3));
		addSequential(new DistanceDriveStraight(-25));
		addSequential(new MoveToBottom(3));
		//addSequential(new DropCube());
		//addSequential(new WaitCommand(1));
		//addSequential(new DropCubeOff());	
	}
}