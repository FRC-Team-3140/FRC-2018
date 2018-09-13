package main.subsystems.subsystemConstants;

public interface DrivetrainConstants {
	/*****************
	 * PID CONSTANTS *
	 *****************/
	// TOLERANCES
	public final double driveTrainDistanceTolerance = 1;
	public final double driveTrainAngleTolerance = 2;
	
	// SLOT
	public static int slotIdx = 0;
	
	// LEFT DRIVETRAIN PID CONSTANTS
	public static double kPLeft = 0.015; // TODO have fun retuning lmfao 
	public static double kILeft = 0;
	public static double kDLeft = 0.625;
	// old constants: 0.015, 0, 0.625
	
	// RIGHT DRIVETRAIN PID CONSTANTS
	public static double kPRight = 0.015;
	public static double kIRight = 0;
	public static double kDRight = 0.68;
	// old constants: 0.015, 0, 0.68
	
	// TURNING
	public static double kPHeadingDefault = 0.0193;
	public static double kIHeadingDefault = 0;
	public static double kDHeadingDefault = 0.00017; //push to sdb
	// old constants: 0.017, 0.0004
	
	// VELOCITY
	public static double cruiseSpeed = 0; // TODO FIND CRUISE SPEED 
	
	// VELOCITY FEED FORWARD FOR FUTURE USE WHEN MOTION PROFILING
	public static double kLeftVeloFeedForward = 0;
	public static double kRightVeloFeedForward = 0;

	/*************
	 * CONSTANTS *
	 *************/
	// JOYSTICK DEADBANDS
	public final double throttleDeadband = 0.02;
	public final double headingDeadband = 0.02;
	
	// WHEEL MEASUREMENTS
	public final double practiceBotLeftWheelRadius = 2;//Update with real measurements
	public final double practiceBotRightWheelRadius = 2;//Update with real measurements
	public final double competitonBotLeftWheelRadius = 2;//Update with real measurements
	public final double competitonBotRightWheelRadius = 2;//Update with real measurements	
	public final double wheelCircum = 2 * practiceBotLeftWheelRadius * Math.PI;
	
	// GEAR RATIOS
	public final double lowGearDriveTrainGearRatio = 12.86;//If it turn out to be a 14:40 initial stage 12.24
	public final double highGearDriveTrainGearRatio = 4.4;//If it turns out to be a 14+40 initial stage 4.19
	
	// FOR TURNING
	public double kDistanceBetweenWheels = 24; // inches
	public double kTurnCircum = kDistanceBetweenWheels * Math.PI;
	public double kScrubFactor = 1;
	public double kMaxTurnRate = 0.5; // percent output

	/***************
	 * TIMED DRIVE *
	 ***************/
	// DRIVING
	public final double timedDrivePercent = -1;//DO NOT CHANGE
	//This is a multiplier that will be computed manually distanceMultiplier * time = distanceDriven (When Robot driving at timedDrivePercent)
	public final double timedDistanceMultiplier = 42.414;//38.58;// (in/s)
	
	// TURNING
	public final double timedTurnPercent = 0.5;//DO NOT CHANGE
	public final double timedTurn90degTime = 0.70;
	public final double timedTurn45degTime = 0.35;
}
