package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class EndPID extends ImprovedCommand {
	
	public EndPID() {
		requires(Robot.dt);
	}
	
	protected void initialize() {
		Robot.dt.endPID();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
