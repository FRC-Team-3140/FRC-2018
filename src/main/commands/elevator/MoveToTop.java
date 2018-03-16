package main.commands.elevator;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

// TODO implement
public class MoveToTop extends TimedCommand {
	public MoveToTop(double timeout) {//5 recommended timeout
		super(timeout);//Timeout forced to a maximum of 5, this is for the emergency case that a limit switch breaks
		//So that the elevator will not continue to drive up.
		requires(Robot.el);
	}
	
    @Override
    protected boolean isFinished() {
    	return true;
    }
}
