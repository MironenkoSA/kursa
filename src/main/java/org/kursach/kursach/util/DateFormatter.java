package org.kursach.kursach.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    
    public static String format(LocalDate date) {
        if (date == null) {
            return "Не указано";
        }
        try {
            return date.format(DATE_FORMATTER);
        } catch (Exception e) {
            return date.toString();
        }
    }
}





