package com.goldencargo.component;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateConverter implements Converter<String, Date> {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    @Override
    public Date convert(String source) {
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(source);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Expected format: " + DATE_FORMAT);
        }
    }
}
