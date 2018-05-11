package main.java.flagship3140.subsystems;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import main.java.flagship3140.interfacesAndAbstracts.ImprovedClass;

public class DriverCamera extends ImprovedClass {
	CameraServer camServer;
	UsbCamera cam;

	public DriverCamera() {
		camServer = CameraServer.getInstance();
		cam = camServer.startAutomaticCapture();
		cam.setFPS(30);
		cam.setResolution(320, 240);
	}

	@Override
	public void check() {
		// TODO Auto-generated method stub
		
	}
}
