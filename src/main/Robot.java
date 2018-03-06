// Copyright (c) 2018 FIRST 3140. All Rights Reserved.

package main;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import interfacesAndAbstracts.ImprovedRobot;
import main.commands.auto.Baseline;
import main.commands.auto.CenterToLeftSwitch;
import main.commands.auto.CenterToRightSwitch;
import main.commands.auto.DoNothing;
import main.commands.auto.LeftToLeftSwitch;
import main.commands.auto.ResetForTeleop;
import main.commands.auto.RightToRightSwitch;
import main.subsystems.DriverCamera;
import main.subsystems.Drivetrain;
import main.subsystems.Elevator;
import main.subsystems.Intake;
import main.subsystems.Pneumatics;

public class Robot extends ImprovedRobot {
	public static Drivetrain dt;
	public static Pneumatics pn;
	public static Intake in;
	public static Elevator el;
	public static DriverCamera dc;
	public static OI oi;

	// AUTO LOGIC
	private enum StartPos {
		LEFT, CENTER, RIGHT
	}

	private enum RobotAction {
		DO_NOTHING, BASELINE, SWITCH
	}

	private static SendableChooser<RobotAction> autoChooser;
	private static SendableChooser<StartPos> startPos;

	private static Command autoCommand;

	@Override
	public void robotInit() {
		// TODO what is this: OI must be at end
		dt = new Drivetrain();
		pn = new Pneumatics();
		in = new Intake();
		el = new Elevator();
		oi = new OI();
		dc = new DriverCamera();

		/* AUTO EXPLAINATION:
		 * EDGECASE- The case where the robot is in the left or right position and neither the switch nor the scale line up.
		 * Do Nothing- Robot won't move during auto
		 * EDGECASE_DoNothing- Robot will act upon given game data except in the Edge Case; in which case it does nothing.
		 * EDGECASE_Baseline- Robot will act upon given game data except in the Edge Case; in which case it crosses the baseline.
		 * EDGECASE_DelayedSwitch- Robot will act upon given game data except in the Edge Case; in which case it waits a specified
		 * 							length of time and then places a cube in the switch.
			SmartDashboard.putString("Do nothing", "Doesn't move during auto");
			SmartDashboard.putString("Edgecases", "When the robot is in the left or right starting position and both the scale" + 
										"and switch are in the opposite position");
			SmartDashboard.putString("No edgecase", "If edgecase doesn't occur, the robot will do an auto depending on starting" +
										"position and switch/scale lineup as long as Do Nothing is NOT chosen");
			SmartDashboard.putString("If edgecase occurs", "If the edgecase occurs, then the robot will either do nothing," +
										"cross baseline, or score in the switch after a 5-sec delay depending on the edgecase" +
										"mode that is chosen");
		*/
		// Auto modes
		autoChooser = new SendableChooser<>();
		autoChooser.addDefault("Do Nothing", RobotAction.DO_NOTHING);
		autoChooser.addObject("Baseline", RobotAction.BASELINE);
		autoChooser.addObject("Switch", RobotAction.SWITCH);
		// Starting Pos
		startPos = new SendableChooser<>();
		startPos.addDefault("Left", StartPos.LEFT);
		startPos.addObject("Center", StartPos.CENTER);
		startPos.addObject("Right", StartPos.RIGHT);
		SmartDashboard.putData("Starting Position", startPos);
		SmartDashboard.putData("Auto Mode", autoChooser);
	}

	@Override
	public void disabledInit() {
		if (autoCommand != null && autoCommand.isRunning())
			autoCommand.cancel();
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}

	@Override
	public void autonomousInit() {
		StartPos start_pos;
		RobotAction robot_act;
		String gmsg = DriverStation.getInstance().getGameSpecificMessage();

		while (gmsg == null || gmsg.length() != 3) {
			gmsg = DriverStation.getInstance().getGameSpecificMessage();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		System.out.println("message" + gmsg);
		System.out.println("auto" + autoChooser.getSelected());
		System.out.println("pos" + startPos.getSelected());

		boolean leftSwitch = gmsg.charAt(0) == 'L';
		boolean leftScale = gmsg.charAt(1) == 'L';

		start_pos = startPos.getSelected();
		robot_act = autoChooser.getSelected();

		if (robot_act == RobotAction.DO_NOTHING)
			autoCommand = new DoNothing();
		else if (robot_act == RobotAction.BASELINE)
			autoCommand = new Baseline();
		else {
			if (start_pos == StartPos.LEFT && leftSwitch)
				autoCommand = new LeftToLeftSwitch();
			else if (start_pos == StartPos.LEFT && leftScale)
				autoCommand = new Baseline();
			else if (start_pos == StartPos.CENTER && leftSwitch)
				autoCommand = new CenterToLeftSwitch();
			else if (start_pos == StartPos.CENTER && !leftSwitch)
				autoCommand = new CenterToRightSwitch();
			else if (start_pos == StartPos.RIGHT && !leftSwitch)
				autoCommand = new RightToRightSwitch();
			else if (start_pos == StartPos.RIGHT && !leftScale)
				autoCommand = new Baseline();
			else
				autoCommand = new Baseline();
		}
		if (autoCommand != null)
			autoCommand.start();
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}

	@Override
	public void teleopInit() {
		new ResetForTeleop().start();
		if (autoCommand != null && autoCommand.isRunning())
			autoCommand.cancel();
	}

	@Override
	public void teleopPeriodic() {
		Runtime runtime = Runtime.getRuntime();

		// SmartDashboard stuff goes here
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Free memory", runtime.freeMemory());
		SmartDashboard.putNumber("Total memory", runtime.totalMemory());
		SmartDashboard.putNumber("Pressure: ", HardwareAdapter.analogPressureSensor1.value());
		allPeriodic();
	}

	@Override
	public void testPeriodic() {
		allPeriodic();
	}

	public void allPeriodic() {
		SmartDashboard.updateValues();
		dt.check();
		pn.check();
		in.check();
		el.check();
		oi.check();
		SmartDashboard.putNumber("Left voltage", dt.getLeftVoltage());
		SmartDashboard.putNumber("Right voltage", dt.getRightVoltage());
	}
}
