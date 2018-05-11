package main.java.flagship3140.commands.drivetrain;

import main.java.flagship3140.OI;
import main.java.flagship3140.Robot;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommand;

public class Drive extends ImprovedCommand {
	public Drive() {
    	requires(Robot.dt);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.driveVelocity(OI.getXbox().getSmoothedMainY(), OI.getXbox().getSmoothedAltX());
    }
    
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