package main.java.flagship3140.util;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * Wraps an analog input for a Rev Robotics Analog Pressure sensor.
 *
 * http://www.revrobotics.com/wp-content/uploads/2015/11/REV-11-1107-DS-00.pdf
 */
public class RevRoboticsAnalogPressureSensor {
    private final AnalogInput mAnalogInput;

    public RevRoboticsAnalogPressureSensor(int analogInputNumber) {
        mAnalogInput = new AnalogInput(analogInputNumber);
    }

    public double value() {
    	double value = 220 * (mAnalogInput.getVoltage() - 0.5) / 4;
        if(value >= 0.0)
        	return value;
        else 
        	return 0.0;
    }    
}