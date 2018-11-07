package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class DriveVeloFF extends ImprovedCommand {
	private double velo;

	public DriveVeloFF(double veloTicks100Ms) {
		requires(Robot.dt);
	}

	protected void execute() {
		Robot.dt.driveTankVeloFF(velo);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}
