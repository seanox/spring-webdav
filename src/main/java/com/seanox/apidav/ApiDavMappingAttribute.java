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

/**
 * TODO:
 *
 * ApiDavMappingAttribute 1.0.0 20210715
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210715
 */
public enum ApiDavMappingAttribute {

    ContentType(Annotation.Attribute.AttributeType.ContentType),
    ContentLength(Annotation.Attribute.AttributeType.ContentLength),
    CreationDate(Annotation.Attribute.AttributeType.CreationDate),
    LastModified(Annotation.Attribute.AttributeType.LastModified),

    ReadOnly(Annotation.Attribute.AttributeType.ReadOnly),
    Hidden(Annotation.Attribute.AttributeType.Hidden),
    Accepted(Annotation.Attribute.AttributeType.Accepted),
    Permitted(Annotation.Attribute.AttributeType.Permitted);

    final Annotation.Attribute.AttributeType type;

    ApiDavMappingAttribute(final Annotation.Attribute.AttributeType type) {
        this.type = type;
    }
}