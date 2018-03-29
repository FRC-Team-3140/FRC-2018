package main.commands.elevator;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

public class MoveToTop extends TimedCommand {
	public MoveToTop(double timeout) {//3 recommended timeout
		super(timeout);//Timeout forced to a maximum of 3, this is for the emergency case that a limit switch breaks
		//So that the elevator will not continue to drive up.
		requires(Robot.el);
	}

    @Override
	protected void execute() {
    	Robot.el.move(-1);
    }

    @Override
	protected boolean isFinished() {
        return Robot.el.isArmAtTop();
    }
}
