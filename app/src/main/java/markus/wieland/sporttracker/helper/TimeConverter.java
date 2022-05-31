package markus.wieland.sporttracker.helper;

import java.util.Locale;

public class TimeConverter {

    private TimeConverter(){}

    public static String convertMillisToString(long timestamp) {
        long millis = timestamp % 1000;
        long second = (timestamp / 1000) % 60;
        long minute = (timestamp / (1000 * 60)) % 60;
        long hour = (timestamp / (1000 * 60 * 60)) % 24;

       return String.format(Locale.getDefault(),"%02d:%02d:%02d.%01d", hour, minute, second, millis);
    }

    public static String formatSpeedPerKm(float speed) {
        int minutes = (int) speed;
        float secondsAsDecimal = speed - minutes;
        int seconds = (int)(secondsAsDecimal * 60);
        return String.format("%02d'%02d\" /km",minutes, seconds);
    }


}
