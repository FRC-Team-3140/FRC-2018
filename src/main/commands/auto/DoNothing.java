package main.commands.auto;

import interfacesAndAbstracts.ImprovedCommand;

public class DoNothing extends ImprovedCommand {
	public DoNothing() {
        // Don't put anything here
    }

	@Override
	protected boolean isFinished() {
		return true;
	}
}
