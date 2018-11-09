package main;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import interfacesAndAbstracts.ImprovedClass;
import lib.joystick.XboxController;
import main.commands.altermativeAuto.AltCenterToLeftSwitch;
import main.commands.altermativeAuto.AltCenterToRightSwitch;
import main.commands.altermativeAuto.AltLeftToLeftScale;
import main.commands.altermativeAuto.AltLeftToLeftSwitch;
import main.commands.altermativeAuto.AltRightToRightScale;
import main.commands.altermativeAuto.AltRightToRightSwitch;
import main.commands.altermativeAuto.RightToRightScaleSwitch;
import main.commands.drivetrain.VeloFF;
import main.commands.elevator.MovePID;
import main.commands.intake.SpinIn;
import main.commands.intake.SpinOff;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.arm.SwitchArm;
import main.commands.pneumatics.shift.ShiftDown;
import main.commands.pneumatics.shift.ShiftUp;
import main.commands.pneumatics.tilt.SwitchTilt;
import main.commands.pneumatics.tilt.TiltDown;
import main.commands.pneumatics.tilt.TiltUp;

public class OI extends ImprovedClass {	
	private static double moveEl = 0;
	private static double driveDt = 0;
	private static double turnDt = 0;
	
	public OI() {
		xbox.setInternalControl(false);
		xbox2.setInternalControl(false);
		
		xbox.leftJoystickPress.whenPressed(new ShiftUp());
		xbox.leftJoystickPress.whenReleased(new ShiftDown());
		
		//xbox.a.whenPressed(new DistanceDriveStraight(12*10));
		//xbox.b.whenPressed(new TurnToAngleGyro(90, 5));
//		
//		xbox.b.whenPressed(new AltLeftToLeftScale());
//		xbox.a.whenPressed(new AltLeftToLeftSwitch());
//		
//		xbox.y.whenPressed(new AltCenterToLeftSwitch());
//		xbox.x.whenPressed(new AltCenterToRightSwitch());
//	//	xbox
		
		xbox.a.whenPressed(new VeloFF());

		xbox2.leftBumper.whenPressed(new SwitchTilt(new TiltDown(), new TiltUp()));
		xbox2.rightBumper.whenPressed(new SwitchArm(new ArmOpen(), new ArmClose()));
		
//		xbox2.b.whileHeld(new SpinIn());
//		xbox2.b.whenReleased(new SpinOff());
////
//		xbox2.x.whenPressed(new MovePID(50, 5));
//		xbox2.y.whenPressed(new MovePID(switchHeight, 3));
//		
		/*xbox.b.whenPressed(new InitPID());
		xbox.b.whileHeld(new DriveLeftPID(12));
		xbox.b.whenReleased(new EndPID());
		xbox.x.whenPressed(new InitPID());
		xbox.x.whileHeld(new DriveRightPID(12)); 
		xbox.x.whenReleased(new EndPID());*/
	//	xbox.y.whenPressed(new InitPID());
		//xbox.y.whenPressed(new DistanceDriveStraight(driveDt));
		//xbox.y.whenReleased(new EndPID());
		
//		xbox.b.whenPressed(new InitPID());
//		xbox.b.whenPressed(new TurnToAngleInit(90));
		//xbox.b.whileHeld(new TurnTest());
//		xbox.b.whenPressed(new EndPID());
		
		//xbox.b.whenPressed(new AltRightToRightScale());
		
		//xbox.x.whenPressed(new InitPID());
		//xbox.x.whenPressed(new TurnToAngleGyro(90, 5));
		//xbox.x.whenReleased(new EndPID());
		
		SmartDashboard.putNumber("Drive for distance", driveDt);
		SmartDashboard.putNumber("Turn to angle", turnDt);
		SmartDashboard.putNumber("Move to height", moveEl);
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
		
		driveDt = SmartDashboard.getNumber("Drive for distance", driveDt);
		turnDt = SmartDashboard.getNumber("Turn to angle", turnDt);
		moveEl = SmartDashboard.getNumber("Move to height", moveEl);
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
	
	public void setArmButton(boolean press) {
		setButtonValues(false, false, false, false, false, press, false, false, false, false, false, false);
		xbox2.setInternalControl(false);
	}
	
	public void setInternalControl(boolean internalControl) {
		xbox.setInternalControl(internalControl);
		xbox2.setInternalControl(internalControl);
	}
}
 