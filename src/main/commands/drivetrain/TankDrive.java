package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import main.Robot;

/**
 *
 */
public class TankDrive extends Command {
	double left;
	double right;

    public TankDrive(double left, double right) {
        requires(Robot.dt);
        this.left = left;
        this.right = right;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.tankDrive(left, right, false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
