package pl.jellytech.machiavelli.cards.utils;

import java.util.concurrent.TimeUnit;

public class TimeUtils {
    private static final int MILLIS_PER_SECOND = 1000;

    public static long MinutesToMilis(int minutes) {
        return TimeUnit.MINUTES.toMillis(minutes);
    }

    public static long TicksToMinutes(long ticks) {
        return MilisecondsToMinutes(TicksToMilliseconds(ticks));
    }

    public static long MilisecondsToMinutes(long milis) {
        return TimeUnit.MILLISECONDS.toMinutes(milis);
    }

    public static int TicksToMilliseconds(long ticks) {
        return (int) (ticks % MILLIS_PER_SECOND);
    }
}
