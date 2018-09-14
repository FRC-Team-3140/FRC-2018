package main.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.commandGroups.cubeManipulator.DropCube;
import main.commands.commandGroups.cubeManipulator.DropCubeOff;
import main.commands.drivetrain.DistanceDriveStraight;
import main.commands.drivetrain.DriveRightPID;
import main.commands.drivetrain.TimedDrive;
import main.commands.drivetrain.TurnToAngle;
import main.commands.drivetrain.TurnToAngleGyro;
import main.commands.elevator.MovePID;
import main.commands.elevator.MoveToBottom;
import main.commands.elevator.MoveToSwitch;
import main.commands.intake.SpinIn;
import main.commands.intake.SpinOff;
import main.commands.intake.SpinOut;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.tilt.TiltDown;
import main.commands.pneumatics.tilt.TiltUp;

public class AltCenterToRightSwitch extends ImprovedCommandGroup {
	public AltCenterToRightSwitch() {
		addSequential(new DistanceDriveStraight(30.375)); //(Break away from wall so there is no resistance on the first turn)
		addSequential(new TurnToAngleGyro(40, 2));
		addSequential(new DistanceDriveStraight(85));
		addSequential(new DriveRightPID(30,2));
		addSequential(new SpinOut());
		/*addSequential(new MovePID(switchHeight, 3));
		addSequential(new DropCube());
		addSequential(new WaitCommand(0.5));
		addSequential(new DropCubeOff());
		addSequential(new DistanceDriveStraight(-60));
		addParallel(new MoveToBottom(1.5));
		/*addSequential(new ArmOpen());
		addSequential(new TiltDown());
		addSequential(new WaitCommand(0.3));
		addSequential(new TurnToAngleGyro(-50, 2));
		addSequential(new SpinIn());
		addSequential(new WaitCommand(0.2));
		addSequential(new DistanceDriveStraight(42));
		addSequential(new WaitCommand(0.1));
		addSequential(new ArmClose());
		addSequential(new WaitCommand(0.25));
		addSequential(new TiltUp());
		addSequential(new SpinOff());
		addSequential(new DistanceDriveStraight(-35));
		addSequential(new TurnToAngleGyro(65, 2));
		addParallel(new MoveToSwitch(1.5));
		addSequential(new DistanceDriveStraight(80));
		addSequential(new DropCube());
		//addSequential(new WaitCommand(0.25));
		//addSequential(new DropCubeOff());
		//addSequential(new WaitCommand(0.2));*/
	}
}
