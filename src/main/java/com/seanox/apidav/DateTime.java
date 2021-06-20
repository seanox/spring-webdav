/**
 * LIZENZBEDINGUNGEN - Seanox Software Solutions ist ein Open-Source-Projekt,
 * im Folgenden Seanox Software Solutions oder kurz Seanox genannt.
 * Diese Software unterliegt der Version 2 der GNU General Public License.
 *
 * apiDAV, API-WebDAV mapping for Spring Boot
 * Copyright (C) 2021 Seanox Software Solutions
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 2 of the GNU General Public License as published by the
 * Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 51
 * Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.seanox.apidav;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

class DateTime {

    final static TimeZone DEFAULT_TIME_ZONE = TimeZone.getTimeZone("GMT");

    private DateTime() {
    }

    static String formatDate(final Date date, final String format) {
        return DateTime.formatDate(DEFAULT_TIME_ZONE, date, format);
    }

    static String formatDate(final TimeZone timeZone, final Date date, final String format) {

        final SimpleDateFormat pattern = new SimpleDateFormat(format, Locale.US);
        pattern.setTimeZone(Objects.nonNull(timeZone) ? timeZone : DEFAULT_TIME_ZONE);
        return pattern.format(date);
    }
}