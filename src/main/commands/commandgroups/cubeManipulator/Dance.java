package main.commands.commandgroups.cubeManipulator;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.intake.SpinIn;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.tilt.TiltDown;
import main.commands.pneumatics.tilt.TiltUp;;

public class Dance extends ImprovedCommandGroup {
	public Dance() {
		addSequential(new ArmOpen());
		addSequential(new WaitCommand(.2));
		addSequential(new ArmClose());
		addSequential(new WaitCommand(.2));
		addSequential(new ArmOpen());
		addSequential(new WaitCommand(.2));
		addSequential(new ArmClose());
		addSequential(new WaitCommand(.2));
		addSequential(new TiltDown());
		addSequential(new WaitCommand(.2));
		addSequential(new TiltUp());
		addSequential(new WaitCommand(.2));
		addSequential(new TiltDown());
		addSequential(new WaitCommand(.2));
		addSequential(new TiltUp());
		addSequential(new WaitCommand(.2));
		addSequential(new SpinIn());
	}
}

