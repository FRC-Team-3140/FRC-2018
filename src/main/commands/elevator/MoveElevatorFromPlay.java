package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

// TODO use or remove
public class MoveElevatorFromPlay extends ImprovedCommand { // NO_UCD (unused code)
	private final double voltage;
	
	public MoveElevatorFromPlay(double voltage) {
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
