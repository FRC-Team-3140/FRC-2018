package main.commands.intake;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.tilt.TiltDown;

// TODO use or remove
public class DeployIntake extends CommandGroup { // NO_UCD (unused code)
    public DeployIntake() {
    	addSequential(new TiltDown());
		addSequential(new WaitCommand(0.5));
    	addSequential(new ArmOpen());
    }
}
