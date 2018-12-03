package util.motion;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import main.Constants;

/*
 * Class to read in and convert to double from the trajectory path files as .txts
 */
public class TrajectoryPath implements Constants {
	private File file;
	private BufferedReader br;
	private double[] leftVelocity; // ft/s
	private double[] rightVelocity;
	private double[] leftPos; //ft
	private double[] rightPos;
	private double[] headingDeg;
	private String current;
	
	private double dt = 0.05;
	
	public TrajectoryPath(String name) {		
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
		
		while(line != null) {
			char ch = line.charAt(0);
			
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
				
				// Fills up arrays appropriately
				if(current.equals("H")) headingDeg[i] = y;
				else if(current.equals("VL")) leftVelocity[i] = y;
				else if(current.equals("VR")) rightVelocity[i] = y;
				
				// Uses pythag from absolute position to get distances needed to travel.
				else if(current.equals("PL")) {
					if(i==0) leftPos[i] = 0;
					else 
						leftPos[i] = Math.sqrt(Math.pow(x-lastPosX, 2) + Math.pow(y-lastPosY, 2)); 
					lastPosX = x;
					lastPosY = y;
				}
				else {
					if(i==0) rightPos[i] = 0;
					else 
						rightPos[i] = Math.sqrt(Math.pow(x-lastPosX, 2) + Math.pow(y-lastPosY, 2)); 
					lastPosX = x;
					lastPosY = y;
				}
				
				i++;
			}
			line = readLine();
		}
	}
	
	public double[] getHeadingArr() {
		return headingDeg;
	}
	
	public double[] getLeftPosArr() {
		return leftPos;
	}
	
	public double[] getRightPosArr() {
		return rightPos;
	}
	
	public double[] getLeftVeloArr() {
		return leftVelocity;
	}
	
	public double[] getRightVeloArr() {
		return leftVelocity;
	}
	
	// Returns the target heading at the time in degrees
	public double getHeading(double time) {
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
	}
	
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
