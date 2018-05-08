package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MovePID extends ImprovedCommand {
	
	private double inches;
	
	public MovePID(double inches) {
		this.inches = inches;
		requires(Robot.el);
	}
	
	protected void initialize() { //or use execute (?)
		Robot.el.movePID(inches);
	}

	@Override
	protected boolean isFinished() {
		//return Robot.el.isIntakeAtPos(inches);
		return true;
	}
	
	protected void end() {
	}
	

}
