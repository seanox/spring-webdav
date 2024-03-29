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
package com.seanox.api.extras;

import com.seanox.webdav.WebDavAttributeMapping;
import com.seanox.webdav.WebDavInputMapping;
import com.seanox.webdav.WebDavMapping;
import com.seanox.webdav.WebDavMappingAttribute;
import com.seanox.webdav.WebDavMetaMapping;
import com.seanox.webdav.MetaData;
import com.seanox.webdav.MetaInputStream;
import com.seanox.webdav.MetaOutputStream;
import com.seanox.webdav.MetaProperties;
import com.seanox.webdav.Properties;
import com.seanox.test.AbstractTest;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;

/**
 * Test the function of the method signatures.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210726
 */
@Profile("test")
@Component
public class SignaturesTestController {

    // Methods do not have a fixed signature, the data type of the parameters
    // is used as a placeholder and filled when called.

    // {@link WebDavMapping}
    // URI, Properties, MetaProperties, MetaOutputStream, Annotation.AnnotationType.Mapping
    // expected data type from return value: void
    // - without
    // - complete +3 more object
    // - complete +3 more object, but sequence reversed
    // - complete all double, the first two repeat at the end

    public static final String MAPPING_A1 = "/extras/signatures/a1.txt";
    public static final String MAPPING_A2 = "/extras/signatures/a2.txt";
    public static final String MAPPING_A3 = "/extras/signatures/a3.txt";
    public static final String MAPPING_A4 = "/extras/signatures/a4.txt";

    @WebDavMapping(path=MAPPING_A1)
    void test_A1() {
    }

    @WebDavMapping(path=MAPPING_A2)
    void test_A2(final URI uri, final Properties properties, final MetaProperties metaProperties, final MetaOutputStream outputStream, final Object o1, final Object o2, final Object o3) throws IOException {
        final String result = "A2-" + AbstractTest.createObjectFingerprint(uri, properties, metaProperties, outputStream, o1, o2, o3);
        outputStream.write(result.getBytes());
    }

    @WebDavMapping(path=MAPPING_A3)
    void test_A3(final Object o1, final Object o2, final Object o3, final MetaOutputStream outputStream, final MetaProperties metaProperties, final Properties properties, final URI uri) throws IOException {
        final String result = "A3-" + AbstractTest.createObjectFingerprint(o1, o2, o3, outputStream, metaProperties, properties, uri);
        outputStream.write(result.getBytes());
    }

    @WebDavMapping(path=MAPPING_A4)
    void test_A4(final URI uri1, final URI uri2, final Properties properties1, final Properties properties2, final MetaProperties metaProperties1, final MetaProperties metaProperties2,
            final MetaData o1, final MetaProperties o2, MetaInputStream o3, final MetaOutputStream o4, final URI uri3, final Properties properties3) throws IOException {
        final String result = "A4-" + AbstractTest.createObjectFingerprint(uri1, uri2, properties1, properties2, metaProperties1, metaProperties2, o1, o2, o3, o4, uri3, properties3);
        o4.write(result.getBytes());
    }

    // {@link WebDavInputMapping}
    // URI, Properties, MetaProperties, MetaInputStream, Annotation.AnnotationType.Input (not public)
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

    @WebDavMapping(path=MAPPING_B1, readOnly=false)
    @WebDavMapping(path=MAPPING_B2, readOnly=false)
    @WebDavMapping(path=MAPPING_B3, readOnly=false)
    @WebDavMapping(path=MAPPING_B4, readOnly=false)
    void test_BX(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(this.resultB.getBytes());
    }
    @WebDavInputMapping(path=MAPPING_B1)
    Object test_B1() {
        this.resultB = "B1";
        return this.resultB;
    }
    @WebDavInputMapping(path=MAPPING_B2)
    Object test_B2(final URI uri, final Properties properties, final MetaProperties metaProperties, final MetaInputStream metaInputStream, final Object o1, final Object o2, final Object o3) {
        this.resultB = "B2-" + AbstractTest.createObjectFingerprint(uri, properties, metaProperties, metaInputStream, o1, o2, o3);
        return this.resultB;
    }
    @WebDavInputMapping(path=MAPPING_B3)
    Object test_B3(final Object o1, final Object o2, final Object o3, final MetaInputStream metaInputStream, final MetaProperties metaProperties, final Properties properties, final URI uri) {
        this.resultB = "B3-" + AbstractTest.createObjectFingerprint(o1, o2, o3, metaInputStream, metaProperties, properties, uri);
        return this.resultB;
    }
    @WebDavInputMapping(path=MAPPING_B4)
    Object test_B4(final URI uri1, final URI uri2, final Properties properties1, final Properties properties2, final MetaProperties metaProperties1, final MetaProperties metaProperties2,
            final MetaData o1, final MetaProperties o2, MetaInputStream o3, final MetaOutputStream o4, final URI uri3, final Properties properties3) {
        this.resultB = "B4-" + AbstractTest.createObjectFingerprint(uri1, uri2, properties1, properties2, metaProperties1, metaProperties2, o1, o2, o3, o4, uri3, properties3);
        return this.resultB;
    }

