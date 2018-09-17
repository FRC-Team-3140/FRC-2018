package main.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.drivetrain.AltDistanceDriveStraight;
import main.commands.drivetrain.DistanceDriveStraight;
import main.commands.drivetrain.DrivePIDWithPulse;
import main.commands.drivetrain.TurnToAngle;
import main.commands.drivetrain.TurnToAngleGyro;
import main.commands.elevator.MovePID;
import main.commands.elevator.MoveToBottom;
import main.commands.elevator.MoveToTop;
import main.commands.intake.SpinOff;
import main.commands.intake.SpinOut;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.tilt.TiltDown;
import main.commands.pneumatics.tilt.TiltUp;

public class AltRightToRightScale extends ImprovedCommandGroup {
	public AltRightToRightScale() {
		addSequential(new AltDistanceDriveStraight(230, 5));
		addSequential(new TurnToAngleGyro(-32, 3));
		addSequential(new WaitCommand(0.3));
		addSequential(new DistanceDriveStraight(12));
		addSequential(new MovePID(elevatorHeight, 4));
		addSequential(new DistanceDriveStraight(43));
		addSequential(new TiltDown());
		addSequential(new WaitCommand(0.1));
		addSequential(new ArmOpen());
		addSequential(new WaitCommand(0.3));
		addSequential(new TiltUp());
		addSequential(new WaitCommand(0.3));
		addSequential(new ArmClose());
		addSequential(new WaitCommand(0.3));
		addSequential(new DistanceDriveStraight(-30));
		addSequential(new MoveToBottom(5));
		// first cube done
		addSequential(new TurnToAngleGyro(-107, 2));
		addSequential(new TiltDown());
		addSequential(new ArmOpen());
		addSequential(new SpinOut()); // FIXME inverted!!
		addSequential(new AltDistanceDriveStraight(35, 2));
		addSequential(new WaitCommand(1));
		addSequential(new SpinOff());
		addSequential(new ArmClose());
	}
}
