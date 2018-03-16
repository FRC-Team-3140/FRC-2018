package main.commands.commandgroups.cubeManipulator;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.intake.SpinIn;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.tilt.TiltDown;
import main.commands.pneumatics.tilt.TiltUp;;

// TODO use this class
public class Dance extends ImprovedCommandGroup { // NO_UCD
	private static double WAIT_TIME=0.2;
	
	public Dance() {
		addSequential(new ArmOpen());
		addSequential(new WaitCommand(WAIT_TIME));
		addSequential(new ArmClose());
		addSequential(new WaitCommand(WAIT_TIME));
		addSequential(new ArmOpen());
		addSequential(new WaitCommand(WAIT_TIME));
		addSequential(new ArmClose());
		addSequential(new WaitCommand(WAIT_TIME));
		addSequential(new TiltDown());
		addSequential(new WaitCommand(WAIT_TIME));
		addSequential(new TiltUp());
		addSequential(new WaitCommand(WAIT_TIME));
		addSequential(new TiltDown());
		addSequential(new WaitCommand(WAIT_TIME));
		addSequential(new TiltUp());
		addSequential(new WaitCommand(WAIT_TIME));
		addSequential(new SpinIn());
	}
}

