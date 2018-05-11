package main.java.flagship3140.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.java.flagship3140.Constants;
import main.java.flagship3140.Robot;

public class TimedTurn extends TimedCommand implements Constants {
	private double throttle;
	private TurnMode mode;

	public TimedTurn(TurnMode mode, double throttle, double timeout) {
		super(timeout);
		this.throttle = throttle;
		this.mode = mode;
		requires(Robot.dt);
	}
	
	protected void execute() {
		Robot.dt.timedTurn(mode, throttle);//Turning is the same between both robots
	}
}
