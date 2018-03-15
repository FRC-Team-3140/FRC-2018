package main.commands.pneumatics.shift;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

class Shift extends ImprovedCommand {

	private DoubleSolenoid.Value v;
    public Shift(DoubleSolenoid.Value v) {
    	requires(Robot.pn);
    	this.v = v;
    }

    // Called just before this Command runs the first time
    @Override
	protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    @Override
	protected void execute() {
    	Robot.pn.shift(v);
    }

    // Make this return true when this Command no longer needs to run execute()
    @Override
	protected boolean isFinished() {
        return true;
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
