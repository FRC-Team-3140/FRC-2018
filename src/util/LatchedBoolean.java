package util;
//Authors: Alexander Peters and Sol Kim
//Date: 1/17

//This is a class that allows you to change the current state of the output each 
//time a button has been pressed.
public class LatchedBoolean {
	private boolean currentValue = false;
	private boolean input;
	private boolean output;
	
	//This is a public method which allows the user to input data
	public void setInput(boolean input){
		this.input = input;
		logic();
	}
	//This is where the logic is run
	private void logic(){
		output = currentValue ^ input;
	}
	
	//This is a public method that allows the user to get the current
	//state of the output
	public boolean getOutput(){
		return output;
	}
}







