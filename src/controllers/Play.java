
package controllers;

import edu.wpi.first.wpilibj.command.Command;
import loopController.Loop;
import main.Constants;
import main.Robot;
import main.commands.drivetrain.DriveFromPlayer;
import main.commands.drivetrain.DriveFromPlayerWithSensors;
import main.commands.elevator.MoveToPosPIDPlay;
import main.commands.intake.IntakeFromPlayer;

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
			
			if(robotState.length == 33 && robotState != null) {
				System.out.println("Playing");
				
				double leftDriveVoltage = Double.parseDouble(robotState[0]);
				double rightDriveVoltage = Double.parseDouble(robotState[1]);
				double elevatorDriveVoltage = Double.parseDouble(robotState[2]);//Unused but necessary to record
				double leftIntakeWheelValue = Double.parseDouble(robotState[3]);
				double rightIntakeWheelValue = Double.parseDouble(robotState[4]);
				
				boolean a = Boolean.parseBoolean(robotState[5]);
				boolean b = Boolean.parseBoolean(robotState[6]);
				boolean x = Boolean.parseBoolean(robotState[7]);
				boolean y = Boolean.parseBoolean(robotState[8]);
				boolean leftBumper = Boolean.parseBoolean(robotState[9]);
				boolean rightBumper = Boolean.parseBoolean(robotState[10]);
				boolean select = Boolean.parseBoolean(robotState[11]);
				boolean start = Boolean.parseBoolean(robotState[12]);
				boolean leftJoystickPress = Boolean.parseBoolean(robotState[13]);
				boolean rightJoystickPress = Boolean.parseBoolean(robotState[14]);
				boolean leftTrigger = Boolean.parseBoolean(robotState[15]);
				boolean rightTrigger = Boolean.parseBoolean(robotState[16]);
				
				boolean a2 = Boolean.parseBoolean(robotState[17]);
				boolean b2 = Boolean.parseBoolean(robotState[18]);
				boolean x2 = Boolean.parseBoolean(robotState[19]);
				boolean y2 = Boolean.parseBoolean(robotState[20]);
				boolean leftBumper2 = Boolean.parseBoolean(robotState[21]);
				boolean rightBumper2 = Boolean.parseBoolean(robotState[22]);
				boolean select2 = Boolean.parseBoolean(robotState[23]);
				boolean start2 = Boolean.parseBoolean(robotState[24]);
				boolean leftJoystickPress2 = Boolean.parseBoolean(robotState[25]);
				boolean rightJoystickPress2 = Boolean.parseBoolean(robotState[26]);
				boolean leftTrigger2 = Boolean.parseBoolean(robotState[27]);
				boolean rightTrigger2 = Boolean.parseBoolean(robotState[28]);
				
				double leftEncoderDistanceTravelled = Double.parseDouble(robotState[29]);
				double rightEncoderDistanceTravelled = Double.parseDouble(robotState[30]);
				double heading = Double.parseDouble(robotState[31]);
				double elevatorEncoderDistanceTravelled = Double.parseDouble(robotState[32]);
			
				Command drive;
				Command intake = new IntakeFromPlayer(leftIntakeWheelValue, rightIntakeWheelValue);
				Command lift = new MoveToPosPIDPlay(elevatorEncoderDistanceTravelled);
				
				if(!isSensorPlayRecordAuto) {
					drive = new DriveFromPlayer(leftDriveVoltage, rightDriveVoltage);
					
				}
				else {
					drive  = new DriveFromPlayerWithSensors(leftEncoderDistanceTravelled, rightEncoderDistanceTravelled, heading);
				}
				
				drive.start();
				intake.start();
				lift.start();
				
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

