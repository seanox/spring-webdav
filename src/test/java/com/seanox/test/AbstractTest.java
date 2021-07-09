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
package com.seanox.test;

import com.seanox.apidav.ApiDavAttributeMapping;
import com.seanox.apidav.ApiDavInputMapping;
import com.seanox.apidav.ApiDavMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * General implementation for the execution of tests.
 *
 * AbstractTest 1.0.0 20210705
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210705
 */
public class AbstractTest {

    protected static Class<?>[] apiAnnotationClasses = new Class[] {
            ApiDavMapping.class, ApiDavMapping.ApiDavMappings.class,
            ApiDavInputMapping.class, ApiDavInputMapping.ApiDavInputMappings.class,
            ApiDavAttributeMapping.class, ApiDavAttributeMapping.ApiDavAttributeMappings.class
    };

    protected static Class<?>[] apiAnnotationRetentionClasses = new Class[] {
            ApiDavMapping.ApiDavMappings.class,
            ApiDavInputMapping.ApiDavInputMappings.class,
            ApiDavAttributeMapping.ApiDavAttributeMappings.class
    };

    private static Annotation[] getDeclaredApiAnnotations(Annotation annotation) {
        final Class<? extends Annotation> annotationType = annotation.annotationType();
        if (Arrays.asList(AbstractTest.apiAnnotationClasses).contains(annotationType)
                && !Arrays.asList(AbstractTest.apiAnnotationRetentionClasses).contains(annotationType))
            return new Annotation[] {annotation};
        if (annotation instanceof ApiDavMapping.ApiDavMappings)
            return ((ApiDavMapping.ApiDavMappings)annotation).value();
        if (annotation instanceof ApiDavInputMapping.ApiDavInputMappings)
            return ((ApiDavInputMapping.ApiDavInputMappings)annotation).value();
        if (annotation instanceof ApiDavAttributeMapping.ApiDavAttributeMappings)
            return ((ApiDavAttributeMapping.ApiDavAttributeMappings)annotation).value();
        return new Annotation[0];
    }

    private static Annotation[] getDeclaredApiAnnotations(Method method) {
        Objects.requireNonNull(method);
        final List<Annotation> annotations = new ArrayList<>();
        for (Annotation annotation : method.getDeclaredAnnotations())
            annotations.addAll(Arrays.asList(AbstractTest.getDeclaredApiAnnotations(annotation)));
        return annotations.toArray(Annotation[]::new);
    }

    public Collection<Annotation> collectApiAnnotationsFromCurrentMethod() {
        final StackTraceElement stackTraceElement = Arrays.stream(new Throwable().getStackTrace()).skip(1)
                .filter(entry ->

                        this.getClass().getName().equals(entry.getClassName())
                        && !entry.getMethodName().contains("$")).findFirst().orElseThrow();
        final Collection<Annotation> annotations = new ArrayList<>();
        Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method -> method.getName().matches(stackTraceElement.getMethodName()))
                .sorted((method, compare) -> method.getName().compareToIgnoreCase(compare.getName()))
                .forEach(method -> annotations.addAll(Arrays.asList(AbstractTest.getDeclaredApiAnnotations(method))));
        return annotations;
    }
}