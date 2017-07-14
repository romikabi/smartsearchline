package com.aita.helpers;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Patterns;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MainHelper {

    public static final int DEFAULT_ID_LETTERS_COUNT = 5;
    private static final char[] c = new char[]{'k', 'm', 'b', 't'};

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");//dd/MM/yyyy
        Date now = new Date();
        return sdfDate.format(now);
    }

    public static String getCurrentTimeStampWithMinutes() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        int pickedYear = c.get(Calendar.YEAR);
        int pickedMonth = c.get(Calendar.MONTH);
        int pickedDay = c.get(Calendar.DAY_OF_MONTH);
        int pickedHour = c.get(Calendar.HOUR_OF_DAY);
        int pickedMinute = c.get(Calendar.MINUTE);

        return String.format(
                Locale.US,
                "%d/%02d/%02d %02d:%02d",
                pickedYear,
                pickedMonth + 1,
                pickedDay,
                pickedHour,
                pickedMinute
        );
    }

    /**
     * @param time input in seconds
     */
    public static String getDateStamp(long time) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd");
        sdfDate.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date now = new Date(time * 1000L);
        return sdfDate.format(now);
    }

    /**
     * @param seconds input in seconds and already in proper timezone
     * @return time in GMT0 timezone
     */
    public static String getTimeStamp(Context context, long seconds) {
        java.text.DateFormat df = DateFormat.getTimeFormat(context);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(new Date(seconds * 1000L));
    }

    public static String getTimeStamp(long seconds) {
        final int hours = (int) TimeUnit.SECONDS.toHours(seconds);
        final int minutes = (int) (TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.HOURS.toMinutes(hours));
        return String.format(getFormatString(hours, minutes), hours, minutes);
    }

    public static String getTimeStampWithMinutes(long seconds) {
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.setTimeInMillis(seconds * 1000L);
        int pickedYear = c.get(Calendar.YEAR);
        int pickedMonth = c.get(Calendar.MONTH);
        int pickedDay = c.get(Calendar.DAY_OF_MONTH);
        int pickedHour = c.get(Calendar.HOUR_OF_DAY);
        int pickedMinute = c.get(Calendar.MINUTE);

        return String.format(
                Locale.US,
                "%d/%02d/%02d %02d:%02d",
                pickedYear,
                pickedMonth + 1,
                pickedDay,
                pickedHour,
                pickedMinute
        );
    }

    public static String getHoursMinutes(long seconds) {
        return String.format(
                "%02d:%02d",
                TimeUnit.SECONDS.toHours(seconds) % 24,
                (TimeUnit.SECONDS.toMinutes(seconds) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.SECONDS.toHours(seconds))) % 60
        );
    }

    public static String getReadableHoursMinutesString(long seconds) {
        /*final Context context = AitaApplication.getInstance().getApplicationContext();
        final long hours = TimeUnit.SECONDS.toHours(seconds);
        final long minutes = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.HOURS.toMinutes(hours);

        String result = "";

        if (hours != 0) {
            result += hours + context.getString(R.string.timeline_hours_letter) + " ";
        }

        if (minutes != 0) {
            result += minutes + context.getString(R.string.timeline_minutes_letter);
        }*/
        //TODO: uncomment&complete
        String result = "commented method";
        return result;
    }

    public static String getTimeToEventText(final long seconds) {
        final long secondsInDay = TimeUnit.DAYS.toSeconds(1);

        if (seconds < secondsInDay) {
            return MainHelper.getReadableHoursMinutesString(seconds);
        } else {
            return MainHelper.getDaysString(seconds);
        }
    }

    public static boolean isRussianLocale() {
        return Locale.getDefault().getLanguage().toLowerCase().contains("ru");
    }

    public static String getFormatString(int hours, int minutes) {
        StringBuilder builder = new StringBuilder();
        if (hours < 10) {
            builder.append("0%d:");
        } else {
            builder.append("%d:");
        }
        if (minutes < 10) {
            builder.append("0%d");
        } else {
            builder.append("%d");
        }
        return builder.toString();
    }

    /**
     * @param timeMillis input in milliseconds TODO: covert to seconds
     */
    public static String formatLocaleDate(Context context, long timeMillis) {
        java.text.DateFormat df = DateFormat.getMediumDateFormat(context);
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(new Date(timeMillis));
    }

    /**
     * @param timeMillis input in milliseconds TODO: covert to seconds
     */
    public static String formatLocaleDateShort(long timeMillis) {
        SimpleDateFormat df = new SimpleDateFormat("MMM d", Locale.getDefault());
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(new Date(timeMillis));
    }

    /**
     * @param time input in milliseconds TODO: covert to seconds
     */
    public static String formatTimeMinutesFromMillis(long time) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS
                        .toMinutes(TimeUnit.MILLISECONDS.toHours(time))
        );
    }

    public static String oneTwoMany(long k) {
        if (k <= 1000) {
            k %= 100;
            if (k > 10 && k < 20) {
                return "M";
            }
            switch ((int) k % 10) {
                case 1:
                    return "O";
                case 2:
                case 3:
                case 4:
                    return "T";
                default:
                    return "M";
            }
        } else {
            k %= 1000;
            if (k > 10 && k < 20) {
                return "M";
            }
            switch ((int) k % 10) {
                case 1:
                    return "O";
                case 2:
                case 3:
                case 4:
                    return "T";
                default:
                    return "M";
            }
        }
    }

    public static String getDaysString(final long seconds) {
        final long days = TimeUnit.SECONDS.toDays(seconds);/*
        switch (oneTwoMany(days)) {
            case "M":
                return days + " " + AitaApplication.getInstance().getString(R.string.days_many);
            case "T":
                return days + " " + AitaApplication.getInstance().getString(R.string.days_two);
            default:
                return days + " " + AitaApplication.getInstance().getString(R.string.days_one);
        }*/
        //TODO:stub-to-complete
        return "another stub to complete";
    }

    public static String formatMany(int type, long count) {
        String text;
       /* if (type == 0) {
            String format = MainHelper.oneTwoMany(count);
            switch (format) {
                case "M":
                    text = AitaApplication.getInstance().getResources().getString(R.string.hours_many);
                    break;
                case "T":
                    text = AitaApplication.getInstance().getResources().getString(R.string.hours_two);
                    break;
                default:
                    text = AitaApplication.getInstance().getResources().getString(R.string.hours_one);
                    break;
            }
        } else if (type == 1) {
            String format = MainHelper.oneTwoMany(count);
            switch (format) {
                case "M":
                    text = AitaApplication.getInstance().getResources().getString(R.string.miles_many);
                    break;
                case "T":
                    text = AitaApplication.getInstance().getResources().getString(R.string.miles_two);
                    break;
                default:
                    text = AitaApplication.getInstance().getResources().getString(R.string.miles_one);
                    break;
            }
        } else if (type == 3) {
            String format = MainHelper.oneTwoMany(count);
            switch (format) {
                case "M":
                    text = AitaApplication.getInstance().getResources().getString(R.string.control_cardView_submitInfo_default_many);
                    break;
                case "T":
                    text = AitaApplication.getInstance().getResources().getString(R.string.control_cardView_submitInfo_default_two);
                    break;
                default:
                    text = AitaApplication.getInstance().getResources().getString(R.string.control_cardView_submitInfo_default_one);
                    break;
            }
        } else if (type == 4) {
            String format = MainHelper.oneTwoMany(count);
            switch (format) {
                case "M":
                    text = AitaApplication.getInstance().getResources().getString(R.string.tips_many);
                    break;
                case "T":
                    text = AitaApplication.getInstance().getResources().getString(R.string.tips_two);
                    break;
                default:
                    text = AitaApplication.getInstance().getResources().getString(R.string.tips_one);
                    break;
            }
        } else if (type == 23) {
            String format = MainHelper.oneTwoMany(count);
            switch (format) {
                case "M":
                    text = AitaApplication.getInstance().getResources().getString(R.string.minute_many);
                    break;
                case "T":
                    text = AitaApplication.getInstance().getResources().getString(R.string.minute_two);
                    break;
                default:
                    text = AitaApplication.getInstance().getResources().getString(R.string.minute_one);
                    break;
            }
        } else {
            String format = MainHelper.oneTwoMany(count);
            switch (format) {
                case "M":
                    text = AitaApplication.getInstance().getResources().getString(R.string.meters_many);
                    break;
                case "T":
                    text = AitaApplication.getInstance().getResources().getString(R.string.meters_two);
                    break;
                default:
                    text = AitaApplication.getInstance().getResources().getString(R.string.meters_one);
                    break;
            }
        }*/
        //TODO:stub-to-complete
        return "another stub to complete";
        // return text;
    }

    public static boolean isDummyOrNull(String str, String dummyPrefix) {
        if (str == null) {
            return true;
        } else {
            str = str.trim();
            return str.isEmpty() ||
                    str.toLowerCase().equals("null") ||
                    str.startsWith(dummyPrefix) ||
                    str.startsWith("Unknown") ||
                    str.startsWith("Неизвест");
        }
    }

    public static boolean isDummyOrNull(String str) {
        return isDummyOrNull(str, "234356789");
    }

    public static long getSecondsForYear(int year) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.set(year, 0, 1, 0, 0, 0);
        return TimeUnit.MILLISECONDS.toSeconds(calendar.getTimeInMillis());
    }

    /**
     * Recursive implementation, invokes itself for each factor of a thousand, increasing the class on each invokation.
     *
     * @param n         the number to format
     * @param iteration in fact this is the class from the array c
     * @return a String representing the number n formatted in a cool looking way.
     */
    public static String shortNumber(double n, int iteration) {
        double d = ((long) n / 100) / 10.0;
        boolean isRound = (d * 10) % 10 == 0; // true if the decimal part is equal to 0 (then it's trimmed anyway)
        return (d < 1000 ? // this determines the class, i.e. 'k', 'm' etc
                ((d > 99.9 || isRound || (!isRound && d > 9.99) ? // this decides whether to trim the decimals
                        (int) d * 10 / 10 : d + "" // (int) d * 10 / 10 drops the decimal
                ) + "" + c[iteration])
                : shortNumber(d, iteration + 1));

    }

    public static boolean isBrokenSamsungDevice() {
        return (Build.MANUFACTURER.equalsIgnoreCase("Samsung")
                && isBetweenAndroidVersions(
                Build.VERSION_CODES.LOLLIPOP,
                Build.VERSION_CODES.LOLLIPOP_MR1
        ));
    }

    private static boolean isBetweenAndroidVersions(int min, int max) {
        return Build.VERSION.SDK_INT >= min && Build.VERSION.SDK_INT <= max;
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public final static boolean isValidPhone(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches();
    }

    public static void log(String dbName, String s) {

    }

    public static void logException(Exception e) {
    }
}
