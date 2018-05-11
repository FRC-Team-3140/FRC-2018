package main.java.flagship3140.led.commands;

import edu.wpi.first.wpilibj.command.Command;
import main.java.flagship3140.Robot;

/**
 * Turns the LED off.
 * 
 * @author Jason
 *
 */
public class LEDOff extends Command {
	public LEDOff() {
		requires(Robot.led);
	}
	
	@Override
	protected void initialize() {
		Robot.led.setState(false);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
