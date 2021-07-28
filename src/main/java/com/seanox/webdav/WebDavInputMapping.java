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

import java.io.InputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;

/**
 * <p>
 *   {@link WebDavInputMapping} is an extension to {@link WebDavMapping} and
 *   annotates a method to receive data (PUT) to a virtual entity, always with
 *   reference to a virtual path.
 * </p>
 * <p>
 *   The annotation can be used multiple times for different virtual entities
 *   in one method. The method has no fixed signature and the data types of the
 *   arguments are considered as placeholders and filled accordingly. If
 *   arguments with the same data type are used multiple times, they are filled
 *   with the same object multiple times. Unknown data types are filled with
 *   {@code null}.
 * </p>
 * <p>
 *   Following data types are supported as placeholders:
 * </p>
 * <ul>
 *   <li>
 *     <b>{@link URI}</b><br>
 *     Path of the virtual entity.
 *   </li>
 *   <li>
 *     <b>{@link Properties}</b><br>
 *     Collector with relevant runtime, request and meta information as a
 *     nested map. The keys in the map are case insensitive.
 *   </li>
 *   <li>
 *     <b>{@link WebDavMappingAttribute}</b><br>
 *     The attribute requested with the method.
 *   </li>
 *   <li>
 *     <b>{@link MetaProperties}</b><br>
 *     MetaProperties, read-only collector with all attributes of the virtual
 *     entity.
 *   </li>
 *   <li>
 *     <b>{@link MetaInputStream}</b><br>
 *     {@link InputStream} with read-only meta information.
 *   </li>
 * </ul>
 * <p>
 *   No return value is expected.
 * </p>
 * <pre>
 *   &#64;WebDavInputMapping(path="/example/file.xls")
 *   void putFile(final MetaInputStream input) throws IOException {
 *       ...
 *   }

 *   &#64;WebDavInputMapping(path="/example/file.txt", accept="text/*", contentLengthMax=1073741824)
 *   void putEnity(final MetaInputStream input) throws IOException {
 *       ...
 *   }
 *
 *   &#64;WebDavInputMapping(path="/example/fileA.txt", accept="text/*", contentLengthMax=1073741824)
 *   &#64;WebDavInputMapping(path="/example/fileB.txt", accept="text/*", contentLengthMax=1073741824)
 *   &#64;WebDavInputMapping(path="/example/fileC.txt", accept="text/*", contentLengthMax=1073741824)
 *   void putEnity(final URI uri, final Properties properties, final MetaProperties metaProperties, final MetaInputStream input) throws IOException {
 *       ...
 *   }
 *
 *   ...
 * </pre>
 *
 * WebDavInputMapping 1.0.0 20210728<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210728
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(WebDavInputMapping.WebDavInputMappings.class)
public @interface WebDavInputMapping {

    // NOTE: Following values use the default values: -1, ""

    /**
     * Path as a reference of the virtual entity
     * @return Path the virtual entity
     */
    String path();

    /**
     * Based on the Accept HTTP header, a comma-separated list of expected
     * MimeType / ContentType. The wildcard character is supported. If the
     * attribute is used, requests that do not match the filter will be
     * rejected with status 406/Not-Acceptable.<br>
     * Without specification or if empty, all content types are accepted.
     * @return Comma-separated list of expected MimeType / ContentType
     */
    String accept() default "";

    /**
     * This attribute limits the size of the request body in bytes. For this
     * purpose the number of bytes read is evaluated. If the limit is exceeded,
     * the requests are rejected with status 413/Payload-Too-Large.<br>
     * Without specification or a value less than 0, the limit is ignored.
     * @return Limit of the request body size in bytes
     */
    int contentLengthMax() default -1;

    /**
     * Optionally to the static values, an array of
     * {@link WebDavInputMappingAttributeExpression} with dynamic expressions,
     * based on the Spring Expression Language.
     * @return Array of {@link WebDavInputMappingAttributeExpression}
     */
    WebDavInputMappingAttributeExpression[] attributeExpressions() default {};

    /**
     * Declaration for {@link WebDavInputMapping} so that
     * {@link WebDavInputMapping} can be used multiple times.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface WebDavInputMappings {
        WebDavInputMapping[] value();
    }
}