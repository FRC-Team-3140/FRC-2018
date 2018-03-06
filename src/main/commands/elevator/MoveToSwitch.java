package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommandGroup;
import main.Robot;

public class MoveToSwitch extends ImprovedCommandGroup {
	public final double switchHeight = 24; //set this in encoder units today...

	public void MoveToSwich() {
		requires(Robot.el);
	}
	
    // Called just before this Command runs the first time
    @Override
	public void initialize() {
    	Robot.el.moveToPosPID(switchHeight);
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {
    	return Robot.el.isIntakeAtPos(switchHeight);
    }

    // Called once after isFinished returns true
    @Override
	protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    @Override
	protected void interrupted() {
    }
}