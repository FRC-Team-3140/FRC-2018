package main.commands.intake;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class ShootWithJoystick extends ImprovedCommand {
	public ShootWithJoystick() {
		requires(Robot.in);
	}
	
	@Override
	protected void execute() {
		Robot.in.moveWithJoystick(-Robot.oi.getXbox2().getSmoothedAltY()); //no negate for other
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
