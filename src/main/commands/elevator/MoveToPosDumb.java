package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MoveToPosDumb extends ImprovedCommand {
	private double pos;
	public MoveToPosDumb(double pos) {
		this.pos = pos;
		requires(Robot.el);
	}
	
	protected void execute() {
		Robot.el.moveToPosDumb(pos);
	}

	@Override
	protected boolean isFinished() {
		return Robot.el.isIntakeAtPos(pos);
	}
	

}
