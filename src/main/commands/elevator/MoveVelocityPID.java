package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MoveVelocityPID extends ImprovedCommand {
	double velocity;
	
	public MoveVelocityPID(double velocity) {
		this.velocity = velocity;
	}
	
	protected void initialize() {
		Robot.el.moveVelocityPID(velocity);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
	
}
