package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class DriveVeloFF extends ImprovedCommand {
	private double velo;

	public DriveVeloFF(double veloTicks100Ms) {
		requires(Robot.dt);
		velo = veloTicks100Ms;
	}

	protected void execute() {
		System.out.println("drive velo FF");
		Robot.dt.driveTankVeloFF(velo);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}

}
