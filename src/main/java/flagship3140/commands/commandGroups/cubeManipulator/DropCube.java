package main.java.flagship3140.commands.commandGroups.cubeManipulator;

import edu.wpi.first.wpilibj.command.WaitCommand;
import main.java.flagship3140.commands.pneumatics.arm.ArmOpen;
import main.java.flagship3140.commands.pneumatics.tilt.TiltDown;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommandGroup;;

public class DropCube extends ImprovedCommandGroup {
	public DropCube() {
		addSequential(new TiltDown());
		addSequential(new WaitCommand(0.3));
		addSequential(new ArmOpen());
	}
}
