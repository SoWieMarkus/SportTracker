package markus.wieland.sporttracker.helper;

import java.util.Locale;

public class TimeConverter {

    private TimeConverter() {
    }

    public static String convertMillisToString(long timestamp) {
        long millis = timestamp % 1000;
        long second = (timestamp / 1000) % 60;
        long minute = (timestamp / (1000 * 60)) % 60;
        long hour = (timestamp / (1000 * 60 * 60)) % 24;

        if (hour == 0) {
            return String.format(Locale.getDefault(), "%02d:%02d", minute, second);
        }
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hour, minute, second);
    }

    public static String formatSpeedPerKm(float speed) {
        int minutes = (int) speed;
        float secondsAsDecimal = speed - minutes;
        int seconds = (int) (secondsAsDecimal * 60);
        return String.format("%02d'%02d\"", minutes, seconds);
    }

    public static String formatDistance(float distance) {
        float distanceInKm = distance / 1000f;
        return String.format("%.2f", distanceInKm);
    }

    public static String formatSpeed(float speed) {
        return String.format("%.2f", speed);
    }


}
