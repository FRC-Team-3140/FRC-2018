package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;
import main.subsystems.subsystemConstants.DrivetrainConstants;

public class DriveToWaypoint extends ImprovedCommand implements DrivetrainConstants {
	double leftTicks, rightTicks, leftVeloTicks100Ms, rightVeloTicks100Ms, headingTarget;

	public DriveToWaypoint(double leftTicks, double rightTicks, double leftVeloTicks100Ms, double rightVeloTicks100Ms, double headingTarget) {
		requires(Robot.dt);
		this.leftTicks = leftTicks;
		this.rightTicks = rightTicks;
		this.leftVeloTicks100Ms = leftVeloTicks100Ms;
		this.rightVeloTicks100Ms = rightVeloTicks100Ms;
		this.headingTarget = headingTarget;
	}
	
	protected void execute() {
		Robot.dt.driveToWaypoint(leftTicks, rightTicks, leftVeloTicks100Ms, rightVeloTicks100Ms, headingTarget);
	}

	@Override
	protected boolean isFinished() {
		boolean left = Math.abs(leftTicks - Robot.dt.getLeftEncoderTicksTravelled()) < ALLOWABLE_ERR;
		boolean right = Math.abs(rightTicks - Robot.dt.getRightEncoderDistanceTravelled()) < ALLOWABLE_ERR;
		return left && right;
	}

}
