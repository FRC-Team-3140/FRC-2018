package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MoveWithJoystick extends ImprovedCommand {
	public MoveWithJoystick() {
		requires(Robot.el);
	}
	
	protected void execute() {
		Robot.el.moveWithJoystick(Robot.oi.getXbox2().getSmoothedMainY()); //no negate for other
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
