package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MoveElevatorFromPlay extends ImprovedCommand {
	private double voltage;
	
	public MoveElevatorFromPlay(double voltage) {
		this.voltage = voltage;
	}
	
	protected void execute() {
		Robot.el.moveFromPlay(voltage);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
