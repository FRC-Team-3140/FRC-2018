package main;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public interface Constants {

	/*************
	 * VARIABLES *
	 *************/
	// TODO use isCompetitionMatch
	public final boolean isCompetitionMatch = true; // NO_UCD
	public final boolean isCompetitionRobot = false;
	// FILE OUTPUT PATH
	public final String outputPath = "/home/lvuser"; // USB output path: /U

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
	public final int autoDelay = 5; // NO_UCD (unused code)

	// REV ROBOTICS SENSORS
	public final int analogSensor = 0;

	// JOYSTICK DEADBANDS
	public final double throttleDeadband = 0.02;
	public final double headingDeadband = 0.02;
	public final double elevatorDeadband = 0.1;

	// TALON VOLTAGE COMPENSATION
	public final double voltageCompensationVoltage = 12.0;
	
	//ROBOT BIAS TEST CONSTANTS
	public final double practiceBotLeftWheelRadius = 2;//Update with real measurements
	public final double practiceBotRightWheelRadius = 2;//Update with real measurements
	public final double competitonBotLeftWheelRadius = 2;//Update with real measurements
	public final double competitonBotRightWheelRadius = 2;//Update with real measurements

	
	public final double testVoltage = 8.0;//Subject to change
	public final double practiceBotLeftFreeRPMAtTestVoltage = 1000.0;//Update with real measurement (+ value only)
	public final double practiceBotRightFreeRPMAtTestVoltage = 1000.0;//Update with real measurement (+ value only)
	public final double competitonBotLeftFreeRPMAtTestVoltage = 1000.0;//Update with real measurement (+ value only)
	public final double competitonBotRightFreeRPMAtTestVoltage = 1000.0;//Update with real measurement (+ value only)
	
	//ROBOT BIAS CONSTANTS COMPUTATION
	public final double competitionBiasLeft = ((testVoltage*competitonBotLeftWheelRadius*competitonBotLeftFreeRPMAtTestVoltage) - 
												(testVoltage*practiceBotLeftWheelRadius*practiceBotLeftFreeRPMAtTestVoltage)) / 
												(practiceBotLeftWheelRadius*practiceBotLeftFreeRPMAtTestVoltage);
	
	public final double competitionBiasRight = ((testVoltage*competitonBotRightWheelRadius*competitonBotRightFreeRPMAtTestVoltage) - 
												(testVoltage*practiceBotRightWheelRadius*practiceBotRightFreeRPMAtTestVoltage)) / 
												(practiceBotRightWheelRadius*practiceBotRightFreeRPMAtTestVoltage);
	
	
	// BIAS VOLTAGES FOR DIFFERENCE BETWEEN ROBOTS COMPENSATION
	//Currently 0, implement if necessary, (+) voltage biases will make 
	//that side of the robot move faster and go farther; (-) voltage biases 
	//will do the opposite. These value should ideally be smaller than 2V.
	//Start small and increase 0.1V-0.5V at a time until the desired effect is achieved.
	public final double leftVoltageBias =(isCompetitionRobot? competitionBiasLeft : 0.0);// compBot:practiceBot // Units, (V), Volts
	public final double rightVoltageBias = (isCompetitionRobot? competitionBiasRight : 0.0);// compBot:practiceBot // Units, (V), Volts

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
	public final double countsPerRev = 4096; // NO_UCD (unused code)

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

	// TODO use or remove
	public final double timedLiftPercent = 0.75; // DO NOT CHANGE // NO_UCD 
	public final double timedLiftMultiplier = 22.125;// (in/s)
	//Time to lift the elevator 3ft at timedLiftPercent of available power.
	// TODO use or remove
	public final double timedLiftTime = 28.5/timedLiftMultiplier; // NO_UCD 
	//Time to lift the elevator 78" or nearly full height at timedLiftPercent of available power.
	// TODO use or remove
	public final double timedLiftFullHeightTime = 78/timedLiftMultiplier; // NO_UCD 

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

	// INTAKE MOTORS
	public final int LEFT_Intake = 1;
	public final int RIGHT_Intake = 0;

	// ELEVATOR MOTORS
	public final int Elevator_Master = 8;
	public final int Elevator_Slave = 7;

	// PNEUMATICS CONTROL MODULE
	public final int PCM_Port1 = 1;

	// INTAKE PNEUMATICS
	public final int INTAKE_EXT = 7;
	public final int INTAKE_RET = 0;
	public final int TILT_EXT = (isCompetitionRobot? 6:1);
	public final int TILT_RET = (isCompetitionRobot? 1:6);
	
	// SHIFTING
	public final int SHIFTER_EXT = (isCompetitionRobot? 5:2);
	public final int SHIFTER_RET = (isCompetitionRobot? 2:5);
}