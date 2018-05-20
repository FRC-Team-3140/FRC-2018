package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class TurnToAngleInit extends ImprovedCommand {
	private double angle;
	
	public TurnToAngleInit(double angle) {
		this.angle = angle;
	}
	
	protected void initialize() {
		Robot.dt.turnToAngleInit(angle);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
