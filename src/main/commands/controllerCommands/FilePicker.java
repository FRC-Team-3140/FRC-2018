package main.commands.controllerCommands;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class FilePicker extends ImprovedCommand {
	private String filePath = "";
	private boolean useFileLookup;
	
	public FilePicker(String filePath, boolean useFileLookup) {
		this.filePath = filePath;
		this.useFileLookup = useFileLookup;
		this.setName(filePath);
	}
	
	protected void initialize() {
		Robot.lg.changePath(filePath, useFileLookup);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
