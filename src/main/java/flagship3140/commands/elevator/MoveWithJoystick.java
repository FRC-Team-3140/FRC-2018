package main.java.flagship3140.commands.elevator;

import main.java.flagship3140.OI;
import main.java.flagship3140.Robot;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommand;

public class MoveWithJoystick extends ImprovedCommand {
	public MoveWithJoystick() {
		requires(Robot.el);
	}
	
	protected void execute() {
		Robot.el.moveWithJoystick(OI.getXbox2().getSmoothedMainY()); //no negate for other
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
