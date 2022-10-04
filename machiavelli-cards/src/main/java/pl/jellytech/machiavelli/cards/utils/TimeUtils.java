package pl.jellytech.machiavelli.cards.utils;

import java.util.concurrent.TimeUnit;

public class TimeUtils {
    public static long MinutesToMilis(int minutes) {
        return TimeUnit.MINUTES.toMillis(minutes);
    }
}
