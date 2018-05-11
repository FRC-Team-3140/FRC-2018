package main.java.flagship3140.led;

import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Controls all the LEDs.
 * 
 * @author Jason
 *
 */
public class LED extends Subsystem {
	// TODO find channel
	private static final int CHANNEL=2;
	
	private DigitalOutput led=new DigitalOutput(CHANNEL);
	
	@Override
	protected void initDefaultCommand() {
		// No default command
	}

	public void setState(boolean state) {
		led.set(!state);
		// TODO why use !(not)? Document it.
	}

	public boolean getAlertLightState() {
		return led.get();
	}

	public void pulseAlertLight(int pulseLength) {
		led.pulse(pulseLength);
		// TODO what is the unit?
	}

	public boolean isPulsing() {
		return led.isPulsing();
	}
}
