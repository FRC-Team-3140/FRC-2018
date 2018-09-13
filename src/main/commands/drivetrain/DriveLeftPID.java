package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class DriveLeftPID extends TimedCommand {
	double inches;
	
	public DriveLeftPID(double inches, double timeout) {
		super(timeout);
		this.inches = inches;
		requires(Robot.dt);
	}
	
	protected void initialize() {
		Robot.dt.initPID();
	}
		
	protected void execute() {
		Robot.dt.drivePID(inches, "left");
	}
	
	protected void end() {
		Robot.dt.endPID();
	}

	@Override
	protected boolean isFinished() {
		return Robot.dt.isLeftDriveAtDistance(inches) || isTimedOut();
	}
}
