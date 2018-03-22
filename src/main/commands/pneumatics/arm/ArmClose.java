package main.commands.pneumatics.arm;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;

public class ArmClose extends ImprovedCommandGroup {
    // Called just before this Command runs the first time
    public ArmClose() {
    	addSequential(new Arm(EXT));
    	addSequential(new WaitCommand(0.1));
    	addSequential(new Arm(OFF));
    }
}
