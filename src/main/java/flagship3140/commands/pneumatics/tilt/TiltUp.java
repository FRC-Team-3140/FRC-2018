package main.java.flagship3140.commands.pneumatics.tilt;

import edu.wpi.first.wpilibj.command.WaitCommand;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommandGroup;

public class TiltUp extends ImprovedCommandGroup {
    // Called just before this Command runs the first time
    public TiltUp() {
    	addSequential(new Tilt(EXT));
    	addSequential(new WaitCommand(0.1));
    	addSequential(new Tilt(OFF));
    }
}