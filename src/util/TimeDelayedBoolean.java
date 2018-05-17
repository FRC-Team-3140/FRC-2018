package util;

import edu.wpi.first.wpilibj.Timer;

/**
 * TimedBoolean.java
 * 
 * Wait a set amount of time before making a boolean value true
 * Reset function allows you to reset the boolean value and wait again
 *
 * @author Tom Bottiglieri
 * Modified by Alexander Peters
 */
public class TimeDelayedBoolean {
	Timer t = new Timer();
	double timeout;
  

	public TimeDelayedBoolean(double timeout) {
		this.timeout = timeout;
		t.start();
	}

	public boolean get() {
		return t.get() > timeout;
	}

	public void resetTimer(double timeout) {
		t.reset();
		this.timeout = timeout;
	}
}
