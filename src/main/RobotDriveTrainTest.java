package main;

import edu.wpi.first.wpilibj.command.CommandGroup;
import main.commands.drivetrain.TimedTankDrive;

public class RobotDriveTrainTest extends CommandGroup {
	public RobotDriveTrainTest() {
		//Left Side Test
		addSequential(new TimedTankDrive(1.0, 0.0, false, 1.0));
		addSequential(new TimedTankDrive(-1.0, 0.0, false, 1.0));
		//Right Side Test
		addSequential(new TimedTankDrive(0.0, 1.0, false, 1.0));
		addSequential(new TimedTankDrive(0.0, -1.0, false, 1.0));		
	}
}