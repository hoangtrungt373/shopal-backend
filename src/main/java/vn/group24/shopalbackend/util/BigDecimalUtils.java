package vn.group24.shopalbackend.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility to perform comparison of BigDecimals
 *
 * @author akc
 */
public class BigDecimalUtils {

    private BigDecimalUtils() {
        // Utility class
    }

    /**
     * Method to verify is the value of the source target is greater than the value of the target
     *
     * @param source
     * @param target
     * @return
     */
    public static boolean isGreaterThan(BigDecimal source, BigDecimal target) {
        boolean isGreaterThan = false;
        if (source != null && target != null && source.compareTo(target) > 0) {
            isGreaterThan = true;
        }
        return isGreaterThan;
    }

    /**
     * Method to verify is the value of the source target is greater or equal to the value of the target
     *
     * @param source
     * @param target
     * @return
     */
    public static boolean isGreaterOrEqualTo(BigDecimal source, BigDecimal target) {
        boolean isGreaterOrEqualTo = false;
        if (source != null && target != null && source.compareTo(target) >= 0) {
            isGreaterOrEqualTo = true;
        }
        return isGreaterOrEqualTo;
    }

    /**
     * Method to verify is the value of the source target is less than the value of the target
     *
     * @param source
     * @param target
     * @return
     */
    public static boolean isLessThan(BigDecimal source, BigDecimal target) {
        boolean isLessThan = false;
        if (source != null && target != null && source.compareTo(target) < 0) {
            isLessThan = true;
        }
        return isLessThan;
    }

    /**
     * Method to verify is the value of the source target is less or equal to the value of the target
     *
     * @param source
     * @param target
     * @return
     */
    public static boolean isLessOrEqualTo(BigDecimal source, BigDecimal target) {
        boolean isLessOrEqualTo = false;
        if (source != null && target != null && source.compareTo(target) <= 0) {
            isLessOrEqualTo = true;
        }
        return isLessOrEqualTo;
    }

    /**
     * Method to verify is the value of the source target is equal to the value of the target
     *
     * @param source
     * @param target
     * @return
     */
    public static boolean isEqualTo(BigDecimal source, BigDecimal target) {
        boolean isEqualTo = false;
        if (source != null && target != null && source.compareTo(target) == 0) {
            isEqualTo = true;
        }
        return isEqualTo;
    }

    /**
     * @param value the nullable BigDecimal
     * @return true if value !=null and &lt;gt; 0.
     */
    public static boolean isNotZero(final BigDecimal value) {
        return value != null && value.signum() != 0;
    }

    /**
     * @param value the nullable BigDecimal
     * @return true if value !=null and 0.
     */
    public static boolean isZero(final BigDecimal value) {
        return value != null && value.signum() == 0;
    }

    /**
     * Add 2 BigDecimal safely (i.e. handles nulls as zeros)
     * @param v1 the nullable BigDecimal
     * @param v2 the nullable BigDecimal
     * @return the sum of the 2 BigDecimal
     */
    private static BigDecimal doAdd(final BigDecimal v1, final BigDecimal v2) {
        BigDecimal total = v1;
        if (v1 != null && v2 != null) {
            total = v1.add(v2);
        } else if (v2 != null) {
            total = v2;
        }
        return total;
    }

    /**
     * Add n BigDecimal safely (i.e. handles nulls)
     * @param start initial BigDecimal
     * @param values series of BigDecimals can be null/empty
     * @return the sum of the n non null BigDecimals
     */
    public static BigDecimal add(final BigDecimal start, final BigDecimal... values) {
        BigDecimal total = start != null ? start : BigDecimal.ZERO;
        if (values != null) {
            for (final BigDecimal v : values) {
                total = doAdd(total, v);
            }
        }
        return total;
    }

    /**
     * Subtract n BigDecimal safely from the start value (i.e. handles nulls as zeros), returns 0
     * @param start starting point, if null, use 0
     * @param values series of BigDecimal to subtract from start, can be null / empty
     * @return start - the series of values
     */
    public static BigDecimal subtract(final BigDecimal start, final BigDecimal... values) {
        BigDecimal total = start != null ? start : BigDecimal.ZERO;
        if (values != null) {
            for (final BigDecimal v : values) {
                total = doSubtract(total, v);
            }
        }
        return total;
    }

    /**
     * Subtract 2 BigDecimal safely (i.e. handles nulls) v1 - v2
     */
    private static BigDecimal doSubtract(final BigDecimal v1, final BigDecimal v2) {
        BigDecimal diff = v1;
        if (v1 != null && v2 != null) {
            diff = v1.subtract(v2);
        } else if (v2 != null) {
            diff = v2.negate();
        }
        return diff;
    }

    /**
     * @return numerator / denominator if they are not null and the denominator is not zero, it returns null otherwise.
     */
    public static BigDecimal divide(final BigDecimal numerator, final BigDecimal denominator, final int rounding) {
        BigDecimal diff = null;
        if (numerator != null && isNotZero(denominator)) {
            diff = numerator.divide(denominator, rounding);
        }
        return diff;
    }

