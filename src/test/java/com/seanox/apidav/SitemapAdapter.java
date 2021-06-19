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

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class SitemapAdapter {

    private static final Method SitemapNormalizePathMethod;

    static {
        try {
            SitemapNormalizePathMethod = Sitemap.class.getDeclaredMethod("normalizePath", String.class);
            SitemapNormalizePathMethod.setAccessible(true);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    private static Exception getTargetException(Exception exception) {
        while (exception instanceof InvocationTargetException) {
            if (!exception.equals(((InvocationTargetException)exception).getTargetException()))
                exception = (Exception)((InvocationTargetException)exception).getTargetException();
            else break;
        }
        return exception;
    }

    private static RuntimeException getTargetRuntimeException(Exception exception) {
        exception = SitemapAdapter.getTargetException(exception);
        if (exception instanceof RuntimeException)
            return (RuntimeException)exception;
        return new RuntimeException(exception);
    }

    public static Sitemap createInstance() {
        return new Sitemap();
    }

    public static String normalizePath(final String path) {
        try {return (String)SitemapNormalizePathMethod.invoke(Sitemap.class, path);
        } catch (Exception exception) {
            throw SitemapAdapter.getTargetRuntimeException(exception);
        }
    }

    public static Object map(final Sitemap sitemap, final Annotation[] annotations)
            throws Exception {
        return sitemap.map(Arrays.stream(annotations).map(annotation -> {
            try {return CallbackAdapter.createCallback(annotation);
            } catch (AnnotationException exception) {
                throw new AdapterException(exception);
            }
        }).toArray(Callback[]::new));
    }
}