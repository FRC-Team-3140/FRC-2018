package main.commands.elevator;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

public class MoveToSwitch extends TimedCommand {
	@SuppressWarnings("unused")
	private static final double switchHeight = 24; // TODO set this in encoder units today... use or remove
	
	public MoveToSwitch(double timeout) {
		super(3);//Timeout forced to a maximum of 3, this is for the emergency case that a limit switch breaks
				//So that the elevator will not continue to drive up.
		requires(Robot.el);
	}
	
	// TODO implement
}