package main.commands.controllerCommands;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class FilePicker extends ImprovedCommand {
	private String filePath = "";
	
	public FilePicker(String filePath) {
		this.filePath = filePath;
		this.setName(filePath);
	}
	
	protected void initialize() {
		//Robot.lg.changePath(filePath, false);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
