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
import java.lang.reflect.Method;

/**
 * Testing private parts and/or components visible only in the package requires
 * an adapter for access.
 *
 * Why are the tests not in com.seanox.apidav?
 * Spring Test is used for the tests. For this @ComponentScan must scan the
 * package. For the release version, however, it should be ensured that the
 * library com.seanox.apidav also works without @ComponentScan and therefore
 * another package is used for the tests of the package com.seanox.apidav.
 */
public class CallbackAdapter {

    public static ApiDavAttribute.AttributeCallback createAttributeCallback(final ApiDavAttribute attributeAnnotation,
            final Object object, final Method method) {
        return ApiDavAttribute.AttributeCallback.create(attributeAnnotation, object, method);
    }

    public static ApiDavInput.InputCallback createInputCallback(final ApiDavInput inputAnnotation,
            final Object object, final Method method) {
        return ApiDavInput.InputCallback.create(inputAnnotation, object, method);
    }

    public static ApiDavMapping.MappingCallback createMappingCallback(final ApiDavMapping mappingAnnotation,
            final Object object, final Method method) throws AnnotationException {
        return ApiDavMapping.MappingCallback.create(mappingAnnotation, object, method);
    }

    public static ApiDavProperty.PropertyCallback createPropertyCallback(final ApiDavProperty propertyAnnotation,
            final Object object, final Method method) {
        return ApiDavProperty.PropertyCallback.create(propertyAnnotation, object, method);
    }

    public static Callback createCallback(final Annotation annotation)
            throws AnnotationException {
        return CallbackAdapter.createCallback(annotation, null, null);
    }

    public static Callback createCallback(final Annotation annotation, final Object object, final Method method)
            throws AnnotationException {
        if (annotation.annotationType().equals(ApiDavAttribute.class))
            return CallbackAdapter.createAttributeCallback((ApiDavAttribute)annotation, object, method);
        else if (annotation.annotationType().equals(ApiDavInput.class))
            return CallbackAdapter.createInputCallback((ApiDavInput)annotation, object, method);
        else if (annotation.annotationType().equals(ApiDavMapping.class))
            return CallbackAdapter.createMappingCallback((ApiDavMapping)annotation, object, method);
        else if (annotation.annotationType().equals(ApiDavProperty.class))
            return CallbackAdapter.createPropertyCallback((ApiDavProperty)annotation, object, method);
        throw new AdapterException("Unsupported annotation type: " + annotation.annotationType());
    }
}