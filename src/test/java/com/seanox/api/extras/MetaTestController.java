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

import com.seanox.webdav.DateTimeAdapter;
import com.seanox.webdav.MetaData;
import com.seanox.webdav.MetaInputStream;
import com.seanox.webdav.MetaOutputStream;
import com.seanox.webdav.MetaProperties;
import com.seanox.webdav.Properties;
import com.seanox.webdav.WebDavMapping;
import com.seanox.webdav.WebDavMappingAttribute;
import com.seanox.webdav.WebDavMappingAttributeExpression;
import com.seanox.webdav.WebDavMetaMapping;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.Objects;

/**
 * Test of the annotation {@link WebDavMetaMapping}.<br>
 * <br>
 * MetaTestController 1.1.0 20210805<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.1.0 20210805
 */
@Profile("test")
@Component
@SuppressWarnings("boxing")
public class MetaTestController {

    // ContentType is set by meta callback.
    // After that, it must be possible to retrieve the value via different ways.

    public static final String MAPPING_A1 = "/extras/meta/a1.txt";
    @WebDavMapping(path=MAPPING_A1)
    void testA1(final MetaOutputStream outputStream) throws IOException {
        outputStream.write((MAPPING_A1 + " " + outputStream.getContentType()).getBytes());
    }
    @WebDavMetaMapping(path=MAPPING_A1)
    void testA1(final MetaData meta) {
        meta.setContentType("m1");
    }

    public static final String MAPPING_A2 = "/extras/meta/a2.txt";
    @WebDavMapping(path=MAPPING_A2)
    void testA2(final MetaData metaData, final MetaOutputStream outputStream) throws IOException {
        outputStream.write((MAPPING_A2 + " " + metaData).getBytes());
    }
    @WebDavMetaMapping(path=MAPPING_A2)
    void testA2(final MetaData meta) {
        meta.setContentType("m2");
    }

    public static final String MAPPING_A3 = "/extras/meta/a3.txt";
    @WebDavMapping(path=MAPPING_A3)
    void testA3(final MetaProperties metaProperties, final MetaOutputStream outputStream) throws IOException {
        outputStream.write((MAPPING_A3 + " " + metaProperties.getContentType()).getBytes());
    }
    @WebDavMetaMapping(path=MAPPING_A3)
    void testA3(final MetaData meta) {
        meta.setContentType("m3");
    }

    // Also null can be used as a value and is then provided like this.
    // The stand value is only used for untouched fields.
    // If the value null is used, the propfind must omit this attribute.

    public static final String MAPPING_A4 = "/extras/meta/a4.txt";
    @WebDavMapping(path=MAPPING_A4)
    void testA4(final MetaOutputStream outputStream) throws IOException {
        outputStream.write((MAPPING_A4 + " " + outputStream.getContentType()).getBytes());
    }
    @WebDavMetaMapping(path=MAPPING_A4)
    void testA4(final MetaData meta) {
        meta.setContentType(null);
    }

    public static final String MAPPING_A5 = "/extras/meta/a5.txt";
    @WebDavMapping(path=MAPPING_A5)
    void testA5(final MetaProperties metaProperties, final MetaOutputStream outputStream) throws IOException {
        outputStream.write((MAPPING_A5 + " " + metaProperties.getContentType()).getBytes());
    }
    @WebDavMetaMapping(path=MAPPING_A5)
    void testA5(final MetaData meta) {
        meta.setContentType(null);
    }

    public static final String MAPPING_A6 = "/extras/meta/a6.txt";
    @WebDavMapping(path=MAPPING_A6)
    void testA6(final MetaProperties metaProperties, final MetaOutputStream outputStream) throws IOException {
        outputStream.write((MAPPING_A6 + " " + metaProperties.getContentType()).getBytes());
    }
    @WebDavMetaMapping(path=MAPPING_A6)
    void testA6() {
    }

    // Following placeholders for methods parameters are supported: Properties, URI, MetaData
    // Order and number are not fixed.

