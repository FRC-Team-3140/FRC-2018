// Copyright (c) 2018 FIRST 3140. All Rights Reserved.

package main;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import interfacesAndAbstracts.ImprovedRobot;
import main.commands.auto.Baseline;
import main.commands.auto.CenterToLeftSwitch;
import main.commands.auto.CenterToRightSwitch;
import main.commands.auto.DoNothing;
import main.commands.auto.LeftToLeftScale;
import main.commands.auto.LeftToLeftSwitch;
import main.commands.auto.ResetForTeleop;
import main.commands.auto.RightToRightScale;
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
	private enum StartPos {LEFT, CENTER, RIGHT}
	private enum RobotAction {DO_NOTHING, BASELINE, SWITCH, SCALE}
	public static StartPos start_pos;
	public static RobotAction robot_act;
	private static SendableChooser<RobotAction> autoChooser;
	private static SendableChooser<StartPos> startPos;
	private static Command autoCommand;
	
	class AutoCommandGroup extends CommandGroup {
		public AutoCommandGroup(Command auto, boolean reset, boolean moveDown) {
			addSequential(auto);
			if(reset) addSequential(new ResetForTeleop(moveDown));
		}
	}
	
	@Override
	public void robotInit() {
		// OI must be at end
		dt = new Drivetrain();
		pn = new Pneumatics();
		in = new Intake();
		el = new Elevator();
		oi = new OI();
		dc = new DriverCamera();
		// Auto modes
		autoChooser = new SendableChooser<>();
		autoChooser.addDefault("Do Nothing", RobotAction.DO_NOTHING);
		autoChooser.addObject("Baseline", RobotAction.BASELINE);
		autoChooser.addObject("Switch Priority", RobotAction.SWITCH);
		autoChooser.addObject("Scale Priority", RobotAction.SCALE);
		// Starting Pos
		startPos = new SendableChooser<>();
		startPos.addDefault("Left", StartPos.LEFT);
		startPos.addObject("Center", StartPos.CENTER);
		startPos.addObject("Right", StartPos.RIGHT);
		SmartDashboard.putData("Starting Position", startPos);
		SmartDashboard.putData("Auto Mode", autoChooser);
		
		//Robot Self Test
		SmartDashboard.putData("Robot Self Test", new RobotSelfTest());
	}
	
	@Override
	public void disabledInit() {
		if(autoCommand != null && autoCommand.isRunning())
			autoCommand.cancel();
	}
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}

	@Override
	public void autonomousInit() {		
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
			
		boolean isSwitch = false;
		start_pos = startPos.getSelected();
		robot_act = autoChooser.getSelected();
		
		if(robot_act == RobotAction.DO_NOTHING)//Do Nothing
			autoCommand = new DoNothing();
		else if(robot_act == RobotAction.BASELINE)//Baseline
			autoCommand = new Baseline();
		else if(robot_act == RobotAction.SWITCH){//Priority Switch
			if(start_pos == StartPos.LEFT) {
				if(leftSwitch) {
					isSwitch = true;
					autoCommand = new LeftToLeftSwitch();
				}
				//else if(leftScale) autoCommand = new LeftToLeftScale();
				else autoCommand = new Baseline();					
			}
			else if(start_pos == StartPos.CENTER) {
				isSwitch = true;
				if(leftSwitch) autoCommand = new CenterToLeftSwitch();
				else autoCommand = new CenterToRightSwitch();
			}
			else if(start_pos == StartPos.RIGHT) {
				if(!leftSwitch) {
					isSwitch = true;
					autoCommand = new RightToRightSwitch();
				}
				//else if(!leftScale) autoCommand = new RightToRightScale();
				else autoCommand = new Baseline();					
			}
		}
		else {//Priority Scale
			if(start_pos == StartPos.LEFT) {
				if(leftScale) autoCommand = new LeftToLeftScale();
				else if(leftSwitch) {
					isSwitch = true;
					autoCommand = new LeftToLeftSwitch();
				}
				else autoCommand = new Baseline();					
			}
			else if(start_pos == StartPos.CENTER) {
				isSwitch = true;
				if(leftSwitch) autoCommand = new CenterToLeftSwitch();
				else autoCommand = new CenterToRightSwitch();
			}
			else if(start_pos == StartPos.RIGHT) {
				if(!leftScale) autoCommand = new RightToRightScale();
				else if(!leftSwitch) {
					isSwitch = true;
					autoCommand = new RightToRightSwitch();
				}
				else autoCommand = new Baseline();				
			}
		}
		
		if(autoCommand != null)
			(autoCommand = new AutoCommandGroup(
					autoCommand, !(autoCommand instanceof Baseline || 
					autoCommand instanceof DoNothing), isSwitch)).start();		
	}
	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}

	@Override
	public void teleopInit() {
		if(autoCommand != null && autoCommand.isRunning())
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
		//SmartDashboard.putNumber("Left voltage", dt.getLeftVoltage());
		//SmartDashboard.putNumber("Right voltage", dt.getRightVoltage());
		SmartDashboard.putBoolean("First Stage Bottom", !stage1BottomSwitch.get());
		SmartDashboard.putBoolean("First Stage Top", !stage1TopSwitch.get());
	}
}
