package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

// TODO use or remove
public class StopElevator extends ImprovedCommand { // NO_UCD (unused code)
	public StopElevator() {
    	requires(Robot.el);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
