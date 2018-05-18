package main;

import interfacesAndAbstracts.ImprovedClass;
import lib.joystick.XboxController;
import main.commands.drivetrain.DistanceDriveStraight;
import main.commands.drivetrain.DriveDistancePID;
import main.commands.drivetrain.DriveLeftPID;
import main.commands.drivetrain.DriveRightPID;
import main.commands.drivetrain.EndPID;
import main.commands.drivetrain.InitPID;
import main.commands.drivetrain.TurnToAnglePID;
import main.commands.elevator.MovePID;
import main.commands.elevator.MoveVelocityPID;
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
		
		xbox.leftJoystickPress.whenPressed(new ShiftUp());
		xbox.leftJoystickPress.whenReleased(new ShiftDown());
		
		//xbox.a.whenPressed(new DistanceDriveStraight(600));
		
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
		
		xbox2.x.whileHeld(new MovePID(switchHeight));
		xbox2.a.whileHeld(new MovePID(50));
		xbox2.b.whileHeld(new MoveVelocityPID(2));
		
		/*xbox.b.whenPressed(new InitPID());
		xbox.b.whileHeld(new DriveLeftPID(12));
		xbox.b.whenReleased(new EndPID());
		xbox.x.whenPressed(new InitPID());
		xbox.x.whileHeld(new DriveRightPID(12)); 
		xbox.x.whenReleased(new EndPID());*/
		xbox.y.whenPressed(new InitPID());
		xbox.y.whileHeld(new DistanceDriveStraight(120));
		xbox.y.whenReleased(new EndPID());
		xbox.b.whenPressed(new InitPID());
		xbox.b.whileHeld(new TurnToAnglePID(90));
		xbox.b.whenPressed(new EndPID());

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
 