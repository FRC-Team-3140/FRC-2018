package main.java.flagship3140.led.commands;

import edu.wpi.first.wpilibj.command.Command;
import main.java.flagship3140.Robot;

/**
 * Turns the LED on.
 * 
 * @author Jason
 *
 */
public class LEDOn extends Command {
	public LEDOn() {
		requires(Robot.led);
	}
	
	@Override
	protected void initialize() {
		Robot.led.setState(true);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
