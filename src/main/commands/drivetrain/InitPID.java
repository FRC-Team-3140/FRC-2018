package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class InitPID extends ImprovedCommand {
	
	public InitPID() {
		requires(Robot.dt);
	}
	
	protected void initialize() {
		Robot.dt.updateHeadingGains();
		Robot.dt.zeroSensors();
		Robot.dt.resetForPID();
	}
	
	protected void end() {
		Robot.dt.startTimer();
		Robot.dt.okayToPID(true);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
	
}
