package sec.secwatchdog.util;

import java.math.BigDecimal;

public class CalculateUtil {

	public static double Get2double(double f) {
		BigDecimal b = new BigDecimal(f);  
		return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();   
	}
}
