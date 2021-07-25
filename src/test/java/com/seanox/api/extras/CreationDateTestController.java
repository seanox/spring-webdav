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
import com.seanox.apidav.ApiDavMapping;
import com.seanox.apidav.ApiDavMappingAttribute;
import com.seanox.apidav.ApiDavMappingAttributeExpression;
import com.seanox.apidav.ApiDavMetaMapping;
import com.seanox.apidav.DateTimeAdapter;
import com.seanox.apidav.MetaData;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

/**
 * Test the function of the CreationDate attribute
 * {@link com.seanox.apidav.ApiDavMapping}.<br>
 * <br>
 * CreationDateTestController 1.0.0 20210721<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210721
 */
@Component
public class CreationDateTestController {

    // TODO: Test A for attributes + Variants of values (valid + invalid)
    // TODO: Test B for expressions + Variants of values (valid + invalid)
    // Test C for callbacks + Variants of values (valid + invalid)
    // Test D of priorities, what is used when -- (MetaOutputStream), Callback, Meta, Expression, Static, (Default)

    // Test of {@link ApiDavAttribute}

    public static final String MAPPING_C1 = "/extras/creationDate/c1.txt";
    public static final String MAPPING_C2 = "/extras/creationDate/c2.txt";
    public static final String MAPPING_C3 = "/extras/creationDate/c3.txt";
    public static final String MAPPING_C4 = "/extras/creationDate/c4.txt";
    public static final String MAPPING_C5 = "/extras/creationDate/c5.txt";
    public static final String MAPPING_C6 = "/extras/creationDate/c6.txt";
    public static final String MAPPING_C7 = "/extras/creationDate/c7.txt";
    public static final String MAPPING_C8 = "/extras/creationDate/c8.txt";
    public static final String MAPPING_C9 = "/extras/creationDate/c9.txt";
    public static final String MAPPING_CA = "/extras/creationDate/cA.txt";
    public static final String MAPPING_CB = "/extras/creationDate/cB.txt";
    public static final String MAPPING_CC = "/extras/creationDate/cC.txt";
    public static final String MAPPING_CD = "/extras/creationDate/cD.txt";
    public static final String MAPPING_CE = "/extras/creationDate/cE.txt";
    public static final String MAPPING_CF = "/extras/creationDate/cF.txt";
    public static final String MAPPING_CG = "/extras/creationDate/cG.txt";

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
    @ApiDavAttributeMapping(path=MAPPING_C1, attribute=ApiDavMappingAttribute.CreationDate)
    String test_C1() {
        return null;
    }
    @ApiDavAttributeMapping(path=MAPPING_C2, attribute=ApiDavMappingAttribute.CreationDate)
    String test_C2() {
        return "";
    }
    @ApiDavAttributeMapping(path=MAPPING_C3, attribute=ApiDavMappingAttribute.CreationDate)
    String test_C3() {
        return " ";
    }
    @ApiDavAttributeMapping(path=MAPPING_C4, attribute=ApiDavMappingAttribute.CreationDate)
    String test_C4() {
        return "1987-06-07 01:02:03";
    }
    @ApiDavAttributeMapping(path=MAPPING_C5, attribute=ApiDavMappingAttribute.CreationDate)
    String test_C5() {
        return "2987-06-07 01:02:03";
    }
    @ApiDavAttributeMapping(path=MAPPING_C6, attribute=ApiDavMappingAttribute.CreationDate)
    String test_C6() {
        return "2987-06-07 01:02";
    }
    @ApiDavAttributeMapping(path=MAPPING_C7, attribute=ApiDavMappingAttribute.CreationDate)
    String test_C7() {
        return "2987-06-07";
    }
    @ApiDavAttributeMapping(path=MAPPING_C8, attribute=ApiDavMappingAttribute.CreationDate)
    String test_C8() {
        return "xxx2987-06-07";
    }
    @ApiDavAttributeMapping(path=MAPPING_C9, attribute=ApiDavMappingAttribute.CreationDate)
    Date test_C9() {
        return null;
    }
    @ApiDavAttributeMapping(path=MAPPING_CA, attribute=ApiDavMappingAttribute.CreationDate)
    Date test_CA() throws ParseException {
        return DateTimeAdapter.parseDate("2456-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @ApiDavAttributeMapping(path=MAPPING_CB, attribute=ApiDavMappingAttribute.CreationDate)
    Date test_CB() throws ParseException {
        return DateTimeAdapter.parseDate("1956-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @ApiDavAttributeMapping(path=MAPPING_CC, attribute=ApiDavMappingAttribute.CreationDate)
    Object test_CC() throws ParseException {
        return DateTimeAdapter.parseDate("2456-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @ApiDavAttributeMapping(path=MAPPING_CD, attribute=ApiDavMappingAttribute.CreationDate)
    Object test_CD() throws ParseException {
        return DateTimeAdapter.parseDate("1956-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @ApiDavAttributeMapping(path=MAPPING_CE, attribute=ApiDavMappingAttribute.CreationDate)
    Object test_CE() throws ParseException {
        return DateTimeAdapter.parseDate("1956-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @ApiDavAttributeMapping(path=MAPPING_CF, attribute=ApiDavMappingAttribute.CreationDate)
    Exception test_CF() {
        return new Exception("Test C8");
    }
    @ApiDavAttributeMapping(path=MAPPING_CG, attribute=ApiDavMappingAttribute.CreationDate)
    Date test_CG() {
        throw new RuntimeException("2987-06-07 01:02");
    }

    // Test of priorities:
    // (MetaOutputStream), Callback, Meta, Expression, Static, (Default)

    public static final String MAPPING_D1 = "/extras/creationDate/d1.txt";
    public static final String MAPPING_D2 = "/extras/creationDate/d2.txt";
    public static final String MAPPING_D3 = "/extras/creationDate/d3.txt";
    public static final String MAPPING_D4 = "/extras/creationDate/d4.txt";
    public static final String MAPPING_D5 = "/extras/creationDate/d5.txt";
    public static final String MAPPING_D6 = "/extras/creationDate/d6.txt";

    @ApiDavMapping(path=MAPPING_D1, creationDate="2005-01-01 00:00:00 GMT", attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.CreationDate, phrase="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').parse('2006-01-01 00:00:00 GMT')")
    })
    void test_D1X() throws ParseException {
    }
    @ApiDavMetaMapping(path=MAPPING_D1)
    void test_D1(final MetaData metaData) throws ParseException {
        metaData.setCreationDate(DateTimeAdapter.parseDate("2007-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT));
    }
    @ApiDavAttributeMapping(path=MAPPING_D1, attribute=ApiDavMappingAttribute.CreationDate)
    Date test_D1() throws ParseException {
        return DateTimeAdapter.parseDate("2008-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }

    @ApiDavMapping(path=MAPPING_D2, creationDate="2005-01-01 00:00:00 GMT", attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.CreationDate, phrase="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').parse('2006-01-01 00:00:00 GMT')")
    })
    void test_D2X() {
    }
    @ApiDavMetaMapping(path=MAPPING_D2)
    void test_D2(final MetaData metaData) throws ParseException {
        metaData.setCreationDate(DateTimeAdapter.parseDate("2007-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT));
    }
    @ApiDavAttributeMapping(path=MAPPING_D2, attribute=ApiDavMappingAttribute.CreationDate)
    Date test_D2() throws ParseException {
        return DateTimeAdapter.parseDate("2008-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }

    @ApiDavMapping(path=MAPPING_D3, creationDate="2005-01-01 00:00:00 GMT", attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.CreationDate, phrase="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').parse('2006-01-01 00:00:00 GMT')")
    })
    void test_D3X() {
    }
    @ApiDavMetaMapping(path=MAPPING_D3)
    void test_D3(final MetaData metaData) throws ParseException {
        metaData.setCreationDate(DateTimeAdapter.parseDate("2007-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT));
    }

    @ApiDavMapping(path=MAPPING_D4, creationDate="2005-01-01 00:00:00 GMT", attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.CreationDate, phrase="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').parse('2006-01-01 00:00:00 GMT')")
    })
    void test_D4X() {
    }

    @ApiDavMapping(path=MAPPING_D5, creationDate="2005-01-01 00:00:00 GMT")
    void test_D5X() {
    }

    @ApiDavMapping(path=MAPPING_D6)
    void test_D6X() {
    }
}