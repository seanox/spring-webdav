/**
 * LIZENZBEDINGUNGEN - Seanox Software Solutions ist ein Open-Source-Projekt,
 * im Folgenden Seanox Software Solutions oder kurz Seanox genannt.
 * Diese Software unterliegt der Version 2 der Apache License.
 *
 * WebDAV mapping for Spring Boot
 * Copyright (C) 2021 Seanox Software Solutions
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.seanox.webdav;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Testing private parts and/or components visible only in the package requires
 * an adapter for access.<br>
 * <br>
 * Why are the tests not in com.seanox.webdav?<br>
 * Spring Test is used for the tests. For this @ComponentScan must scan the
 * package. For the release version, however, it should be ensured that the
 * library com.seanox.webdav also works without @ComponentScan and therefore
 * another package is used for the tests of the package com.seanox.webdav.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210725
 */
public class MappingAdapter {

    private static final Method MappingNormalizePathMethod;

    static {
        try {
            MappingNormalizePathMethod = Mapping.class.getDeclaredMethod("normalizePath", String.class);
            MappingNormalizePathMethod.setAccessible(true);
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
        exception = MappingAdapter.getTargetException(exception);
        if (exception instanceof RuntimeException)
            return (RuntimeException)exception;
        return new RuntimeException(exception);
    }

    public static Mapping createInstance() {
        return new Mapping();
    }

    public static String normalizePath(final String path) {
        try {return (String)MappingNormalizePathMethod.invoke(Mapping.class, path);
        } catch (Exception exception) {
            throw MappingAdapter.getTargetRuntimeException(exception);
        }
    }

    public static Object map(final Object mapping, final java.lang.annotation.Annotation[] annotations)
            throws Exception {
        return ((Mapping)mapping).map(Arrays.stream(annotations).map(annotation -> {
            try {return AnnotationAdapter.createAnnotation(annotation);
            } catch (AnnotationException exception) {
                throw new AdapterException(exception);
            }
        }).toArray(Annotation[]::new));
    }
}