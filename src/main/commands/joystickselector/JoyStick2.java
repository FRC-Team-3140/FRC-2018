package main.commands.joystickselector;

import edu.wpi.first.wpilibj.command.Command;
import main.OI;

public class JoyStick2 extends Command {

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return true;
	}
	
	protected void initialize() {
		OI.TwoController();
    }
	    

}
