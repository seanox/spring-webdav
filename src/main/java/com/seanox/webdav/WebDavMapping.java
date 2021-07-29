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

import java.io.OutputStream;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;

/**
 * <p>
 *   {@link WebDavMapping} annotates a method for using the WebDAV
 *   implementation, to send data (GET) and defines the virtual entity.
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
 *     <b>{@link MetaProperties}</b><br>
 *     MetaProperties, read-only collector with all attributes of the virtual
 *     entity.
 *   </li>
 *   <li>
 *     <b>{@link MetaOutputStream}</b><br>
 *     {@link OutputStream} with meta information for the response header.
 *   </li>
 * </ul>
 * <p>
 *   No return value is expected.
 * </p>
 * <pre>
 *   &#64;WebDavMapping(path="/example/file.txt")
 *   void getEntity(final MetaOutputStream output) throws IOException {
 *       ...
 *   }

 *   &#64;WebDavMapping(path="/example/file.txt", accept="text/*", contentLengthMax=1073741824)
 *   void getEntity(final MetaOutputStream output) throws IOException {
 *       ...
 *   }
 *
 *   &#64;WebDavMapping(path="/example/fileA.txt", contentLength=1073741824, lastModified="2000-01-01 00:00:00")
 *   &#64;WebDavMapping(path="/example/fileB.txt", contentLength=1073741824, lastModified="2000-01-01 00:00:00")
 *   &#64;WebDavMapping(path="/example/fileC.txt", contentLength=1073741824, lastModified="2000-01-01 00:00:00")
 *   void getEntity(final URI uri, final Properties properties, final MetaProperties metaProperties, final MetaOutputStream output) throws IOException {
 *       ...
 *   }
 *
 *   ...
 * </pre>
 *
 * WebDavMapping 1.0.0 20210725<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210725
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(WebDavMapping.WebDavMappings.class)
public @interface WebDavMapping {

    // NOTE: Following values use the default values: -1, ""

    /**
     * Path as a reference of the virtual entity
     * @return Path the virtual entity
     */
    String path();

    /** Static value of ContentType */
    String contentType() default "";

    /** Static value of ContentLength */
    int contentLength() default -1;

    /** Static value of LastModified (yyyy-MM-dd HH:mm:ss) */
    String creationDate() default "";

    /** Static value of LastModified (yyyy-MM-dd HH:mm:ss) */
    String lastModified() default "";

    /** Static value of flag ReadOnly */
    boolean readOnly() default true;

    /** Static value of flag Hidden */
    boolean hidden() default false;

    /** Static value of flag Accepted */
    boolean accepted() default true;

    /** Static value of flag Permitted */
    boolean permitted() default true;

    /**
     * Optionally to the static values, an array of
     * {@link WebDavMappingAttributeExpression} with dynamic expressions,
     * based on the Spring Expression Language.
     * @return Array of {@link WebDavMappingAttributeExpression}
     */
    WebDavMappingAttributeExpression[] attributeExpressions() default {};

    /**
     * Declaration for {@link WebDavMapping} so that
     * {@link WebDavMapping} can be used multiple times.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface WebDavMappings {
        WebDavMapping[] value();
    }
}