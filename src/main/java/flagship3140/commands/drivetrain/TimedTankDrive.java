package main.java.flagship3140.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.java.flagship3140.Robot;

public class TimedTankDrive extends TimedCommand {
	double left, right;
	boolean squareInputs;
	
	public TimedTankDrive(double left, double right, boolean squareInputs, double time) {
		super(time);
		requires(Robot.dt);
		this.left = left;
		this.right = right;
		this.squareInputs = squareInputs;
	}
	
    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.tankDrive(left, right, squareInputs);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}