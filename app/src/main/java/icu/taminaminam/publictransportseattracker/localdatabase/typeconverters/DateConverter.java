package icu.taminaminam.publictransportseattracker.localdatabase.typeconverters;

import androidx.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Date toDate(long date) {
        return new Date(date);
    }

    @TypeConverter
    public static long fromDate(Date date) {
        return date == null ? Calendar.getInstance().getTimeInMillis() : date.getTime();
    }
}
