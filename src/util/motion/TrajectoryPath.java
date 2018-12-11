package util.motion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

import main.Constants;
import main.subsystems.subsystemConstants.DrivetrainConstants;
import util.EncoderHelper;

/*
 * Class to read in and convert to double from the trajectory path files as .txts
 */
public class TrajectoryPath implements Constants, DrivetrainConstants {
	private File file;
	private BufferedReader br;

	private List<Double> leftVelocity = new ArrayList<>();
	private List<Double> rightVelocity = new ArrayList<>(); 
	private List<Integer> leftPos = new ArrayList<>(); 
	private List<Integer> rightPos = new ArrayList<>(); 
	private List<Double> headingDeg = new ArrayList<>(); 

	private String current;
	
	private double dt = 0.05;
	
	public TrajectoryPath(String name) {	
		//System.out.println("Directory: " + System.getProperty("user.dir"));
		file = new File(outputPath + name + ".txt");
		try {
			br = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		init();
	}
	
	// Initializes the class by reading in the appropriate file
	public void init() {
		current = "H";
		int i = 0;
		double lastPosX = 0;
		double lastPosY = 0;
		String line = readLine();
		
		//TODO: fix for empty lines
		while(line != null) {
			char ch;
			if(line != "")
				ch = line.charAt(0);
			else 
				ch = 'X';
			
			// Changes the array to fill up if the we've reached the next array in the txt file
			if(ch == 'H' || ch == 'V' || ch == 'P') {
				if(ch == 'H') current = "H";
				else if(line.contains("VL")) current = "VL";
				else if(line.contains("VR")) current = "VR";
				else if(line.contains("PR")) current = "PR";
				else if(line.contains("PL")) current = "PL";

				i = 0;
			}
			else if(ch == 'X') {
				// Does nothing if the line just says "X:   Y:"
			}
			else {
				String[] dtState = line.split("\t");
				double x = Double.parseDouble(dtState[0]);
				double y = Double.parseDouble(dtState[1]);
				
				//System.out.println(current);
				// Fills up arrays appropriately
				if(current.equals("H")) 
					headingDeg.add(y);
				else if(current.equals("VL")) {
					leftVelocity.add(EncoderHelper.inSecToTicks100Ms(y*12, 4096, wheelCircum));
				}
				else if(current.equals("VR")) {
					rightVelocity.add(EncoderHelper.inSecToTicks100Ms(y*12, 4096, wheelCircum));
				}
				
				// Uses pythag from absolute position to get distances needed to travel.
				else if(current.equals("PL")) {
					if(i==0) leftPos.add(0);
					else  {
						double posFt = Math.sqrt(Math.pow(x-lastPosX, 2) + Math.pow(y-lastPosY, 2)); 
						leftPos.add(EncoderHelper.inchesToEncoderTicks(12*posFt, wheelCircum, 4096));
					}
					lastPosX = x;
					lastPosY = y;
				}
				else {
					if(i==0) rightPos.add(0);
					else {
						double posFt = Math.sqrt(Math.pow(x-lastPosX, 2) + Math.pow(y-lastPosY, 2));
						rightPos.add(EncoderHelper.inchesToEncoderTicks(12*posFt, wheelCircum, 4096));
					}
					lastPosX = x;
					lastPosY = y;
				}
				
				i++;
			}
			line = readLine();
		}
		System.out.println(headingDeg.size());
	}
	
	public List<Double> getHeadingList() {
		return headingDeg;
	}
	
	public List<Integer> getLeftPosList() {
		return leftPos;
	}
	
	public List<Integer> getRightPosList() {
		return rightPos;
	}
	
	public List<Double> getLeftVeloList() {
		return leftVelocity;
	}
	
	public List<Double> getRightVeloList() {
		return rightVelocity;
	}
	
	// Returns the target heading at the time in degrees
	/*public double getHeading(double time) {
		int i = timeToIndex(time);
		return headingDeg[i];
	}
	
	// Returns the target distance of the right side.
	// Units depend on the original text file
	public double getRightDist(double time) {
		int i = timeToIndex(time);
		return rightPos[i];
	}
	
	public double getLeftDist(double time) {
		int i = timeToIndex(time);
		return leftPos[i];
	}
	
	public double getRightVelocity(double time) {
		int i = timeToIndex(time);
		return rightVelocity[i];
	}
	
	public double getLeftVelocity(double time) {
		int i = timeToIndex(time);
		return leftVelocity[i];
	}*/
	
	// Reads one line from the file
	private String readLine() {
		String line = "";
		try {
			if(br != null)
				line = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}	
	
	// Returns the target index for the arrays depending on the time
	private int timeToIndex(double timeSec) {
		return (int) Math.round(timeSec/dt);
	}

}
