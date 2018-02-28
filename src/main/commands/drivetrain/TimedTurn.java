package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Constants.TurnMode;
import main.Robot;

public class TimedTurn extends TimedCommand {
	private double throttle;
	private TurnMode mode;

	public TimedTurn(TurnMode mode, double throttle, double timeout) {
		super(timeout);
		this.throttle = throttle;
		this.mode = mode;
		requires(Robot.dt);
	}
	
	protected void execute() {
		Robot.dt.timedTurn(mode, -throttle);
	}

}
