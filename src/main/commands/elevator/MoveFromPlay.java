package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MoveFromPlay extends ImprovedCommand {
	private double voltage;
	
	public MoveFromPlay(double voltage) {
		this.voltage = voltage;
	}
	
	@Override
	protected void execute() {
		Robot.el.moveFromPlay(voltage);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
