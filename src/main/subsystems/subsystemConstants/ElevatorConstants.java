package main.subsystems.subsystemConstants;

public interface ElevatorConstants {
	/*****************
	 * PID CONSTANTS *
	 *****************/
	public int slotIdx = 0;
	public double kP = 0.7;
	public double kI = 0;
	public double kD = 3;
	public double veloFeedForward = 0; // FOR FUTURE USE
	public int toleranceTicks = 30;

	/*************
	 * CONSTANTS *
	 *************/
	// JOYSTICK DEADBAND
	public final double elevatorDeadband = 0.1;

	// SPINDLE AND PULLEY RATIOS. IN INCHES.
	public final double spindleDiameter = 2; 
	public final double spindleCircum = Math.PI * spindleDiameter;
	//Place Holder Meaning for every gearRatio turns of the encoder the elevator drum rotates 1 turn
	public final double elevatorGearRatio = 1.0; 
	
	// HEIGHTS IN INCHES
	public final double elevatorHeight = 65;
	public final double elevatorTolerance = 1;
	public final double switchHeight = 24; 
	public final double scaleNeutralHeight = 55;
	public final double scaleUpHeight = 64; 
	
	/**************
	 * TIMED LIFT *
	 **************/
	public final double timedLiftPercent = 0.75;//DO NOT CHANGE
	public final double timedLiftMultiplier = 41.6;// (in/s)
	//Time to lift the elevator 3ft at timedLiftPercent of available power.
	public final double timedLiftTime = 28.5/timedLiftMultiplier;
	//Time to lift the elevator 78" or nearly full height at timedLiftPercent of available power.
	public final double timedLiftFullHeightTime = 78/timedLiftMultiplier;
}
