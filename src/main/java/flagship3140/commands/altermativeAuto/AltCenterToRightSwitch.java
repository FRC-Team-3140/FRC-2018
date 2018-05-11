package main.java.flagship3140.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import main.java.flagship3140.commands.commandGroups.cubeManipulator.DropCube;
import main.java.flagship3140.commands.commandGroups.cubeManipulator.DropCubeOff;
import main.java.flagship3140.commands.drivetrain.DistanceDriveStraight;
import main.java.flagship3140.commands.drivetrain.TimedDrive;
import main.java.flagship3140.commands.drivetrain.TurnToAngle;
import main.java.flagship3140.commands.elevator.MoveToBottom;
import main.java.flagship3140.commands.elevator.MoveToSwitch;
import main.java.flagship3140.commands.intake.SpinOff;
import main.java.flagship3140.commands.intake.SpinOut;
import main.java.flagship3140.commands.pneumatics.arm.ArmClose;
import main.java.flagship3140.commands.pneumatics.arm.ArmOpen;
import main.java.flagship3140.commands.pneumatics.tilt.TiltDown;
import main.java.flagship3140.commands.pneumatics.tilt.TiltUp;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommandGroup;

public class AltCenterToRightSwitch extends ImprovedCommandGroup {
	public AltCenterToRightSwitch() {
		addSequential(new DistanceDriveStraight(30.375)); //(Break away from wall so there is no resistance on the first turn)
		/*addSequential(new TurnToAngle(90));
		addSequential(new DistanceDriveStraight(60.5));
		addSequential(new TurnToAngle(-90));
		addSequential(new WaitCommand(0.25));*/
		addSequential(new TurnToAngle(30));
		addParallel(new MoveToSwitch(1.5));
		addSequential(new DistanceDriveStraight(103));
		//addSequential(new DistanceDriveStraight(90.375));
		addSequential(new DropCube());
		addSequential(new WaitCommand(0.5));
		addSequential(new DropCubeOff());
		addSequential(new DistanceDriveStraight(-60));
		addParallel(new MoveToBottom(1.5));
		addSequential(new ArmOpen());
		addSequential(new TiltDown());
		addSequential(new SpinOut());
		addSequential(new TurnToAngle(-50));
		//addSequential(new DistanceDriveStraight(38));
		//addSequential(new TurnToAngle(90));
		addSequential(new TimedDrive(-1, 0.75));
		addSequential(new WaitCommand(0.1));
		addSequential(new ArmClose());
		addSequential(new WaitCommand(0.25));
		addSequential(new TiltUp());
		addSequential(new DistanceDriveStraight(-35));
		addSequential(new TurnToAngle(65));
		addSequential(new SpinOff());
		addParallel(new MoveToSwitch(1.5));
		addSequential(new DistanceDriveStraight(80), 1.5);
		addSequential(new DropCube());
		//addSequential(new WaitCommand(0.25));
		//addSequential(new DropCubeOff());
		//addSequential(new WaitCommand(0.2));
	}
}
