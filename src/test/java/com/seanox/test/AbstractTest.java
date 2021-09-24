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
package com.seanox.test;

import com.seanox.webdav.WebDavAttributeMapping;
import com.seanox.webdav.WebDavInputMapping;
import com.seanox.webdav.WebDavMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * General implementation for the execution of tests.<br>
 * <br>
 * AbstractTest 1.0.0 20210718<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210718
 */
public class AbstractTest {

    protected static Class<?>[] apiAnnotationClasses = new Class[] {
            WebDavMapping.class, WebDavMapping.WebDavMappings.class,
            WebDavInputMapping.class, WebDavInputMapping.WebDavInputMappings.class,
            WebDavAttributeMapping.class, WebDavAttributeMapping.WebDavAttributeMappings.class
    };

    protected static Class<?>[] apiAnnotationRetentionClasses = new Class[] {
            WebDavMapping.WebDavMappings.class,
            WebDavInputMapping.WebDavInputMappings.class,
            WebDavAttributeMapping.WebDavAttributeMappings.class
    };

    private static Annotation[] getDeclaredApiAnnotations(final Annotation annotation) {
        final Class<? extends Annotation> annotationType = annotation.annotationType();
        if (Arrays.asList(AbstractTest.apiAnnotationClasses).contains(annotationType)
                && !Arrays.asList(AbstractTest.apiAnnotationRetentionClasses).contains(annotationType))
            return new Annotation[] {annotation};
        if (annotation instanceof WebDavMapping.WebDavMappings)
            return ((WebDavMapping.WebDavMappings)annotation).value();
        if (annotation instanceof WebDavInputMapping.WebDavInputMappings)
            return ((WebDavInputMapping.WebDavInputMappings)annotation).value();
        if (annotation instanceof WebDavAttributeMapping.WebDavAttributeMappings)
            return ((WebDavAttributeMapping.WebDavAttributeMappings)annotation).value();
        return new Annotation[0];
    }

    private static Annotation[] getDeclaredApiAnnotations(final Method method) {
        Objects.requireNonNull(method);
        final List<Annotation> annotations = new ArrayList<>();
        for (Annotation annotation : method.getDeclaredAnnotations())
            annotations.addAll(Arrays.asList(AbstractTest.getDeclaredApiAnnotations(annotation)));
        return annotations.toArray(Annotation[]::new);
    }

    public Collection<Annotation> collectApiAnnotationsFromCurrentMethod() {
        final StackTraceElement stackTraceElement = Arrays.stream(new Throwable().getStackTrace()).skip(1)
                .filter(entry -> this.getClass().getName().equals(entry.getClassName())
                        && !entry.getMethodName().contains("$")).findFirst().orElseThrow();
        final Collection<Annotation> annotations = new ArrayList<>();
        Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method -> method.getName().matches(stackTraceElement.getMethodName()))
                .sorted((method, compare) -> method.getName().compareToIgnoreCase(compare.getName()))
                .forEach(method -> annotations.addAll(Arrays.asList(AbstractTest.getDeclaredApiAnnotations(method))));
        return annotations;
    }

    public static String createObjectFingerprint(final Object... objects) {
        final Map<Object, String> repository = new HashMap<>();
        final StringBuilder fingerprint = new StringBuilder();
        for (final Object object : objects) {
            if (Objects.nonNull(object)) {
                if (!repository.containsKey(object)) {
                    final int index = repository.size();
                    fingerprint.append(Character.toString((char)65 +index));
                    repository.put(object, Character.toString((char)97 +index));
                } else fingerprint.append(repository.get(object));
            } else fingerprint.append("-");
        }
        return fingerprint.toString();
    }
}