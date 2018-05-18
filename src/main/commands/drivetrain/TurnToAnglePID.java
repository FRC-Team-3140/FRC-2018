package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class TurnToAnglePID extends ImprovedCommand {
	private double angle;
	
	public TurnToAnglePID(double angle) {
		this.angle = angle;
	}
	
	protected void initialize() {
		Robot.dt.turnToAnglePID(angle);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
