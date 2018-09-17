package main.commands.intake;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;
//FIXME
public class SpinOutTime extends TimedCommand {

	public SpinOutTime(double timeout) {
		super(timeout);
		requires(Robot.in);
	}
	
	protected void execute() {
		Robot.in.spinIn();
	}
	
	protected void end() {
		Robot.in.spinOff();
	}

}
