package lib.joystick;

import edu.wpi.first.wpilibj.Joystick;

abstract class InterfaceableJoystick extends Joystick {
	
	public InterfaceableJoystick(int port) { // NO_UCD
		super(port);
	}
	
	public abstract void check();
	public abstract void setInternalControl(boolean internalControl);
	public abstract boolean getInternalControl();
	public abstract void setButtonStatus(boolean trigger, boolean thumb, boolean three, boolean four, boolean five, boolean six);
}