    String testB1Result;
    public static final String MAPPING_B1 = "/extras/meta/b1.txt";
    @WebDavMapping(path=MAPPING_B1)
    void testB1(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(this.testB1Result.getBytes());
    }
    @WebDavMetaMapping(path=MAPPING_B1)
    void testB1(final MetaData meta1, final MetaData meta2) {
        this.testB1Result = String.format("%s %s %s %s",
                meta1.getUri(), Objects.nonNull(meta1), Objects.nonNull(meta2), meta1 == meta2);
    }

    String testB2Result;
    public static final String MAPPING_B2 = "/extras/meta/b2.txt";
    @WebDavMapping(path=MAPPING_B2)
    void testB2(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(this.testB2Result.getBytes());
    }
    @WebDavMetaMapping(path=MAPPING_B2)
    void testB2(final MetaData meta1) {
        this.testB2Result = String.format("%s %s",
                meta1.getUri(), Objects.nonNull(meta1));
    }

    String testB3Result;
    public static final String MAPPING_B3 = "/extras/meta/b3.txt";
    @WebDavMapping(path=MAPPING_B3)
    void testB3(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(this.testB3Result.getBytes());
    }
    @WebDavMetaMapping(path=MAPPING_B3)
    void testB3(final Properties properties, final URI uri, final MetaData meta1) {
        this.testB3Result = String.format("%s %s %s %s",
                meta1.getUri(), Objects.nonNull(properties), Objects.nonNull(uri), Objects.nonNull(meta1));
    }

    String testB4Result;
    public static final String MAPPING_B4 = "/extras/meta/b4.txt";
    @WebDavMapping(path=MAPPING_B4)
    void testB4(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(this.testB4Result.getBytes());
    }
    @WebDavMetaMapping(path=MAPPING_B4)
    void testB4(final URI uri1, final Properties properties1, final MetaData meta1, final URI uri2, final Properties properties2, final String string,
            final MetaProperties metaProperties, final MetaOutputStream outputStream, final MetaInputStream inputStream) {
        this.testB4Result = String.format("%s %s %s %s %s %s %s %s %s %s %s",
                meta1.getUri(),
                Objects.nonNull(uri1), Objects.nonNull(uri2), uri1 == uri2,
                Objects.nonNull(properties1), Objects.nonNull(properties2), properties1 == properties2,
                Objects.isNull(metaProperties), Objects.isNull(string), Objects.isNull(outputStream), Objects.isNull(inputStream));
    }

    String testB5Result;
    public static final String MAPPING_B5 = "/extras/meta/b5.txt";
    @WebDavMapping(path=MAPPING_B5)
    void testB5(final MetaOutputStream outputStream) throws IOException {
        outputStream.write(this.testB5Result.getBytes());
    }
    @WebDavMetaMapping(path=MAPPING_B5)
    void testB5(final Object... objects) {
        this.testB5Result = MAPPING_B5 + " " + Objects.isNull(objects);
    }

    // Function of all attributes is tested.
    // Test by the way of multiple @WebDavMapping annotation.

    public static final String MAPPING_C1 = "/extras/meta/c1.txt";
    public static final String MAPPING_C2 = "/extras/meta/c2.txt";
    public static final String MAPPING_C3 = "/extras/meta/c3.txt";
    public static final String MAPPING_C4 = "/extras/meta/c4.txt";
    public static final String MAPPING_C5 = "/extras/meta/c5.txt";
    public static final String MAPPING_C6 = "/extras/meta/c6.txt";
    public static final String MAPPING_C7 = "/extras/meta/c7.txt";
    public static final String MAPPING_C8 = "/extras/meta/c8.txt";
    public static final String MAPPING_C9 = "/extras/meta/c9.txt";
    public static final String MAPPING_CA = "/extras/meta/cA.txt";
    public static final String MAPPING_CB = "/extras/meta/cB.txt";
    public static final String MAPPING_CC = "/extras/meta/cC.txt";

