package main.commands.intake;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.tilt.TiltUp;

/**
 *
 */
public class DeployIntakeOff extends CommandGroup {

    public DeployIntakeOff() {
		addSequential(new ArmClose());
		addSequential(new WaitCommand(0.5));
    	addSequential(new TiltUp());
    }
}
