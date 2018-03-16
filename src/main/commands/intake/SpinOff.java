package main.commands.intake;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class SpinOff extends ImprovedCommand {
	public SpinOff() {
		requires(Robot.in);
	}

	@Override
	protected void execute() {
		Robot.in.spinOff();
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
