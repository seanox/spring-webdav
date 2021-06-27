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

import org.springframework.util.LinkedCaseInsensitiveMap;

import java.util.Map;

/**
 * Case-insensitive properties based on a {@link java.util.Map}.
 *
 * @param <V> Data type of value
 *
 * Properties 1.0.0 20210627
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210627
 */
public class Properties<V> extends LinkedCaseInsensitiveMap<V> {

    public Properties() {
        super();
    }

    public Properties(Map<String, V> map) {
        final Properties properties = new Properties<>();
        properties.putAll(this);
    }

    @Override
    public Properties<V> clone() {
        return new Properties<V>(this);
    }
}