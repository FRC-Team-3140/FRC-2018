package main.commands.elevator;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

public class TimedLift extends TimedCommand {
	private double throttle;

	public TimedLift(double throttle, double timeout) {
		super(timeout);
		this.throttle = throttle;
		requires(Robot.el);
	}
	
	protected void execute() {
//		Robot.el.move(throttle);
	}
	
	// Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//return Robot.el.isArmAtTop();
    	return true;
    }
}
