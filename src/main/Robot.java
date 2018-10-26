// Copyright (c) 2018 FIRST 3140. All Rights Reserved.

package main;

/***
 * TODO
 * Get 2 cube right side autos
 * Test to make sure it's called at the right time
 * Test switch (make sure all one cube), baseline autos 
 * Left scale
 * 
 ****/

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import util.Logger;
import controllers.Record;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import interfacesAndAbstracts.ImprovedRobot;
import loopController.Looper;
import main.commands.altermativeAuto.AltBaseline;
import main.commands.altermativeAuto.AltCenterToLeftSwitch;
import main.commands.altermativeAuto.AltCenterToRightSwitch;
import main.commands.altermativeAuto.AltLeftToLeftScale;
import main.commands.altermativeAuto.AltLeftToLeftSwitch;
import main.commands.altermativeAuto.AltLeftToRightSwitch;
import main.commands.altermativeAuto.AltRightToLeftSwitch;
import main.commands.altermativeAuto.AltRightToRightScale;
import main.commands.altermativeAuto.AltRightToRightSwitch;
import main.commands.altermativeAuto.DoNothing;
import main.commands.altermativeAuto.RightToRightScaleSwitch;
import main.commands.controllerCommands.FileCreator;
import main.commands.controllerCommands.FileDeletor;
import main.commands.controllerCommands.StartPlay;
import main.commands.controllerCommands.StartRecord;
import main.commands.drivetrain.TankDrive;
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
	
	// PLAY AND RECORD	
	public static Logger lg;
    private static Looper autoLooper;
    private static SendableChooser<Command> fileChooser;
    //private static Command autoPlayCommand;
    private Command lastSelectedFile = new DoNothing();
    private static String newFileName = "";
    private static List<File> listOfFiles = new ArrayList<File>();
    private static int lastNumOfFiles = 0;
    
	// AUTO LOGIC
	private enum StartPos {LEFT, CENTER, RIGHT}
	private enum RobotAction {DO_NOTHING, BASELINE, SWITCH, SCALE}
	//private enum RobotAction{DO_Nothing, EDGECASE_DoNothing, EDGECASE_Baseline, EDGECASE_SwitchFromBehind}
	public static StartPos start_pos;
	public static RobotAction robot_act;
	private static SendableChooser<RobotAction> autoChooser;
	private static SendableChooser<StartPos> startPos;
	private static Command autoCommand;
	
	// Competition Mode: Picking a recording and running it
	private static Command competitionFilePicker;
	private String fileToPlay = null;
	private static Command competitionPlayCommand;
	//private static Command autoCommand;
	
