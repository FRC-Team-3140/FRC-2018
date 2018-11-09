package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import main.Robot;

public class VeloFF extends Command{
	double inches;
	
	public VeloFF(double inches) {
		requires(Robot.dt);
		this.inches = inches;
	}
	
	public void initialize() {
		Robot.dt.initDriveFF(inches);
	}
	
	public void execute() {
		Robot.dt.driveVeloFF();
	}
	
	public void end() {
		Robot.dt.endPID();
	}

	@Override
	protected boolean isFinished() {
		return false;
		// TODO actually make this return true when it is done uwu
	}

}
