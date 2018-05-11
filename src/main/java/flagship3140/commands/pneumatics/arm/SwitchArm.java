package main.java.flagship3140.commands.pneumatics.arm;

import main.java.flagship3140.Robot;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommandGroup;
import main.java.flagship3140.interfacesAndAbstracts.SwitchCommandGroup;

public class SwitchArm extends SwitchCommandGroup {

	public SwitchArm(ImprovedCommandGroup trueCommand, ImprovedCommandGroup falseCommand) {
		super(trueCommand, falseCommand);
	}

	@Override
	public boolean source() {
		return Robot.pn.isArmClose();
	}
}