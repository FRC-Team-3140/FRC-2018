package main;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import util.RevRoboticsAnalogPressureSensor;

public interface HardwareAdapter extends Constants{
	//DRIVETRAIN
	public static WPI_TalonSRX leftDriveMaster = new WPI_TalonSRX(LEFT_DRIVE_MASTER);
	public static WPI_TalonSRX leftDriveSlave1 = new WPI_TalonSRX(LEFT_DRIVE_SLAVE_1);
	public static WPI_TalonSRX rightDriveMaster = new WPI_TalonSRX(RIGHT_DRIVE_MASTER);
	public static WPI_TalonSRX rightDriveSlave1 = new WPI_TalonSRX(RIGHT_DRIVE_SLAVE_1);
	 
	//INTAKE
	public static Spark leftIntakeMotor = new Spark(LEFT_INTAKE);
	public static Spark rightIntakeMotor = new Spark(RIGHT_INTAKE);
	
	//ELEVATOR
	public static WPI_TalonSRX elevatorMaster = new WPI_TalonSRX(ELEVATOR_MASTER);
	public static WPI_TalonSRX elevatorSlave = new WPI_TalonSRX(ELEVATOR_SLAVE);
	
	//SENSORS
	public static RevRoboticsAnalogPressureSensor analogPressureSensor1 = new RevRoboticsAnalogPressureSensor(ANALOG_SENSOR);
	public static DigitalInput stage1BottomSwitch = new DigitalInput(STAGE_1_BOTTOM);
	public static DigitalInput stage1TopSwitch = new DigitalInput(STAGE_1_TOP);
	public static DigitalInput stage2BottomSwitch = new DigitalInput(STAGE_2_BUTTOM);
	public static DigitalInput stage2TopSwitch = new DigitalInput(STAGE_2_TOP);
	public static DigitalInput switchHeightSwitch = new DigitalInput(SWITCH_LIMIT);
	
	//PNEUMATICS
	public static DoubleSolenoid shifter = new DoubleSolenoid(PCM_PORT_1, SHIFTER_EXT, SHIFTER_RET);
	public static DoubleSolenoid tilter = new DoubleSolenoid(PCM_PORT_1, TILT_EXT, TILT_RET);
	// TODO use or remove
	public static Compressor comp = new Compressor(PCM_PORT_1); // NO_UCD (unused code)
	public static DoubleSolenoid intakeArm = new DoubleSolenoid(PCM_PORT_1, INTAKE_EXT, INTAKE_RET);
}