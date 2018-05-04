// Copyright (c) 2018 FIRST 3140. All Rights Reserved.

package main;

import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import interfacesAndAbstracts.ImprovedRobot;
import main.subsystems.DriverCamera;
import main.subsystems.Drivetrain;

public class Robot extends ImprovedRobot {
	public static Drivetrain dt;
	//public static DriverCamera dc;
	public static OI oi;
	
	@Override
	public void robotInit() {
		// OI must be at end
		dt = new Drivetrain();
		oi = new OI();
		//dc = new DriverCamera();
	}
	
	@Override
	public void disabledInit() {
	}
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}

	@Override
	public void autonomousInit() {
	}
	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}

	@Override
	public void teleopInit() {
	}	

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}
	
	@Override
	public void testPeriodic() {
		allPeriodic();
	}
	
	public void allPeriodic() {
		//Runtime runtime = Runtime.getRuntime();
		SmartDashboard.updateValues();
		dt.check();
		oi.check();
		//SmartDashboard.putNumber("Free memory", runtime.freeMemory());
		//SmartDashboard.putNumber("Total memory", runtime.totalMemory());
		//SmartDashboard.putData(Scheduler.getInstance());
		// DriveTrain Encoders
		SmartDashboard.putNumber("DriveTrain Left Encoder Distance", dt.getLeftEncoderDistanceTravelled());
		SmartDashboard.putNumber("DriveTrain Left Input", leftDriveMaster.get());
		SmartDashboard.putNumber("DriveTrain Right Encoder Distance", dt.getRightEncoderDistanceTravelled());
		SmartDashboard.putNumber("DriveTrain Right Input", rightDriveMaster.get());
		SmartDashboard.putNumber("DriveTrain Distance", dt.getDistanceTravelled());
		// DriveTrain Gyro
		SmartDashboard.putNumber("NavX Heading", dt.getHeading());
	}
}
