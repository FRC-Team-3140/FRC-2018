package interfacesAndAbstracts;

// TODO use this class
public abstract class SwitchCommandGroup extends ImprovedCommandGroup {
	final ImprovedCommandGroup trueCommand;
	final ImprovedCommandGroup falseCommand;
	
	public SwitchCommandGroup(ImprovedCommandGroup trueCommand, ImprovedCommandGroup falseCommand) {
		this.trueCommand = trueCommand;
		this.falseCommand = falseCommand;
	}
	
	@Override
	public void initialize() {
		if(source()) trueCommand.start();
        else falseCommand.start();
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	public abstract boolean source();
}
