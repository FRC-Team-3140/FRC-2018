
package controllers;

import edu.wpi.first.wpilibj.command.Command;
import loopController.Loop;
import main.Constants;
import main.Robot;
import main.commands.drivetrain.DriveFromPlayer;
import main.commands.elevator.MoveFromPlay;

public class Play implements Loop, Constants {
	private static boolean playOK = false;
	private static boolean finished = false;
	
	public static void okToPlay(boolean okToPlay) {
		playOK = okToPlay;
		if(playOK) System.out.println("Ok To Play");
		else System.out.println("Not Ok To Play");
	}
	
	@Override
	public void onStart() {
	}

	@Override
	public void onLoop() {
		if(playOK)
			execute();
	}
	
	@Override
	public void onStop() {
	}
	
	private void execute() {
		String line = Robot.lg.readLine();
		if((line) != null) { 
			String[] robotState = line.split(",");
			
			if(robotState.length == 27 && robotState != null) {
				System.out.println("playing");
				
				double leftVoltage = Double.parseDouble(robotState[0]);
				double rightVoltage = Double.parseDouble(robotState[1]);
				boolean a = Boolean.parseBoolean(robotState[2]);
				boolean b = Boolean.parseBoolean(robotState[3]);
				boolean x = Boolean.parseBoolean(robotState[4]);
				boolean y = Boolean.parseBoolean(robotState[5]);
				boolean leftBumper = Boolean.parseBoolean(robotState[6]);
				boolean rightBumper = Boolean.parseBoolean(robotState[7]);
				boolean select = Boolean.parseBoolean(robotState[8]);
				boolean start = Boolean.parseBoolean(robotState[9]);
				boolean leftJoystickPress = Boolean.parseBoolean(robotState[10]);
				boolean rightJoystickPress = Boolean.parseBoolean(robotState[11]);
				boolean leftTrigger = Boolean.parseBoolean(robotState[12]);
				boolean rightTrigger = Boolean.parseBoolean(robotState[13]);
				double elevatorVoltage = Double.parseDouble(robotState[14]);
				boolean a2 = Boolean.parseBoolean(robotState[15]);
				boolean b2 = Boolean.parseBoolean(robotState[16]);
				boolean x2 = Boolean.parseBoolean(robotState[17]);
				boolean y2 = Boolean.parseBoolean(robotState[18]);
				boolean leftBumper2 = Boolean.parseBoolean(robotState[19]);
				boolean rightBumper2 = Boolean.parseBoolean(robotState[20]);
				boolean select2 = Boolean.parseBoolean(robotState[21]);
				boolean start2 = Boolean.parseBoolean(robotState[22]);
				boolean leftJoystickPress2 = Boolean.parseBoolean(robotState[23]);
				boolean rightJoystickPress2 = Boolean.parseBoolean(robotState[24]);
				boolean leftTrigger2 = Boolean.parseBoolean(robotState[25]);
				boolean rightTrigger2 = Boolean.parseBoolean(robotState[26]);
			
				Command drive = new DriveFromPlayer(leftVoltage, rightVoltage);
				Command move = new MoveFromPlay(elevatorVoltage);
				drive.start();
				move.start();
				Robot.oi.setButtonValues(a, b, x, y, leftBumper, rightBumper, select, start, leftJoystickPress, rightJoystickPress, leftTrigger, rightTrigger);
				Robot.oi.setButtonValues2(a2, b2, x2, y2, leftBumper2, rightBumper2, select2, start2, leftJoystickPress2, rightJoystickPress2, leftTrigger2, rightTrigger2);
			}
		}
		else {
			finished = true;
			System.out.println("Finished Playing");
		}
	}
	
	public static boolean isFinished() {
		return finished;
	}
	
	public static void reset() {
		finished = false;
	}
}

