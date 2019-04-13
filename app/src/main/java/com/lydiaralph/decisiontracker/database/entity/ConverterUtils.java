package com.lydiaralph.decisiontracker.database.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import androidx.room.TypeConverter;

public class ConverterUtils {
    @TypeConverter
    public LocalDate fromTimestamp(Long value) {
        return value == null ?
                LocalDate.now() :
                Instant.ofEpochSecond(value).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @TypeConverter
    public Long dateToTimestamp(LocalDate date) {
        if (date == null) {
            return null;
        } else {
            return date.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
        }
    }
}
