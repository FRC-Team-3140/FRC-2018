package util;

public class SmartDashboardInteractions {
	/*private boolean lastBigAngle;
	private double lastHeading, lastTtolerance, lastTSKP, lastTSKI, lastTSKD, lastTBKP, lastTBKI, lastTBKD, 
					lastTSmallMaxV, lastTSmallMinV, lastTBigMaxV, lastTBigMinV,
					lastDistance, lastDTolerance, lastDKP, lastDKI, lastDKD, lastDminV, lastDmaxV;
	private int lastSwitchAngle;
	private double heading, Ttolerance;
	private int switchAngle;
	private double TSKP, TSKI, TSKD, TBKP, TBKI, TBKD, TSmallMaxV, TSmallMinV, TBigMaxV, TBigMinV;
	private double distance;
	private double Dtolerance;
	private double DKP, DKI, DKD, DminV, DmaxV;

	public SmartDashboardInteractions() {
		update();
		lastHeading = heading;
		lastTtolerance = Ttolerance;
		lastTSKP = TSKP;
		lastTSKI = TSKI;
		lastTSKD = TSKD;
		lastTBKP = TBKP;
		lastTBKI = TBKI;
		lastTBKD = TBKD;
		lastTSmallMaxV = TSmallMaxV;
		lastTSmallMinV = TSmallMinV;
		lastTBigMaxV = TBigMaxV;
		lastTBigMinV = TBigMinV;
		lastDistance = distance;
		lastDTolerance = Dtolerance;
		lastDKP = DKP;
		lastDKI = DKI;
		lastDKD = DKD;
		lastDminV = DminV;
		lastDmaxV = DmaxV;
		lastSwitchAngle = switchAngle;
	}

	@SuppressWarnings("deprecation")
	public void update() {
		this.heading = SmartDashboard.getDouble("Angle Target", 0.0);
		this.switchAngle = SmartDashboard.getInt("Turn In Place Controller Switch Angle", 45);

		this.TSKP = SmartDashboard.getDouble("Turning KP Small Angle", 0.0);
		this.TSKI = SmartDashboard.getDouble("Turning KI Small Angle", 0.0);
		this.TSKD = SmartDashboard.getDouble("Turning KD Small Angle", 0.0);
		this.TBKP = SmartDashboard.getDouble("Turning KP Big Angle", 0.0);
		this.TBKI = SmartDashboard.getDouble("Turning KI Big Angle", 0.0);
		this.TBKD = SmartDashboard.getDouble("Turning KD Big Angle", 0.0);
		this.TSmallMaxV = SmartDashboard.getDouble("Turning MaxVoltage Small Angle", 0.0);
		this.TSmallMinV = SmartDashboard.getDouble("Turning MinVoltage Small Angle", 0.0);
		this.TBigMaxV = SmartDashboard.getDouble("Turning MaxVoltage Big Angle", 0.0);
		this.TBigMinV = SmartDashboard.getDouble("Turning MinVoltage Big Angle", 0.0);
		this.Ttolerance = SmartDashboard.getDouble("Turning Tolerance", 0.0);
		this.distance = -SmartDashboard.getDouble("Distance To Drive To", 0.0);
		this.DKP = SmartDashboard.getDouble("Distance KP", 0.0);
		this.DKI = SmartDashboard.getDouble("Distance KI", 0.0);
		this.DKD = SmartDashboard.getDouble("Distance KD", 0.0);
		this.Dtolerance = SmartDashboard.getDouble("Distance Tolerance", 0.0);
		this.DminV = SmartDashboard.getDouble("Distance MinVoltage", 0.0);
		this.DmaxV = SmartDashboard.getDouble("Distance MaxVoltage", 0.0);

	}

	public double getHeading() {
		return heading;
	}

	public double getTurningTolerance() {
		return Ttolerance;
	}

	public int switchAngle() {
		return switchAngle;
	}

	public double getTurningKP(boolean big) {
		if(big)
			return TBKP;
		return TSKP;
	}

	public double getTurningKI(boolean big) {
		if(big)
			return TBKI;
		return TSKI;
	}

	public double getTurningKD(boolean big) {
		if(big)
			return TBKD;
		return TSKD;
	}

	public double getTurningSmallMaxV() {
		return TSmallMaxV;
	}
	public double getTurningSmallMinV() {
		return TSmallMinV;
	}
	
	public double getTurningBigMaxV() {
		return TBigMaxV;
	}
	
	public double getTurningBigMinV() {
		return TBigMinV;
	}

	public double getDistance() {
		return distance;
	}

	public double getDistancetolerance() {
		return Dtolerance;
	}

	public double getDistanceKP() {
		return DKP;
	}

	public double getDistanceKI() {
		return DKI;
	}

	public double getDistanceKD() {
		return DKD;
	}
	
	public double getDistanceMinV() {
		return DminV;
	}

	public double getDistanceMaxV() {
		return DmaxV;
	}
	
	public boolean haveAnyTurnVarsChanged() {
		boolean returnBool;
		update();
		if (lastHeading != heading || lastTtolerance != Ttolerance || lastTBKP != TBKP || lastTBKI != TBKI
				|| lastTBKD != TBKD || lastTSKP != TSKP || lastTSKI != TSKI || lastTSKD != TSKD
				|| lastTSmallMaxV != TSmallMaxV || lastTSmallMinV != TSmallMinV || lastTBigMaxV != TBigMaxV
				|| lastTBigMinV != TBigMinV || lastSwitchAngle != switchAngle)
			returnBool = true;

		else
			returnBool = false;

		lastHeading = heading;
		lastTtolerance = Ttolerance;
		lastTBKP = TBKP;
		lastTBKI = TBKI;
		lastTBKD = TBKD;
		lastTSKP = TSKP;
		lastTSKI = TSKI;
		lastTSKD = TSKD;
		lastTSmallMaxV = TSmallMaxV;
		lastTSmallMinV = TSmallMinV;
		lastTBigMaxV = TBigMaxV;
		lastTBigMinV = TBigMinV;
		lastSwitchAngle = switchAngle;

		return returnBool;

	}
	
	public boolean haveAnyDistanceVarsChanged() {
		boolean returnBool;
		update();
		if(lastDistance != distance ||
			lastDTolerance != Dtolerance ||
			lastDKP != DKP ||
			lastDKI != DKI ||
			lastDKD != DKD ||
			lastDminV != DminV ||
			lastDmaxV != DmaxV
		) returnBool = true;
		
		else returnBool = false;
		
		lastDistance = distance;
		lastDTolerance = Dtolerance;
		lastDKP = DKP;
		lastDKI = DKI;
		lastDKD = DKD;
		lastDminV = DminV;
		lastDmaxV = DmaxV;
		
		return returnBool;
		
	}
	
	/*public boolean haveAnyVarsChanged() {
	    try {
	    	Class<?> objClass = this.getClass();
	    	
	        for(Field field : objClass.getFields()) {
	            String name = field.getName();
	            Object value = field.get(this);
	            
	            for(Field fieldNew : objClass.getFields()) {
	            	String nameNew = fieldNew.getName();
	            	Object valueNew = fieldNew.get(this);
	            	if(nameNew.equals(name) && valueNew != value)
	            		return true;
	            }
	        }
	        return false;
	    } catch(Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}*/
}
