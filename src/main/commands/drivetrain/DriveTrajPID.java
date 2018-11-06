package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class DriveTrajPID extends ImprovedCommand{
	double leftTicks, rightTicks, leftVeloTicks100Ms, rightVeloTicks100Ms, headingTarget;

	public DriveTrajPID(double leftTicks, double rightTicks, double leftVeloTicks100Ms, double rightVeloTicks100Ms, double headingTarget) {
		requires(Robot.dt);
		this.leftTicks = leftTicks;
		this.rightTicks = rightTicks;
		this.leftVeloTicks100Ms = leftVeloTicks100Ms;
		this.rightVeloTicks100Ms = rightVeloTicks100Ms;
		this.headingTarget = headingTarget;
	}
	
	protected void execute() {
		Robot.dt.driveFromPlayPID(leftTicks, rightTicks, leftVeloTicks100Ms, rightVeloTicks100Ms, headingTarget);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
