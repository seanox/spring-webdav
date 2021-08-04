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
import com.seanox.webdav.MetaOutputStream;
import com.seanox.webdav.WebDavAttributeMapping;
import com.seanox.webdav.WebDavMapping;
import com.seanox.webdav.WebDavMappingAttribute;
import com.seanox.webdav.WebDavMappingAttributeExpression;
import com.seanox.webdav.WebDavMetaMapping;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

/**
 * Test the function of the LastModified attribute for
 * {@link WebDavMapping}.<br>
 * <br>
 * LastModifiedTestController 1.0.0 20210726<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210726
 */
@Profile("test")
@Component
public class LastModifiedTestController {

    // TODO: Test A for attributes + Variants of values (valid + invalid)
    // TODO: Test B for expressions + Variants of values (valid + invalid)
    // Test C for callbacks + Variants of values (valid + invalid)
    // Test D of priorities, what is used when -- (MetaOutputStream), Callback, Meta, Expression, Static, (Default)

    // Test of {@link WebDavAttribute}

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
    @WebDavMapping(path=MAPPING_CD)
    @WebDavMapping(path=MAPPING_CE)
    @WebDavMapping(path=MAPPING_CF)
    @WebDavMapping(path=MAPPING_CG)
    void test_CX() {
    }
    @WebDavAttributeMapping(path=MAPPING_C1, attribute=WebDavMappingAttribute.LastModified)
    String test_C1() {
        return null;
    }
    @WebDavAttributeMapping(path=MAPPING_C2, attribute=WebDavMappingAttribute.LastModified)
    String test_C2() {
        return "";
    }
    @WebDavAttributeMapping(path=MAPPING_C3, attribute=WebDavMappingAttribute.LastModified)
    String test_C3() {
        return " ";
    }
    @WebDavAttributeMapping(path=MAPPING_C4, attribute=WebDavMappingAttribute.LastModified)
    String test_C4() {
        return "1987-06-07 01:02:03";
    }
    @WebDavAttributeMapping(path=MAPPING_C5, attribute=WebDavMappingAttribute.LastModified)
    String test_C5() {
        return "2987-06-07 01:02:03";
    }
    @WebDavAttributeMapping(path=MAPPING_C6, attribute=WebDavMappingAttribute.LastModified)
    String test_C6() {
        return "2987-06-07 01:02";
    }
    @WebDavAttributeMapping(path=MAPPING_C7, attribute=WebDavMappingAttribute.LastModified)
    String test_C7() {
        return "2987-06-07";
    }
    @WebDavAttributeMapping(path=MAPPING_C8, attribute=WebDavMappingAttribute.LastModified)
    String test_C8() {
        return "xxx2987-06-07";
    }
    @WebDavAttributeMapping(path=MAPPING_C9, attribute=WebDavMappingAttribute.LastModified)
    Date test_C9() {
        return null;
    }
    @WebDavAttributeMapping(path=MAPPING_CA, attribute=WebDavMappingAttribute.LastModified)
    Date test_CA() throws ParseException {
        return DateTimeAdapter.parseDate("2456-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @WebDavAttributeMapping(path=MAPPING_CB, attribute=WebDavMappingAttribute.LastModified)
    Date test_CB() throws ParseException {
        return DateTimeAdapter.parseDate("1956-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @WebDavAttributeMapping(path=MAPPING_CC, attribute=WebDavMappingAttribute.LastModified)
    Object test_CC() throws ParseException {
        return new Timestamp(DateTimeAdapter.parseDate("2456-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT).getTime());
    }
    @WebDavAttributeMapping(path=MAPPING_CD, attribute=WebDavMappingAttribute.LastModified)
    Object test_CD() throws ParseException {
        return DateTimeAdapter.parseDate("1956-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @WebDavAttributeMapping(path=MAPPING_CE, attribute=WebDavMappingAttribute.LastModified)
    Object test_CE() throws ParseException {
        return DateTimeAdapter.parseDate("1956-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @WebDavAttributeMapping(path=MAPPING_CF, attribute=WebDavMappingAttribute.LastModified)
    Exception test_CF() {
        return new Exception("Test C8");
    }
    @WebDavAttributeMapping(path=MAPPING_CG, attribute=WebDavMappingAttribute.LastModified)
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

    @WebDavMapping(path=MAPPING_D1, lastModified="2005-01-01 00:00:00 GMT", attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').parse('2006-01-01 00:00:00 GMT')")
    })
    void test_D1X(final MetaOutputStream outputStream) throws ParseException {
        outputStream.setLastModified(DateTimeAdapter.parseDate("2009-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT));
    }
    @WebDavMetaMapping(path=MAPPING_D1)
    void test_D1(final MetaData metaData) throws ParseException {
        metaData.setLastModified(DateTimeAdapter.parseDate("2007-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT));
    }
    @WebDavAttributeMapping(path=MAPPING_D1, attribute=WebDavMappingAttribute.LastModified)
    Date test_D1() throws ParseException {
        return DateTimeAdapter.parseDate("2008-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }

    @WebDavMapping(path=MAPPING_D2, lastModified="2005-01-01 00:00:00 GMT", attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').parse('2006-01-01 00:00:00 GMT')")
    })
    void test_D2X() {
    }
    @WebDavMetaMapping(path=MAPPING_D2)
    void test_D2(final MetaData metaData) throws ParseException {
        metaData.setLastModified(DateTimeAdapter.parseDate("2007-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT));
    }
    @WebDavAttributeMapping(path=MAPPING_D2, attribute=WebDavMappingAttribute.LastModified)
    Date test_D2() throws ParseException {
        return DateTimeAdapter.parseDate("2008-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }

    @WebDavMapping(path=MAPPING_D3, lastModified="2005-01-01 00:00:00 GMT", attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').parse('2006-01-01 00:00:00 GMT')")
    })
    void test_D3X() {
    }
    @WebDavMetaMapping(path=MAPPING_D3)
    void test_D3(final MetaData metaData) throws ParseException {
        metaData.setLastModified(DateTimeAdapter.parseDate("2007-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT));
    }

    @WebDavMapping(path=MAPPING_D4, lastModified="2005-01-01 00:00:00 GMT", attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').parse('2006-01-01 00:00:00 GMT')")
    })
    void test_D4X() {
    }

    @WebDavMapping(path=MAPPING_D5, lastModified="2005-01-01 00:00:00 GMT")
    void test_D5X() {
    }

    @WebDavMapping(path=MAPPING_D6)
    void test_D6X() {
    }
}