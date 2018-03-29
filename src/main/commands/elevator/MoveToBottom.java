package main.commands.elevator;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

public class MoveToBottom extends TimedCommand {
	public MoveToBottom(double timeout) {//1.5 recommended timeout
		super(timeout);//Timeout forced to a maximum of 1.5, this is for the emergency case that a limit switch breaks
		//So that the elevator will not continue to drive up.
		requires(Robot.el);
	}

    @Override
	protected void execute() {
    	Robot.el.move(1);
    }
    
    @Override
	protected boolean isFinished() {
    	return Robot.el.isArmAtBottom();
    }
}