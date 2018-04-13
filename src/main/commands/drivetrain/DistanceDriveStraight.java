package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class DistanceDriveStraight extends ImprovedCommand {
	private double distance;

    public DistanceDriveStraight(double distance) {
    	this.distance = distance;
    	requires(Robot.dt);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.dt.zeroSensors();
    }           

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double throttle = timedDrivePercent * Math.signum(distance);
    	Robot.dt.driveWithGyroCorrection(throttle, throttle, 0.0);
    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return Robot.dt.isDriveTrainAtDistance(distance);    	
    }

    // Called once after isFinished returns true
    protected void end() {
    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
