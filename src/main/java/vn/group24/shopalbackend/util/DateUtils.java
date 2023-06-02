package vn.group24.shopalbackend.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import org.springframework.util.Assert;


/** Conversion methods for date objects**/
public abstract class DateUtils {

    private DateUtils() {
    }

    public static boolean isTwoPeriodOverlap(LocalDate startDate1, LocalDate endDate1, LocalDate startDate2,
                                             LocalDate endDate2) {

        return (endDate2 == null || !endDate2.isBefore(startDate1)) && (endDate1 == null || !endDate1.isBefore(startDate2));
    }

    public static Date toJavaUtilDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toJavaUtilDateNullSafe(LocalDate localDate) {
        return Optional.ofNullable(localDate)
                .map(date -> toJavaUtilDate(date))
                .orElseGet(() -> null);
    }

    public static LocalDate beginDateOfYear(int year) {
        return LocalDate.of(year, 1, 1);
    }

    public static LocalDate endDateOfYear(int year) {
        return LocalDate.of(year, Month.DECEMBER.getValue(), 31);
    }

    public static LocalDateTime startDay(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MIN);
    }

    public static LocalDateTime startDay(int year, int month, int day) {
        return startDay(LocalDate.of(year, month, day));
    }

    public static LocalDateTime endDay(LocalDate date) {
        return LocalDateTime.of(date, LocalTime.MAX);
    }

    public static LocalDateTime endDay(int year, int month, int day) {
        return endDay(LocalDate.of(year, month, day));
    }

    public static LocalDate max(LocalDate... dates) {
        if (dates.length == 0) {
            return null;
        }

        return Collections.max(Arrays.asList(dates));
    }

    public static LocalDate min(LocalDate... dates) {
        if (dates.length == 0) {
            return null;
        }

        return Collections.min(Arrays.asList(dates));
    }

    public static int compareTo(LocalDate d1, LocalDate d2) {
        if (d1 == null) {
            if (d2 == null) {
                return 0;
            }
            return -1;
        }
        if (d2 == null) {
            return 1;
        }
        return d1.compareTo(d2);
    }

    public static LocalDate firstDayOfMonth(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        return date.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * Return first day of next month
     * Ex:
     *   date = 2018-11-22
     *   result = 2018-12-01
     * @param date LocalDate
     * @return LocalDate: first day of next month
     */
    public static LocalDate firstDayOfNextMonth(LocalDate date) {
        Assert.notNull(date, "Date must not be null");
        return date.plusMonths(1).withDayOfMonth(1);
    }

    /**
     * Return last day of next month
     * Ex:
     *   date = 2018-11-22
     *   result = 2018-12-31
     * @param date LocalDate
     * @return LocalDate: first day of next month
     */
    public static LocalDate lastDayOfNextMonth(LocalDate date) {
        Assert.notNull(date, "Date must not be null");
        return date.plusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
    }

    public static boolean isFirstDayOfMonth(LocalDate date) {
        return date.equals(firstDayOfMonth(date.getYear(), date.getMonthValue()));
    }

    public static LocalDate lastDayOfMonth(int year, int month) {
        LocalDate date = LocalDate.of(year, month, 1);
        return date.with(TemporalAdjusters.lastDayOfMonth());
    }

    public static LocalDate lastDayOfPreviousMonth(LocalDate date) {
        return date.minusMonths(1).with(TemporalAdjusters.lastDayOfMonth());
    }

    public static boolean isLastDayOfMonth(LocalDate date) {
        return date.equals(lastDayOfMonth(date.getYear(), date.getMonthValue()));
    }

    /**
     * check period1 cover period2
     * @param startDate1 start date 1
     * @param endDate1 end date 1
     * @param startDate2 start date 2
     * @param endDate2 end date 2
     * @return true if period1 cover period 2, otherwise false
     */
    public static boolean isPeriod1CoverPeriod2(LocalDate startDate1, LocalDate endDate1, LocalDate startDate2, LocalDate endDate2) {
        startDate1 = startDate1 != null ? startDate1 : LocalDate.MIN;
        endDate1 = endDate1 != null ? endDate1 : LocalDate.MAX;
        startDate2 = startDate2 != null ? startDate2 : LocalDate.MIN;
        endDate2 = endDate2 != null ? endDate2 : LocalDate.MAX;

        return !startDate1.isAfter(startDate2) && !endDate1.isBefore(endDate2);
    }

}
