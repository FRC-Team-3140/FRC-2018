package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class DriveEachSidePID extends ImprovedCommand {
	private double leftInches, rightInches;
	
	public DriveEachSidePID(double left, double right) {
		leftInches = left;
		rightInches = right;
	}
	
	protected void initialize() {
		Robot.dt.drivePID(leftInches, rightInches);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
