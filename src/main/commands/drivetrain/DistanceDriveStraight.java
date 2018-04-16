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
    	double throttle = -timedDrivePercent * Math.signum(distance);
    	/*
    	if(Robot.dt.isDriveAtDistanceGreaterThan(distance * 0.5))
        	Robot.dt.driveWithGyroCorrection(throttle*0.75, throttle *0.75, 0.0);
		else if(Robot.dt.isDriveAtDistanceGreaterThan(distance * 0.75))
	    	Robot.dt.driveWithGyroCorrection(throttle*0.55, throttle*0.55, 0.0);
		else if(Robot.dt.isDriveAtDistanceGreaterThan(distance * .85))
	    	Robot.dt.driveWithGyroCorrection(throttle*0.40, throttle*0.40, 0.0);
		else if(Robot.dt.isDriveAtDistanceGreaterThan(distance * 0.9))
	    	Robot.dt.driveWithGyroCorrection(throttle*0.27, throttle*0.27, 0.0);
		else
	    	Robot.dt.driveWithGyroCorrection(throttle, throttle, 0.0);
	    */
    	if(Robot.dt.isDriveAtDistanceGreaterThan(distance * 0.9))
	    	Robot.dt.driveWithGyroCorrection(throttle*0.27, throttle*0.27, 0.0);
    	else if(Robot.dt.isDriveAtDistanceGreaterThan(distance * .85))
	    	Robot.dt.driveWithGyroCorrection(throttle*0.40, throttle*0.40, 0.0);
    	else if(Robot.dt.isDriveAtDistanceGreaterThan(distance * 0.75))
	    	Robot.dt.driveWithGyroCorrection(throttle*0.55, throttle*0.55, 0.0);
    	else if(Robot.dt.isDriveAtDistanceGreaterThan(distance * 0.5))
        	Robot.dt.driveWithGyroCorrection(throttle*0.75, throttle *0.75, 0.0);
		else
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
