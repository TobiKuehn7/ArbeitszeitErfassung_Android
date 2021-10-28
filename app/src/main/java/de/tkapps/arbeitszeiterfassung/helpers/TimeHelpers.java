package de.tkapps.arbeitszeiterfassung.helpers;

import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeHelpers {

    /**
     * this function converts a string which contains a datetime to a date which contains a datetime
     * @param str the string should contain a datetime in this format: "YYYY-MM-DD'T'HH:mm:ss" (example: "2021-10-07T14:06:54")
     * @return a Date which contains the given datetime from the string
     */
    public static Date saveStringToDate(String str) {
        char[] strings = str.toCharArray();
        if (strings[0] != '2') {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < strings.length; i++) {
                sb.append(strings[i]);
            }
            str = sb.toString();
        }
        LocalDateTime dateTime = LocalDateTime.parse(str);
        return java.util.Date
                .from(dateTime.atZone(ZoneId.systemDefault())
                        .toInstant());
    }

    /**
     * this function function turns a show string into a date object
     * @param str the show string of the date
     * @return a Date which contains the given datetime from the string
     */
    public static Date showStringToDate(String str) {
        String[] dateTimeParts;

        if (str.contains(": ")) {
            dateTimeParts = str.split(": ")[1].split(", ");
        } else {
            dateTimeParts = str.split(", ");
        }

        String[] dateParts = dateTimeParts[0].split("\\.");
        String date = dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0];

        String time = dateTimeParts[1];

        return saveStringToDate(date + "T" + time);
    }

    /**
     * this function converts a date object which contains a datetime to a string into this format: "yyyy-MM-dd'T'HH:mm:ss"
     * @param date the date object which should be converted to string
     * @return the string which can be saved
     */
    public static String dateToSaveString(Date date) {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * this function is for returning a better string from given date for showing in app
     * @param date the date object which should be converted to string
     * @return the string which can be shown in app
     */
    public static String dateToShowString(Date date) {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy', 'HH:mm:ss");
        return dateFormat.format(date);
    }

    /**
     * methode to calculate the time difference between start and end of working day
     * @param dateStart datetime from work start
     * @param dateEnd datetime from work end
     * @return the time difference between work start and work end
     */
    public static Date calcTimeDiff(Date dateStart, Date dateEnd) {
        long timeDifference = dateEnd.getTime() - dateStart.getTime();
        return new Date(timeDifference);
    }
}