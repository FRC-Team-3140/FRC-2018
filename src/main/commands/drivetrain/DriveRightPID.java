package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

public class DriveRightPID extends TimedCommand {
	double inches;
	
	public DriveRightPID(double timeout, double inches) {
		super(timeout);
		this.inches = inches;
		requires(Robot.dt);
	}
	
	protected void initialize() {
		Robot.dt.initPID();
	}
	
	protected void execute() {
		Robot.dt.drivePID(inches, "right");
	}
	
	protected void end() {
		Robot.dt.endPID();
	}

	@Override
	protected boolean isFinished() {
		return Robot.dt.isRightDriveAtDistance(inches);
	}

}
