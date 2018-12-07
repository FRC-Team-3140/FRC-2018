package main;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import interfacesAndAbstracts.ImprovedClass;
import lib.joystick.XboxController;
import main.commands.drivetrain.DriveProfile;
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

		xbox.a.whenPressed(new VeloFF(120));

		xbox2.leftBumper.whenPressed(new SwitchTilt(new TiltDown(), new TiltUp()));
		xbox2.rightBumper.whenPressed(new SwitchArm(new ArmOpen(), new ArmClose()));
		
		xbox.b.whenPressed(new DriveProfile(myFirstPath));
		
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
 