    @WebDavMapping(path=MAPPING_C1)
    @WebDavMapping(path=MAPPING_C2)
    @WebDavMapping(path=MAPPING_C3)
    @WebDavMapping(path=MAPPING_C4)
    @WebDavMapping(path=MAPPING_C5)
    @WebDavMapping(path=MAPPING_C6)
    @WebDavMapping(path=MAPPING_C7)
    @WebDavMapping(path=MAPPING_C8)
    @WebDavMapping(path=MAPPING_C9)
    @WebDavMapping(path=MAPPING_CA)
    @WebDavMapping(path=MAPPING_CB)
    @WebDavMapping(path=MAPPING_CC)
    void testCX(final MetaProperties metaProperties, final MetaOutputStream outputStream) throws IOException {
        outputStream.write(metaProperties.getUri().toString().getBytes());
    }
    @WebDavMetaMapping(path=MAPPING_C1)
    void testC1(final MetaData metaData) {
        metaData.setContentType(null);
        metaData.setContentLength(null);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(false);
    }
    @WebDavMetaMapping(path=MAPPING_C2)
    void testC2(final MetaData metaData) {
        metaData.setContentType("TesT");
        metaData.setContentLength(null);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @WebDavMetaMapping(path=MAPPING_C3)
    void testC3(final MetaData metaData) {
        metaData.setContentType(null);
        metaData.setContentLength(-100);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @WebDavMetaMapping(path=MAPPING_C4)
    void testC4(final MetaData metaData) {
        metaData.setContentType(null);
        metaData.setContentLength(-1);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @WebDavMetaMapping(path=MAPPING_C5)
    void testC5(final MetaData metaData) {
        metaData.setContentType(null);
        metaData.setContentLength(0);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @WebDavMetaMapping(path=MAPPING_C6)
    void testC6(final MetaData metaData) {
        metaData.setContentType(null);
        metaData.setContentLength(1);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @WebDavMetaMapping(path=MAPPING_C7)
    void testC7(final MetaData metaData) {
        metaData.setContentType(null);
        metaData.setContentLength(100);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @WebDavMetaMapping(path=MAPPING_C8)
    void testC8(final MetaData metaData) throws ParseException {
        metaData.setContentType(null);
        metaData.setContentLength(null);
        metaData.setCreationDate(DateTimeAdapter.parseDate("2000-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT));
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @WebDavMetaMapping(path=MAPPING_C9)
    void testC9(final MetaData metaData) throws ParseException {
        metaData.setContentType(null);
        metaData.setContentLength(null);
        metaData.setCreationDate(null);
        metaData.setLastModified(DateTimeAdapter.parseDate("2000-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT));
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @WebDavMetaMapping(path=MAPPING_CA)
    void testCA(final MetaData metaData) {
        metaData.setContentType(null);
        metaData.setContentLength(null);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(true);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @WebDavMetaMapping(path=MAPPING_CB)
    void testCB(final MetaData metaData) {
        metaData.setContentType(null);
        metaData.setContentLength(null);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(true);
        metaData.setPermitted(true);
    }
    @WebDavMetaMapping(path=MAPPING_CC)
    void testCC(final MetaData metaData) {
        metaData.setContentType(null);
        metaData.setContentLength(null);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }

    // MetaData based on the initial values of attributes + expressions from WebDavMapping.

    public static final String MAPPING_D1 = "/extras/meta/d1.txt";
    public static final String MAPPING_D2 = "/extras/meta/d2.txt";

    @WebDavMapping(path=MAPPING_D1)
    @WebDavMapping(path=MAPPING_D2, readOnly=false, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.Hidden, phrase="true")
    })
    void testDX(final MetaProperties metaProperties, final MetaOutputStream outputStream) throws IOException {
        outputStream.write((metaProperties.getUri() + " " + outputStream.getContentType()).getBytes());
    }
    @WebDavMetaMapping(path=MAPPING_D1)
    @WebDavMetaMapping(path=MAPPING_D2)
    void testDX(final MetaData metaData) {
        metaData.setContentType(
                metaData.isReadOnly()
                + "-" + metaData.isHidden()
        );
    }
}