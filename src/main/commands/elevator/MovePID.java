package main.commands.elevator;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

// FIXME fix this
public class MovePID extends TimedCommand {
	private double inches;
	
	public MovePID(double inches, double timeout) {
		super(timeout);
		this.inches = inches;
		requires(Robot.el);	
	}
	
	protected void execute() {
		Robot.el.movePID(inches);
	}

	@Override
	protected boolean isFinished() {
		return Robot.el.isIntakeAtPos(inches); //|| isTimedOut();
	}
	
	protected void end() {
		//Robot.el.endElevatorPID();
	}
	
	protected void interrupted() {
		end();
	}

}
