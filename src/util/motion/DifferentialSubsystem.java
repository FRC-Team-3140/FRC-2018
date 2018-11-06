package util.motion;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;
import util.ChezyMath;
import util.DriveHelper;

public class DifferentialSubsystem {
	
	private DriveHelper driveHelper = new DriveHelper(7.5);
	private WPI_TalonSRX left, right;
	private double kP = 0;
	private double kD = 0;
	private double kI = 0;
	public static double kPHeading = 0;
	public static double kIHeading = 0;
	public static double kDHeading = 0; //push to sdb
	
	private static double lastHeadingIntegral = 0;
	private static double lastHeadingError = 0;
	private static double lastTime = 0;
	
	//Timer for PID
	private Timer timer = new Timer();
	
	//SENSORS
	private static AHRS NavX;	

	public DifferentialSubsystem(WPI_TalonSRX left, WPI_TalonSRX right) {
		this.left = left;
		this.right = right;
		
		try {
			/* Communicate w/navX-MXP via the MXP SPI Bus.                                     */
	        /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
	        /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
	        NavX = new AHRS(SPI.Port.kMXP); 
		} catch (RuntimeException ex) {
	          DriverStation.reportError("Error instantiating navX-MXP:  " + ex.getMessage(), true);
	    }
	}
	
	
	/**
	 * Uses cascaded loop to drive straight to a certain target position. So far, angle can only be 0
	 * @param ticks  ticks forward to drive
	 * @param angle  target angle to end up at
	 */
	public void driveStraightPID(double ticks) {

		double t = timer.get();
		double dt = t - lastTime;

		double heading = getHeading();
		double headingError = ChezyMath.getDifferenceInAngleDegrees(heading, 0);
		double derivative = ChezyMath.getDifferenceInAngleDegrees(lastHeadingError, headingError) / dt;
		double integral = lastHeadingIntegral + (headingError * dt);
		double gyroCorrection = headingError * kPHeading + integral * kIHeading + derivative * kDHeading;

		left.set(ControlMode.Position, ticks, DemandType.ArbitraryFeedForward, gyroCorrection);
		right.set(ControlMode.Position, ticks, DemandType.ArbitraryFeedForward, -gyroCorrection);

		lastHeadingError = headingError;
		lastHeadingIntegral = integral;
		lastTime = t;
	}
	
	public double getHeading() {
		return NavX.getYaw();
	}
}
