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
package com.seanox.webdav;

/**
 * <p>
 *   Constants for attributes of {@link WebDavMappingAttributeExpression} and
 *   {@link WebDavAttributeMapping}.
 * </p>
 *
 * WebDavMappingAttribute 1.0.0 20210715<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210715
 */
public enum WebDavMappingAttribute {

    /** Constant for attribute ContentType */
    ContentType(Annotation.Attribute.AttributeType.ContentType),

    /** Constant for attribute ContentLength */
    ContentLength(Annotation.Attribute.AttributeType.ContentLength),

    /** Constant for attribute CreationDate */
    CreationDate(Annotation.Attribute.AttributeType.CreationDate),

    /** Constant for attribute LastModified */
    LastModified(Annotation.Attribute.AttributeType.LastModified),

    /** Constant for attribute ReadOnly */
    ReadOnly(Annotation.Attribute.AttributeType.ReadOnly),

    /** Constant for attribute Hidden */
    Hidden(Annotation.Attribute.AttributeType.Hidden),

    /** Constant for attribute Accepted */
    Accepted(Annotation.Attribute.AttributeType.Accepted),

    /** Constant for attribute Permitted */
    Permitted(Annotation.Attribute.AttributeType.Permitted);

    final Annotation.Attribute.AttributeType type;

    WebDavMappingAttribute(final Annotation.Attribute.AttributeType type) {
        this.type = type;
    }
}