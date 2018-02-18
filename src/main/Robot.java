/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import Util.Logger;
import controllers.Play;
import controllers.Record;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import interfacesAndAbstracts.ImprovedRobot;
import interfacesAndAbstracts.ImprovedSendableChooser;
import loopController.Looper;
import main.commands.controllerCommands.DoNothing;
import main.commands.controllerCommands.FileCreator;
import main.commands.controllerCommands.FilePicker;
import main.commands.controllerCommands.StartPlay;
import main.commands.controllerCommands.StartRecord;
import main.subsystems.Drivetrain;
import main.subsystems.Pneumatics;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends ImprovedRobot {
	public static OI oi;
	public static Drivetrain dt;
	public static Pneumatics pn;
	public static Logger lg;
    private Looper autoLooper;
    ImprovedSendableChooser<Command> fileChooser;
    private Command autoPlayCommand = new StartPlay();
    private Command lastSelectedFile = new DoNothing();
    private static String newFileName = "";
    private static List<File> listOfFiles = new ArrayList<File>();
    private static int lastNumOfFiles = 0;
	
	public Robot() {
		//OI must be the last class added, this will make it the last class to be instantiated
		//This is needed in order to ensure classes are defined in the correct order and null errors do not occur
	}

	@Override
	public void robotInit() {
		//**************************************************Instantiate Robot Systems
		dt = Drivetrain.newInstance();
		pn = Pneumatics.newInstance();
		oi = OI.newInstance();
		//Other Utility Classes
		lg = new Logger();
		autoLooper = new Looper(kLooperDt);
		autoLooper.register(new Record());
		autoLooper.register(new Play()); 
		
        //**************************************************SmartDashboard
		if(!isCompetition) {
			SmartDashboard.putData("Record", new StartRecord());
			SmartDashboard.putData("Play", new StartPlay());
		}
		//FileSelector
    	fileChooser = new ImprovedSendableChooser<>();
    	fileChooser.addDefault("Do Nothing", new DoNothing());
    	SmartDashboard.putData("File Selector", fileChooser);
    	//FileAdder
    	if(!isCompetition) {
    		SmartDashboard.putString("New File Name", "");
    		SmartDashboard.putData("Create a new file", new FileCreator()); 
    	}
    	//Knowing Where You Are At
//    	SmartDashboard.putString("Working File", lg.getWorkingFile());
//    	SmartDashboard.putString("Working Path", outputPath);
	}
	
	@Override
	public void disabledInit() {
		if(autoPlayCommand.isRunning())
			autoPlayCommand.cancel();
		autoLooper.stop();		
	}
	
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}

	@Override
	public void autonomousInit() {
		autoLooper.start();
		autoPlayCommand.start();
	}


	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		allPeriodic();
	}

	@Override
	public void teleopInit() {
		if(autoPlayCommand.isRunning())
			autoPlayCommand.cancel();
		if(!isCompetition)
			autoLooper.start();
	}
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("Analog Sensor 1 value", HardwareAdapter.analogPressureSensor1.value());
		allPeriodic();
	}

	@Override
	public void testPeriodic() {
		allPeriodic();
	}
	
	private void checkForSmartDashboardUpdates() {
		if(!isCompetition && !newFileName.equals(SmartDashboard.getString("New File Name", "")))
				newFileName = SmartDashboard.getString("New File Name", "");		
		if(fileChooser.getSelected() != lastSelectedFile) {
			fileChooser.getSelected().start();
			lastSelectedFile = fileChooser.getSelected();
		}
		if(lg.getFiles(outputPath).length > lastNumOfFiles) {    	
	        for(File file: lg.getFiles(outputPath))
	        	if(!fileNameInListOfFiles(listOfFiles, file)) {
	        		fileChooser.addObject(file.getName(), new FilePicker(file.getPath()));
	        		listOfFiles.add(file);
	        	}
	    	lastNumOfFiles = lg.getFiles(outputPath).length;
		}
		else if(lg.getFiles(outputPath).length < lastNumOfFiles) {    	
	        for(File file: lg.getFiles(outputPath))
	        	if(fileNameInListOfFiles(listOfFiles, file)) {
	        		fileChooser.removeObject(file.getName(), new FilePicker(file.getPath()));
	        		listOfFiles.remove(file);
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
	
	public void allPeriodic() {
		SmartDashboard.updateValues();
		checkForSmartDashboardUpdates();
		autoLooper.outputToSmartDashboard();
		dt.check();
		pn.check();
		oi.check();
		// Knowing where you're at
		if(!isCompetition) {
			SmartDashboard.putString("Working File", lg.getWorkingFile()); // Later add if-statement to minimize data sent
			SmartDashboard.putString("Working Path", outputPath);
		}
	}
	
	public static String getNewFileName() {
		return newFileName;
	}
}
