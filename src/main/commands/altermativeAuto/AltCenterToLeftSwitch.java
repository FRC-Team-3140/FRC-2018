package main.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.commandGroups.cubeManipulator.DropCube;
import main.commands.commandGroups.cubeManipulator.DropCubeOff;
import main.commands.drivetrain.DistanceDriveStraight;
import main.commands.drivetrain.DriveLeftPID;
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

public class AltCenterToLeftSwitch extends ImprovedCommandGroup {
	public AltCenterToLeftSwitch() {
		addSequential(new DistanceDriveStraight(30.375)); //(Break away from wall so there is no resistance on the first turn)
		addSequential(new TurnToAngleGyro(-30, 2));
		addParallel(new MovePID(switchHeight, 2));
		addSequential(new DistanceDriveStraight(90));//Might be 90 or less needs testing
		addSequential(new DriveLeftPID(20));
		addSequential(new DropCube()); // actually use this one tho
		//addSequential(new SpinOut()); // temporary
		addSequential(new WaitCommand(1));
		addSequential(new DropCubeOff());
		addSequential(new DistanceDriveStraight(-60));
		//addParallel(new MoveToBottom(1.5));
		/*addSequential(new ArmOpen());
		addSequential(new TiltDown());
		addSequential(new SpinIn());
		addSequential(new TurnToAngleGyro(50, 2));
		addSequential(new WaitCommand(0.1));
		addSequential(new ArmClose());
		addSequential(new WaitCommand(0.25));
		addSequential(new TiltUp());
		addSequential(new DistanceDriveStraight(-35));
		addSequential(new TurnToAngleGyro(-75, 2.5));
		addSequential(new SpinOff());
		//addParallel(new MoveToSwitch(1.5));
		addSequential(new DistanceDriveStraight(80));
		addSequential(new DropCube());*/
		//addSequential(new WaitCommand(0.25));
		//addSequential(new DropCubeOff());
		//addSequential(new WaitCommand(0.2));
		
	}
}
