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
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.driveVelocity(throttle, 0.0);
    }
}
