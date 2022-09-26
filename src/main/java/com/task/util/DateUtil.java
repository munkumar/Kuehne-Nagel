package com.task.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * The Time class is a utility class , which will build provide utilities function related to dates.
 */
public class DateUtil {

    /**
     * To support specific date format which is yyyy-MM-dd.
     */
    static final DateFormat dtf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     *  To build current or today's date as string
     * @return String with current date value in given format.
     */
    public static String getCurrentDateAsString() {
        Date date = new Date();
        String currentDate = dtf.format(date);
        return currentDate;
    }

    /**
     *  To build date of 90 days before current or today's date as string
     * @return String with date value of 90 days before from current-date in given format.
     */
    public static String getDateBefore90daysAsString() {
        Date date = new Date();
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -90);
        Date before90days = cal.getTime();
        String currentDate = dtf.format(before90days);
        return currentDate;
    }

}
