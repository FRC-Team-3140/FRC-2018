package main;

import interfacesAndAbstracts.ImprovedClass;
import lib.joystick.XboxController;
import main.commands.commandgroups.cubeManipulator.DropCube;
import main.commands.commandgroups.cubeManipulator.DropCubeOff;
import main.commands.commandgroups.cubeManipulator.IntakeCube;
import main.commands.commandgroups.cubeManipulator.IntakeCubeOff;
import main.commands.commandgroups.cubeManipulator.PushOutCube;
import main.commands.commandgroups.cubeManipulator.PushOutCubeOff;
import main.commands.drivetrain.TimedDrive;
import main.commands.drivetrain.TimedTurn;
import main.commands.elevator.TimedLift;
import main.commands.intake.SpinOff;
import main.commands.intake.SpinOut;
import main.commands.pneumatics.arm.ArmClose;
import main.commands.pneumatics.arm.ArmOpen;
import main.commands.pneumatics.shift.ShiftDown;
import main.commands.pneumatics.shift.ShiftUp;
import main.commands.pneumatics.tilt.TiltDown;
import main.commands.pneumatics.tilt.TiltUp;

public class OI extends ImprovedClass {	
	public OI() {
		xbox.setInternalControl(false);
		xbox2.setInternalControl(false);
		
		// Shoots out without elevator
		xbox.leftBumper.whenPressed(new ShiftUp());
		xbox.leftBumper.whenReleased(new ShiftDown());
		//xbox.b.whenPressed(new TimedTurn(TurnMode.Left, timedTurnPercent, timedTurn45degTime));
		
		xbox2.rightTrigger.whenPressed(new SpinOut());
		xbox2.rightTrigger.whenReleased(new SpinOff());
		xbox2.y.whenReleased(new IntakeCubeOff());
		xbox2.y.whenPressed(new IntakeCube());
		xbox2.x.whenPressed(new PushOutCube());
		xbox2.x.whenReleased(new PushOutCubeOff());
		xbox2.b.whenPressed(new DropCube());
		xbox2.b.whenReleased(new DropCubeOff());
		xbox2.rightBumper.whenPressed(new TiltDown());
		xbox2.rightBumper.whenReleased(new TiltUp());
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
	
	/*public void DoNothing() {
		Robot.robot_act = Robot.RobotAction.DO_Nothing;
	}
	public void Baseline() {
		Robot.robot_act = Robot.RobotAction.Baseline;
	}
	public void Switch() {
		Robot.robot_act = Robot.RobotAction.Switch;
	}*/

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
 