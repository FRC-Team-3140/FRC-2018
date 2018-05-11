package main.java.flagship3140.util;

public class PlanPath {
	private double dispX;
	private double dispY;
	
	public PlanPath(double dispX, double dispY) {
		this.dispX = dispX;
		this.dispY = dispY;
	}
	
	//Returns the angle that the robot must turn towards before driving the hypotenuse and turn away from after
	public double getAngle() {
		return Math.atan((double)2/3*dispY/dispX);
	}
	
	//Approximates the displacement X and Y delta between the robot and the target
	public double getHypotenuse() {
		return Math.sqrt(Math.pow(dispX, 2) + Math.pow((double)2/3*dispY, 2));
		
	}
	
	//Approximates the last distance that must be travelled to after turning back to 
	public double getDisplacementToFinalDest(double collisionTolerance) {
		return (double)1/3*dispY - collisionTolerance;
	}
}
