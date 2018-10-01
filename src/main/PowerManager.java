package main;

import edu.wpi.first.wpilibj.RobotController;

/**
 * Manages the power of the robot.
 */
public class PowerManager {
	private static double DANGER_VOLTAGE=7.5;
	
	/**
	 * Gets the voltage of the robot battery.
	 * 
	 * @return the battery voltage in Volts
	 */
	public double getVoltage() {
		return RobotController.getBatteryVoltage();
	}
	
	/**
	 * Returns if the robot is in danger of blacking out.
	 * 
	 * @return true if the robot is in danger of blacking out, false otherwise
	 */
	public boolean isDangerous() {
		return getVoltage()<DANGER_VOLTAGE;
	}
}
