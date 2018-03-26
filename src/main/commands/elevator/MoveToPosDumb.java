package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MoveToPosDumb extends ImprovedCommand {
	private int pos;
	public MoveToPosDumb(int pos) {
		this.pos = pos;
	}
	
	protected void execute() {
		Robot.el.moveToPosDumb(pos);
	}

	@Override
	protected boolean isFinished() {
		return Robot.el.isIntakeAtPos(pos);
	}
	

}
