package main.commands.pneumatics.shift;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class Shift extends ImprovedCommand {
	private DoubleSolenoid.Value v;
	
    public Shift(DoubleSolenoid.Value v) {
    	requires(Robot.pn);
    	this.v = v;
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.pn.shift(v);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }
}
