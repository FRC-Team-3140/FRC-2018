package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MoveToPosPIDPlay extends ImprovedCommand{
	double targetPos;
	
	public MoveToPosPIDPlay(double targetPos) {
		requires(Robot.el);
		this.targetPos = targetPos;
	}
	
	protected void execute() {
		Robot.el.moveWithPID(targetPos);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}