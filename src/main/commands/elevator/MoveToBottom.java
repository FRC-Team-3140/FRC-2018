package main.commands.elevator;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

public class MoveToBottom extends TimedCommand {
	public MoveToBottom(double timeout) {//1.5 recommended timeout
		super(timeout);//Timeout forced to a maximum of 1.5, this is for the emergency case that a limit switch breaks
		//So that the elevator will not continue to drive up.
		requires(Robot.el);
	}
	
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.el.move(-0.6);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.el.isArmAtBottom();
    	//return false;
    }
}