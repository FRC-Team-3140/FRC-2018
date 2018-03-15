package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

// TODO use or remove
public class DriveTrainOff extends ImprovedCommand { // NO_UCD (unused code)
	public DriveTrainOff() {
    	requires(Robot.dt);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    	Robot.dt.driveVoltageTankTest(0.0, 0.0);
    }
    
    @Override
	protected boolean isFinished() {
        return true;
    }
}
