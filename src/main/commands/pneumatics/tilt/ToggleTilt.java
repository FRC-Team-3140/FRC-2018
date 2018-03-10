package main.commands.pneumatics.tilt;

import edu.wpi.first.wpilibj.command.CommandGroup;
import main.Robot;

public class ToggleTilt extends CommandGroup {
	
	public ToggleTilt() {
		if(Robot.pn.isTiltDown()) addSequential(new TiltUp());
		else addSequential(new TiltDown());
	}

}
