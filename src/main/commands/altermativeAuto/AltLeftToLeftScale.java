package main.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.drivetrain.DistanceDriveStraight;
import main.commands.drivetrain.TurnToAngle;
import main.commands.drivetrain.TurnToAngleGyro;
import main.commands.elevator.MovePID;
import main.commands.elevator.MoveToBottom;
import main.commands.elevator.MoveToTop;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.tilt.TiltDown;
import main.commands.pneumatics.tilt.TiltUp;

public class AltLeftToLeftScale extends ImprovedCommandGroup {
	public AltLeftToLeftScale() {
		addSequential(new DistanceDriveStraight(230));
		addSequential(new TurnToAngleGyro(-30));
		addSequential(new WaitCommand(0.3));
		addSequential(new DistanceDriveStraight(36));
		addParallel(new MovePID(elevatorHeight));
		addSequential(new DistanceDriveStraight(6));
		addSequential(new TiltDown());
		addSequential(new WaitCommand(0.3));
		addSequential(new TurnToAngleGyro(10));
		addSequential(new WaitCommand(0.3));
		addSequential(new ArmOpen());
	}
}
