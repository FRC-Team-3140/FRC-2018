package main.commands.controllerCommands;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;

public class DelayedPlay extends ImprovedCommandGroup {
	public DelayedPlay(String fileToPlay, int delay) {
		addSequential(new WaitCommand(delay));
		addSequential(new FilePicker(fileToPlay));
		addSequential(new StartPlay());
	}
}