//	class AutoCommandGroup extends CommandGroup {
//		public AutoCommandGroup(Command auto, boolean reset, boolean moveDown) {
//			addSequential(auto);
//			if(reset) addSequential(new ResetForTeleop(moveDown));
//		}
//	}
	
	@Override
	public void robotInit() {
		// OI must be at end
		dt = new Drivetrain();
		pn = new Pneumatics();
		in = new Intake();
		el = new Elevator();
		oi = new OI();
		dc = new DriverCamera();
		
		lg = new Logger();
		autoLooper = new Looper(kLooperDt);
		autoLooper.register(new Record());
		
		SmartDashboard.putData("Record", new StartRecord());
		SmartDashboard.putData("Play", new StartPlay());
		// File adder
		SmartDashboard.putString("New File Name", "");
		SmartDashboard.putData("Create a new file", new FileCreator()); 
		// File deleter
		SmartDashboard.putData("Delete a file", new FileDeletor());
		//FileSelector
    	fileChooser = new SendableChooser<>();
    	fileChooser.addDefault("", new DoNothing());
    	SmartDashboard.putData("File Selector", fileChooser);
    	
//    	SmartDashboard.putNumber("Throttle", 0.0);
    	SmartDashboard.putData("Tank Drive", new TankDrive(.1,.1));
		
		SmartDashboard.putString("NOTICE:", "Whenever you redeploy code you must restart shuffleboard; And whenever you "
				+ "delete a file you must restart robot code.");
		
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
		//Auto Features to Disable
		SmartDashboard.putBoolean("Disable Scale Auto", false);
		SmartDashboard.putBoolean("Disable Switch From Behind", true);
		
		//Robot Self Test
		SmartDashboard.putData("Robot Self Test", new RobotSelfTest());
		SmartDashboard.putData("Robot Drive Train Test", new RobotDriveTrainTest());
	}
	
	@Override
	public void disabledInit() {
//		if(autoCommand != null && autoCommand.isRunning())
//			autoCommand.cancel();
	}
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}

	@Override
	public void autonomousInit() {		
		autoLooper.start();
		
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
		boolean scaleDisabled = false;
		boolean behindSwitchDisabled = true;
		boolean scaleSwitchDisabled = false;
			
		boolean isSwitch = false;
		start_pos = startPos.getSelected();
		robot_act = autoChooser.getSelected();
		
		if(robot_act == RobotAction.DO_NOTHING)//Do Nothing
			autoCommand = new DoNothing();
		
		else if(robot_act == RobotAction.BASELINE)//Baseline
			autoCommand = new AltBaseline();
		
		else if(robot_act == RobotAction.SWITCH) {//Priority Switch
			if(start_pos == StartPos.LEFT) {
				if(leftSwitch) {
					isSwitch = true;
					autoCommand = new AltLeftToLeftSwitch();
				}
				else if(!behindSwitchDisabled) {
					isSwitch = true;
					autoCommand = new AltLeftToRightSwitch();
				}
				else if(leftScale && !scaleDisabled) autoCommand = new AltLeftToLeftScale();
				else autoCommand = new AltBaseline();					
			}
			else if(start_pos == StartPos.CENTER) {
				isSwitch = true;
				if(leftSwitch) autoCommand = new AltCenterToLeftSwitch();
				else autoCommand = new AltCenterToRightSwitch();
			}
			else if(start_pos == StartPos.RIGHT) {
				if(!leftSwitch) {
					isSwitch = true;
					if(!leftScale && !scaleSwitchDisabled)
						autoCommand = new RightToRightScaleSwitch();
					else autoCommand = new AltRightToRightSwitch();
				}
				else if(!behindSwitchDisabled) {
					isSwitch = true;
					autoCommand = new AltRightToLeftSwitch();
				}
				else if(!leftScale && !scaleDisabled) autoCommand = new AltRightToRightScale();
				else autoCommand = new AltBaseline();					
			}
		}
		else {//Priority Scale
			if(start_pos == StartPos.LEFT) {
				if(leftScale && !scaleDisabled) autoCommand = new AltLeftToLeftScale();
				else if(leftSwitch) {
					isSwitch = true;
					autoCommand = new AltLeftToLeftSwitch();
				}
				else if(!behindSwitchDisabled) {
					isSwitch = true;
					autoCommand = new AltLeftToRightSwitch();
				}
				else autoCommand = new AltBaseline();					
			}
			else if(start_pos == StartPos.CENTER) {
				isSwitch = true;
				if(leftSwitch) autoCommand = new AltCenterToLeftSwitch();
				else autoCommand = new AltCenterToRightSwitch();
			}
			else if(start_pos == StartPos.RIGHT) {
				if(!leftScale && !scaleDisabled) {
					if(!leftSwitch && !scaleSwitchDisabled) autoCommand = new RightToRightScaleSwitch();
					else autoCommand = new AltRightToRightScale();
					
				}
				else if(!leftSwitch) {
					isSwitch = true;
					autoCommand = new AltRightToRightSwitch();
				}
				else if(!behindSwitchDisabled) {
					isSwitch = true;
					autoCommand = new AltRightToLeftSwitch();
				}
				else autoCommand = new AltBaseline();				
			}
		}
		
		if(autoCommand != null) autoCommand.start();
//			(autoCommand = new AutoCommandGroup(
//					autoCommand, !(autoCommand instanceof AltBaseline || 
//					autoCommand instanceof DoNothing), isSwitch)).start();	
		
		/*if(robot_act == RobotAction.DO_NOTHING)//Do Nothing
			autoCommand = new DoNothing();
		else if(robot_act == RobotAction.BASELINE)//Baseline
			autoCommand = new Baseline();
		else if(robot_act == RobotAction.SWITCH){//Priority Switch
			if(start_pos == StartPos.LEFT) {
				if(leftSwitch) {
					isSwitch = true;
					autoCommand = new AltLeftToLeftSwitch();
				}
				else if(!behindSwitchDisabled) {
					isSwitch = true;
					autoCommand = new AltLeftToRightSwitch();
				}
				else if(leftScale && !scaleDisabled) autoCommand = new AltLeftToLeftScale();
				else autoCommand = new Baseline();					
			}
			else if(start_pos == StartPos.CENTER) {
				isSwitch = true;
				if(leftSwitch) autoCommand = new AltCenterToLeftSwitch();
				else autoCommand = new AltCenterToRightSwitch();
			}
			else if(start_pos == StartPos.RIGHT) {
				if(!leftSwitch) {
					isSwitch = true;
					autoCommand = new AltRightToRightSwitch();
				}
				else if(!behindSwitchDisabled) {
					isSwitch = true;
					autoCommand = new AltRightToLeftSwitch();
				}
				else if(!leftScale && !scaleDisabled) autoCommand = new AltRightToRightScale();
				else autoCommand = new Baseline();					
			}
		}
		else {//Priority Scale
			if(start_pos == StartPos.LEFT) {
				if(leftScale && !scaleDisabled) autoCommand = new AltLeftToLeftScale();
				else if(leftSwitch) {
					isSwitch = true;
					autoCommand = new AltLeftToLeftSwitch();
				}
				else if(!behindSwitchDisabled) {
					isSwitch = true;
					autoCommand = new AltLeftToRightSwitch();
				}
				else autoCommand = new Baseline();					
			}
			else if(start_pos == StartPos.CENTER) {
				isSwitch = true;
				if(leftSwitch) autoCommand = new AltCenterToLeftSwitch();
				else autoCommand = new AltCenterToRightSwitch();
			}
			else if(start_pos == StartPos.RIGHT) {
				if(!leftScale && !scaleDisabled) autoCommand = new AltRightToRightScale();
				else if(!leftSwitch) {
					isSwitch = true;
					autoCommand = new AltRightToRightSwitch();
				}
				else if(!behindSwitchDisabled) {
					isSwitch = true;
					autoCommand = new AltRightToLeftSwitch();
				}
				else autoCommand = new Baseline();				
			}
		}
		
		if(autoCommand != null)
			(autoCommand = new AutoCommandGroup(
					autoCommand, !(autoCommand instanceof Baseline || 
					autoCommand instanceof DoNothing), isSwitch)).start();*/
	}
	
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}

	@Override
	public void teleopInit() {
		autoLooper.start();
//		if(autoCommand != null && autoCommand.isRunning())
//			autoCommand.cancel();
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
	
	private boolean fileNameInListOfFiles(List<File> l, File f) {
		for(File file: l) {
			if(file.getName().toLowerCase().equals(f.getName().toLowerCase()))
				return true;
		}
		return false;
	}
	
	public static SendableChooser<Command> getFileChooser() {
		return fileChooser;
	}
	
	public static Command getFile() {
		return fileChooser.getSelected();
	}
	
	public static String getNewFileName() {
		return newFileName;
	}
	
	public void allPeriodic() {
		//Runtime runtime = Runtime.getRuntime();
		SmartDashboard.updateValues();
		dt.check();
		pn.check();
		in.check();
		el.check();
		oi.check();
		//SmartDashboard.putNumber("Free memory", runtime.freeMemory());
		//SmartDashboard.putNumber("Total memory", runtime.totalMemory());
		//SmartDashboard.putData(Scheduler.getInstance());
		//Pressure
		SmartDashboard.putNumber("Pressure: ", HardwareAdapter.analogPressureSensor1.value());
		// DriveTrain Encoders
		SmartDashboard.putNumber("DriveTrain Left Encoder Distance", dt.getLeftEncoderDistanceTravelled());
		SmartDashboard.putNumber("DriveTrain Left Input", leftDriveMaster.getMotorOutputPercent());
		SmartDashboard.putNumber("DriveTrain Left Input 2", leftDriveSlave1.getMotorOutputPercent());
		SmartDashboard.putNumber("DriveTrain Right Encoder Distance", dt.getRightEncoderDistanceTravelled());
		SmartDashboard.putNumber("DriveTrain Right Input", rightDriveMaster.getMotorOutputPercent());
		SmartDashboard.putNumber("DriveTrain Right Input 2", rightDriveSlave1.getMotorOutputPercent());
		SmartDashboard.putNumber("DT Left Speed encoder", dt.getLeftEncoderVelocity());
		SmartDashboard.putNumber("DT Right Speed encoder", dt.getRightEncoderVelocity());
		
		SmartDashboard.putNumber("DriveTrain Distance", dt.getDistanceTravelled());
		// DriveTrain Gyro
		SmartDashboard.putNumber("NavX Heading", dt.getHeading());
		// Limit Switch States
		SmartDashboard.putBoolean("First Stage Bottom", !stage1BottomSwitch.get());
		SmartDashboard.putBoolean("First Stage Top", !stage1TopSwitch.get());
		// Elevator encoder
		SmartDashboard.putNumber("Elevator encoder inches", el.getDistanceTravelled());
		// elevator current
		SmartDashboard.putNumber("el master current", el.getElevatorMasterCurrent());
		SmartDashboard.putNumber("el slave current", el.getElevatorSlaveCurrent());
		
		//SmartDashboard.putNumber("is this number right?", el.distanceToTicks(el.getTicksTravelled()));
		SmartDashboard.putNumber("Elevator encoder ticks", el.getTicksTravelled());
		SmartDashboard.putNumber("elevator input", elevatorMaster.get());
		SmartDashboard.putNumber("Elevator master voltage", elevatorMaster.getMotorOutputVoltage());
		SmartDashboard.putNumber("elevator slave voltage", elevatorSlave.getMotorOutputVoltage());
		SmartDashboard.putNumber("Elevator speed ticks", el.getElevatorVelocity());
		SmartDashboard.putNumber("Elevator master power", (el.getElevatorMasterVoltage() * elevatorMaster.getMotorOutputVoltage()));
		SmartDashboard.putNumber("Elevator slave power", (el.getElevatorSlaveVoltage() * elevatorSlave.getMotorOutputVoltage()));
		
		SmartDashboard.putString("Working File", lg.getWorkingFile());
		SmartDashboard.putString("Working Path", outputPath);
	}
}
