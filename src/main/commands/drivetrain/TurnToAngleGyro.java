package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class TurnToAngleGyro extends ImprovedCommand {
	private double angle;
	
	public TurnToAngleGyro(double angle) {
		this.angle = angle;
	}
	
	protected void initialize() {
		Robot.dt.turnToAngleGyro(angle);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
