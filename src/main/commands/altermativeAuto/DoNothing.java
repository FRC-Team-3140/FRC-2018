package main.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;

public class DoNothing extends ImprovedCommandGroup {
	
	public DoNothing() {
		addSequential(new WaitCommand(15));
	}

}
