package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MoveFullThrottle extends ImprovedCommand {
	//This command is to test for f-gain
	
	protected void execute() {
		Robot.el.move(-1);
	}

	@Override
	protected boolean isFinished() {
		return false;
	}

}
