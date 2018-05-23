package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class TurnToAngleGyro extends TimedCommand {
	private double angle;

	public TurnToAngleGyro(double angle, double timeout) {
		super(timeout);
		requires(Robot.dt);
		this.angle = angle;	
	}

	protected void initialize() {
		Robot.dt.initPID();
	}
	
	protected void execute() {
		Robot.dt.turnToAngleGyro(angle);
	}
	
	protected void end() {
		Robot.dt.endPID();
	}
	
	protected void interrupted() {
		end();
	}

	@Override
	protected boolean isFinished() {
		return Robot.dt.isDriveTrainAtAngle(angle);
	}

}
