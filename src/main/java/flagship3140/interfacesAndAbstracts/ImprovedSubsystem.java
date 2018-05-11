package main.java.flagship3140.interfacesAndAbstracts;

import edu.wpi.first.wpilibj.command.Subsystem;
import main.java.flagship3140.Constants;
import main.java.flagship3140.Hardware;

public abstract class ImprovedSubsystem extends Subsystem implements Constants, Hardware {
	public abstract void check();
	public abstract void zeroSensors();
}
