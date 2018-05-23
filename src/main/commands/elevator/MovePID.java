package main.commands.elevator;

import edu.wpi.first.wpilibj.command.TimedCommand;
import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MovePID extends TimedCommand {
	
	public MovePID(double inches, double timeout) {
		super(timeout);
		this.inches = inches;
		requires(Robot.el);	
	}

	private double inches;
	
	
	protected void execute() {
		Robot.el.movePID(inches);
	}

	@Override
	protected boolean isFinished() {
		return Robot.el.isIntakeAtPos(inches);
	}
	
	protected void end() {
	}
	

}
