package main.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.commandGroups.cubeManipulator.DropCube;
import main.commands.commandGroups.cubeManipulator.DropCubeOff;
import main.commands.drivetrain.AltDistanceDriveStraight;
import main.commands.drivetrain.DistanceDriveStraight;
import main.commands.drivetrain.DriveLeftPID;
import main.commands.drivetrain.TurnToAngleGyro;
import main.commands.elevator.MovePID;
import main.commands.elevator.MoveToBottom;
import main.commands.intake.SpinOut;

public class AltCenterToLeftSwitch extends ImprovedCommandGroup {
	public AltCenterToLeftSwitch() {
		addSequential(new DistanceDriveStraight(30.375)); //(Break away from wall so there is no resistance on the first turn)
		addSequential(new TurnToAngleGyro(-40, 2));
		addSequential(new AltDistanceDriveStraight(85, 4));
		addSequential(new DriveLeftPID(30, 2));
		addSequential(new SpinOut());
		/*addSequential(new MovePID(switchHeight, 3));
		addSequential(new DropCube()); // drops the first cube
		addSequential(new WaitCommand(0.5));
		addSequential(new DropCubeOff());
		
		addSequential(new DistanceDriveStraight(-60)); // backs from switch
		addParallel(new MoveToBottom(1.5));*/
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
