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
import com.seanox.apidav.MetaInputStream;
import com.seanox.apidav.MetaOutputStream;
import com.seanox.apidav.MetaProperties;
import com.seanox.apidav.Properties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * Test of the annotation {@link ApiDavMetaMapping} functions.
 *
 * MetaTestController 1.0.0 20210711
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210711
 */
@Profile("test")
@Component
public class MetaTestController {

    // ContentType is set by meta callback.
    // After that, it must be possible to retrieve the value via different ways.

    public static final String MAPPING_A1 = "/extras/meta/a1.txt";
    @ApiDavMapping(path=MAPPING_A1)
    void testA1(MetaOutputStream outputStream) throws IOException {
        outputStream.write((MAPPING_A1 + " " + outputStream.getContentType()).getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_A1)
    void testA1(MetaData meta) {
        meta.setContentType("m1");
    }

    public static final String MAPPING_A2 = "/extras/meta/a2.txt";
    @ApiDavMapping(path=MAPPING_A2)
    void testA2(MetaData metaData, MetaOutputStream outputStream) throws IOException {
        outputStream.write((MAPPING_A2 + " " + metaData).getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_A2)
    void testA2(MetaData meta) {
        meta.setContentType("m2");
    }

    public static final String MAPPING_A3 = "/extras/meta/a3.txt";
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

    public static final String MAPPING_A4 = "/extras/meta/a4.txt";
    @ApiDavMapping(path=MAPPING_A4)
    void testA4(MetaProperties metaProperties, MetaOutputStream outputStream) throws IOException {
        outputStream.write((MAPPING_A4 + " " + outputStream.getContentType()).getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_A4)
    void testA4(MetaData meta) {
        meta.setContentType(null);
    }

    public static final String MAPPING_A5 = "/extras/meta/a5.txt";
    @ApiDavMapping(path=MAPPING_A5)
    void testA5(MetaProperties metaProperties, MetaOutputStream outputStream) throws IOException {
        outputStream.write((MAPPING_A5 + " " + metaProperties.getContentType()).getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_A5)
    void testA5(MetaData meta) {
        meta.setContentType(null);
    }

    public static final String MAPPING_A6 = "/extras/meta/a6.txt";
    @ApiDavMapping(path=MAPPING_A6)
    void testA6(MetaProperties metaProperties, MetaOutputStream outputStream) throws IOException {
        outputStream.write((MAPPING_A6 + " " + metaProperties.getContentType()).getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_A6)
    void testA6(MetaData meta) {
    }

    // Following placeholders for methods parameters are supported: Properties, URI, MetaData
    // Order and number are not fixed.

    String testB1Result;
    public static final String MAPPING_B1 = "/extras/meta/b1.txt";
    @ApiDavMapping(path=MAPPING_B1)
    void testB1(MetaProperties metaProperties, MetaOutputStream outputStream) throws IOException {
        outputStream.write(testB1Result.getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_B1)
    void testB1(MetaData meta1, MetaData meta2) {
        testB1Result = String.format("%s %s %s %s",
                meta1.getUri(), Objects.nonNull(meta1), Objects.nonNull(meta2), meta1 == meta2);
    }

    String testB2Result;
    public static final String MAPPING_B2 = "/extras/meta/b2.txt";
    @ApiDavMapping(path=MAPPING_B2)
    void testB2(MetaProperties metaProperties, MetaOutputStream outputStream) throws IOException {
        outputStream.write(testB2Result.getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_B2)
    void testB2(URI uri, MetaData meta1) {
        testB2Result = String.format("%s %s",
                meta1.getUri(), Objects.nonNull(meta1));
    }

    String testB3Result;
    public static final String MAPPING_B3 = "/extras/meta/b3.txt";
    @ApiDavMapping(path=MAPPING_B3)
    void testB3(MetaProperties metaProperties, MetaOutputStream outputStream) throws IOException {
        outputStream.write(testB3Result.getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_B3)
    void testB3(Properties properties, URI uri, MetaData meta1) {
        testB3Result = String.format("%s %s %s %s",
                meta1.getUri(), Objects.nonNull(properties), Objects.nonNull(uri), Objects.nonNull(meta1));
    }

    String testB4Result;
    public static final String MAPPING_B4 = "/extras/meta/b4.txt";
    @ApiDavMapping(path=MAPPING_B4)
    void testB4(MetaProperties metaProperties, MetaOutputStream outputStream) throws IOException {
        outputStream.write(testB4Result.getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_B4)
    void testB4(URI uri1, Properties properties1, MetaData meta1, URI uri2, Properties properties2, String string,
                MetaProperties metaProperties, MetaOutputStream outputStream, MetaInputStream inputStream) {
        testB4Result = String.format("%s %s %s %s %s %s %s %s %s %s %s",
                meta1.getUri(),
                Objects.nonNull(uri1), Objects.nonNull(uri2), uri1 == uri2,
                Objects.nonNull(properties1), Objects.nonNull(properties2), properties1 == properties2,
                Objects.isNull(metaProperties), Objects.isNull(string), Objects.isNull(outputStream), Objects.isNull(inputStream));
    }

    String testB5Result;
    public static final String MAPPING_B5 = "/extras/meta/b5.txt";
    @ApiDavMapping(path=MAPPING_B5)
    void testB5(MetaProperties metaProperties, MetaOutputStream outputStream) throws IOException {
        outputStream.write(testB5Result.getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_B5)
    void testB5(Object... objects) {
        testB5Result = MAPPING_B5 + " " + Objects.isNull(objects);
    }

    // Function of all attributes is tested.
    // Test by the way of multiple @ApiDavMapping annotation.

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
    @ApiDavMapping(path=MAPPING_C1)
    @ApiDavMapping(path=MAPPING_C2)
    @ApiDavMapping(path=MAPPING_C3)
    @ApiDavMapping(path=MAPPING_C4)
    @ApiDavMapping(path=MAPPING_C5)
    @ApiDavMapping(path=MAPPING_C6)
    @ApiDavMapping(path=MAPPING_C7)
    @ApiDavMapping(path=MAPPING_C8)
    @ApiDavMapping(path=MAPPING_C9)
    @ApiDavMapping(path=MAPPING_CA)
    @ApiDavMapping(path=MAPPING_CB)
    @ApiDavMapping(path=MAPPING_CC)
    void testCX(MetaProperties metaProperties, MetaOutputStream outputStream) throws IOException {
        outputStream.write(metaProperties.getUri().toString().getBytes());
    }
    @ApiDavMetaMapping(path=MAPPING_C1)
    void testC1(MetaData metaData) {
        metaData.setContentType(null);
        metaData.setContentLength(null);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(false);
    }
    @ApiDavMetaMapping(path=MAPPING_C2)
    void testC2(MetaData metaData) {
        metaData.setContentType("TesT");
        metaData.setContentLength(null);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @ApiDavMetaMapping(path=MAPPING_C3)
    void testC3(MetaData metaData) {
        metaData.setContentType(null);
        metaData.setContentLength(-100l);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @ApiDavMetaMapping(path=MAPPING_C4)
    void testC4(MetaData metaData) {
        metaData.setContentType(null);
        metaData.setContentLength(-1l);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @ApiDavMetaMapping(path=MAPPING_C5)
    void testC5(MetaData metaData) {
        metaData.setContentType(null);
        metaData.setContentLength(0l);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @ApiDavMetaMapping(path=MAPPING_C6)
    void testC6(MetaData metaData) {
        metaData.setContentType(null);
        metaData.setContentLength(1l);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @ApiDavMetaMapping(path=MAPPING_C7)
    void testC7(MetaData metaData) {
        metaData.setContentType(null);
        metaData.setContentLength(100l);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @ApiDavMetaMapping(path=MAPPING_C8)
    void testC8(MetaData metaData) throws ParseException {
        metaData.setContentType(null);
        metaData.setContentLength(null);
        metaData.setCreationDate(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss Z").parse("2000-01-01 00:00:00 GMT"));
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @ApiDavMetaMapping(path=MAPPING_C9)
    void testC9(MetaData metaData) throws ParseException {
        metaData.setContentType(null);
        metaData.setContentLength(null);
        metaData.setCreationDate(null);
        metaData.setLastModified(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss Z").parse("2000-01-01 00:00:00 GMT"));
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @ApiDavMetaMapping(path=MAPPING_CA)
    void testCA(MetaData metaData) throws ParseException {
        metaData.setContentType(null);
        metaData.setContentLength(null);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(true);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
    @ApiDavMetaMapping(path=MAPPING_CB)
    void testCB(MetaData metaData) throws ParseException {
        metaData.setContentType(null);
        metaData.setContentLength(null);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(true);
        metaData.setPermitted(true);
    }
    @ApiDavMetaMapping(path=MAPPING_CC)
    void testCC(MetaData metaData) throws ParseException {
        metaData.setContentType(null);
        metaData.setContentLength(null);
        metaData.setCreationDate(null);
        metaData.setLastModified(null);
        metaData.setReadOnly(false);
        metaData.setHidden(false);
        metaData.setPermitted(true);
    }
}