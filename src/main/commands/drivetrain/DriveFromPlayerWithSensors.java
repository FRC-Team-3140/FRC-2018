package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class DriveFromPlayerWithSensors extends ImprovedCommand {
	private double leftEncTargetPos;
	private double rightEncTargetPos;
	private double targetHeading;
	
	public DriveFromPlayerWithSensors(double leftEncTargetPos, double rightEncTargetPos, double targetHeading) {
    	requires(Robot.dt);    	
    	this.leftEncTargetPos = leftEncTargetPos;
    	this.rightEncTargetPos = rightEncTargetPos;
    	this.targetHeading = targetHeading;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.driveProfileWithPid(leftEncTargetPos, rightEncTargetPos, targetHeading);
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
