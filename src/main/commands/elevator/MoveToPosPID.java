package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MoveToPosPID extends ImprovedCommand {
	private double pos;
	
	public MoveToPosPID(double pos) {
		this.pos = pos;
		requires(Robot.el);
	}
	
	protected void execute() {
		Robot.el.moveToPosPID(pos);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
