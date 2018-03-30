package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MoveToPosPID extends ImprovedCommand {
	private double targetPos;
	
	public MoveToPosPID(double targetPos) {
		requires(Robot.el);
		this.targetPos = targetPos;
	}
	
	protected void execute() {
		Robot.el.moveWithPID(targetPos);
	}

	@Override
	protected boolean isFinished() {
		return Robot.el.isIntakeAtPos(targetPos);
	}
	

}