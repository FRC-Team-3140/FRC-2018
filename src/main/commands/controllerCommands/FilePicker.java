package main.commands.controllerCommands;

import interfacesAndAbstracts.ImprovedCommand;

// TODO are we using this
public class FilePicker extends ImprovedCommand {
	@SuppressWarnings("unused")
	// TODO are we using this
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
