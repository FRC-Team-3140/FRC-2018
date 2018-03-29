package main.commands.auto;

import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.drivetrain.TimedDrive;
import main.commands.elevator.MoveToBottom;
import main.commands.intake.SpinOff;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.tilt.TiltDown;
import main.commands.pneumatics.tilt.TiltUp;

public class ResetForTeleop extends ImprovedCommandGroup {
	public ResetForTeleop(boolean moveDown) {
		addSequential(new ArmOpen());
		addSequential(new SpinOff());
		if(moveDown) {
			addSequential(new TiltDown());
			addSequential(new TimedDrive(-TIMED_DRIVE_PERCENT, 30/TIMED_DISTANCE_MULTIPLIER));//Drive backwards
			addSequential(new MoveToBottom(1.5));//Let Elevator Down
		}
		addSequential(new TiltUp());
	}
}
