package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

public class AltDistanceDriveStraight extends TimedCommand {
	private double inches;

	public AltDistanceDriveStraight(double inches, double timeout) {
		super(timeout);
		this.inches = inches;
		requires(Robot.dt);
	}

	protected void initialize() {
		Robot.dt.initPID();
		System.out.println("hi");
	}
	
	protected void execute() {
		Robot.dt.driveWithAnglePID(inches, 0);
	}
	
	protected void end() {
		Robot.dt.endPID();
	}
	
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return Robot.dt.isDriveTrainAtDistance(inches) || isTimedOut();
	}

}
