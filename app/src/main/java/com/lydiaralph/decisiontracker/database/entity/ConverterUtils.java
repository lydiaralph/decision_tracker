package com.lydiaralph.decisiontracker.database.entity;

import java.util.Calendar;

import androidx.room.TypeConverter;

public class ConverterUtils {
    @TypeConverter
    public Calendar fromTimestamp(Long value) {
        Calendar returnDate = Calendar.getInstance();
        returnDate.setTimeInMillis(value);
        return value == null ? null : returnDate;
    }

    @TypeConverter
    public Long dateToTimestamp(Calendar date) {
        if (date == null) {
            return null;
        } else {
            return date.getTimeInMillis();
        }
    }
}
