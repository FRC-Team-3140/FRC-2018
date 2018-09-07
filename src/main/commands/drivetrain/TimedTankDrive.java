package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

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

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.tankDrive(left, right, squareInputs);
    }
}