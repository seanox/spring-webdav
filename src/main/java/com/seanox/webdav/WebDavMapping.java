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
 *     nested case-insensitive map.
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
 *   void getEntity(final URI uri, final MetaProperties metaProperties, final MetaOutputStream output) throws IOException {
 *       ...
 *   }
 *
 *   ...
 * </pre>
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 2021801
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

    /** Static value of CreationDate (yyyy-MM-dd HH:mm:ss) */
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