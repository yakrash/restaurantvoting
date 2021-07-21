package su.bzz.restaurantvoting.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    private static final LocalTime start = LocalTime.of(11, 59);

    public static LocalDate atStartOfNextDay() {
        LocalDate localDate = LocalDate.now();
        return localDate.plus(1, ChronoUnit.DAYS);
    }

    public static boolean isTimeVoting() {
        LocalTime nowTime = LocalTime.now();
        return (nowTime.compareTo(start) < 0);
    }
}
