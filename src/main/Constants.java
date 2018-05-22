package main;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public interface Constants {
	
	/*************
	 * VARIABLES *
	 *************/
	public final boolean isCompetitionRobot = true;
	
	// FILE OUTPUT PATH
	public final String outputPath = "/home/lvuser"; //"/U";
	
	// FILE NAMES
	public final String LEFT_LeftSwitch = "LEFT_LSwitch.txt";
	public final String LEFT_Scale = "LEFT_Scale.txt";
	public final String LEFT_RightSwitch = "LEFT_RSwitch.txt";
	public final String LEFT_SwitchAndScale = "LEFT_SwitchScale.txt";
	public final String RIGHT_RightSwitch = "RIGHT_RSwitch.txt";
	public final String RIGHT_Scale = "RIGHT_Scale.txt";
	public final String RIGHT_LeftSwitch = "RIGHT_LSwitch.txt";
	public final String RIGHT_SwitchAndScale = "RIGHT_SwitchScale.txt";
	public final String MID_RightSwitch = "MID_RSwitch.txt";
	public final String MID_LeftSwitch = "MID_LSwitch.txt";
	public final String driveBaseline = "Baseline.txt";
	
	public final double kLooperDt = .005;
	
	// Auto Delay Time
	// This is the time that the robot will wait before executing the selected auto in an EDGECASE situation.
	public final int autoDelay = 5; 
	
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
	public final double wheelCircum = 2 * practiceBotLeftWheelRadius * Math.PI;
	public final double testVoltage = 8.0;//Subject to change
	
	// VP Integrated Encoder
	public final double countsPerRev = 1024;
	public final double quadConversionFactor = countsPerRev * 4;
	public final FeedbackDevice magEncoder = FeedbackDevice.CTRE_MagEncoder_Relative;
	public final int timeout = 10;
	public final int pidIdx = 0;
	
	// DRIVETRAIN
	//Place Holder Meaning for every gearRatio turns of the encoder the wheel rotates 1 turn
	public final double lowGearDriveTrainGearRatio = 12.86;//If it turn out to be a 14:40 initial stage 12.24
	public final double highGearDriveTrainGearRatio = 4.4;//If it turns out to be a 14+40 initial stage 4.19
	public final boolean invertPIDHeadingCorrection = true;
	// ELEVATOR LENGTHS 
	// CALIBRATE THESE- ALL ARE IN INCHES
	public final double spindleDiameter = 2; 
	public final double spindleCircum = Math.PI * spindleDiameter;
	//Place Holder Meaning for every gearRatio turns of the encoder the elevator drum rotates 1 turn
	public final double elevatorGearRatio = 1.0; 
	public final double elevatorHeight = 65;
	public final double elevatorTolerance = 1;
	public final double switchHeight = 24; 
	public final double scaleNeutralHeight = 55;
	public final double scaleUpHeight = 64; 
			
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
	public final ControlMode POSITION_MODE = ControlMode.Position;
	public final DemandType ARB_FEED_FORWARD = DemandType.ArbitraryFeedForward;
	
	public final double timedDrivePercent = -1;//DO NOT CHANGE
	//This is a multiplier that will be computed manually distanceMultiplier * time = distanceDriven (When Robot driving at timedDrivePercent)
	public final double timedDistanceMultiplier = 42.414;//38.58;// (in/s)
	public final double driveTrainDistanceTolerance = 1;
	public final double driveTrainAngleTolerance = 1;
	
	public static enum TurnMode {Right, Left};
	public final double timedTurnPercent = 0.5;//DO NOT CHANGE
	public final double timedTurn90degTime = 0.70;
	public final double timedTurn45degTime = 0.35;
	
	public final double timedLiftPercent = 0.75;//DO NOT CHANGE
	public final double timedLiftMultiplier = 41.6;// (in/s)
	//Time to lift the elevator 3ft at timedLiftPercent of available power.
	public final double timedLiftTime = 28.5/timedLiftMultiplier;
	//Time to lift the elevator 78" or nearly full height at timedLiftPercent of available power.
	public final double timedLiftFullHeightTime = 78/timedLiftMultiplier;
	// length of robot
	public final double robotLength = 38.5;
	// subtracted from last move on auto
	public final double safetyFactor = 4.0;
	
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
	public final int LEFT_Intake = 1;
	public final int RIGHT_Intake = 0;
	
	// ELEVATOR MOTORS
	public final int Elevator_Master = 8;
	public final int Elevator_Slave = 7;
	
	// PNEUMATICS CONTROL MODULE
	public final int PCM_Port1 = 1;
	public final int PCM_Port2 = 2;
	
	// INTAKE PNEUMATICS
	public final int INTAKE_EXT = 7;
	public final int INTAKE_RET = 0;
	public final int TILT_EXT = (isCompetitionRobot? 6:1);
	public final int TILT_RET = (isCompetitionRobot? 1:6);
	
	// SHIFTING
	public final int SHIFTER_EXT = (isCompetitionRobot? 5:2);
	public final int SHIFTER_RET = (isCompetitionRobot? 2:5);
	
	// SWITCHES
	public final int STAGE1_Bottom = 0;
	public final int STAGE1_Top = 1;
}