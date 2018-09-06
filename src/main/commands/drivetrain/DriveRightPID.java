package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class DriveRightPID extends ImprovedCommand {
	double inches;
	
	public DriveRightPID(double inches) {
		this.inches = inches;
		requires(Robot.dt);
	}
	
	protected void initialize() {
		Robot.dt.initPID();
	}
	
	protected void execute() {
		Robot.dt.drivePID(inches, "right");
	}
	
	protected void end() {
		Robot.dt.endPID();
	}

	@Override
	protected boolean isFinished() {
		return Robot.dt.isRightDriveAtDistance(inches);
	}

}
