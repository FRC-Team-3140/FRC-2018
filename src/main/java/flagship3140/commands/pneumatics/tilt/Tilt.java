package main.java.flagship3140.commands.pneumatics.tilt;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import main.java.flagship3140.Robot;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommand;

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
