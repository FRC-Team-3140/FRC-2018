package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class DriveFromPlayer extends ImprovedCommand {
	private double leftVoltage;
	private double rightVoltage;
	
	public DriveFromPlayer(double leftVoltage, double rightVoltage) {
    	requires(Robot.dt);
    	this.leftVoltage = leftVoltage;
    	this.rightVoltage = rightVoltage;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.driveVoltageTank(leftVoltage, rightVoltage, voltageCompensationVoltageDriveTrainRecordAndPlay);
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
