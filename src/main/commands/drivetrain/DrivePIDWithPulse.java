package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

public class DrivePIDWithPulse extends TimedCommand {
	private double inches;

	public DrivePIDWithPulse(double inches, double timeout) {
		super(timeout);
		requires(Robot.dt);
		requires(Robot.el);
		this.inches = inches;	
	}
	
	protected void initialize() {
		Robot.dt.initPID();
		System.out.println("hi");
	}
	
	protected void execute() {
		Robot.dt.driveWithAnglePID(inches, 0);
		Robot.el.move(1.0);
	}
	
	protected void end() {
		Robot.dt.endPID();
		Robot.el.move(0.0);
	}
	
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return Robot.dt.isDriveTrainAtDistance(inches) || isTimedOut();
	}


}
