package main;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import lib.joystick.XboxController;
import util.RevRoboticsAnalogPressureSensor;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;

public interface HardwareAdapter extends Constants{
	//OI
	public static XboxController xbox = new XboxController(Xbox_Port);
	public static XboxController xbox2 = new XboxController(Xbox_Port2);
	
	//DRIVETRAIN
	public static WPI_TalonSRX leftDriveMaster = new WPI_TalonSRX(LEFT_Drive_Master);
	public static WPI_TalonSRX leftDriveSlave1 = new WPI_TalonSRX(LEFT_Drive_Slave1);
	public static WPI_TalonSRX rightDriveMaster = new WPI_TalonSRX(RIGHT_Drive_Master);
	public static WPI_TalonSRX rightDriveSlave1 = new WPI_TalonSRX(RIGHT_Drive_Slave1);
	 
	//INTAKE
	public static Spark leftIntakeMotor = new Spark(LEFT_Intake);
	public static Spark rightIntakeMotor = new Spark(RIGHT_Intake);
	
	//ELEVATOR
	public static WPI_TalonSRX elevatorMaster = new WPI_TalonSRX(Elevator_Master);
	public static WPI_TalonSRX elevatorSlave = new WPI_TalonSRX(Elevator_Slave);
	
	//SENSORS
	public static RevRoboticsAnalogPressureSensor analogPressureSensor1 = new RevRoboticsAnalogPressureSensor(analogSensor);
	public static DigitalInput stage1BottomSwitch = new DigitalInput(STAGE1_Bottom);
	public static DigitalInput stage1TopSwitch = new DigitalInput(STAGE1_Top);
		
	//PNEUMATICS
	public static DoubleSolenoid shifter = new DoubleSolenoid(PCM_Port1, SHIFTER_EXT, SHIFTER_RET);
	public static DoubleSolenoid tilter = new DoubleSolenoid(PCM_Port1, TILT_EXT, TILT_RET);
	public static Compressor comp = new Compressor(PCM_Port1);
	public static DoubleSolenoid intakeArm = new DoubleSolenoid(PCM_Port1, INTAKE_EXT, INTAKE_RET);
}