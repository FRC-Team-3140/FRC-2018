package main.java.flagship3140.util;

import edu.wpi.first.wpilibj.SerialPort;

public class TfMini {
	private final SerialPort.Port portTest = SerialPort.Port.kMXP;
	private final int baudRate = 115200;
	private SerialPort p;
	   
	public TfMini() {
		p = new SerialPort(baudRate, portTest);
	}  
	
	public double getValue() {
		return p.read(1)[0];
	}
}
