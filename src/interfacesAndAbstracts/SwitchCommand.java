package interfacesAndAbstracts;

// TODO use this class
public abstract class SwitchCommand extends ImprovedCommand { // NO_UCD (unused code)
	final ImprovedCommand trueCommand;
	final ImprovedCommand falseCommand;
	
	public SwitchCommand(ImprovedCommand trueCommand, ImprovedCommand falseCommand) {
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