package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MovePID extends ImprovedCommand {
	
	private double inches;
	
	public MovePID(double inches) {
		this.inches = inches;
		requires(Robot.el);
	}
	
	protected void execute() {
		Robot.el.movePID(inches);
	}

	@Override
	protected boolean isFinished() {
		return Robot.el.isIntakeAtPos(inches);
	}
	
	protected void end() {
	}
	

}
