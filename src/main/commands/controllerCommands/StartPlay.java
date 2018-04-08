package main.commands.controllerCommands;

import controllers.Play;
import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class StartPlay extends ImprovedCommand {
	public StartPlay() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.dt.zeroSensors();
    	Robot.dt.zeroPIDVariables();
    	Robot.el.zeroSensors();
    	Robot.el.zeroPIDVariables();
    	Robot.lg.resetForRead();
    	if(isSensorPlayRecordAuto)
    		Robot.setRobotOperationMode(RobotOperationMode.SensorPlaying);
    	else
    		Robot.setRobotOperationMode(RobotOperationMode.Playing);
    	Robot.oi.setInternalControl(true);
    	Play.okToPlay(true); 
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Play.isFinished();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Play.okToPlay(false);
    	Robot.oi.setInternalControl(false);
    	Robot.setRobotOperationMode(RobotOperationMode.Normal);
    	Play.reset();
    	Robot.el.zeroPIDVariables();
    	Robot.dt.zeroPIDVariables();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
