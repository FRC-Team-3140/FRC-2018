package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MovePIDParallel extends ImprovedCommand {
	private double inches;
	
	public MovePIDParallel(double inches) {
		this.inches = inches;
		requires(Robot.el);	
	}
	
	protected void initialize() {
		Robot.el.movePID(inches);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {
		//Robot.el.endElevatorPID();
	}
	
	protected void interrupted() {
		end();
	}

}
