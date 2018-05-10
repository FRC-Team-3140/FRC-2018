package main.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.drivetrain.AltDistanceDriveStraight;
import main.commands.drivetrain.DistanceDriveStraight;
import main.commands.drivetrain.DriveLeftPID;
import main.commands.drivetrain.TurnToAngleGyro;
import main.commands.elevator.MoveToSwitch;
import main.commands.intake.SpinOutTime;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.tilt.TiltDown;
import main.commands.pneumatics.tilt.TiltUp;

public class AltCenterToLeftSwitch extends ImprovedCommandGroup {
	public AltCenterToLeftSwitch() {
		addSequential(new DistanceDriveStraight(30.375)); //(Break away from wall so there is no resistance on the first turn)
		addSequential(new TurnToAngleGyro(-40, 2));
		addSequential(new AltDistanceDriveStraight(85, 2));
		addSequential(new DriveLeftPID(30, 2));
		addSequential(new SpinOutTime(5));
		
		addSequential(new DistanceDriveStraight(-60)); // backs from switch
		addSequential(new ArmOpen());
		addSequential(new TiltDown());
		addSequential(new WaitCommand(0.3));
		addSequential(new TurnToAngleGyro(45, 2));
		addSequential(new AltDistanceDriveStraight(30, 2));
		addSequential(new WaitCommand(0.1));
		addSequential(new ArmClose());
		addSequential(new WaitCommand(0.25));
		addSequential(new TiltUp());
		
		addSequential(new DistanceDriveStraight(-35));
		addSequential(new TurnToAngleGyro(-65, 2));
		addParallel(new MoveToSwitch(1.5));
		addSequential(new DistanceDriveStraight(65));
		addSequential(new DriveLeftPID(20, 2));
		addSequential(new SpinOutTime(3));
		
	}
}
