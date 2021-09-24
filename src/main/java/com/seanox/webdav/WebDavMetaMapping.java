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