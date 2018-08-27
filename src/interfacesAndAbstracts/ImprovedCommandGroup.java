package interfacesAndAbstracts;

import edu.wpi.first.wpilibj.command.CommandGroup;
import main.Constants;
import main.HardwareAdapter;
import main.subsystems.subsystemConstants.DrivetrainConstants;
import main.subsystems.subsystemConstants.ElevatorConstants;

public abstract class ImprovedCommandGroup extends CommandGroup implements Constants, HardwareAdapter, DrivetrainConstants, ElevatorConstants {
}
