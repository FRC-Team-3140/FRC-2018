package main;

import interfacesAndAbstracts.ImprovedClass;
import lib.joystick.XboxController;
import main.Constants.TurnMode;
import main.commands.drivetrain.TimedDrive;
import main.commands.drivetrain.TimedTurn;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.arm.SwitchArm;
import main.commands.pneumatics.shift.ShiftDown;
import main.commands.pneumatics.shift.ShiftUp;
import main.commands.pneumatics.tilt.SwitchTilt;
import main.commands.pneumatics.tilt.TiltDown;
import main.commands.pneumatics.tilt.TiltUp;

public class OI extends ImprovedClass {	
	public OI() {
		xbox.setInternalControl(false);
		xbox2.setInternalControl(false);
		
		// Shoots out without elevator
		xbox.leftJoystickPress.whenPressed(new ShiftUp());
		xbox.leftJoystickPress.whenReleased(new ShiftDown());
		
		/*
		// auto testing calibration
		xbox.a.whenPressed(new TimedTurn(TurnMode.Left, timedTurnPercent, timedTurn90degTime));
		xbox.x.whenPressed(new TimedTurn(TurnMode.Left, timedTurnPercent, timedTurn45degTime));
		xbox.y.whenPressed(new TimedDrive(timedDrivePercent, 2.5));
	    */		
		xbox2.leftBumper.whenPressed(new SwitchTilt(new TiltDown(), new TiltUp()));
		xbox2.rightBumper.whenPressed(new SwitchArm(new ArmOpen(), new ArmClose()));
	}
	
	public static XboxController getXbox() {
		return xbox;
	}
	
    public static XboxController getXbox2() {
		return xbox2;
	}
    
	public void check() {
		xbox.check();
		xbox2.check();
	}
	
	/**************
	 * PLAY/RECORD *
	 ***************/
	public void setButtonValues(boolean a, boolean b, boolean x, boolean y, boolean leftBumper, boolean rightBumper,
			boolean select, boolean start, boolean leftJoystickPress, boolean rightJoystickPress,
			boolean leftTrigger, boolean rightTrigger) {
		xbox.setInternalControl(true);
		xbox.setButtonStatus(a, b, x, y, leftBumper, rightBumper, select, start, leftJoystickPress, rightJoystickPress, leftTrigger, rightTrigger);
	}
	
	public void setButtonValues2(boolean a, boolean b, boolean x, boolean y, boolean leftBumper, boolean rightBumper,
			boolean select, boolean start, boolean leftJoystickPress, boolean rightJoystickPress,
			boolean leftTrigger, boolean rightTrigger) {
		xbox2.setInternalControl(true);
		xbox2.setButtonStatus(a, b, x, y, leftBumper, rightBumper, select, start, leftJoystickPress, rightJoystickPress, leftTrigger, rightTrigger);
	}
	
	public void setInternalControl(boolean internalControl) {
		xbox.setInternalControl(internalControl);
		xbox2.setInternalControl(internalControl);
	}
}
 