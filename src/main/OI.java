package main;

import interfacesAndAbstracts.ImprovedClass;
import lib.joystick.XboxController;

public class OI extends ImprovedClass {	
	public OI() {
		xbox.setInternalControl(false);
	}
	
	public static XboxController getXbox() {
		return xbox;
	}
	
	public void check() {
		xbox.check();
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
 