package com.middlename.budgetingapp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date getCurrentDateTime() {
        return Calendar.getInstance().getTime();
    }

    public static String formatDate(Date date) {
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)
                && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            // Date is today
            DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
            return timeFormat.format(date);
        } else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR)
                && calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
            // Date is yesterday
            return "Yesterday";
        } else {
            // Date is n days ago or earlier
            long diffInMillis = today.getTimeInMillis() - calendar.getTimeInMillis();
            int diffInDays = (int) (diffInMillis / (24 * 60 * 60 * 1000));

            if (diffInDays < 30) {
                // Date is n days ago
                return diffInDays + " days ago";
            } else {
                // Date is more than 30 days ago
                DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM);
                return dateFormat.format(date);
            }
        }
    }

}
