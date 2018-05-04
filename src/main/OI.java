package main;

import interfacesAndAbstracts.ImprovedClass;
import lib.joystick.XboxController;
import main.commands.elevator.MoveToTop;
import main.commands.intake.SpinIn;
import main.commands.intake.SpinOut;
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
	
	public void setInternalControl(boolean internalControl) {
		xbox.setInternalControl(internalControl);
	}
}
 