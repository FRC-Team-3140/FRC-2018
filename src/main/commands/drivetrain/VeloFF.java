package main.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import main.Robot;

public class VeloFF  extends Command{
	
	public VeloFF() {
		requires(Robot.dt);
	}
	
	public void initialize() {
		Robot.dt.initDriveFF(120);
	}
	
	public void execute() {
		Robot.dt.driveVeloFF();
	}
	
	public void end() {
		Robot.dt.endPID();
	}

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
