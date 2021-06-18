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
import lombok.Builder;
import lombok.Getter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiDavAttribute {

    String path();
    Attribute attribute();

    enum Attribute {
        Meta, ReadOnly, Hidden, Permission, Acceptance
    }

    @Getter(AccessLevel.PACKAGE)
    static class AttributeCallback extends Callback {

        private Attribute attribute;

        @Builder(access=AccessLevel.PRIVATE)
        AttributeCallback(final String path, final Type type, final Object object, final Method method,
                final Attribute attribute) {

            super(path, type, object, method);

            this.attribute = attribute;
        }

        static AttributeCallback create(final ApiDavAttribute annotation, final Object object, final Method method) {

            return AttributeCallback.builder()
                    .path(annotation.path())
                    .type(Type.Attribute)
                    .object(object)
                    .method(method)

                    .attribute(annotation.attribute())
                    .build();
        }
    }
}