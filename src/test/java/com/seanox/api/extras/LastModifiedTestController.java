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

import com.seanox.apidav.ApiDavAttributeMapping;
import com.seanox.apidav.ApiDavMapping;
import com.seanox.apidav.ApiDavMappingAttribute;
import com.seanox.apidav.ApiDavMappingAttributeExpression;
import com.seanox.apidav.ApiDavMetaMapping;
import com.seanox.apidav.DateTimeAdapter;
import com.seanox.apidav.MetaData;
import com.seanox.apidav.MetaOutputStream;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

/**
 * Test of the annotation {@link ApiDavAttributeMapping}
 *     + {@link ApiDavMappingAttribute#LastModified} functions.
 *
 * LastModifiedTestController 1.0.0 20210721
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210721
 */
@Component
public class LastModifiedTestController {

    // TODO:

    void test_A() {
    }

    void test_B() {
    }

    // Test of {@link ApiDavAttribute}

    public static final String MAPPING_C1 = "/extras/lastModified/c1.txt";
    public static final String MAPPING_C2 = "/extras/lastModified/c2.txt";
    public static final String MAPPING_C3 = "/extras/lastModified/c3.txt";
    public static final String MAPPING_C4 = "/extras/lastModified/c4.txt";
    public static final String MAPPING_C5 = "/extras/lastModified/c5.txt";
    public static final String MAPPING_C6 = "/extras/lastModified/c6.txt";
    public static final String MAPPING_C7 = "/extras/lastModified/c7.txt";
    public static final String MAPPING_C8 = "/extras/lastModified/c8.txt";
    public static final String MAPPING_C9 = "/extras/lastModified/c9.txt";
    public static final String MAPPING_CA = "/extras/lastModified/cA.txt";
    public static final String MAPPING_CB = "/extras/lastModified/cB.txt";
    public static final String MAPPING_CC = "/extras/lastModified/cC.txt";
    public static final String MAPPING_CD = "/extras/lastModified/cD.txt";
    public static final String MAPPING_CE = "/extras/lastModified/cE.txt";
    public static final String MAPPING_CF = "/extras/lastModified/cF.txt";
    public static final String MAPPING_CG = "/extras/lastModified/cG.txt";

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
    @ApiDavMapping(path=MAPPING_CD)
    @ApiDavMapping(path=MAPPING_CE)
    @ApiDavMapping(path=MAPPING_CF)
    @ApiDavMapping(path=MAPPING_CG)
    void test_CX() {
    }
    @ApiDavAttributeMapping(path=MAPPING_C1, attribute=ApiDavMappingAttribute.LastModified)
    String test_C1() {
        return null;
    }
    @ApiDavAttributeMapping(path=MAPPING_C2, attribute=ApiDavMappingAttribute.LastModified)
    String test_C2() {
        return "";
    }
    @ApiDavAttributeMapping(path=MAPPING_C3, attribute=ApiDavMappingAttribute.LastModified)
    String test_C3() {
        return " ";
    }
    @ApiDavAttributeMapping(path=MAPPING_C4, attribute=ApiDavMappingAttribute.LastModified)
    String test_C4() {
        return "1987-06-07 01:02:03";
    }
    @ApiDavAttributeMapping(path=MAPPING_C5, attribute=ApiDavMappingAttribute.LastModified)
    String test_C5() {
        return "2987-06-07 01:02:03";
    }
    @ApiDavAttributeMapping(path=MAPPING_C6, attribute=ApiDavMappingAttribute.LastModified)
    String test_C6() {
        return "2987-06-07 01:02";
    }
    @ApiDavAttributeMapping(path=MAPPING_C7, attribute=ApiDavMappingAttribute.LastModified)
    String test_C7() {
        return "2987-06-07";
    }
    @ApiDavAttributeMapping(path=MAPPING_C8, attribute=ApiDavMappingAttribute.LastModified)
    String test_C8() {
        return "xxx2987-06-07";
    }
    @ApiDavAttributeMapping(path=MAPPING_C9, attribute=ApiDavMappingAttribute.LastModified)
    Date test_C9() {
        return null;
    }
    @ApiDavAttributeMapping(path=MAPPING_CA, attribute=ApiDavMappingAttribute.LastModified)
    Date test_CA() throws ParseException {
        return DateTimeAdapter.parseDate("2456-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @ApiDavAttributeMapping(path=MAPPING_CB, attribute=ApiDavMappingAttribute.LastModified)
    Date test_CB() throws ParseException {
        return DateTimeAdapter.parseDate("1956-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @ApiDavAttributeMapping(path=MAPPING_CC, attribute=ApiDavMappingAttribute.LastModified)
    Object test_CC() throws ParseException {
        return DateTimeAdapter.parseDate("2456-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @ApiDavAttributeMapping(path=MAPPING_CD, attribute=ApiDavMappingAttribute.LastModified)
    Object test_CD() throws ParseException {
        return DateTimeAdapter.parseDate("1956-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @ApiDavAttributeMapping(path=MAPPING_CE, attribute=ApiDavMappingAttribute.LastModified)
    Object test_CE() throws ParseException {
        return DateTimeAdapter.parseDate("1956-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @ApiDavAttributeMapping(path=MAPPING_CF, attribute=ApiDavMappingAttribute.LastModified)
    Exception test_CF() {
        return new Exception("Test C8");
    }
    @ApiDavAttributeMapping(path=MAPPING_CG, attribute=ApiDavMappingAttribute.LastModified)
    Date test_CG() {
        throw new RuntimeException("2987-06-07 01:02");
    }

    // Test of priorities:
    // (MetaOutputStream), Callback, Meta, Expression, Static, (Default)

    public static final String MAPPING_D1 = "/extras/lastModified/d1.txt";
    public static final String MAPPING_D2 = "/extras/lastModified/d2.txt";
    public static final String MAPPING_D3 = "/extras/lastModified/d3.txt";
    public static final String MAPPING_D4 = "/extras/lastModified/d4.txt";
    public static final String MAPPING_D5 = "/extras/lastModified/d5.txt";
    public static final String MAPPING_D6 = "/extras/lastModified/d6.txt";

    @ApiDavMapping(path=MAPPING_D1, lastModified="2005-01-01 00:00:00 GMT", attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').parse('2006-01-01 00:00:00 GMT')")
    })
    void test_D1X(final MetaOutputStream outputStream) throws ParseException {
        outputStream.setLastModified(DateTimeAdapter.parseDate("2009-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT));
    }
    @ApiDavMetaMapping(path=MAPPING_D1)
    void test_D1(final MetaData metaData) throws ParseException {
        metaData.setLastModified(DateTimeAdapter.parseDate("2007-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT));
    }
    @ApiDavAttributeMapping(path=MAPPING_D1, attribute=ApiDavMappingAttribute.LastModified)
    Date test_D1() throws ParseException {
        return DateTimeAdapter.parseDate("2008-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }

    @ApiDavMapping(path=MAPPING_D2, lastModified="2005-01-01 00:00:00 GMT", attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').parse('2006-01-01 00:00:00 GMT')")
    })
    void test_D2X() {
    }
    @ApiDavMetaMapping(path=MAPPING_D2)
    void test_D2(final MetaData metaData) throws ParseException {
        metaData.setLastModified(DateTimeAdapter.parseDate("2007-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT));
    }
    @ApiDavAttributeMapping(path=MAPPING_D2, attribute=ApiDavMappingAttribute.LastModified)
    Date test_D2() throws ParseException {
        return DateTimeAdapter.parseDate("2008-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }

    @ApiDavMapping(path=MAPPING_D3, lastModified="2005-01-01 00:00:00 GMT", attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').parse('2006-01-01 00:00:00 GMT')")
    })
    void test_D3X() {
    }
    @ApiDavMetaMapping(path=MAPPING_D3)
    void test_D3(final MetaData metaData) throws ParseException {
        metaData.setLastModified(DateTimeAdapter.parseDate("2007-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT));
    }

    @ApiDavMapping(path=MAPPING_D4, lastModified="2005-01-01 00:00:00 GMT", attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.LastModified, phrase="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').parse('2006-01-01 00:00:00 GMT')")
    })
    void test_D4X() {
    }

    @ApiDavMapping(path=MAPPING_D5, lastModified="2005-01-01 00:00:00 GMT")
    void test_D5X() {
    }

    @ApiDavMapping(path=MAPPING_D6)
    void test_D6X() {
    }
}