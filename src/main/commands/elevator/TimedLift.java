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
		Robot.el.moveTimed(throttle);
	}


}
