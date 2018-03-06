package main.commands.intake;


import Util.DriveHelper;
import interfacesAndAbstracts.ImprovedCommand;
import main.OI;
import main.Robot;

public class ShootWithJoystick extends ImprovedCommand {
	private DriveHelper driveHelper = new DriveHelper(7.5);
	public ShootWithJoystick() {
		requires(Robot.el);
	}
	
	protected void execute() {
		Robot.el.moveWithJoystick(-OI.getXbox2().getSmoothedAltY()); //no negate for other
	}

	@Override
	protected boolean isFinished() {
		return true;
	}
}
