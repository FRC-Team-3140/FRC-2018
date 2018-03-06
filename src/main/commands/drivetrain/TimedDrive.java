package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

// XXX start from this class
public class TimedDrive extends TimedCommand {
	private double throttle;
	
	/*public TimedDrive(double throttle, double heading, double time) {
    	super(time);
    	this.throttle = throttle;
    	this.heading = heading;
    	requires(Robot.dt);
    }*/
	
    public TimedDrive(double throttle, double time) {
    	super(time);
    	this.throttle = throttle;
    	//this.heading = 0.0;
    	requires(Robot.dt);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }           

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.driveVelocity(throttle, 0.0);
    }
    // Make this return true when this Command no longer needs to run execute()

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
