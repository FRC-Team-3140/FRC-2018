package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.TimedCommand;
import main.Constants;
import main.Robot;

public class TimedTurn extends TimedCommand implements Constants {
	private double throttle;
	private TurnMode mode;

	public TimedTurn(TurnMode mode, double throttle, double timeout) {
		super(timeout);
		this.throttle = throttle;
		this.mode = mode;
		requires(Robot.dt);
	}
	
	// Called just before this Command runs the first time
    protected void initialize() {
    }           

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		Robot.dt.timedTurn(mode, throttle);//Turning is the same between both robots
	}

    // Called once after isFinished returns true
    protected void end() {
    	//No need to turn off the drivetrain, because the default command Drive
    	//will take over and set the drivetain to 0, because the joystick is default
    	//0.0, 0.0 at its neutral postion.
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

}
