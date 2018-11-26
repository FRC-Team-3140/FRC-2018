package main.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.drivetrain.AltDistanceDriveStraight;
import main.commands.drivetrain.DistanceDriveStraight;
import main.commands.drivetrain.TurnToAngleGyro;
import main.commands.elevator.MovePID;
import main.commands.elevator.MovePIDParallel;
import main.commands.elevator.MoveToBottom;
import main.commands.intake.SpinIn;
import main.commands.intake.SpinOff;
import main.commands.intake.SpinOut;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.tilt.TiltDown;
import main.commands.pneumatics.tilt.TiltUp;

public class RightToRightScaleSwitch extends ImprovedCommandGroup {
	
	public RightToRightScaleSwitch() {
		addSequential(new AltDistanceDriveStraight(230, 5));
		addSequential(new TurnToAngleGyro(-27, 2.5));
		addSequential(new MovePIDParallel(elevatorHeight));
		addSequential(new WaitCommand(1.5));
		addSequential(new TiltDown());
		addSequential(new DistanceDriveStraight(65));

		//addSequential(new SpinIn());
		addSequential(new ArmOpen());
		addSequential(new WaitCommand(0.3));
		
//		addSequential(new SpinOff());
//		addSequential(new ArmClose());
		addSequential(new TiltUp());
		addSequential(new ArmClose());

		addSequential(new DistanceDriveStraight(-35));
		
		// first cube done
		addSequential(new MoveToBottom(2.5));
		addSequential(new TurnToAngleGyro(-110, 2));
		addSequential(new TiltDown());		addSequential(new ArmOpen());
		addSequential(new SpinOut()); // FIXME inverted!!
		addSequential(new AltDistanceDriveStraight(40, 1.5));
		addSequential(new WaitCommand(0.5));
		addSequential(new SpinOff());
		addSequential(new ArmClose());
		addSequential(new WaitCommand(0.2));
		
		addSequential(new MovePID(switchHeight, 2));
		addSequential(new AltDistanceDriveStraight(15, 1.5));
//		addSequential(new SpinIn());
		addSequential(new ArmOpen());
		addSequential(new WaitCommand(1));
	}

}