    // {@link WebDavMetaMapping}
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

    @WebDavMapping(path=MAPPING_C1)
    @WebDavMapping(path=MAPPING_C2)
    @WebDavMapping(path=MAPPING_C3)
    @WebDavMapping(path=MAPPING_C4)
    void test_CX(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(this.resultC.getBytes());
    }
    @WebDavMetaMapping(path=MAPPING_C1)
    Object test_C1() {
        this.resultC = "C1";
        return this.resultC;
    }
    @WebDavMetaMapping(path=MAPPING_C2)
    Object test_C2(final URI uri, final Properties properties, final MetaData metaData, final Object o1, final Object o2, final Object o3) {
        this.resultC = "C2-" + AbstractTest.createObjectFingerprint(uri, properties, metaData, o1, o2, o3);
        return this.resultC;
    }
    @WebDavMetaMapping(path=MAPPING_C3)
    Object test_C3(final Object o1, final Object o2, final Object o3, final MetaData metaData, final Properties properties, final URI uri) {
        this.resultC = "C3-" + AbstractTest.createObjectFingerprint(o1, o2, o3, metaData, properties, uri);
        return this.resultC;
    }
    @WebDavMetaMapping(path=MAPPING_C4)
    Object test_C4(final URI uri1, final URI uri2, final Properties properties1, final Properties properties2, final MetaData metaData1, final MetaData metaData2,
            final MetaData o1, final MetaProperties o2, final MetaInputStream o3, final MetaOutputStream o4, final URI uri3, final Properties properties3) {
        this.resultC = "C4-" + AbstractTest.createObjectFingerprint(uri1, uri2, properties1, properties2, metaData1, metaData2, o1, o2, o3, o4, uri3, properties3);
        return this.resultC;
    }

    // {@link WebDavAttributeMapping}
    // URI, Properties, WebDavMappingAttribute
    // expected data type from return value: depending on the attributes - Boolean, Integer, String, Date
    // - without
    // - complete +3 more object
    // - complete +3 more object, but sequence reversed
    // - complete all double, the first two repeat at the end

    public static final String MAPPING_D1 = "/extras/signatures/d1.txt";
    public static final String MAPPING_D2 = "/extras/signatures/d2.txt";
    public static final String MAPPING_D3 = "/extras/signatures/d3.txt";
    public static final String MAPPING_D4 = "/extras/signatures/d4.txt";
    public static final String MAPPING_D5 = "/extras/signatures/d5.txt";

    private String resultD;

    @WebDavMapping(path=MAPPING_D1)
    @WebDavMapping(path=MAPPING_D2)
    @WebDavMapping(path=MAPPING_D3)
    @WebDavMapping(path=MAPPING_D4)
    @WebDavMapping(path=MAPPING_D5)
    void test_DX(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(this.resultD.getBytes());
    }
    @WebDavAttributeMapping(path=MAPPING_D1, attribute=WebDavMappingAttribute.ContentType)
    Object test_D1() {
        this.resultD = "D1";
        return this.resultD;
    }
    @WebDavAttributeMapping(path=MAPPING_D2, attribute=WebDavMappingAttribute.ContentType)
    Object test_D2(final URI uri, final Properties properties, final WebDavMappingAttribute attribute, final Object o1, final Object o2, final Object o3) {
        this.resultD = "D2-" + AbstractTest.createObjectFingerprint(uri, properties, attribute, o1, o2, o3);
        return this.resultD;
    }
    @WebDavAttributeMapping(path=MAPPING_D3, attribute=WebDavMappingAttribute.ContentType)
    Object test_D3(final Object o1, final Object o2, final Object o3, final WebDavMappingAttribute attribute, final Properties properties, final URI uri) {
        this.resultD = "D3-" + AbstractTest.createObjectFingerprint(o1, o2, o3, attribute, properties, uri);
        return this.resultD;
    }
    @WebDavAttributeMapping(path=MAPPING_D4, attribute=WebDavMappingAttribute.ContentType)
    Object test_D4(final URI uri1, final URI uri2, final Properties properties1, final Properties properties2, final WebDavMappingAttribute attribute1, final WebDavMappingAttribute attribute2,
                   final MetaData o1, final MetaProperties o2, final MetaInputStream o3, final MetaOutputStream o4, final URI uri3, final Properties properties3) {
        this.resultD = "D4-" + AbstractTest.createObjectFingerprint(uri1, uri2, properties1, properties2, attribute1, attribute2, o1, o2, o3, o4, uri3, properties3);
        return this.resultD;
    }

    @WebDavAttributeMapping(path=MAPPING_D5, attribute=WebDavMappingAttribute.ContentType)
    Object test_D4(final URI uri,
                   final ApplicationContext applicationContext,
                   final ServletContext servletContext, final ServletRequest servletRequest, final ServletResponse servletResponse,
                   final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse, final HttpSession httpSession) {
        this.resultD = "D5-" + AbstractTest.createObjectFingerprint(uri,
                applicationContext,
                servletContext, servletRequest, servletResponse,
                httpServletRequest, httpServletResponse, httpSession);
        return this.resultD;
    }
}