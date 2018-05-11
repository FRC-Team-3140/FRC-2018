package main.java.flagship3140.commands.intake;

import main.java.flagship3140.OI;
import main.java.flagship3140.Robot;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommand;

public class ShootWithJoystick extends ImprovedCommand {
	public ShootWithJoystick() {
		requires(Robot.in);
	}
	
	protected void execute() {
		Robot.in.moveWithJoystick(-OI.getXbox2().getSmoothedAltY()); //no negate for other
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
