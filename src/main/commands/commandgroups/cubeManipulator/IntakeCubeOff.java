package main.commands.commandgroups.cubeManipulator;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.intake.SpinOff;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.tilt.TiltUp;

public class IntakeCubeOff extends ImprovedCommandGroup {
	public IntakeCubeOff() {
		addSequential(new ArmClose());
		addSequential(new WaitCommand(0.3));
    	addSequential(new TiltUp());
    	addSequential(new WaitCommand(2.5));
    	addSequential(new SpinOff());
    }
}
