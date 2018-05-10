package main.commands.altermativeAuto;

import edu.wpi.first.wpilibj.command.WaitCommand;
import interfacesAndAbstracts.ImprovedCommandGroup;
import main.commands.drivetrain.AltDistanceDriveStraight;
import main.commands.drivetrain.DistanceDriveStraight;
import main.commands.drivetrain.TurnToAngleGyro;
import main.commands.intake.SpinOutTime;

public class AltLeftToLeftSwitch extends ImprovedCommandGroup {
	public AltLeftToLeftSwitch() {
		addSequential(new DistanceDriveStraight(148.75));
		addSequential(new TurnToAngleGyro(90, 2.5));
		addSequential(new WaitCommand(0.25));
		addSequential(new AltDistanceDriveStraight(40,2));
		addSequential(new SpinOutTime(5));
//		addSequential(new WaitCommand(5));
//		addSequential(new SpinOff());
		/*addSequential(new MovePID(switchHeight, 3));
		addSequential(new WaitCommand(0.5));
		addSequential(new TiltDown());
		addSequential(new WaitCommand(0.3));
		addSequential(new ArmOpen());
		addSequential(new WaitCommand(0.3));
		addSequential(new TiltUp());*/
	}
}
