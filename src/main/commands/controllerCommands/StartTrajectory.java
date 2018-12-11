package main.commands.controllerCommands;

import controllers.FollowTrajectory;
import interfacesAndAbstracts.ImprovedCommand;

public class StartTrajectory extends ImprovedCommand {
	private String name;
	
	public StartTrajectory(String name) {
		this.name = name;
	}
	
	protected void initialize() {
		//FollowTrajectory.setCanExecute(true, name);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
