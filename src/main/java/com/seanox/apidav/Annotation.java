package com.seanox.apidav;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter(AccessLevel.PACKAGE)
@AllArgsConstructor(access=AccessLevel.PACKAGE)
abstract class Annotation {

    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    private final String path;
    private final Type   type;
    private final Object object;
    private final Method method;

    static Date convertDateTime(String datetime)
            throws ParseException {
        if (datetime.isBlank())
            return null;
        return new SimpleDateFormat(DATE_FORMAT).parse(datetime.trim());
    }

    static String convertText(String text) {
        if (text.isBlank())
            return null;
        return text.trim();
    }

    enum Type {
        Attribute, Input, Mapping, Property
    }
}