package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class Drive extends ImprovedCommand {
	public Drive() {
    	requires(Robot.dt);
    }
	
    @Override
	protected void execute() {    	
    	Robot.dt.driveVelocity(Robot.oi.getXbox().getSmoothedMainY(), -Robot.oi.getXbox().getSmoothedAltX());
    }
    
    @Override
	protected boolean isFinished() {
        return true;
    }
}