package Util;

import main.Constants;

/**
 *
 * @author Joseph Grube
 */
// TODO use singleton
public class DriveHelper implements Constants{
	public DriveHelper(double negInertiaScalar) {
	}
	
	public double handleDeadband(double val, double deadband) {
		return (Math.abs(val) > Math.abs(deadband)) ? val : 0.0;
	}
	
	public double handleOverPower(double joystickVal){
		if(Math.abs(joystickVal) > 1.0)
			return Math.signum(joystickVal);
		else
			return joystickVal;
	}
}
