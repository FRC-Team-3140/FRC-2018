package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Robot;

/**
 * Drive for a certain amount of time.
 * 
 * @author Yuchen Jing
 *
 */
public class TimedDrive extends TimedCommand {
	private double throttle, heading;

	/**
	 * Constructor with default heading of 0.
	 * <p>
	 * <b>Parameter description copied from {@linkplain edu.wpi.first.wpilibj.drive.DifferentialDrive#arcadeDrive(double, double)
	 * DifferentialDrive}
	 * 
	 * @param throttle The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
	 * @param time time
	 */
	public TimedDrive(double throttle, double time) {
		this(throttle, 0, time);
	}

	/**
	 * Constructor.
	 * <p>
	 * <b>Parameter description copied from {@linkplain edu.wpi.first.wpilibj.drive.DifferentialDrive#arcadeDrive(double, double)
	 * DifferentialDrive}
	 * 
	 * @param throttle The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
	 * @param heading The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is positive.
	 * @param time time
	 */
	public TimedDrive(double throttle, double heading, double time) { // NO_UCD (use default)
		super(time);
		this.throttle=throttle;
		this.heading=heading;
		requires(Robot.dt);
	}
	
	/**
	 * Calls the DriveTrain's driveVelocity(double, double).
	 */
	@Override
	protected void execute() {
		Robot.dt.driveVelocity(throttle, heading);
	}
}