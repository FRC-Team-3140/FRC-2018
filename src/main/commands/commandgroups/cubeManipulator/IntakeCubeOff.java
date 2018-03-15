package main.commands.commandgroups.cubeManipulator;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.intake.SpinOff;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.tilt.TiltUp;

// TODO use this class
public class IntakeCubeOff extends ImprovedCommandGroup { // NO_UCD (unused code)
	public IntakeCubeOff() {
		addSequential(new ArmClose());
		addSequential(new WaitCommand(0.5));
    	addSequential(new TiltUp());
    	addSequential(new WaitCommand(1.5));
    	addSequential(new SpinOff());
    }
}
