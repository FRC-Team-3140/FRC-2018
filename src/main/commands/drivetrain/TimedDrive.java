package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Constants;
import main.Robot;


public class TimedDrive extends TimedCommand implements Constants {
	private double throttle;
	
    public TimedDrive(double throttle, double time) {
    	super(time);
    	this.throttle = throttle;
    	requires(Robot.dt);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }           

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(isCompetitionRobot)
    		Robot.dt.driveVelocity(throttle, 0.0);
    	else
    		Robot.dt.driveVelocity(-throttle, 0.0);
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
