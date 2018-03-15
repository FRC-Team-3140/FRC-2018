package main.commands.intake;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

// TODO use or remove
public class IntakeFromPlayer extends ImprovedCommand { // NO_UCD (unused code)
	private final double leftIntakeWheelValue;
	private final double rightIntakeWheelValue;
	
	public IntakeFromPlayer(double leftIntakeWheelValue, double rightIntakeWheelValue) {
		requires(Robot.in);
		this.leftIntakeWheelValue = leftIntakeWheelValue;
		this.rightIntakeWheelValue = rightIntakeWheelValue;
	}
	
	@Override
	protected void execute() {
		Robot.in.setIntakeValues(leftIntakeWheelValue, rightIntakeWheelValue);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}