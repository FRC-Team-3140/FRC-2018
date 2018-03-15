package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class StopElevator extends ImprovedCommand {
	public StopElevator() {
    	requires(Robot.el);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
