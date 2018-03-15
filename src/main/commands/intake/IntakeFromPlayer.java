package main.commands.intake;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class IntakeFromPlayer extends ImprovedCommand {
	private double leftIntakeWheelValue;
	private double rightIntakeWheelValue;
	public IntakeFromPlayer(double leftIntakeWheelValue, double rightIntakeWheelValue) {
		requires(Robot.in);
		this.leftIntakeWheelValue = leftIntakeWheelValue;
		this.rightIntakeWheelValue = rightIntakeWheelValue;
	}
	
	protected void execute() {
		Robot.in.setIntakeValues(leftIntakeWheelValue, rightIntakeWheelValue);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}