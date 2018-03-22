package main;

import interfacesAndAbstracts.ImprovedClass;
import lib.joystick.XboxController;
import main.commands.elevator.MoveToBottom;
import main.commands.elevator.MoveToSwitch;
import main.commands.elevator.MoveToTop;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.arm.SwitchArm;
import main.commands.pneumatics.shift.ShiftDown;
import main.commands.pneumatics.shift.ShiftUp;
import main.commands.pneumatics.tilt.SwitchTilt;
import main.commands.pneumatics.tilt.TiltDown;
import main.commands.pneumatics.tilt.TiltUp;

public class OI extends ImprovedClass { // NO_UCD (use default)
	private final XboxController xbox = new XboxController(XBOX_PORT);
	private final XboxController xbox2 = new XboxController(XBOX_PORT_2);
	
	public OI() {
		xbox.setInternalControl(false);
		xbox2.setInternalControl(false);

		// Shoots out without elevator
		xbox.leftJoystickPress.whenPressed(new ShiftUp());
		xbox.leftJoystickPress.whenReleased(new ShiftDown());
		
		xbox2.leftBumper.whenPressed(new SwitchTilt(new TiltDown(), new TiltUp()));
		xbox2.rightBumper.whenPressed(new SwitchArm(new ArmOpen(), new ArmClose()));
		
		xbox2.x.whenPressed(new MoveToBottom(3));
		xbox2.y.whenPressed(new MoveToSwitch(3));
		xbox2.b.whenPressed(new MoveToTop(5));
	}

	public XboxController getXbox() {
		return xbox;
	}

	public XboxController getXbox2() {
		return xbox2;
	}

	@Override
	public void check() {
		xbox.check();
		xbox2.check();
	}

	/**************
	 * PLAY/RECORD *
	 ***************/
	public void setInternalControl(boolean internalControl) {
		xbox.setInternalControl(internalControl);
		xbox2.setInternalControl(internalControl);
	}
}
