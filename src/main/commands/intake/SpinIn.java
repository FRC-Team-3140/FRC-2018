package main.commands.intake;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class SpinIn extends ImprovedCommand {
	public SpinIn() {
		requires(Robot.in);
	}
	
    @Override
	protected void execute() {
    	Robot.in.spinIn();
    }

    @Override
	protected boolean isFinished() {
        return true;
    }
}
