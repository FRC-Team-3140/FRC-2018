package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class TurnToAngle extends ImprovedCommand {
	private double angle;
    public TurnToAngle(double angle) {
    	this.angle = angle;
    	requires(Robot.dt);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.dt.zeroGyro();
    }           

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(angle < 0)
    		Robot.dt.timedTurn(TurnMode.Left, 0.5);//Turning is the same between both robots
    	else
    		Robot.dt.timedTurn(TurnMode.Right, 0.5);//Turning is the same between both robots

    }
    
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return Robot.dt.isDriveTrainAtAngle(angle);    	
    }

    // Called once after isFinished returns true
    protected void end() {
    }
    
    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
