package main.java.flagship3140.commands.elevator;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.java.flagship3140.Robot;

public class TimedLift extends TimedCommand {
	private double throttle;

	public TimedLift(double throttle, double timeout) {
		super(timeout);
		this.throttle = throttle;
		requires(Robot.el);
	}
	
	protected void execute() {
		Robot.el.move(throttle);
	}
	
	// Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//        return Robot.el.isArmAtTop();
    	return false;
    }
}
