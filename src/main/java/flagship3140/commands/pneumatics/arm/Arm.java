package main.java.flagship3140.commands.pneumatics.arm;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import main.java.flagship3140.Robot;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommand;

public class Arm extends ImprovedCommand {
	public DoubleSolenoid.Value v;
	
	public Arm(DoubleSolenoid.Value v) {
		requires(Robot.pn);
		this.v = v;
	}
	
    protected void execute() {
    	Robot.pn.toggleArm(v);
    }

	@Override
	protected boolean isFinished() {
		return true;
	}
}
