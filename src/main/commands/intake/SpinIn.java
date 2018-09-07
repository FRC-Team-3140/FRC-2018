package main.commands.intake;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class SpinIn extends ImprovedCommand {
	public SpinIn() {
		requires(Robot.in);
	}
	
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.in.spinIn();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }
}