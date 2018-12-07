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
	public final boolean invertPIDHeadingCorrection = false;
	
	// FILE OUTPUT PATH
	public final String outputPath = "/home/lvuser/";
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
	
	public final double kLooperDt = .1;
	
	// Auto Delay Time
	// This is the time that the robot will wait before executing the selected auto in an EDGECASE situation.
	public final int autoDelay = 5; 
	
	// REV ROBOTICS SENSORS
	public final int analogSensor = 0;
	
	// TALON VOLTAGE COMPENSATION
	public final double voltageCompensationVoltage = 12.0;
	
	//ROBOT BIAS TEST CONSTANTS
	public final double testVoltage = 8.0;//Subject to change
	
	// VP Integrated Encoder
	public final double countsPerRev = 1024;
	public final double quadConversionFactor = countsPerRev * 4;
	public final FeedbackDevice magEncoder = FeedbackDevice.CTRE_MagEncoder_Relative;
	public final int timeout = 10;
	public final int pidIdx = 0;

			
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
	
	public static enum TurnMode {Right, Left};
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