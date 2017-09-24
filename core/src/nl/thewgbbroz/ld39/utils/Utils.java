package nl.thewgbbroz.ld39.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {
	private Utils() {
	}

	public static String formatLargeNumber(float n) {
		if (n < 1000)
			return String.valueOf((int) n);

		if (n < 1000000) {
			double d = round(n / 1000d, 1);
			return String.valueOf(d) + "K";
		}
		
		double d = round(n / 1000000d, 2);
		return String.valueOf(d) + "M";
	}

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
