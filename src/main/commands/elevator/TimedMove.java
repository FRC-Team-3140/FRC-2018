package main.commands.elevator;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

public class TimedMove extends TimedCommand {
	private double throttle;

	public TimedMove(double throttle, double timeout) {
		super(timeout);
		this.throttle = throttle;
		requires(Robot.el);
	}
	
	protected void execute() {
		Robot.el.moveTimed(throttle);
	}


}
