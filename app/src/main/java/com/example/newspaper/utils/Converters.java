package com.example.newspaper.utils;

import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Converters {
    @TypeConverter
    public static Instant fromString(String value) {
        return value == null ? null : Instant.parse(value);
    }

    @TypeConverter
    public static String instantToString(Instant date) {
        return date == null ? null : date.toString();
    }
}
