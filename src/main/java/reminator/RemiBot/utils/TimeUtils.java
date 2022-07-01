package reminator.RemiBot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    static {
        TIME_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
    }
    public static String formatTime(long duration) {
        return TIME_FORMAT.format(new Date(duration));
    }
}
