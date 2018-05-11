package main.java.flagship3140;

import main.java.flagship3140.commands.pneumatics.arm.ArmClose;
import main.java.flagship3140.commands.pneumatics.arm.ArmOpen;
import main.java.flagship3140.commands.pneumatics.arm.SwitchArm;
import main.java.flagship3140.commands.pneumatics.shift.ShiftDown;
import main.java.flagship3140.commands.pneumatics.shift.ShiftUp;
import main.java.flagship3140.commands.pneumatics.tilt.SwitchTilt;
import main.java.flagship3140.commands.pneumatics.tilt.TiltDown;
import main.java.flagship3140.commands.pneumatics.tilt.TiltUp;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedClass;
import main.java.flagship3140.joystick.XboxController;

public class OI extends ImprovedClass {	
	public OI() {
		xbox.setInternalControl(false);
		xbox2.setInternalControl(false);
		
		// Shoots out without elevator
		xbox.leftJoystickPress.whenPressed(new ShiftUp());
		xbox.leftJoystickPress.whenReleased(new ShiftDown());
		//xbox.a.whenPressed(new SpinIn());
		
		//xbox.a.whenPressed(new DistanceDriveStraight(600));
		/*
		// auto testing calibration
		xbox.a.whenPressed(new TimedTurn(TurnMode.Left, timedTurnPercent, timedTurn90degTime));
		xbox.x.whenPressed(new TimedTurn(TurnMode.Left, timedTurnPercent, timedTurn45degTime));
		xbox.y.whenPressed(new TimedDrive(timedDrivePercent, 2.5));
	    */
		
		//xbox.x.whenPressed(new DistanceDriveStraight(84));
		//xbox.b.whenPressed(new DistanceDriveStraight(-12));
		//xbox.a.whenPressed(new TurnToAngle(90));
		//xbox.y.whenPressed(new TestAutoTurning());
		/*
		xbox2.a .whenPressed(new MoveToBottom(1.5));
		xbox2.b.whenPressed(new MoveToSwitch(1.5));
		xbox2.y.whenPressed(new MoveToTop(3));
	*/
		//xbox.b.whenPressed(new TurnToAngle(90));
		//xbox.a.whenPressed(new MoveToBottom(1.5));
		//xbox.y.whenPressed(new MoveToSwitch(1.5));
		//xbox.rightBumper.whenPressed(new MoveToTop(3));
		//xbox.leftBumper.whenPressed(new AltRightToRightSwitch());

		
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
 