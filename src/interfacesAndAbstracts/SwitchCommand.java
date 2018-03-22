package interfacesAndAbstracts;

public abstract class SwitchCommand extends ImprovedCommand {
	ImprovedCommand trueCommand;
	ImprovedCommand falseCommand;
	
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