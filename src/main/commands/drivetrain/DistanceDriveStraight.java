package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class DistanceDriveStraight extends ImprovedCommand {
	private double inches;

	public DistanceDriveStraight(double inches) {
		requires(Robot.dt);
		this.inches = inches;
	}
	
	protected void initialize() {
		Robot.dt.initPID();
	}
	
	protected void execute() {
		Robot.dt.driveWithAnglePID(inches, 0);
	}
	
	protected void end() {
		Robot.dt.endPID();
	}
	
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return Robot.dt.isDriveTrainAtDistance(inches);
	}

}
