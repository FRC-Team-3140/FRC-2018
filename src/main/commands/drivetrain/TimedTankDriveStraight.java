package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

public class TimedTankDriveStraight extends TimedCommand {
	double left, right;
	
	public TimedTankDriveStraight(double left, double right, double time) {
		super(time);
		requires(Robot.dt);
		this.left = left;
		this.right = right;
	}
	
    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.dt.zeroGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.driveWithGyroCorrection(left, right, 0.0);
    }
}
