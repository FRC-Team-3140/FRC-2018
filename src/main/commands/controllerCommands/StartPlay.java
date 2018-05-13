package main.commands.controllerCommands;

import controllers.Play;
import edu.wpi.first.wpilibj.command.Command;
import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;
import main.commands.drivetrain.EndPID;
import main.commands.drivetrain.InitPID;

public class StartPlay extends ImprovedCommand {
	public StartPlay() {
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lg.resetForRead();
    	Robot.oi.setInternalControl(true);
    	Command init = new InitPID();
    	init.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Command play = new Play();
    	play.start();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Play.finished();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Command end = new EndPID();
    	end.start();
    	Robot.oi.setInternalControl(false);
    	Play.reset();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
