package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class TurnTest extends ImprovedCommand {
	
	public TurnTest() {
		requires(Robot.dt);
	}
	
	protected void execute() {
		Robot.dt.turn();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}

	
	
}
