package com.seanox.apidav;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

/**
 * Testing private parts and/or components visible only in the package requires
 * an adapter for access.<br>
 * <br>
 * Why are the tests not in com.seanox.apidav?<br>
 * Spring Test is used for the tests. For this @ComponentScan must scan the
 * package. For the release version, however, it should be ensured that the
 * library com.seanox.apidav also works without @ComponentScan and therefore
 * another package is used for the tests of the package com.seanox.apidav.<br>
 * <br>
 * DateTimeAdapter 1.0.0 20210725<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210725
 */
public class DateTimeAdapter {

    public static TimeZone DEFAULT_TIME_ZONE = DateTime.DEFAULT_TIME_ZONE;

    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private DateTimeAdapter() {
    }

    public static String formatDate(final Date date, final String format) {
        return DateTime.formatDate(DEFAULT_TIME_ZONE, date, format);
    }

    public static String formatDate(final TimeZone timeZone, final Date date, final String format) {
        return DateTime.formatDate(timeZone, date, format);
    }

    public static Date parseDate(final String date, final String format)
            throws ParseException {
        return DateTimeAdapter.parseDate(DEFAULT_TIME_ZONE, date, format);
    }

    public static Date parseDate(final TimeZone timeZone, final String date, final String format)
            throws ParseException {
        final SimpleDateFormat pattern = new SimpleDateFormat(format, Locale.US);
        pattern.setTimeZone(Objects.nonNull(timeZone) ? timeZone : DEFAULT_TIME_ZONE);
        return pattern.parse(date);
    }

    public static Date getBuildDate() {

        final String source = Sitemap.class.getName().replaceAll("\\.", "/") + ".class";
        final URL url = Sitemap.class.getClassLoader().getResource(source);
        if (Objects.nonNull(url.getProtocol())) {
            if (url.getProtocol().equals("jar"))
                return new Date(new java.io.File(url.getFile().replaceAll("(?i)(^file:)|(!.*$)", "")).lastModified());
            if (url.getProtocol().equals("file"))
                return new Date(new java.io.File(url.getFile()).lastModified());
        }
        return null;
    }
}