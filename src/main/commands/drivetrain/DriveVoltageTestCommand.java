package main.commands.drivetrain;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class DriveVoltageTestCommand extends ImprovedCommand {
	public DriveVoltageTestCommand() {
    	requires(Robot.dt);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.dt.driveVoltageTankTest(testVoltage, testVoltage, voltageCompensationVoltage);
    }
    
    protected boolean isFinished() {
        return true;
    }
}
