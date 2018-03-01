package main.commands.controllerCommands;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class FileDeletor extends ImprovedCommand {
	public FileDeletor() {
	}
	
	// Called just before this Command runs the first time
    protected void initialize() {
		//Robot.lg.deleteFile(Robot.getFileChooser().getSelected().getName());
    }    	

	@Override
	protected boolean isFinished() {
		return true;
	}

}
