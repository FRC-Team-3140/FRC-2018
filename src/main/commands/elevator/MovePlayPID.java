package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MovePlayPID extends ImprovedCommand {
	double distanceTicks;
	double veloTicks100Ms;
	
	public MovePlayPID(double distanceTicks, double veloTicks100Ms) {
		this.distanceTicks = distanceTicks;
		this.veloTicks100Ms = veloTicks100Ms;
	}
	
	protected void execute() {
		Robot.el.playbackPID(veloTicks100Ms, distanceTicks);
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
