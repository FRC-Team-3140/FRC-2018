package main.commands.controllerCommands;

import controllers.Play;
import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class StartPlay extends ImprovedCommand {
	public StartPlay() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.dt.enableVoltageComp(true);
    	Robot.dt.zeroSensors();
    	Robot.dt.zeroPIDVariables();
    	Robot.el.enableVoltageComp(true);
    	Robot.el.zeroSensors();
    	Robot.el.zeroPIDVariables();
    	Robot.lg.resetForRead();
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
    	Play.reset();
    	Robot.el.enableVoltageComp(false);
    	Robot.el.zeroSensors();
    	Robot.el.zeroPIDVariables();
    	Robot.dt.zeroPIDVariables();
    	Robot.dt.zeroSensors();
    	Robot.dt.enableVoltageComp(false);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
