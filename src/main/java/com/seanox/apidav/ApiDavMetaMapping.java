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

/**
 * ApiDavMetaMapping annotates a method for getting the meta data for a virtual
 * entity. The method has no fixed signature and the data types of the
 * arguments are considered as placeholders and filled accordingly.<br>
 * <br>
 * The following data types are supported:
 * <ul>
 *   <li>URI: Path of the virtual entity.</li>
 *   <li>
 *     Properties: Collector with relevant runtime, request and meta
 *     information as a nested map. The keys in the map are case insensitive.
 *   </li>
 *   </li>
 *     MetaData: Writable collector containing all relevant attributes for a
 *     virtual entity.
 *   </li>
 * </ul>
 * Expected return value: void<br>
 * <br>
 * Examples of implementation:<br>
 * <pre>
 *   @ApiDavMetaMapping(path="/example/file.txt")
 *   void testA5(final MetaData meta) {
 *       ...
 *   }
 * </pre>
 * <pre>
 *   @ApiDavMetaMapping(path="/example/file.txt")
 *   void testA5(final URI uri, final Properties properties, final MetaData meta) {
 *       ...
 *   }
 * </pre>
 * A method can be used multiple times with this annotation for different
 * virtual entities.
 *
 * ApiDavMetaMapping 1.0.0 20210703
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210703
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(ApiDavMetaMapping.ApiDavMetaMappings.class)
public @interface ApiDavMetaMapping {

    /** Referenced path of the virtual entity. */
    String path();

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface ApiDavMetaMappings {
        ApiDavMetaMapping[] value();
    }
}