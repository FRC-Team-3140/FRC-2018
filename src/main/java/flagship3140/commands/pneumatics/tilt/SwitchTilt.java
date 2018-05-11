package main.java.flagship3140.commands.pneumatics.tilt;

import main.java.flagship3140.Robot;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommandGroup;
import main.java.flagship3140.interfacesAndAbstracts.SwitchCommandGroup;

public class SwitchTilt extends SwitchCommandGroup {

	public SwitchTilt(ImprovedCommandGroup trueCommand, ImprovedCommandGroup falseCommand) {
		super(trueCommand, falseCommand);
	}

	@Override
	public boolean source() {
		return Robot.pn.isTiltUp();
	}
}
