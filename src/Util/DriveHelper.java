package Util;

import main.Constants;

/**
 * Toolkit for driving
 * 
 * @author Joseph Grube & TheSuperDuck
 */
public class DriveHelper implements Constants {
	/**
	 * Prevents over powering and use deadband.
	 * 
	 * @param joystick_value joystick value
	 * @param deadband deadband
	 * @return the restricted value
	 */
	public static double handleDrive(double joystick_value, double deadband) {
		return handleOverPower(handleDeadband(joystick_value, deadband));
	}
	
	private static double handleDeadband(double val, double deadband) {
		return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
	}
	
	private static double handleOverPower(double joystickVal){
		if (Math.abs(joystickVal) > 1.0)
			return Math.signum(joystickVal);
		else
			return joystickVal;
	}
}
