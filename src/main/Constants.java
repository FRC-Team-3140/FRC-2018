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
	public final boolean isCompetitionRobot = true;
	
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
    public static double straightDriveKp = 0.025;

	/*************
	 * CONSTANTS *
	 *************/
	// TALON CONTROL MODES
	public final ControlMode SLAVE_MODE = ControlMode.Follower;
	public final ControlMode PERCENT_VBUS_MODE = ControlMode.PercentOutput;
	public final NeutralMode BRAKE_MODE = NeutralMode.Brake;

	public final double driveTrainDistanceTolerance = 1;
	public final double driveTrainAngleTolerance = 2.5;
	
	// length of robot
	public final double robotLength = 38.5;
	// subtracted from last move on auto
	public final double safetyFactor = 4.0;
	
	/*********
	 * PORTS *
	 *********/	
	// XBOX PORTS
	public final int Xbox_Port = 0;
	
	// DRIVETRAIN TALONS (CAN BUS)
	public final int LEFT_Drive_Master = 3;
	public final int LEFT_Drive_Slave1 = 6;
	public final int RIGHT_Drive_Master = 12;
	public final int RIGHT_Drive_Slave1 = 5;
	public final int LEFT_Drive_Slave2 = 9;
	public final int RIGHT_Drive_Slave2 = 4;
	
	// PNEUMATICS CONTROL MODULE
	public final int PCM_Port1 = 1;
	
}