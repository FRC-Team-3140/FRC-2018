package main.java.flagship3140.commands.intake;

import main.java.flagship3140.Robot;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommand;

public class SpinOut extends ImprovedCommand {
	public SpinOut() {
		requires(Robot.in);
	}
	
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.in.spinOut();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}