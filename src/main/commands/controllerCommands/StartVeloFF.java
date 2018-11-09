package main.commands.controllerCommands;

import controllers.VelocityFF;
import interfacesAndAbstracts.ImprovedCommand;
import main.subsystems.subsystemConstants.DrivetrainConstants;
import util.EncoderHelper;

public class StartVeloFF extends ImprovedCommand implements DrivetrainConstants {
	private double in;

	public StartVeloFF(double inches) {
		in = EncoderHelper.inchesToEncoderTicks(inches, wheelCircum, quadConversionFactor);
		//in = inches;
	}
	
	protected void initialize() {
		VelocityFF.start(in);
	}

	@Override
	protected boolean isFinished() {
		return true;
	}

}
