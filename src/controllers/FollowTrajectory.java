package controllers;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import loopController.Loop;
import main.Constants;
import main.commands.drivetrain.EndPID;
import main.commands.drivetrain.InitPID;
import main.subsystems.subsystemConstants.DrivetrainConstants;
import util.EncoderHelper;
import util.motion.TrajectoryPath;

/*
 * Looper to do PID magic from trajectories
 */

public class FollowTrajectory implements Loop, Constants, DrivetrainConstants {
	
	private static boolean execute = false;	
	private static TrajectoryPath currentPath;
	private static Timer timer = new Timer();

	@Override
	public void onStart() {
		
	}

	@Override
	public void onLoop() {
		if(execute) execute();
	}

	@Override
	public void onStop() {
		
	}
	
	public void execute() {
		/*double t = timer.get();
		double headingDeg = currentPath.getHeading(t);
		double leftVelo = currentPath.getLeftVelocity(t);
		double rightVelo = currentPath.getRightVelocity(t);
		double leftDist = currentPath.getLeftDist(t);
		double rightDist = currentPath.getRightDist(t);
		
		double leftDistTicks = EncoderHelper.inchesToEncoderTicks(leftDist, wheelCircum, quadConversionFactor);
		double rightDistTicks = EncoderHelper.inchesToEncoderTicks(rightDist, wheelCircum, quadConversionFactor);
		double leftVeloTicks100Ms = EncoderHelper.inSecToTicks100Ms(leftVelo, quadConversionFactor, wheelCircum);
		double rightVeloTicks100Ms = EncoderHelper.inSecToTicks100Ms(rightVelo, quadConversionFactor, wheelCircum);

//		Command drive = new DriveTrajPID(leftDistTicks, rightDistTicks, leftVeloTicks100Ms, rightVeloTicks100Ms, headingDeg);
//		drive.start();
	}
	
	public static void setCanExecute(boolean b, String name) {
		if(b) {
			Command init = new InitPID();
			init.start();
			TrajectoryPath path = new TrajectoryPath(name);
			currentPath = path;
			timer.start();
		}
		else {
			Command end = new EndPID();
			end.start();
			timer.stop();
			timer.reset();
		}
		execute = b;	*/
	}

}
