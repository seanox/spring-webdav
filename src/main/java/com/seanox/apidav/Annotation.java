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