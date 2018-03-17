package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class DriveVoltageTestCommand extends ImprovedCommand {
	public DriveVoltageTestCommand() {
    	requires(Robot.dt);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.driveVoltageTankTest(testVoltage, testVoltage);
    }
    
    protected boolean isFinished() {
        return false;
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
