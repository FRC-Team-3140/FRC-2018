package main.java.flagship3140.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import main.java.flagship3140.commands.drivetrain.DistanceDriveStraight;
import main.java.flagship3140.commands.drivetrain.TurnToAngle;
import main.java.flagship3140.commands.elevator.MoveToBottom;
import main.java.flagship3140.commands.elevator.MoveToTop;
import main.java.flagship3140.commands.pneumatics.arm.ArmClose;
import main.java.flagship3140.commands.pneumatics.arm.ArmOpen;
import main.java.flagship3140.commands.pneumatics.tilt.TiltDown;
import main.java.flagship3140.commands.pneumatics.tilt.TiltUp;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommandGroup;

public class AltLeftToLeftScale extends ImprovedCommandGroup {
	public AltLeftToLeftScale() {
		addSequential(new DistanceDriveStraight((260-robotLength/2)+10));
		addSequential(new TurnToAngle(35));
		addSequential(new WaitCommand(0.25));
		addSequential(new DistanceDriveStraight(14));
		addSequential(new MoveToTop(3));
		addSequential(new TiltDown());
		addSequential(new DistanceDriveStraight(22));
		addSequential(new WaitCommand(0.3));
		addSequential(new TiltDown());
		addSequential(new ArmOpen());
		addSequential(new WaitCommand(0.5));
		addSequential(new TiltUp());
		addSequential(new ArmClose());
		addSequential(new WaitCommand(0.3));
		addSequential(new DistanceDriveStraight(-4));
		addSequential(new MoveToBottom(3));
	}
}
