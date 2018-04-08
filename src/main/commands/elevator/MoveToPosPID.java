package main.commands.elevator;

import interfacesAndAbstracts.ImprovedCommand;
import main.Robot;

public class MoveToPosPID extends ImprovedCommand {
	private double targetPos;
	
	public MoveToPosPID(double targetPos) {
		requires(Robot.el);
		this.targetPos = targetPos;
	}
	
	// Called just before this Command runs the first time
    protected void initialize() {
    	Robot.setRobotOperationMode(RobotOperationMode.DefaultVoltComp);
    	Robot.el.zeroPIDVariables();
    }
	
	protected void execute() {
		Robot.el.moveWithPID(targetPos);
	}

	@Override
	protected boolean isFinished() {
		return Robot.el.isIntakeAtPos(targetPos);
	}
	
	protected void end() {
    	Robot.setRobotOperationMode(RobotOperationMode.Normal);
    	Robot.el.zeroPIDVariables();
	}
	
	protected void interupted() {
		end();
	}
}