package pl.jellytech.machiavelli.cards.utils;

import java.util.concurrent.TimeUnit;

public class TimeUtils {
    private static final int MILLIS_PER_SECOND = 1000;

    public static long minutesToMilis(int minutes) {
        return TimeUnit.MINUTES.toMillis(minutes);
    }

    public static long ticksToMinutes(long ticks) {
        return milisecondsToMinutes(ticksToMilliseconds(ticks));
    }

    public static long milisecondsToMinutes(long milis) {
        return TimeUnit.MILLISECONDS.toMinutes(milis);
    }

    public static int ticksToMilliseconds(long ticks) {
        return new Long(ticks % MILLIS_PER_SECOND).intValue();
    }
}
