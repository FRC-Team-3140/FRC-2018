package util;

public class MathHelper {
	public static double limit(double val, double limit) {
		if(Math.abs(val) >= limit)
			return limit * Math.signum(val);
		else return val;
	}
}
