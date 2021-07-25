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
package com.seanox.api.extras;

import com.seanox.apidav.ApiDavAttributeMapping;
import com.seanox.apidav.ApiDavInputMapping;
import com.seanox.apidav.ApiDavMapping;
import com.seanox.apidav.ApiDavMappingAttribute;
import com.seanox.apidav.ApiDavMetaMapping;
import com.seanox.apidav.MetaData;
import com.seanox.apidav.MetaInputStream;
import com.seanox.apidav.MetaOutputStream;
import com.seanox.apidav.MetaProperties;
import com.seanox.apidav.Properties;
import com.seanox.test.AbstractTest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

/**
 * Test the function of the method signatures.<br>
 * <br>
 * SignaturesTestController 1.0.0 20210720<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210720
 */
@Component
public class SignaturesTestController {

    // Methods do not have a fixed signature, the data type of the parameters
    // is used as a placeholder and filled when called.

    // {@link ApiDavMapping}
    // URI, final Properties, final MetaProperties, final MetaOutputStream, Annotation.AnnotationType.Mapping
    // expected data type from return value: void
    // - without
    // - complete +3 more object
    // - complete +3 more object, but sequence reversed
    // - complete all double, the first two repeat at the end

    public static final String MAPPING_A1 = "/extras/signatures/a1.txt";
    public static final String MAPPING_A2 = "/extras/signatures/a2.txt";
    public static final String MAPPING_A3 = "/extras/signatures/a3.txt";
    public static final String MAPPING_A4 = "/extras/signatures/a4.txt";

    @ApiDavMapping(path=MAPPING_A1)
    void test_A1() {
    }

    @ApiDavMapping(path=MAPPING_A2)
    void test_A2(final URI uri, final Properties properties, final MetaProperties metaProperties, final MetaOutputStream outputStream, final Object o1, final Object o2, final Object o3) throws IOException {
        final String result = "A2-" + AbstractTest.createObjectFingerprint(uri, properties, metaProperties, outputStream, o1, o2, o3);
        outputStream.write(result.getBytes());
    }

    @ApiDavMapping(path=MAPPING_A3)
    void test_A3(final Object o1, final Object o2, final Object o3, final MetaOutputStream outputStream, final MetaProperties metaProperties, final Properties properties, final URI uri) throws IOException {
        final String result = "A3-" + AbstractTest.createObjectFingerprint(o1, o2, o3, outputStream, metaProperties, properties, uri);
        outputStream.write(result.getBytes());
    }

    @ApiDavMapping(path=MAPPING_A4)
    void test_A4(final URI uri1, final URI uri2, final Properties properties1, final Properties properties2, final MetaProperties metaProperties1, final MetaProperties metaProperties2,
            final MetaData o1, final MetaProperties o2, MetaInputStream o3, final MetaOutputStream o4, final URI uri3, final Properties properties3) throws IOException {
        final String result = "A4-" + AbstractTest.createObjectFingerprint(uri1, uri2, properties1, properties2, metaProperties1, metaProperties2, o1, o2, o3, o4, uri3, properties3);
        o4.write(result.getBytes());
    }

    // {@link ApiDavInputMapping}
    // URI, final Properties, final MetaProperties, MetaInputStream, Annotation.AnnotationType.Input (not public)
    // expected data type from return value: void
    // - without
    // - complete +3 more object
    // - complete +3 more object, but sequence reversed
    // - complete all double, the first two repeat at the end

    public static final String MAPPING_B1 = "/extras/signatures/b1.txt";
    public static final String MAPPING_B2 = "/extras/signatures/b2.txt";
    public static final String MAPPING_B3 = "/extras/signatures/b3.txt";
    public static final String MAPPING_B4 = "/extras/signatures/b4.txt";

    private String resultB;

    @ApiDavMapping(path=MAPPING_B1, isReadOnly=false)
    @ApiDavMapping(path=MAPPING_B2, isReadOnly=false)
    @ApiDavMapping(path=MAPPING_B3, isReadOnly=false)
    @ApiDavMapping(path=MAPPING_B4, isReadOnly=false)
    void test_BX(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(this.resultB.getBytes());
    }
    @ApiDavInputMapping(path=MAPPING_B1)
    Object test_B1() {
        this.resultB = "B1";
        return this.resultB;
    }
    @ApiDavInputMapping(path=MAPPING_B2)
    Object test_B2(final URI uri, final Properties properties, final MetaProperties metaProperties, final MetaInputStream metaInputStream, final Object o1, final Object o2, final Object o3) {
        this.resultB = "B2-" + AbstractTest.createObjectFingerprint(uri, properties, metaProperties, metaInputStream, o1, o2, o3);
        return this.resultB;
    }
    @ApiDavInputMapping(path=MAPPING_B3)
    Object test_B3(final Object o1, final Object o2, final Object o3, final MetaInputStream metaInputStream, final MetaProperties metaProperties, final Properties properties, final URI uri) {
        this.resultB = "B3-" + AbstractTest.createObjectFingerprint(o1, o2, o3, metaInputStream, metaProperties, properties, uri);
        return this.resultB;
    }
    @ApiDavInputMapping(path=MAPPING_B4)
    Object test_B4(final URI uri1, final URI uri2, final Properties properties1, final Properties properties2, final MetaProperties metaProperties1, final MetaProperties metaProperties2,
            final MetaData o1, final MetaProperties o2, MetaInputStream o3, final MetaOutputStream o4, final URI uri3, final Properties properties3) {
        this.resultB = "B4-" + AbstractTest.createObjectFingerprint(uri1, uri2, properties1, properties2, metaProperties1, metaProperties2, o1, o2, o3, o4, uri3, properties3);
        return this.resultB;
    }

