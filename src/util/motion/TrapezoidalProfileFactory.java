package util.motion;

import java.util.ArrayList;
import java.util.List;

public class TrapezoidalProfileFactory {
	
	/*
	 * Writes an array of positions and speeds | [position][speed]
	 * Each row in the array has an interval of 10ms
	 * Speed is in units per 100 ms and position is in encoder ticks
	 * 
	 * @param distanceUnits  Distance to drive in native units aka encoder ticks
	 * @param cruiseVelocity The targeted velocity to cruise at in units per 100 ms
	 * @param acceleration   The acceleration to hit the target velocity in units per 100ms per second
	 */
	public static double[][] getProfile(double distanceUnits, double cruiseVelocity, double acceleration) {
		List<Double> speedList = new ArrayList<Double>();
		List<Double> posList = new ArrayList<Double>();

		double acceleration10ms = acceleration / 100; // units per 100ms per 10ms
		double timeToAccel10ms = cruiseVelocity / acceleration10ms; // time it takes get to cruise velocity in 10ms
		double cruiseVelocity10ms = cruiseVelocity / 10; // cruise velo in units per 10ms
		double totalAccelDistanceUnits = cruiseVelocity10ms * timeToAccel10ms; //distance traveled during accel and deccel
		
		int i = 0;
		// Writes a velocity starting from 0 that increments until it gets to the cruise velocity
		for(double s = 0; s < cruiseVelocity; s = s + acceleration10ms) {
			speedList.add(i, s);
			i++;
		}
		
		if(distanceUnits >= totalAccelDistanceUnits) {
			double cruiseDistanceUnits = distanceUnits - totalAccelDistanceUnits; // Distance to travel during cruise in units
			int timeToCruise10ms = (int) Math.round(cruiseDistanceUnits / cruiseVelocity10ms); // amount of time in 10ms to cruise
			for(int s = 0; s < timeToCruise10ms; s++) {
				speedList.add(i, cruiseVelocity);
				i++;
			}
		}
		else {
			System.out.println("too small distance"); //TODO make triangular motion profile for this case\
			System.out.println("total accel distance: " + totalAccelDistanceUnits);
			double[][] zero = new double[1][2];
			zero[0][0] = 0;
			zero[0][1] = 0;
			return zero;
		}
		
		// Writes the de-acceleration velocities with same method as above
		for(double s = cruiseVelocity; s >= 0; s = s - acceleration10ms) {
			speedList.add(i, s);
			i++;
		}
		
		double pos = 0; // Variable to help write the pos array
		for(int m = 0; m < speedList.size(); m++) {
			pos = pos + (speedList.get(m) / 10);
			posList.add(m, pos);
		}
		
		double[][] profile = new double[speedList.size()][2];
		for(int m = 0; m < speedList.size(); m++) { 
			profile[m][0] = posList.get(m);
			profile[m][1] = speedList.get(m);
		}
		System.out.println("T-Profile Factory generated");
		return profile;
	}
}
