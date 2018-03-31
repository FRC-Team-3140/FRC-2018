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
import main.commands.elevator.MoveFullThrottle;
import main.commands.elevator.MoveToPosPID;
import main.commands.elevator.StopElevator;
import main.commands.elevator.TimedLift;
import main.commands.intake.DeployIntake;
import main.commands.intake.DeployIntakeOff;
import main.commands.intake.SpinOff;
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
		xbox2.setInternalControl(false);
		
		// Shoots out without elevator
		xbox.leftJoystickPress.whenPressed(new ShiftUp());
		xbox.leftJoystickPress.whenReleased(new ShiftDown());
		//xbox.rightBumper.whenPressed(new TimedLift(timedLiftPercent, timedLiftTime));
		//xbox.b.whenPressed(new TimedTurn(TurnMode.Left, timedTurnPercent, timedTurn90degTime));
		//xbox.y.whenPressed(new TimedLift(timedLiftPercent, timedLiftTime));
		//xbox.x.whenPressed(new TimedLift(timedLiftPercent, timedLiftFullHeightTime));
		//xbox.x.whenPressed(new MoveToPosPID(switchHeight));
		//xbox.y.whenPressed(new MoveToPosPID(0));
		//xbox.b.whenPressed(new MoveToPosPID(elevatorHeight));

		
		//xbox2.rightTrigger.whileHeld(new SpinOut());
		//xbox2.rightTrigger.whenReleased(new SpinOff());
		//xbox2.y.whenReleased(new IntakeCubeOff());
		//xbox2.leftTrigger.whenPressed(new DeployIntake());
		//xbox2.leftTrigger.whenReleased(new DeployIntakeOff());
		//xbox2.start.whenPressed(new MoveToSwitch(5));
		//xbox2.select.whenPressed(new MoveToScale(5));
		//xbox2.y.whenReleased(new IntakeCubeOff());
		//xbox2.y.whileHeld(new IntakeCube());
		//xbox2.x.whileHeld(new PushOutCube());
		//xbox2.x.whenReleased(new PushOutCubeOff());
		//xbox2.b.whileHeld(new DropCube());
		//xbox2.b.whenReleased(new DropCubeOff());
		xbox2.leftBumper.whenPressed(new SwitchTilt(new TiltDown(), new TiltUp()));
		xbox2.rightBumper.whenPressed(new SwitchArm(new ArmOpen(), new ArmClose()));
//		xbox2.x.whenPressed(new MoveToPosDumb(switchHeight));
//		xbox2.y.whenPressed(new MoveToPosDumb(elevatorBottom));
//		xbox2.b.whenPressed(new MoveToPosDumb(elevatorHeight));
		//xbox2.rightBumper.whenPressed(new TiltDown());
		//xbox2.rightBumper.whenReleased(new TiltUp());
		//xbox2.b.whenReleased(new DropCubeOff());
		//xbox2.rightBumper.whenPressed(new TiltDown());
		//xbox2.rightBumper.whenReleased(new TiltUp());
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
 