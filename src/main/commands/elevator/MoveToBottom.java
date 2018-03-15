package main.commands.elevator;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

// TODO implement
public class MoveToBottom extends TimedCommand {
	public MoveToBottom(double timeout) {//3 recommended timeout
		super(timeout);//Timeout forced to a maximum of 5, this is for the emergency case that a limit switch breaks
		//So that the elevator will not continue to drive up.
		requires(Robot.el);
	}
	
	@Override
    protected boolean isFinished() {
    	return true;
    }
}