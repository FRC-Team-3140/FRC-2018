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
import main.commands.auto.LeftToLeftScale;
import main.commands.auto.LeftToLeftSwitch;
import main.commands.auto.ResetForTeleop;
import main.commands.auto.RightToRightScale;
import main.commands.auto.RightToRightSwitch;
import main.subsystems.DriverAlerts;
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
	public static DriverAlerts da;	
	public static OI oi;
	// PLAY AND RECORD
	/*
	public static Logger lg;
    private static Looper autoLooper;
    private static SendableChooser<Command> fileChooser;
    private static Command autoPlayCommand;
    private Command lastSelectedFile = new DoNothing();
    private static String newFileName = "";
    private static List<File> listOfFiles = new ArrayList<File>();
    private static int lastNumOfFiles = 0;
    */
	// AUTO LOGIC
	private enum StartPos {LEFT, CENTER, RIGHT}
	private enum RobotAction {DO_NOTHING, BASELINE, SWITCH}
	//private enum RobotAction{DO_Nothing, EDGECASE_DoNothing, EDGECASE_Baseline, EDGECASE_DelayedSwitch}
	// TODO start_pos and robot_act could probably be removed
	public static StartPos start_pos;
	public static RobotAction robot_act;
	private static SendableChooser<RobotAction> autoChooser;
	private static SendableChooser<StartPos> startPos;
	
	// Competition Mode: Picking a recording and running it
	//private static Command competitionFilePicker;
	//private String fileToPlay = null;
	//private static Command competitionPlayCommand;
	private static Command autoCommand;
	
	@Override
	public void robotInit() {
		// OI must be at end
		dt = new Drivetrain();
		pn = new Pneumatics();
		in = new Intake();
		el = new Elevator();
		oi = new OI();
		dc = new DriverCamera();
		// da = new DriverAlerts();	
		//lg = new Logger();
		//autoLooper = new Looper(kLooperDt);
		//autoLooper.register(new Record());
		//autoLooper.register(new Play()); 

		/*
        //**************************************************SmartDashboard
    	if(!isCompetitionMatch) {
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
    		
    		SmartDashboard.putString("NOTICE:", "Whenever you redeploy code you must restart shuffleboard; And whenever you "
					+ "delete a file you must restart robot code.");
    	}
    	
    	else {
    		/* AUTO EXPLAINATION:
    		 * EDGECASE- The case where the robot is in the left or right position and neither the switch nor the scale line up.
    		 * Do Nothing- Robot won't move during auto
    		 * EDGECASE_DoNothing- Robot will act upon given game data except in the Edge Case; in which case it does nothing.
    		 * EDGECASE_Baseline- Robot will act upon given game data except in the Edge Case; in which case it crosses the baseline.
    		 * EDGECASE_DelayedSwitch- Robot will act upon given game data except in the Edge Case; in which case it waits a specified
    		 * 							length of time and then places a cube in the switch.
    		 */
		/*
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
			/*
			autoChooser.addObject("Go Robot Go!: EdgeCase_DoNothing", () -> {
				robot_act = RobotAction.EDGECASE_DoNothing;
			});
			autoChooser.addObject("Go Robot Go!: EdgeCase_BaseLine", () -> {
				robot_act = RobotAction.EDGECASE_Baseline;
			});
			autoChooser.addObject("Go Robot Go!: EdgeCase_DelayedSwitch", () -> {
				robot_act = RobotAction.EDGECASE_DelayedSwitch;
			});
			SmartDashboard.putData("Auto Mode", autoChooser);
			*/
			// Starting Pos
			startPos = new SendableChooser<>();
			startPos.addDefault("Left", StartPos.LEFT);
			startPos.addObject("Center", StartPos.CENTER);
			startPos.addObject("Right", StartPos.RIGHT);
			SmartDashboard.putData("Starting Position", startPos);
			SmartDashboard.putData("Auto Mode", autoChooser);
		//}
			
		//Robot Self Test
		SmartDashboard.putData("Robot Self Test", new RobotSelfTest());
	}
	
	@Override
	public void disabledInit() {
		if(autoCommand != null && autoCommand.isRunning())
			autoCommand.cancel();
		/*if(isCompetitionMatch) {
			if(autoPlayCommand.isRunning()) autoPlayCommand.cancel();
		}
		autoLooper.stop();		
		*/
	}
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}

	@Override
	public void autonomousInit() {
		//autoLooper.start();
		if (true) {
			//autoCommand = autoChooser.getSelected();
			//autoCommand.start();
			/*Command pos = (Command) startPos.getSelected();
			pos.start();*/
			// Makes sure game message is correct
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
			
			if(robot_act == RobotAction.DO_NOTHING)
				autoCommand = new DoNothing();
			else if(robot_act == RobotAction.BASELINE)
				autoCommand = new Baseline();
			else {
				if(start_pos == StartPos.LEFT && leftSwitch)
					autoCommand = new LeftToLeftSwitch();
				else if(start_pos == StartPos.LEFT && leftScale)
					autoCommand = new Baseline();
				else if(start_pos == StartPos.CENTER && leftSwitch)
					autoCommand = new CenterToLeftSwitch();
				else if(start_pos == StartPos.CENTER && !leftSwitch)
					autoCommand = new CenterToRightSwitch();
				else if(start_pos == StartPos.RIGHT && !leftSwitch)
					autoCommand = new RightToRightSwitch();
				else if(start_pos == StartPos.RIGHT && !leftScale)
					autoCommand = new Baseline();				
				else
					autoCommand = new Baseline();
			}
			//autoCommand = new Baseline();
			if(autoCommand != null)
			autoCommand.start();
			
			
			
			
			//boolean leftScale = (gmsg.charAt(1) == 'L');
			//boolean delayedSwitch = false;

			/*if (robot_act != RobotAction.DO_Nothing) { // Do something chosen
				switch (start_pos) { // Checks which starting position was chosen
				// Following code choose auto mode based on starting position for switch and scale
				case LEFT:
					if (leftSwitch && leftScale)
						fileToPlay = LEFT_SwitchAndScale;
					else if (leftSwitch && !leftScale)
						fileToPlay = LEFT_LeftSwitch;
					else if (!leftSwitch && leftScale)
						fileToPlay = LEFT_Scale;
					else {
						if (robot_act == RobotAction.EDGECASE_Baseline) fileToPlay = driveBaseline;
						else if(robot_act == RobotAction.EDGECASE_DelayedSwitch) {
							fileToPlay = LEFT_RightSwitch;
							delayedSwitch = true;
						}
					}
					break;
				case MIDDLE:
					if (leftSwitch)
						fileToPlay = MID_LeftSwitch;
					else
						fileToPlay = MID_RightSwitch;
					break;
				case RIGHT:
					if (!leftSwitch && !leftScale)
						fileToPlay = RIGHT_SwitchAndScale;
					else if (leftSwitch && !leftScale)
						fileToPlay = RIGHT_Scale;
					else if (!leftSwitch && leftScale)
						fileToPlay = RIGHT_RightSwitch;
					else {
						if (robot_act == RobotAction.EDGECASE_Baseline) fileToPlay = driveBaseline;
						else if(robot_act == RobotAction.EDGECASE_DelayedSwitch) {
							fileToPlay = RIGHT_LeftSwitch;
							delayedSwitch = true;
						}							
					}
					break;
				}
				if(fileToPlay != null && !delayedSwitch) {
					competitionFilePicker = new FilePicker(fileToPlay);
					competitionFilePicker.start(); // Changes path to the chosen file
					competitionPlayCommand = new StartPlay();
				}
				else if(fileToPlay != null && delayedSwitch)
					competitionPlayCommand = new DelayedPlay(fileToPlay, autoDelay);
				else
					competitionPlayCommand = new DoNothing();
					
			} 
			else { // Do nothing chosen
				competitionPlayCommand = new DoNothing();
			}

			if (competitionPlayCommand != null)
				competitionPlayCommand.start(); // Starts the appropriate command
			*/
		}
	}

	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}

	@Override
	public void teleopInit() {
		new ResetForTeleop().start();
		if(autoCommand != null && autoCommand.isRunning())
			autoCommand.cancel();
		/*
		if(isCompetitionMatch) {
			if(autoPlayCommand.isRunning())
				autoPlayCommand.cancel();
		}
		if(!isCompetitionMatch)
			autoLooper.start();
		*/
	}	

	@Override
	public void teleopPeriodic() {
		Runtime runtime = Runtime.getRuntime();

		// SmartDashboard stuff goes here
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Free memory", runtime.freeMemory());
		SmartDashboard.putNumber("Total memory", runtime.totalMemory());
		SmartDashboard.putNumber("Pressure: ", HardwareAdapter.analogPressureSensor1.value());
		//SmartDashboard.putBoolean("Cube Detected: ", cubeSensor1.get());
		allPeriodic();
	}
	
	@Override
	public void testPeriodic() {
		allPeriodic();
	}
	/*
	private void checkForSmartDashboardUpdates() {
		if (!isCompetitionMatch && !newFileName.equals(SmartDashboard.getString("New File Name", "")))
			newFileName = SmartDashboard.getString("New File Name", "");
		
		if (fileChooser.getSelected() != lastSelectedFile && fileChooser.getSelected() != null) {
			fileChooser.getSelected().start();
			lastSelectedFile = fileChooser.getSelected();
		}
		
		if (lg.getFiles(outputPath).length != lastNumOfFiles) {
			for (File file : lg.getFiles(outputPath))
				if (!fileNameInListOfFiles(listOfFiles, file)) {
					fileChooser.addObject(file.getName(), new FilePicker(file.getPath()));
					listOfFiles.add(file);
				}
			lastNumOfFiles = lg.getFiles(outputPath).length;
		} 
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
	*/
	public void allPeriodic() {
		SmartDashboard.updateValues();
		/*if(!isCompetitionMatch) {
			checkForSmartDashboardUpdates();
		}*/
		//autoLooper.outputToSmartDashboard();
		dt.check();
		pn.check();
		in.check();
		el.check();
		oi.check();
		//SmartDashboard.putNumber("Left voltage", dt.getLeftVoltage());
		//SmartDashboard.putNumber("Right voltage", dt.getRightVoltage());
		SmartDashboard.putBoolean("First Stage Bottom", !stage1BottomSwitch.get());
		SmartDashboard.putBoolean("First Stage Top", !stage1TopSwitch.get());
		SmartDashboard.putBoolean("Second Stage Bottom", !stage2BottomSwitch.get());
		SmartDashboard.putBoolean("Second Stage Top", !stage2TopSwitch.get());
		SmartDashboard.putBoolean("Switch Height", switchHeightSwitch.get());
		
		/*
		// Knowing where you're at
		if(!isCompetitionMatch) {
			SmartDashboard.putString("Working File", lg.getWorkingFile());
			SmartDashboard.putString("Working Path", outputPath);
		}
		*/
	}
	/*
	public static String getNewFileName() {
		return newFileName;
	}
	*/
}
