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

/**
 * Constants for attributes of {@link WebDavMappingAttributeExpression} and
 * {@link WebDavAttributeMapping}.
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