/**
 * LIZENZBEDINGUNGEN - Seanox Software Solutions ist ein Open-Source-Projekt,
 * im Folgenden Seanox Software Solutions oder kurz Seanox genannt.
 * Diese Software unterliegt der Version 2 der GNU General Public License.
 *
 * WebDAV mapping for Spring Boot
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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

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
 * SitemapAdapter 1.0.0 20210725<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210725
 */
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

    public static Object map(final Object sitemap, final java.lang.annotation.Annotation[] annotations)
            throws Exception {
        return ((Sitemap)sitemap).map(Arrays.stream(annotations).map(annotation -> {
            try {return AnnotationAdapter.createAnnotation(annotation);
            } catch (AnnotationException exception) {
                throw new AdapterException(exception);
            }
        }).toArray(Annotation[]::new));
    }
}