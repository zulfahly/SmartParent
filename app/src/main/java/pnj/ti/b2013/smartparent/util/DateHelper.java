package pnj.ti.b2013.smartparent.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

/**
 * Created by badrinteractive on 7/25/16.
 */
public class DateHelper {

    public static int yearDifference(Date startDate, Date endDate) {
        Calendar endCalendar = new GregorianCalendar();
        Calendar startCalendar = new GregorianCalendar();

        endCalendar.setTime(endDate);
        startCalendar.setTime(startDate);

        return endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
    }

    public static int monthDifference(Date startDate, Date endDate) {
        Calendar endCalendar = new GregorianCalendar();
        Calendar startCalendar = new GregorianCalendar();

        endCalendar.setTime(endDate);
        startCalendar.setTime(startDate);

        int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);

        return diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
    }

    public static long dayDifference(Date startDate, Date endDate) {
        return TimeUnit.DAYS.convert(endDate.getTime() - startDate.getTime(), TimeUnit.MILLISECONDS);
    }

}
