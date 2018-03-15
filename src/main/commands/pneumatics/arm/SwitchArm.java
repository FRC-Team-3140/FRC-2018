package main.commands.pneumatics.arm;

import interfacesAndAbstracts.ImprovedCommandGroup;
import interfacesAndAbstracts.SwitchCommandGroup;
import main.Robot;

public class SwitchArm extends SwitchCommandGroup {
	public SwitchArm(ImprovedCommandGroup trueCommand, ImprovedCommandGroup falseCommand) {
		super(trueCommand, falseCommand);
	}

	@Override
	public boolean source() {
		return Robot.pn.isArmClose();
	}
}