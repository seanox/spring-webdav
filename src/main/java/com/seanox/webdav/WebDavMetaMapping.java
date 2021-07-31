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

/**
 * <p>
 *   WebDavMetaMapping annotates a method for getting the meta data for a
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
 *     nested case-insensitive map.
 *   </li>
 *   <li>
 *     <b>{@link WebDavMappingAttribute}</b><br>
 *     The attribute requested with the method.
 *   </li>
 *   <li>
 *     <b>{@link MetaData}</b><br>
 *     Writable collector containing all relevant attributes for a virtual
 *     entity.
 *   </li>
 * </ul>
 * <p>
 *   No return value is expected.
 * </p>
 * <pre>
 *   &#64;WebDavMetaMapping(path="/example/file.txt")
 *   void setMetaData(final MetaData meta) {
 *       meta.set...
 *       ...
 *   }
 *
 *   &#64;WebDavMetaMapping(path="/example/file.txt")
 *   void setMetaData(final URI uri, final Properties properties, final MetaData meta) {
 *       meta.set...
 *       ...
 *   }
 *
 *   &#64;WebDavMetaMapping(path="/example/fileA.txt")
 *   &#64;WebDavMetaMapping(path="/example/fileB.txt")
 *   &#64;WebDavMetaMapping(path="/example/fileC.txt")
 *   void setMetaData(final MetaData meta) {
 *       meta.set...
 *       ...
 *   }
 *
 *   ...
 * </pre>
 *
 * WebDavMetaMapping 1.0.0 20210731<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210731
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(WebDavMetaMapping.WebDavMetaMappings.class)
public @interface WebDavMetaMapping {

    /**
     * Path as a reference of the virtual entity
     * @return Path the virtual entity
     */
    String path();

    /**
     * Declaration for {@link WebDavMetaMapping} so that
     * {@link WebDavMetaMapping} can be used multiple times.
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface WebDavMetaMappings {
        WebDavMetaMapping[] value();
    }
}