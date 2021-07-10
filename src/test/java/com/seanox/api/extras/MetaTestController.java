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
package com.seanox.api.extras;

import com.seanox.apidav.ApiDavMapping;
import com.seanox.apidav.ApiDavMetaMapping;
import com.seanox.apidav.MetaData;
import com.seanox.apidav.MetaOutputStream;
import com.seanox.apidav.MetaProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Example for the integration of apiDAV into a RestController.
 *
 * In general, a managed bean is required.
 * There are various annotations for this: e.g. @Component, @Service, @RestController, ...
 * The methods and annotations for apiDAV combine well with @RestController.
 *
 * MetaTestController 1.0.0 20210710
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210710
 */
@Component
public class MetaTestController {

    // ContentType is set by meta callback.
    // After that, it must be possible to retrieve the value via different ways.

    private static final String MAPPING_A1 = "/extras/meta/a1.txt";
    @ApiDavMapping(path=MAPPING_A1)
    void testA1(MetaOutputStream outputStream) throws IOException {
        outputStream.write((MAPPING_A1 + " " + outputStream.getContentType()).getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_A1)
    void testA1(MetaData meta) {
        meta.setContentType("m1");
    }

    private static final String MAPPING_A2 = "/extras/meta/a2.txt";
    @ApiDavMapping(path=MAPPING_A2)
    void testA2(MetaData metaData, MetaOutputStream outputStream) throws IOException {
        outputStream.write((MAPPING_A2 + " " + metaData).getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_A2)
    void testA2(MetaData meta) {
        meta.setContentType("m2");
    }

    private static final String MAPPING_A3 = "/extras/meta/a3.txt";
    @ApiDavMapping(path=MAPPING_A3)
    void testA3(MetaProperties metaProperties, MetaOutputStream outputStream) throws IOException {
        outputStream.write((MAPPING_A3 + " " + metaProperties.getContentType()).getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_A3)
    void testA3(MetaData meta) {
        meta.setContentType("m3");
    }

    // Also null can be used as a value and is then provided like this.
    // The stand value is only used for untouched fields.
    // If the value null is used, the propfind must omit this attribute.

    private static final String MAPPING_A4 = "/extras/meta/a4.txt";
    @ApiDavMapping(path=MAPPING_A4)
    void testB1(MetaProperties metaProperties, MetaOutputStream outputStream) throws IOException {
        outputStream.write((MAPPING_A4 + " " + outputStream.getContentType()).getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_A4)
    void testB1(MetaData meta) {
        meta.setContentType(null);
    }

    private static final String MAPPING_A5 = "/extras/meta/a5.txt";
    @ApiDavMapping(path=MAPPING_A5)
    void testA5(MetaProperties metaProperties, MetaOutputStream outputStream) throws IOException {
        outputStream.write((MAPPING_A5 + " " + metaProperties.getContentType()).getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_A5)
    void testA5(MetaData meta) {
        meta.setContentType(null);
    }
}