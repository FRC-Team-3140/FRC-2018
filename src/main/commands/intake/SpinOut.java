package main.commands.intake;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class SpinOut extends ImprovedCommand {
	public SpinOut() {
		requires(Robot.in);
	}
	
    @Override
	protected void execute() {
    	Robot.in.spinOut();
    }

    @Override
	protected boolean isFinished() {
        return true;
    }
}
