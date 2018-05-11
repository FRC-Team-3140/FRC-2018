package main.java.flagship3140.commands.auto;

import main.java.flagship3140.commands.drivetrain.DistanceDriveStraight;
import main.java.flagship3140.commands.elevator.MoveToBottom;
import main.java.flagship3140.commands.intake.SpinOff;
import main.java.flagship3140.commands.pneumatics.arm.ArmOpen;
import main.java.flagship3140.commands.pneumatics.tilt.TiltUp;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommandGroup;

public class ResetForTeleop extends ImprovedCommandGroup {
	public ResetForTeleop(boolean moveDown) {
		addSequential(new ArmOpen());
		addSequential(new SpinOff());
		if(moveDown) {
			//addSequential(new TiltDown());
			addSequential(new DistanceDriveStraight(-30));//Drive backwards
			addSequential(new MoveToBottom(1.5));//Let Elevator Down
		}
		addSequential(new TiltUp());
	}
}
