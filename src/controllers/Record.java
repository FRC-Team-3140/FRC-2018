package controllers;

import loopController.Loop;
import main.Constants;

// TODO are we using this
public class Record implements Loop, Constants {
	private static boolean recordOK = false;

	public static void okToRecord(boolean okToRecord) {
		recordOK = okToRecord;
		if(recordOK) System.out.println("Ok To Record");
		else System.out.println("Not Ok To Record");
	}
	
	@Override
	public void onStart() {
	}

	@Override
	public void onLoop() {
		if(recordOK)
			execute();
	}
	
	@Override
	public void onStop() {
	}
	
	private void execute() {
		/*Robot.lg.writeLine(Robot.dt.getLeftVoltage() + "," + Robot.dt.getRightVoltage() + "," + controller.a.get() + "," + controller.b.get() + "," + controller.x.get() 
				 + "," + controller.y.get() + "," + controller.leftBumper.get() + "," + controller.rightBumper.get() + "," + controller.select.get() + "," 
				 + controller.start.get() + "," + controller.leftJoystickPress.get() + "," + controller.rightJoystickPress.get() + "," + controller.leftTrigger.get()
				 + "," + controller.rightTrigger.get() + "," + Robot.el.getElevatorVoltage() + "," + controller2.a.get() + "," + controller2.b.get() + "," + controller2.x.get() 
				 + "," + controller2.y.get() + "," + controller2.leftBumper.get() + "," + controller2.rightBumper.get() + "," + controller2.select.get() + "," 
				 + controller2.start.get() + "," + controller2.leftJoystickPress.get() + "," + controller2.rightJoystickPress.get() + "," + controller2.leftTrigger.get()
				 + "," + controller2.rightTrigger.get());*/
	}
}

