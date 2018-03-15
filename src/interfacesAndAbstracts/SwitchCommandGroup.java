package interfacesAndAbstracts;

public abstract class SwitchCommandGroup extends ImprovedCommandGroup {
	ImprovedCommandGroup trueCommand;
	ImprovedCommandGroup falseCommand;
	
	public SwitchCommandGroup(ImprovedCommandGroup trueCommand, ImprovedCommandGroup falseCommand) {
		this.trueCommand = trueCommand;
		this.falseCommand = falseCommand;
	}
	
	public void initialize() {
		if(source()) trueCommand.start();
        else falseCommand.start();
	}

	public boolean isFinished() {
		return true;
	}

	public abstract boolean source();
}