    /**
     * @return numerator / denominator if they are not null and the denominator is not zero, it returns null otherwise.
     */
    public static BigDecimal divide(final int numeratorScale, final BigDecimal numerator, final BigDecimal denominator, final int rounding) {
        BigDecimal diff = null;
        if (numerator != null && isNotZero(denominator)) {
            diff = numerator.setScale(numeratorScale, rounding).divide(denominator, rounding);
        }
        return diff;
    }

    /**
     * @return numerator / denominator if they are not null and the denominator is not zero, it returns null otherwise.
     */
    public static BigDecimal divide(final BigDecimal numerator, final BigDecimal denominator, final int scale, final int rounding) {
        BigDecimal diff = null;
        if (numerator != null && isNotZero(denominator)) {
            diff = numerator.divide(denominator, rounding);
        }
        return BigDecimalUtils.setScale(diff, scale, rounding);
    }

    public static BigDecimal multiply(final BigDecimal value, final BigDecimal multiplicand) {
        BigDecimal diff = null;
        if (value != null && multiplicand != null) {
            diff = value.multiply(multiplicand);
        }
        return diff;
    }

    public static BigDecimal multiply(final BigDecimal value, final BigDecimal... multiplicand) {
        BigDecimal diff = null;
        if (value != null && multiplicand != null) {
            diff = value;
            for (final BigDecimal bd : multiplicand) {
                if (bd != null) {
                    diff = diff.multiply(bd);
                }
            }
        }
        return diff;
    }

    /**
     * @return 1 if v1 &gt; v2 or v2==null and v2!=null
     * @return 0 if v1 == v2 or v1==null and v2==null
     * @return -1 if v1 &lt; v2 or v1==null and v2!=null
     */
    public static int compareTo(final BigDecimal v1, final BigDecimal v2) {
        int ret = 1;
        if (v1 != null && v2 != null) {
            ret = v1.compareTo(v2);
        } else if (v1 == null && v2 == null) {
            ret = 0;
        } else if (v1 == null) {
            ret = -1;
        }
        return ret;
    }

    /**
     * @return return the min amount
     */
    public static BigDecimal min(final BigDecimal v1, final BigDecimal v2) {
        if (v1 == null) {
            return v2;
        } else if (v2 == null) {
            return v1;
        }
        return v1.compareTo(v2) <= 0 ? v1 : v2;
    }

    /**
     * @return return the max amount
     */
    public static BigDecimal max(final BigDecimal... v1) {
        if (v1 == null) {
            return null;
        }
        BigDecimal max = null;
        for (final BigDecimal bd : v1) {
            max = BigDecimalUtils.compareTo(max, bd) >= 0 ? max : bd;
        }
        return max;
    }

    /**
     * returns a new BigDecimal with correct scale.
     *
     * @param bd
     * @return new bd or null
     */
    public static BigDecimal setScale(final BigDecimal bd, final int scale) {
        return setScale(bd, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * returns a new BigDecimal with correct Scale.
     *
     * @param bd
     * @return new bd or null
     */
    public static BigDecimal setScale(final BigDecimal bd, final Integer scale) {
        return setScale(bd, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * returns a new BigDecimal with correct Scales.PERCENT_SCALE. This is used
     * by the table renderer.
     *
     * @param bd
     * @return new bd or null
     */
    public static BigDecimal setScale(final BigDecimal bd, final Integer scale, final int rounding) {
        if (bd != null && scale != null) {
            return bd.setScale(scale, rounding);
        }
        return null;
    }

    /**
     * Verifies a big decimal is within the range provided invclusive
     *
     * @param source
     * @param lowerLimit
     * @param upperLimit
     * @return
     */
    public static boolean isValueInRangeInclusive(BigDecimal source, BigDecimal lowerLimit, BigDecimal upperLimit) {
        if (source != null && lowerLimit != null && upperLimit != null) {
            return isGreaterOrEqualTo(source, lowerLimit) && isLessOrEqualTo(source, upperLimit);
        }

        return false;
    }

    /**
     * Verifies a big decimal is within the range provided exclusive
     *
     * @param source
     * @param lowerLimit
     * @param upperLimit
     * @return
     */
    public static boolean isValueInRangeExclusive(BigDecimal source, BigDecimal lowerLimit, BigDecimal upperLimit) {
        if (source != null && lowerLimit != null && upperLimit != null) {
            return isGreaterThan(source, lowerLimit) && isLessThan(source, upperLimit);
        }

        return false;
    }

    /**
     * Rounding of Big Decimal to Int
     * Note that -5.5 will result in -6
     * https://docs.oracle.com/javase/7/docs/api/java/math/RoundingMode.html
     * @param operand
     * @return
     */
    public int intVal(BigDecimal operand) {
        if (operand == null) {
            return 0;
        }
        return operand.setScale(0, RoundingMode.UP).intValue();
    }

    /**
     * Check if 2 BigDecimal has the same sign
     * @param v1
     * @param v2
     * @return
     */
    public static boolean isSameSign(BigDecimal v1, BigDecimal v2) {
        return isSameSign(v1, v2, false);
    }

    public static boolean isSameSign(BigDecimal v1, BigDecimal v2, boolean acceptNull) {
        if (v1 != null && v2 != null) {
            return checkSameSign(v1, v2);
        }
        return acceptNull;
    }

    private static boolean checkSameSign(BigDecimal v1, BigDecimal v2) {
        return v1.signum() == v2.signum();
    }
}

