package main.commands.elevator;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

public class MoveToTop extends TimedCommand {
	public MoveToTop(double timeout) {//3 recommended timeout
		super(timeout);//Timeout forced to a maximum of 3, this is for the emergency case that a limit switch breaks
		//So that the elevator will not continue to drive up.
		requires(Robot.el);
	}
	
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.el.move(0.4);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.el.isArmAtTop();
    	//return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.el.move(0.0);
    }
}
