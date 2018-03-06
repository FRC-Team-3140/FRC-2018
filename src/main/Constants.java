package main;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public interface Constants {

	/*************
	 * VARIABLES *
	 *************/
	public final boolean isCompetitionMatch = true;
	public final boolean isCompetitionRobot = true;

	// FILE OUTPUT PATH
	public final String outputPath = "/home/lvuser"; // USB output path: /U

	// FILE NAMES
	public final String LEFT_LeftSwitch = "LEFT_LSwitch";
	public final String LEFT_Scale = "LEFT_Scale";
	public final String LEFT_RightSwitch = "LEFT_RSwitch";
	public final String LEFT_SwitchAndScale = "LEFT_SwitchScale";
	public final String RIGHT_RightSwitch = "RIGHT_RSwitch";
	public final String RIGHT_Scale = "RIGHT_Scale";
	public final String RIGHT_LeftSwitch = "RIGHT_LSwitch";
	public final String RIGHT_SwitchAndScale = "RIGHT_SwitchScale";
	public final String MID_RightSwitch = "MID_RSwitch";
	public final String MID_LeftSwitch = "MID_LSwitch";
	public final String driveBaseline = "Baseline";

	// Auto Delay Time
	// This is the time that the robot will wait before executing the selected auto
	// in an EDGECASE situation.
	public final int autoDelay = 5;

	// REV ROBOTICS SENSORS
	public final int analogSensor = 0;

	// JOYSTICK DEADBANDS
	public final double throttleDeadband = 0.02;
	public final double headingDeadband = 0.02;
	public final double elevatorDeadband = 0.1;

	// TALON VOLTAGE COMPENSATION
	public final double voltageCompensationVoltage = 12.0;

	// LOOPER CONSTANTS
	public final double kLooperDt = 0.005;// 0.005

	/*************
	 * CONSTANTS *
	 *************/
	// PNEUMATIC STATES
	public final DoubleSolenoid.Value EXT = Value.kForward;
	public final DoubleSolenoid.Value RET = Value.kReverse;
	public final DoubleSolenoid.Value OFF = Value.kOff;

	// TALON CONTROL MODES
	public final ControlMode SLAVE_MODE = ControlMode.Follower;
	public final ControlMode PERCENT_VBUS_MODE = ControlMode.PercentOutput;
	public final NeutralMode BRAKE_MODE = NeutralMode.Brake;

	// ENCODERS STUFF
	public final double countsPerRev = 4096; // what is this actually supposed to be?
	public final FeedbackDevice encoder = FeedbackDevice.CTRE_MagEncoder_Relative;

	public final double timedDrivePercent = -0.75;// DO NOT CHANGE
	// This is a multiplier that will be computed manually distanceMultiplier * time
	// = distanceDriven (When Robot driving at timedDrivePercent)
	public final double timedDistanceMultiplier = 38.58;// (in/s)

	public static enum TurnMode {
		Right, Left
	};

	public final double timedTurnPercent = 0.5;// DO NOT CHANGE
	public final double timedTurn90degTime = 0.70;
	public final double timedTurn45degTime = 0.35;

	public final double timedLiftPercent = 0.75;// DO NOT CHANGE
	public final double timedLiftMultiplier = 22.125;// (in/s)
	// Time to lift the elevator 3ft at timedLiftPercent of available power.
	public final double timedLiftTime = 36 / timedLiftMultiplier;
	// Time to lift the elevator 78" or nearly full height at timedLiftPercent of
	// available power.
	public final double timedLiftFullHeightTime = 78 / timedLiftMultiplier;

	/*********
	 * PORTS *
	 *********/
	// XBOX PORTS
	public final int Xbox_Port = 0;
	public final int Xbox_Port2 = 1;

	// DRIVETRAIN TALONS (CAN BUS)
	public final int LEFT_Drive_Master = 3;
	public final int LEFT_Drive_Slave1 = 6;
	public final int RIGHT_Drive_Master = 12;
	public final int RIGHT_Drive_Slave1 = 5;
	public final int LEFT_Drive_Slave2 = 9;
	public final int RIGHT_Drive_Slave2 = 4;

	// INTAKE MOTORS
	public final int LEFT_Intake = 1;// (isCompetitionRobot? 11:11);// compBot:practiceBot
	public final int RIGHT_Intake = 0;/// (isCompetitionRobot? 10:10);// compBot:practiceBot

	// ELEVATOR MOTORS
	public final int Elevator_Master = 8;
	public final int Elevator_Slave = 7;

	// PNEUMATICS CONTROL MODULE
	public final int PCM_Port1 = 1;
	public final int PCM_Port2 = 2;

	// INTAKE PNEUMATICS
	public final int INTAKE_EXT = 7;
	public final int INTAKE_RET = 0;
	public final int TILT_EXT = 6;
	public final int TILT_RET = 1;

	// SHIFTING
	public final int SHIFTER_EXT = 5;
	public final int SHIFTER_RET = 2;
}