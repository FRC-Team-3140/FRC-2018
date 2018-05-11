package main.java.flagship3140.commands.auto;

import main.java.flagship3140.interfacesAndAbstracts.ImprovedCommand;

public class DoNothing extends ImprovedCommand {
	public DoNothing() {
        // Don't put anything here
    }

	@Override
	protected boolean isFinished() {
		return false;
	}
}
