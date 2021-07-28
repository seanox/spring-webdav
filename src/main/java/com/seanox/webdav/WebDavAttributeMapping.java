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

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.net.URI;
import java.util.Date;

/**
 * <p>
 *   The WebDAV implementation is based on a virtual file system whose virtual
 *   entities, always with reference to a virtual path, also have properties.
 * </p>
 * <p>
 *   In the context of annotations and virtual entities, properties are called
 *   attributes.
 * </p>
 * <p>
 *   Properties are needed for different requests and can be provided in
 *   different ways and with different priority.
 * </p>
 * <ol>
 *   <li>
 *     <b>{@link WebDavAttributeMapping}</b><br>
 *     A method can be annotated with {@link WebDavAttributeMapping} for an
 *     attribute.
 *   </li>
 *   <li>
 *     <b>{@link WebDavMetaMapping}</b><br>
 *     As an alternative to single attributes, a collector with all attributes
 *     can also be used. With {@link WebDavMetaMapping} a method is annotated
 *     where the collector is passed.
 *   </li>
 *   <li>
 *     <b>{@link WebDavMapping} + {@link WebDavMappingAttributeExpression}</b><br>
 *      With {@link WebDavMapping} attributes can be defined statically
 *      directly. In combination with {@link WebDavMappingAttributeExpression}
 *      the Spring Expression Language can then be used for dynamic values.
 *   </li>
 *   <li>
 *     <b>{@link WebDavMapping}</b><br>
 *      With {@link WebDavMapping} attributes can be defined statically
 *      directly.
 *   </li>
 * </ol>
 * <p>
 *   {@link WebDavAttributeMapping} annotates a method to get a property for a
 *   virtual entity. The annotation can be used multiple times for different
 *   virtual entities in one method. The method has no fixed signature and the
 *   data types of the arguments are considered as placeholders and filled
 *   accordingly. If arguments with the same data type are used multiple times,
 *   they are filled with the same object multiple times. Unknown data types
 *   are filled with {@code null}.
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
 * </ul>
 * <p>
 *   The expected data type of the return value depends on the attribute:
 *   {@link Boolean}, {@link Integer}, {@link String}, {@link Date}
 * </p>
 * <pre>
 *     &#64;WebDavAttributeMapping(path="/example/file.txt", attribute=WebDavMappingAttribute.ContentType)
 *     String getContentType() {
 *         return ...;
 *     }
 *
 *     &#64;WebDavAttributeMapping(path="/example/fileA.txt", attribute=WebDavMappingAttribute.ContentLength)
 *     &#64;WebDavAttributeMapping(path="/example/fileB.txt", attribute=WebDavMappingAttribute.ContentLength)
 *     &#64;WebDavAttributeMapping(path="/example/fileC.txt", attribute=WebDavMappingAttribute.ContentLength)
 *     Integer getContentLength(final URI uri, final Properties properties, final WebDavMappingAttribute attribute) {
 *         return ...;
 *     }
 *
 *     &#64;WebDavAttributeMapping(path="/example/file.txt", attribute=WebDavMappingAttribute.CreationTime)
 *     Date getCreationTime() {
 *         return ...;
 *     }
 *
 *     &#64;WebDavAttributeMapping(path="/example/file.txt", attribute=WebDavMappingAttribute.LastModified)
 *     Date getLastModified() {
 *         return ...;
 *     }
 *
 *     &#64;WebDavAttributeMapping(path="/example/file.txt", attribute=WebDavMappingAttribute.ReadOnly)
 *     Boolean isReadOnly() {
 *         return ...;
 *     }
 *
 *     &#64;WebDavAttributeMapping(path="/example/file.txt", attribute=WebDavMappingAttribute.Hidden)
 *     Boolean isHidden() {
 *         return ...;
 *     }
 *
 *     &#64;WebDavAttributeMapping(path="/example/file.txt", attribute=WebDavMappingAttribute.Accepted)
 *     Boolean isAccepted() {
 *         return ...;
 *     }
 *
 *     &#64;WebDavAttributeMapping(path="/example/file.txt", attribute=WebDavMappingAttribute.Permitted)
 *     Boolean isPermitted() {
 *         return ...;
 *     }
 *
 *     ...
 * </pre>
 *
 * WebDavAttributeMapping 1.0.0 20210703<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210703
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(WebDavAttributeMapping.WebDavAttributeMappings.class)
public @interface WebDavAttributeMapping {

    /**
     * Path as a reference of the virtual entity
     * @return Path the virtual entity
     */
    String path();

    /**
     * Referenced attribute
     * @return Referenced attribute
     */
    WebDavMappingAttribute attribute();

    /**
     * Declaration for {@link WebDavAttributeMapping} so that
     * {@link WebDavAttributeMapping} can be used multiple times.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface WebDavAttributeMappings {
        WebDavAttributeMapping[] value();
    }
}