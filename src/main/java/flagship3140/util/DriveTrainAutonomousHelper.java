package main.java.flagship3140.util;

import main.java.flagship3140.Constants;
import main.java.flagship3140.Hardware;

public class DriveTrainAutonomousHelper implements Constants, Hardware{
	/*private double leftVelocityTarget, leftPositionTarget;
	private double rightVelocityTarget, rightPositionTarget;
	private double headingTarget;
	private double leftVelocityOut, rightVelocityOut;
	
	public DriveTrainAutonomousHelper() {
		Robot.dt.resetGyro();
	}
	
	public void setLeftTargets(double velocity) {
		this.leftVelocityTarget = velocity;
	}	
	
	public void setRightTargets(double velocity) {
		this.rightVelocityTarget = velocity;
			
	}
	public void setLeftPositionTarget(double x, double y) {
		this.leftPositionTarget = getDistanceLeft(x,y);
	}
	public void setRightPositionTarget(double x, double y) {
		this.rightPositionTarget = getDistanceRight(x,y);
		
	}
	public void setHeadingTargets(double heading) {
		this.headingTarget = heading;		
	}
	//im gay
	
	public void doTheMath() {
		double leftPercentError =   (leftVelocityTarget - Robot.dt.getLeftVelocity())/ leftVelocityTarget;
		double rightPercentError =   (rightVelocityTarget - Robot.dt.getRightVelocity())/ rightVelocityTarget;
		double leftDispPercentError = (leftPositionTarget - Robot.dt.getDistanceTraveledLeft())/ leftPositionTarget;
		double rightDispPercentError = (rightPositionTarget - Robot.dt.getDistanceTraveledRight())/ rightPositionTarget;
		double headingPercentError = (headingTarget - Robot.dt.getGyro().getYaw())/ headingTarget;
		if(headingTarget < 0.4)
			headingPercentError = 0.0;
		double left = leftVelocityTarget;// / 5.85;// - Robot.dt.getLeftVelocity())/leftVelocityTarget); //* leftWheelVelocityKP);// + ((leftPositionTarget - Robot.dt.getDistanceTraveledLeft()) * leftWheelPositionKP);
		double right = rightVelocityTarget;// / 5.85;//- Robot.dt.getRightVelocity())/rightVelocityTarget); //* rightWheelVelocityKP);// + ((rightPositionTarget - Robot.dt.getDistanceTraveledRight()) * rightWheelPositionKP);	
		double heading = headingTarget;// - Robot.dt.getGyro().getYaw()) * headingControllerKP;
		//System.out.println(rightVelocityTarget + " , " + Robot.dt.getRightVelocity());
		//VOODO HAPPENS HERE!
		leftVelocityOut = (left + leftPercentError + leftDispPercentError)/(TrajectoryDriveController.voltageStart*0.54166);// + heading; 
		rightVelocityOut = (right + rightPercentError + rightDispPercentError)/(TrajectoryDriveController.voltageStart*0.54166);///5.85;// - heading;
		System.out.println((leftPercentError+rightPercentError)/2 + " , " + (leftDispPercentError + rightDispPercentError)/2);//+ " , " + headingPercentError/2);
	}
	public double getDistanceLeft(double x, double y) {
		return Math.sqrt(Math.pow((0 - x),2) + Math.pow((0 - y),2));
	}
	public double getDistanceRight(double x, double y) {
		return Math.sqrt(Math.pow((0 - x),2) + Math.pow((0 - y),2));
	}
	public double getAdjustedLeftVelocity() {
		return leftVelocityOut;
	}
	
	public double getAdjustedRightVelocity() {
		return rightVelocityOut;
	}*/
}
