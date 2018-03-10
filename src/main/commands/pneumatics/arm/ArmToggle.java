package main.commands.pneumatics.arm;

import edu.wpi.first.wpilibj.command.CommandGroup;
import main.Robot;

public class ArmToggle extends CommandGroup {
	public ArmToggle() {
		if(Robot.pn.isArmOpen()) addSequential(new ArmClose());
		else addSequential(new ArmOpen());
	}

}
