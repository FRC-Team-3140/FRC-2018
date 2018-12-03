package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;
import main.subsystems.subsystemConstants.DrivetrainConstants;
import util.motion.TrajectoryPath;

public class DriveProfile extends ImprovedCommand implements DrivetrainConstants {
	TrajectoryPath path;

	public DriveProfile(TrajectoryPath path) {
		requires(Robot.dt);
		this.path = path;
	}
	
	protected void initialize() {
		Robot.dt.driveWithProfile(path);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
