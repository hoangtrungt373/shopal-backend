package vn.group24.shopalbackend.util;

import java.text.DecimalFormat;
import java.util.Random;

/**
 *
 * @author ttg
 */
public class StringUtils {

    public static String generateOtp() {
        return new DecimalFormat("000000").format(new Random().nextInt(999999));
    }
}
