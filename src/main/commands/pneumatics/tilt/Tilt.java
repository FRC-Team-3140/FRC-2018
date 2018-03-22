package main.commands.pneumatics.tilt;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class Tilt extends ImprovedCommand {
	public DoubleSolenoid.Value v;
	
	public Tilt(DoubleSolenoid.Value v) {
		requires(Robot.pn);
		this.v = v;
	}
	
	public void execute() {
		Robot.pn.tilt(v);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
