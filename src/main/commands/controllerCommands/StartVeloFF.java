package main.commands.controllerCommands;

import controllers.VelocityFF;
import interfacesAndAbstracts.ImprovedCommand;
import main.subsystems.subsystemConstants.DrivetrainConstants;
import util.EncoderHelper;

public class StartVeloFF extends ImprovedCommand implements DrivetrainConstants {
	private int distUnits;

	public StartVeloFF(double inches) {
		distUnits = EncoderHelper.inchesToEncoderTicks(inches, wheelCircum, quadConversionFactor);
	}
	
	protected void initialize() {
		VelocityFF.start(distUnits);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
