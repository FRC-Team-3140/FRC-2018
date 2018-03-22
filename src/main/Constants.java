package main;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public interface Constants {
	public enum TurnMode {
		RIGHT, LEFT
	}

	/*************
	 * VARIABLES *
	 *************/
	// TODO use isCompetitionMatch
	public final boolean IS_COMPETITION_MATCH = true; // NO_UCD
	public final boolean IS_COMPETITION_ROBOT = true;
	// FILE OUTPUT PATH
	public final String OUTPUT_PATH = "/home/lvuser"; // USB output path: /U

	// FILE NAMES
	// TODO use the names
	public final String LEFT_LEFT_SWITCH = "LEFT_LEFT_SWITCH"; // NO_UCD (unused code)
	public final String LEFT_SCALE = "LEFT_SCALE"; // NO_UCD (unused code)
	public final String LEFT_RIGHT_SWITCH = "LEFT_RIGHT_SWITCH"; // NO_UCD (unused code)
	public final String LEFT_SWITCH_AND_SCALE = "LEFT_SWITCH_AND_SCALE"; // NO_UCD (unused code)
	public final String RIGHT_RIGHT_SWITCH = "RIGHT_RIGHT_SWITCH"; // NO_UCD (unused code)
	public final String RIGHT_SCALE = "RIGHT_SCALE"; // NO_UCD (unused code)
	public final String RIGHT_LEFT_SWITCH = "RIGHT_LEFT_SWITCH"; // NO_UCD (unused code)
	public final String RIGHT_SWITCH_AND_SCALE = "RIGHT_SWITCH_AND_SCALE"; // NO_UCD (unused code)
	public final String MID_RIGHT_SWITCH = "MID_RIGHT_SWITCH"; // NO_UCD (unused code)
	public final String MID_LEFT_SWITCH = "MID_LEFT_SWITCH"; // NO_UCD (unused code)
	public final String BASELINE = "BASELINE"; // NO_UCD (unused code)

	// Auto Delay Time
	// This is the time that the robot will wait before executing the selected auto
	// in an EDGECASE situation.
	// TODO do we need this?
	public final int AUTO_DELAY = 5; // NO_UCD (unused code)

	// REV ROBOTICS SENSORS
	public final int ANALOG_SENSOR = 0;

	// JOYSTICK DEADBANDS
	public final double THROTTLE_DEADBAND = 0.02;
	public final double HEADING_DEADBAND = 0.02;
	public final double ELEVATOR_DEADBAND = 0.1;

	// TALON VOLTAGE COMPENSATION
	public final double VOLTAGE_COMPENSATION_VOLTAGE = 12.0;
	
	//ROBOT BIAS TEST CONSTANTS
	public final double PRACTICE_BOT_LEFT_WHEEL_RADIUS = 2; // TODO Update with real measurements
	public final double PRACTICE_BOT_RIGHT_WHEEL_RADIUS = 2;// TODO Update with real measurements
	public final double COMPETITION_BOT_LEFT_WHEEL_RADIUS = 2;// TODO Update with real measurements
	public final double COMPETITION_BOT_RIGHT_WHEEL_RADIUS = 2;// TODO Update with real measurements

	
	public final double TEST_VOLTAGE = 8.0;//Subject to change
	public final double PRACTICE_BOT_LEFT_FREE_RPM_AT_TEST_VOLTAGE = 1000.0;// TODO Update with real measurement (+ value only)
	public final double PRACTICE_BOT_RIGHT_FREE_RPM_AT_TEST_VOLTAGE = 1000.0;// TODO Update with real measurement (+ value only)
	public final double COMPETITION_BOT_LEFT_FREE_RPM_AT_TEST_VOLTAGE = 1000.0;// TODO Update with real measurement (+ value only) and use this value NO_UCD
	public final double COMPETITION_BOT_RIGHT_FREE_RPM_AT_TEST_VOLTAGE = 1000.0;// TODO Update with real measurement (+ value only) and use this value NO_UCD
	
	//ROBOT BIAS CONSTANTS COMPUTATION
	public final double COMPETITION_BIAS_LEFT = ((TEST_VOLTAGE * COMPETITION_BOT_LEFT_WHEEL_RADIUS
			* PRACTICE_BOT_LEFT_FREE_RPM_AT_TEST_VOLTAGE)
			- (TEST_VOLTAGE * PRACTICE_BOT_LEFT_WHEEL_RADIUS * PRACTICE_BOT_LEFT_FREE_RPM_AT_TEST_VOLTAGE))
			/ (PRACTICE_BOT_LEFT_WHEEL_RADIUS * PRACTICE_BOT_LEFT_FREE_RPM_AT_TEST_VOLTAGE);

	public final double COMPETITION_BIAS_RIGHT = ((TEST_VOLTAGE * COMPETITION_BOT_RIGHT_WHEEL_RADIUS
			* PRACTICE_BOT_RIGHT_FREE_RPM_AT_TEST_VOLTAGE)
			- (TEST_VOLTAGE * PRACTICE_BOT_RIGHT_WHEEL_RADIUS * PRACTICE_BOT_RIGHT_FREE_RPM_AT_TEST_VOLTAGE))
			/ (PRACTICE_BOT_RIGHT_WHEEL_RADIUS * PRACTICE_BOT_RIGHT_FREE_RPM_AT_TEST_VOLTAGE);
	
	
	/* BIAS VOLTAGES FOR DIFFERENCE BETWEEN ROBOTS COMPENSATION
	 * Currently 0, implement if necessary, (+) voltage biases will make 
	 * that side of the robot move faster and go farther; (-) voltage biases 
	 * will do the opposite. These value should ideally be smaller than 2V.
	 * Start small and increase 0.1V-0.5V at a time until the desired effect is achieved.
	 */
	public final double LEFT_VOLTAGE_BIAS = (IS_COMPETITION_ROBOT? COMPETITION_BIAS_LEFT : 0.0); // compBot:practiceBot // Units, (V), Volts
	public final double RIGHT_VOLTAGE_BIAS = (IS_COMPETITION_ROBOT? COMPETITION_BIAS_RIGHT : 0.0); // compBot:practiceBot // Units, (V), Volts
	
	/*************
	 * CONSTANTS *
	 *************/
	// PNEUMATIC STATES
	public final DoubleSolenoid.Value EXT = Value.kForward;
	public final DoubleSolenoid.Value RET = Value.kReverse;
	public final DoubleSolenoid.Value OFF = Value.kOff;

	// TALON CONTROL MODES
	public final NeutralMode BRAKE_MODE = NeutralMode.Brake;

	// ENCODERS STUFF
	// TODO do we need this? what is this actually supposed to be?
	public final double COUNTS_PER_REV = 4096; // NO_UCD (unused code)

	public final double TIMED_DRIVE_PERCENT = -0.75;// DO NOT CHANGE
	// This is a multiplier that will be computed manually distanceMultiplier * time
	// = distanceDriven (When Robot driving at timedDrivePercent)
	public final double TIMED_DISTANCE_MULTIPLIER = 38.58;// (in/s)

	public final double TIMED_TURN_PERCENT = 0.5;// DO NOT CHANGE
	public final double TIMED_TURN_90_DEG_TIME = 0.70;
	public final double TIMED_TURN_45_DEG_TIME = 0.35;

	// TODO use or remove
	public final double TIMED_LIFT_PERCENT = 0.75; // DO NOT CHANGE // NO_UCD 
	public final double TIMED_LIFT_MULTIPLIER = 22.125;// (in/s)
	//Time to lift the elevator 3ft at timedLiftPercent of available power.
	// TODO use or remove
	public final double TIMED_LIFT_TIME = 28.5/TIMED_LIFT_MULTIPLIER; // NO_UCD 
	//Time to lift the elevator 78" or nearly full height at timedLiftPercent of available power.
	// TODO use or remove
	public final double TIMED_LIFT_FULL_HEIGHT_TIME = 78/TIMED_LIFT_MULTIPLIER; // NO_UCD 
	// length of robot
	public final double ROBOT_LENGTH = 38.5;
	// subtracted from last move on auto
	public final double SAFETY_FACTOR = 4.0;
	
	/*********
	 * PORTS *
	 *********/
	// XBOX PORTS
	public final int XBOX_PORT = 0;
	public final int XBOX_PORT_2 = 1;

	// DRIVETRAIN TALONS (CAN BUS)
	public final int LEFT_DRIVE_MASTER = 3;
	public final int LEFT_DRIVE_SLAVE_1 = 6;
	public final int RIGHT_DRIVE_MASTER = 12;
	public final int RIGHT_DRIVE_SLAVE_1 = 5;

	// INTAKE MOTORS
	public final int LEFT_INTAKE = 1;
	public final int RIGHT_INTAKE = 0;

	// ELEVATOR MOTORS
	public final int ELEVATOR_MASTER = 8;
	public final int ELEVATOR_SLAVE = 7;

	// PNEUMATICS CONTROL MODULE
	public final int PCM_PORT_1 = 1;

	// INTAKE PNEUMATICS
	public final int INTAKE_EXT = 7;
	public final int INTAKE_RET = 0;
	public final int TILT_EXT = (IS_COMPETITION_ROBOT? 6:1);
	public final int TILT_RET = (IS_COMPETITION_ROBOT? 1:6);
	
	// SHIFTING
	public final int SHIFTER_EXT = (IS_COMPETITION_ROBOT? 5:2);
	public final int SHIFTER_RET = (IS_COMPETITION_ROBOT? 2:5);
	
	// SWITCHES
	public final int STAGE_1_BOTTOM = 0;
	public final int STAGE_1_TOP = 1;
	public final int STAGE_2_BUTTOM = 2;
	public final int STAGE_2_TOP = 3;
	public final int SWITCH_LIMIT = 4;
}