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
abstract class Callback {

    private static final String DATE_FORMAT = "yyyy-MM-dd h-h:mm:ss";

    private String path;
    private Type   type;
    private Object object;
    private Method method;

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