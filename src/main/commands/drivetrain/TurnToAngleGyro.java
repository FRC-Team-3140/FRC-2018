package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class TurnToAngleGyro extends ImprovedCommand {
	private double angle;
	
	public TurnToAngleGyro(double angle) {
		requires(Robot.dt);
		this.angle = angle;
	}
	
	protected void initialize() {
		Robot.dt.initPID();
	}
	
	protected void execute() {
		Robot.dt.turnToAngleGyro(angle);
	}
	
	protected void end() {
		Robot.dt.endPID();
	}
	
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return Robot.dt.isDriveTrainAtAngle(angle);
	}

}
