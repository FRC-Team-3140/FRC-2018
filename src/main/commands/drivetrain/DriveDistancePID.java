package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class DriveDistancePID extends ImprovedCommand {
	private double inches;
	
	public DriveDistancePID(double inches) {
		requires(Robot.dt);
		this.inches = inches;
	}
	
	protected void initialize() {
		Robot.dt.drivePID(inches);
	}
	
	protected void execute() {
		//Robot.dt.drivePID(inches);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
	

}
