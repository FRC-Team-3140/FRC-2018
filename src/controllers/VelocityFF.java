package controllers;

import loopController.Loop;
import main.Constants;
import main.commands.drivetrain.DriveVeloFF;
import main.subsystems.subsystemConstants.DrivetrainConstants;
import util.EncoderHelper;
import util.motion.TrapezoidalProfileFactory;

public class VelocityFF implements Loop, Constants, DrivetrainConstants{
	
	private static boolean canDrive =false;
	private static double[][] currentProf;
	private static int i = 0;
	private double targetVelo;

	public VelocityFF() {
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoop() {	
		if (canDrive)execute();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
	}
	
	public void execute() {
		targetVelo = currentProf[i][1];
		DriveVeloFF drive = new DriveVeloFF(targetVelo);
		drive.start();
		System.out.println(targetVelo);
		i += 10;
	}
	
	public static void start(double in) {
		double units = EncoderHelper.inchesToEncoderTicks(in, wheelCircum, quadConversionFactor);
		//System.out.println(arg0);
		System.out.println(units);
		currentProf = TrapezoidalProfileFactory.getProfile(units, cruiseSpeed, RAMP_RATE_UNITS_100MS_S);
//		for(int x = 0; x <currentProf.length; x++) System.out.println(currentProf[x][1]);
		i = 0;
		canDrive = true;
	}

}
