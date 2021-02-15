package data.transpool.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Util {
    public final static int PLACES = 2;
    public static double round(double value) {
        if (PLACES < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(PLACES, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
