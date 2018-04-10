package main.commands.controllerCommands;

import controllers.Record;
import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class StartRecord extends ImprovedCommand {
	public StartRecord() {
    }

	// Called just before this Command runs the first time
    protected void initialize() {
    	Robot.dt.zeroSensors();
    	Robot.el.zeroSensors();
    	Robot.lg.resetForWrite();
    	Record.reachedFirstNonZero(false);
    	if(isSensorPlayRecordAuto) {
    		Robot.dt.setVoltageComp(true, voltageCompensationVoltageDriveTrainSensorRecord, timeout);
    		Robot.el.setVoltageComp(true, voltageCompensationVoltageElevatorSensorRecord, timeout);
    	}
    	else {
    		Robot.dt.setVoltageComp(true, voltageCompensationVoltageDriveTrainRecordAndPlay, timeout);
    		Robot.el.setVoltageComp(true, voltageCompensationVoltageElevatorRecordAndPlay, timeout);
    	}
    	Record.okToRecord(true);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Record.okToRecord(false);
    	Robot.dt.setVoltageComp(false, 0.0, timeout);
    	Robot.el.setVoltageComp(false, 0.0, timeout);    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}