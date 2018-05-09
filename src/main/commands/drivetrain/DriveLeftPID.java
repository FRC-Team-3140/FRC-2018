package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class DriveLeftPID extends ImprovedCommand {
	double inches;
	
	public DriveLeftPID(double inches) {
		this.inches = inches;
		requires(Robot.dt);
	}
	
	protected void initialize() {
		Robot.dt.drivePID(inches, "left");
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
