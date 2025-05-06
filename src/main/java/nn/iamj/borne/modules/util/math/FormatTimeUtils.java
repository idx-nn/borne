package nn.iamj.borne.modules.util.math;

public final class FormatTimeUtils {

    private FormatTimeUtils() {}

    public static String formatTimeNoSecondsADV(long i) {
        int hours = (int) ((i / 3600));
        int minutes = (int) ((i % 3600) / 60);
        if (hours == 0) {
            return minutes + " " + plurals(minutes, "мин.", "мин.", "мин.");
        } else return hours + " " + plurals(hours, "ч.", "ч.", "ч.") + " " + minutes + " " + plurals(minutes, "мин.", "мин.", "мин.");
    }

    public static String formatTimeNoSeconds(long i) {
        int days = (int) ((i / 3600)/24);
        int hours = (int) ((i / 3600) - (days*24));
        int minutes = (int) ((i % 3600) / 60);
        if (days == 0) {
            if (hours == 0) {
                return minutes + " " + plurals(minutes, "минуту", "минуты", "минут");
            } else return hours + " " + plurals(hours, "час", "часа", "часов") + " " + minutes + " " + plurals(minutes, "минуту", "минуты", "минут");
        } else return days + " " + plurals(days,"день", "дня", "дней") + " " + hours + " " + plurals(hours, "час", "часа", "часов") + " " + minutes + " " + plurals(minutes, "минуту", "минуты", "минут");
    }

    public static String formatTime(long i) {
        int days = (int) ((i / 3600)/24);
        int hours = (int) ((i / 3600) - (days*24));
        int minutes = (int) ((i % 3600) / 60);
        int seconds = (int) (i % 60);
        if (days == 0) {
            if (hours == 0) {
                if (minutes != 0) {
                    return minutes + " " + plurals(minutes, "минуту", "минуты", "минут") + " " + seconds + " " + plurals(seconds, "секунду", "секунды", "секунд");
                } else return seconds + " " + plurals(seconds, "секунду", "секунды", "секунд");
            } else return hours + " " + plurals(hours, "час", "часа", "часов") + " " + minutes + " " + plurals(minutes, "минуту", "минуты", "минут") + " " + seconds + " " + plurals(seconds, "секунду", "секунды", "секунд");
        } else return days + " " + plurals(days,"день", "дня", "дней") + " " + hours + " " + plurals(hours, "час", "часа", "часов") + " " + minutes + " " + plurals(minutes, "минуту", "минуты", "минут") + " " + seconds + " " + plurals(seconds, "секунду", "секунды", "секунд");
    }

    public static String plurals(int n, final String form1, final String form2, final String form3) {
        if (n == 0) return form3;
        n = Math.abs(n) % 100;
        if (n > 10 && n < 20) return form3;
        n %= 10;
        if (n > 1 && n < 5) return form2;
        if (n == 1) return form1;
        return form3;
    }

}
