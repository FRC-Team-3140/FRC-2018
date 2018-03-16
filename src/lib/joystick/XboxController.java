package lib.joystick;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.InternalButton;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

public class XboxController extends Joystick {
	private static boolean internalControl = false;
	/**
	 * Buttons
	 */
	private final JoystickButton aButton;
	private final JoystickButton bButton;
	private final JoystickButton xButton;
	private final JoystickButton yButton;
	private final JoystickButton selectButton;
	private final JoystickButton startButton;
	// Thumb-stick buttons
	private final JoystickButton leftJoystickPressButton;
	private final JoystickButton rightJoystickPressButton;
	// Bumpers
	private final JoystickButton leftBumperButton;
	private final JoystickButton rightBumperButton;
	// Triggers
	private final Button leftTriggerButton;
	private final Button rightTriggerButton;
	
	/**
	 * Internal Buttons
	 */
	public InternalButton a; // NO_UCD (use default)
	public InternalButton b; // NO_UCD (use final)
	public InternalButton x; // NO_UCD (use final)
	public InternalButton y; // NO_UCD (use final)
	public InternalButton select; // NO_UCD (use default)
	public InternalButton start; // NO_UCD (use default)
	// Thumb-stick InternalButtons
	public InternalButton leftJoystickPress; // NO_UCD (use final)
	public InternalButton rightJoystickPress; // NO_UCD (use default)
	// Bumpers
	public InternalButton leftBumper; // NO_UCD (use final)
	public InternalButton rightBumper; // NO_UCD (use final)
	// Triggers
	public InternalButton leftTrigger; // NO_UCD (use default)
	public InternalButton rightTrigger; // NO_UCD (use default)
	
	public XboxController(int port) {
		super(port);
		//Buttons
		aButton = new JoystickButton(this, 1);
		bButton = new JoystickButton(this, 2);
		xButton = new JoystickButton(this, 3);
		yButton = new JoystickButton(this, 4);
		leftBumperButton = new JoystickButton(this, 5);
		rightBumperButton = new JoystickButton(this, 6);
		selectButton = new JoystickButton(this, 7);
		startButton = new JoystickButton(this, 8);
		leftJoystickPressButton = new JoystickButton(this, 9);
		rightJoystickPressButton = new JoystickButton(this, 10);
		leftTriggerButton = new AnalogButton(this, 2, 0.1);
		rightTriggerButton = new AnalogButton(this, 3, 0.1);
		//InternalButtons
		a = new InternalButton();
		b = new InternalButton();
		x = new InternalButton();
		y = new InternalButton();
		leftBumper = new InternalButton();
		rightBumper = new InternalButton();
		select = new InternalButton();
		start = new InternalButton();
		leftJoystickPress = new InternalButton();
		rightJoystickPress = new InternalButton();
		leftTrigger = new InternalButton();
		rightTrigger = new InternalButton();
	}
	
	public void check() {
		if(!internalControl) {
			a.setPressed(aButton.get());
			b.setPressed(bButton.get());
			x.setPressed(xButton.get());
			y.setPressed(yButton.get());
			leftBumper.setPressed(leftBumperButton.get());
			rightBumper.setPressed(rightBumperButton.get());
			select.setPressed(selectButton.get());
			start.setPressed(startButton.get());
			leftJoystickPress.setPressed(leftJoystickPressButton.get());
			rightJoystickPress.setPressed(rightJoystickPressButton.get());
			leftTrigger.setPressed(leftTriggerButton.get());
			rightTrigger.setPressed(rightTriggerButton.get());
		}
	}
	
	public double getMainX(){
		return super.getRawAxis(0);
	}
	
	public double getMainY(){
		return super.getRawAxis(1);
	}
	
	public double getAltX(){
		return super.getRawAxis(4);
	}
	
	public double getAltY(){
		return super.getRawAxis(5);
	}
	
	public double getSmoothedMainX() {
		//return Math.pow(super.getRawAxis(0), 3) * -1;
		return -Math.sin(Math.PI/2 * super.getRawAxis(0));
	}
	
	public double getSmoothedMainY() {
		//return Math.pow(super.getRawAxis(1), 5);
		return Math.sin(Math.PI/2 * super.getRawAxis(1));
	}
	
	public double getSmoothedAltX() {
		//return Math.pow(super.getRawAxis(4), 3) * -1;
		return -Math.sin(Math.PI/2 * super.getRawAxis(4));
	}
	
	public double getSmoothedAltY() {
		//return Math.pow(super.getRawAxis(5), 3);
		return Math.sin(Math.PI/2 * super.getRawAxis(5));
	}
	
	public void setInternalControl(boolean internalControl) {
		XboxController.internalControl = internalControl;		
	}

	public boolean getInternalControl() {
		return internalControl;
	}
}
