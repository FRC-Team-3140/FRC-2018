package main.java.flagship3140.commands.pneumatics.shift;

import edu.wpi.first.wpilibj.command.WaitCommand;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommandGroup;

public class ShiftUp extends ImprovedCommandGroup {
    public ShiftUp() {
    	addSequential(new Shift(RET));
    	addSequential(new WaitCommand(0.1));
    	addSequential(new Shift(OFF));
    }
}
