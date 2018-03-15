package main.commands.pneumatics.shift;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

class Shift extends ImprovedCommand {
	private final DoubleSolenoid.Value v;
	
    public Shift(DoubleSolenoid.Value v) {
    	requires(Robot.pn);
    	this.v = v;
    }

    @Override
	protected void execute() {
    	Robot.pn.shift(v);
    }

    @Override
	protected boolean isFinished() {
        return true;
    }
}
