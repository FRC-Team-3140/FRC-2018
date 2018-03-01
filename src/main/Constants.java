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
	public final boolean isCompetitionMatch = false;
	public final boolean isCompetitionRobot = false;
	
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
	// This is the time that the robot will wait before executing the selected auto in an EDGECASE situation.
	public final int autoDelay = 5; 
	
	// REV ROBOTICS SENSORS
	public final int analogSensor = 0;
	
	// JOYSTICK DEADBANDS
	public final double throttleDeadband = 0.02;
	public final double headingDeadband = 0.02;
	public final double elevatorDeadband = 0.1;
	
	// DRIVEHELPER
//	public final double smoothThrottleDif = 0.1; //Set to max difference you want
	
	// SENSORS
//	public final int sensorTimeoutMs = 10; //timeout for sensor methods. 10ms will allow checking to be performed.	
//	public final int cubeSensor = 0;
	
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
	
	public final double timedDrivePercent = 0.75;//DO NOT CHANGE
	//This is a multiplier that will be computed manually distanceMultiplier * time = distanceDriven (When Robot driving at timedDrivePercent)
	public final double timedDistanceMultiplier = 2.0;// (in/s)
	
	public static enum TurnMode { Right, Left };
	public final double timedTurnPercent = 0.5;//DO NOT CHANGE
	public final double timedTurn90degTime = 2.0;
	public final double timedTurn45degTime = 1.0;
	
	public final double timedLiftPercent = 0.5;
	//Time to lift the elevator 3ft at timedLiftPercent of available power.
	public final double timedLiftTime = 2;
	//Time to lift the elevator 78" or nearly full height at timedLiftPercent of available power.
	public final double timedLiftFullHeightTime = 4;

	
	// ELEVATOR LENGTHS
	/*public final double spindleDiameter = 2; //placeholder
	public final double spindleCircum = Math.PI * spindleDiameter;
	public final double elevatorHeight = 86;  
	public final double elevatorTolerance = 2;
	public final double switchHeight = 24; //set this in encoder units today...
	public final double scaleHeight = 70; 
	public final double nearSetpoint = 12;
	
	// ELEVATOR SPEEDS
	public final double defaultElevatorSpeed = 0.8;
	public final double slowElevatorSpeed = 0.2;
	public final int cruiseVelocity = 125000; //native units of encoder per 100 ms
	public final int acceleration = 62500; //native units of encoder per 100 ms per second
	
	// MOTION MAGIC ELEVATOR STUFF
	public final int elevatorIdx = 0;
	public final int pidIdx = 0;
	public final double fGain = 0.01066 ;// 1023/max speed
	public final double elevator_kP = 0;
	public final double elevator_kI = 0;
	public final double elevator_kD = 0;*/
	
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
	public final int LEFT_Intake = 1;//(isCompetitionRobot? 11:11);// compBot:practiceBot
	public final int RIGHT_Intake = 0;///(isCompetitionRobot? 10:10);// compBot:practiceBot
	
	// ELEVATOR MOTORS
	public final int Elevator_Master = 8;
	public final int Elevator_Slave = 7;
	
	// PNEUMATICS CONTROL MODULE
	public final int PCM_Port1 = 1;
	public final int PCM_Port2 = 2;
	
	// INTAKE PNEUMATICS
	public final int INTAKE_EXT = 7;//(isCompetitionRobot? 0:0);// compBot:practiceBot
	public final int INTAKE_RET = 0;//(isCompetitionRobot? 1:1);// compBot:practiceBot	
	public final int TILT_EXT = 1;//(isCompetitionRobot? 5:5);// compBot:practiceBot
	public final int TILT_RET = 6;//(isCompetitionRobot? 4:4);// compBot:practiceBot
	
	// SHIFTING
	public final int SHIFTER_EXT = 2;//(isCompetitionRobot? 2:2);// compBot:practiceBot
	public final int SHIFTER_RET = 5;//(isCompetitionRobot? 3:3);// compBot:practiceBot
	
	// CLIMB AND RELATED PNEUMATICS
//	public final int PTO_EXT = 6;
//	public final int PTO_RET = 7;
//	public final int FORK_EXT = 1;
//	public final int FORK_RET = 2;
//	public final int HOOK_EXT = 3;
//	public final int HOOK_RET = 4;
//	
	// SWITCHES
//	public final int STAGE1_Bottom = 8;
//	public final int STAGE1_Top = 2;
//	public final int STAGE2_Bottom = 1;
//	public final int STAGE2_Top = 3;
//	public final int INTAKE_Switch = 5;

	//Driver Alert
//	public final double alertOnTime = 0.125;
//	public final double alertOffTime = 0.125;
//	public final int driverAlertsPort = 10;
//	public final int DriverAlert_DigiOut = 2;	
}