    // {@link ApiDavMetaMapping}
    // URI, final Properties, MetaData, Annotation.AnnotationType.Meta (not public)
    // expected data type from return value: void
    // - without
    // - complete +3 more object
    // - complete +3 more object, but sequence reversed
    // - complete all double, the first two repeat at the end

    public static final String MAPPING_C1 = "/extras/signatures/c1.txt";
    public static final String MAPPING_C2 = "/extras/signatures/c2.txt";
    public static final String MAPPING_C3 = "/extras/signatures/c3.txt";
    public static final String MAPPING_C4 = "/extras/signatures/c4.txt";

    private String resultC;

    @ApiDavMapping(path=MAPPING_C1)
    @ApiDavMapping(path=MAPPING_C2)
    @ApiDavMapping(path=MAPPING_C3)
    @ApiDavMapping(path=MAPPING_C4)
    void test_CX(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(this.resultC.getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_C1)
    Object test_C1() {
        this.resultC = "C1";
        return this.resultC;
    }
    @ApiDavMetaMapping(path=MAPPING_C2)
    Object test_C2(final URI uri, final Properties properties, final MetaData metaData, final Object o1, final Object o2, final Object o3) {
        this.resultC = "C2-" + AbstractTest.createObjectFingerprint(uri, properties, metaData, o1, o2, o3);
        return this.resultC;
    }
    @ApiDavMetaMapping(path=MAPPING_C3)
    Object test_C3(final Object o1, final Object o2, final Object o3, final MetaData metaData, final Properties properties, final URI uri) {
        this.resultC = "C3-" + AbstractTest.createObjectFingerprint(o1, o2, o3, metaData, properties, uri);
        return this.resultC;
    }
    @ApiDavMetaMapping(path=MAPPING_C4)
    Object test_C4(final URI uri1, final URI uri2, final Properties properties1, final Properties properties2, final MetaData metaData1, final MetaData metaData2,
            final MetaData o1, final MetaProperties o2, final MetaInputStream o3, final MetaOutputStream o4, final URI uri3, final Properties properties3) {
        this.resultC = "C4-" + AbstractTest.createObjectFingerprint(uri1, uri2, properties1, properties2, metaData1, metaData2, o1, o2, o3, o4, uri3, properties3);
        return this.resultC;
    }

    // {@link ApiDavAttributeMapping}
    // URI, final Properties, ApiDavMappingAttribute
    // expected data type from return value: depending on the attributes - Boolean, Integer, String, Date
    // - without
    // - complete +3 more object
    // - complete +3 more object, but sequence reversed
    // - complete all double, the first two repeat at the end

    public static final String MAPPING_D1 = "/extras/signatures/d1.txt";
    public static final String MAPPING_D2 = "/extras/signatures/d2.txt";
    public static final String MAPPING_D3 = "/extras/signatures/d3.txt";
    public static final String MAPPING_D4 = "/extras/signatures/d4.txt";

    private String resultD;

    @ApiDavMapping(path=MAPPING_D1)
    @ApiDavMapping(path=MAPPING_D2)
    @ApiDavMapping(path=MAPPING_D3)
    @ApiDavMapping(path=MAPPING_D4)
    void test_DX(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(this.resultD.getBytes());
    }
    @ApiDavAttributeMapping(path=MAPPING_D1, attribute=ApiDavMappingAttribute.ContentType)
    Object test_D1() {
        this.resultD = "D1";
        return this.resultD;
    }
    @ApiDavAttributeMapping(path=MAPPING_D2, attribute=ApiDavMappingAttribute.ContentType)
    Object test_D2(final URI uri, final Properties properties, final ApiDavMappingAttribute attribute, final Object o1, final Object o2, final Object o3) {
        this.resultD = "D2-" + AbstractTest.createObjectFingerprint(uri, properties, attribute, o1, o2, o3);
        return this.resultD;
    }
    @ApiDavAttributeMapping(path=MAPPING_D3, attribute=ApiDavMappingAttribute.ContentType)
    Object test_D3(final Object o1, final Object o2, final Object o3, final ApiDavMappingAttribute attribute, final Properties properties, final URI uri) {
        this.resultD = "D3-" + AbstractTest.createObjectFingerprint(o1, o2, o3, attribute, properties, uri);
        return this.resultD;
    }
    @ApiDavAttributeMapping(path=MAPPING_D4, attribute=ApiDavMappingAttribute.ContentType)
    Object test_D4(final URI uri1, final URI uri2, final Properties properties1, final Properties properties2, final ApiDavMappingAttribute attribute1, final ApiDavMappingAttribute attribute2,
            final MetaData o1, final MetaProperties o2, final MetaInputStream o3, final MetaOutputStream o4, final URI uri3, final Properties properties3) {
        this.resultD = "D4-" + AbstractTest.createObjectFingerprint(uri1, uri2, properties1, properties2, attribute1, attribute2, o1, o2, o3, o4, uri3, properties3);
        return this.resultD;
    }
}