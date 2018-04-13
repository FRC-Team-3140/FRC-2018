package main.commands.commandGroups.cubeManipulator;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.tilt.TiltDown;;

public class DropCube extends ImprovedCommandGroup {
	public DropCube() {
		addSequential(new TiltDown());
		addSequential(new WaitCommand(0.3));
		addSequential(new ArmOpen());
	}
}
