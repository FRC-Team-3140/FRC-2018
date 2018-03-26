package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MoveToPosPID extends ImprovedCommand {
	private int pos;
	
	public MoveToPosPID(int pos) {
		this.pos = pos;
	}
	
	protected void execute() {
		Robot.el.moveToPosPID(pos);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
