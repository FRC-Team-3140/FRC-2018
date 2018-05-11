package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class DistanceDriveStraight extends ImprovedCommand {
	private double inches;

	public DistanceDriveStraight(double inches) {
		requires(Robot.dt);
		this.inches = inches;
	}
	
	protected void execute() {
		Robot.dt.driveWithAnglePID(inches, 0);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
