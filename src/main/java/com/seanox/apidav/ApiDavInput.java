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

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(ApiDavInput.ApiDavInputs.class)
public @interface ApiDavInput {

    // Following values use the default values: -1, ""

    String path();

    String accept()           default "";
    long   contentLengthMax() default -1;

    AttributeExpression[] attributeExpressions() default {};

    @interface AttributeExpression {

        Attribute attribute();
        String    phrase();

        enum Attribute {
            Accept(Annotation.Attribute.AttributeType.Accept),
            ContentLengthMax(Annotation.Attribute.AttributeType.ContentLengthMax),
            Accepted(Annotation.Attribute.AttributeType.Accepted);

            final Annotation.Attribute.AttributeType attributeType;

            Attribute(final Annotation.Attribute.AttributeType type) {
                this.attributeType = type;
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface ApiDavInputs {
        ApiDavInput[] value();
    }
}