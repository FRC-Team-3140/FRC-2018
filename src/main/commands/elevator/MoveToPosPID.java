package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;

public class MoveToPosPID extends ImprovedCommand {
	private double targetPos;
	
	public MoveToPosPID(double targetPos) {
		this.targetPos = targetPos;
	}
	
	protected void execute() {
		
	}

	@Override
	protected boolean isFinished() {
		//TODO
		return false;
	}
	

}
