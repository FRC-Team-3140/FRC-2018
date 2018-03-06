package main.commands.controllerCommands;

import interfacesAndAbstracts.ImprovedCommand;

public class FileCreator extends ImprovedCommand {
	public FileCreator() {
	}
	
	// Called just before this Command runs the first time
    protected void initialize() {
		//Robot.lg.createNewFile(Robot.getNewFileName());
    }    	

	@Override
	protected boolean isFinished() {
		return true;
	}
}
