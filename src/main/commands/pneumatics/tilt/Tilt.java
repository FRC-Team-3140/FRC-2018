package main.commands.pneumatics.tilt;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class Tilt extends ImprovedCommand {
	public DoubleSolenoid.Value val;
	
	public Tilt(DoubleSolenoid.Value v) {
		requires(Robot.pn);
		val = v;
	}
	
	public void execute() {
		Robot.pn.tilt(val);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
