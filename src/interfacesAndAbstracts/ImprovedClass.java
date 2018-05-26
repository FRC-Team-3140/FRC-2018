package interfacesAndAbstracts;

import main.Constants;
import main.HardwareAdapter;
import main.subsystems.subsystemConstants.DrivetrainConstants;
import main.subsystems.subsystemConstants.ElevatorConstants;

public abstract class ImprovedClass implements Constants, HardwareAdapter, DrivetrainConstants, ElevatorConstants {
	public abstract void check();
}
