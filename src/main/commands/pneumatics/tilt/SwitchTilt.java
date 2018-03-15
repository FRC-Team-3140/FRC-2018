package main.commands.pneumatics.tilt;

import interfacesAndAbstracts.ImprovedCommandGroup;
import interfacesAndAbstracts.SwitchCommandGroup;
import main.Robot;

public class SwitchTilt extends SwitchCommandGroup {

	public SwitchTilt(ImprovedCommandGroup trueCommand, ImprovedCommandGroup falseCommand) {
		super(trueCommand, falseCommand);
	}

	@Override
	public boolean source() {
		return Robot.pn.isTiltUp();
	}
}
