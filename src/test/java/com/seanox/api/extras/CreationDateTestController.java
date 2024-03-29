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
import com.seanox.webdav.WebDavMapping;
import com.seanox.webdav.WebDavMappingAttribute;
import com.seanox.webdav.WebDavMappingAttributeExpression;
import com.seanox.webdav.WebDavMetaMapping;
import com.seanox.webdav.DateTimeAdapter;
import com.seanox.webdav.MetaData;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;

/**
 * Test the function of the CreationDate attribute for
 * {@link WebDavMapping}.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210726
 */
@Profile("test")
@Component
public class CreationDateTestController {

    // TODO: Test A for attributes + Variants of values (valid + invalid)
    // TODO: Test B for expressions + Variants of values (valid + invalid)
    // Test C for callbacks + Variants of values (valid + invalid)
    // Test D of priorities, what is used when -- (MetaOutputStream), Callback, Meta, Expression, Static, (Default)

    // Test of {@link WebDavAttribute}

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
    @WebDavAttributeMapping(path=MAPPING_C1, attribute=WebDavMappingAttribute.CreationDate)
    String test_C1() {
        return null;
    }
    @WebDavAttributeMapping(path=MAPPING_C2, attribute=WebDavMappingAttribute.CreationDate)
    String test_C2() {
        return "";
    }
    @WebDavAttributeMapping(path=MAPPING_C3, attribute=WebDavMappingAttribute.CreationDate)
    String test_C3() {
        return " ";
    }
    @WebDavAttributeMapping(path=MAPPING_C4, attribute=WebDavMappingAttribute.CreationDate)
    String test_C4() {
        return "1987-06-07 01:02:03";
    }
    @WebDavAttributeMapping(path=MAPPING_C5, attribute=WebDavMappingAttribute.CreationDate)
    String test_C5() {
        return "2987-06-07 01:02:03";
    }
    @WebDavAttributeMapping(path=MAPPING_C6, attribute=WebDavMappingAttribute.CreationDate)
    String test_C6() {
        return "2987-06-07 01:02";
    }
    @WebDavAttributeMapping(path=MAPPING_C7, attribute=WebDavMappingAttribute.CreationDate)
    String test_C7() {
        return "2987-06-07";
    }
    @WebDavAttributeMapping(path=MAPPING_C8, attribute=WebDavMappingAttribute.CreationDate)
    String test_C8() {
        return "xxx2987-06-07";
    }
    @WebDavAttributeMapping(path=MAPPING_C9, attribute=WebDavMappingAttribute.CreationDate)
    Date test_C9() {
        return null;
    }
    @WebDavAttributeMapping(path=MAPPING_CA, attribute=WebDavMappingAttribute.CreationDate)
    Date test_CA() throws ParseException {
        return DateTimeAdapter.parseDate("2456-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @WebDavAttributeMapping(path=MAPPING_CB, attribute=WebDavMappingAttribute.CreationDate)
    Date test_CB() throws ParseException {
        return DateTimeAdapter.parseDate("1956-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @WebDavAttributeMapping(path=MAPPING_CC, attribute=WebDavMappingAttribute.CreationDate)
    Object test_CC() throws ParseException {
        return DateTimeAdapter.parseDate("2456-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @WebDavAttributeMapping(path=MAPPING_CD, attribute=WebDavMappingAttribute.CreationDate)
    Object test_CD() throws ParseException {
        return DateTimeAdapter.parseDate("1956-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @WebDavAttributeMapping(path=MAPPING_CE, attribute=WebDavMappingAttribute.CreationDate)
    Object test_CE() throws ParseException {
        return DateTimeAdapter.parseDate("1956-01-02 03:04:05 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }
    @WebDavAttributeMapping(path=MAPPING_CF, attribute=WebDavMappingAttribute.CreationDate)
    Exception test_CF() {
        return new Exception("Test C8");
    }
    @WebDavAttributeMapping(path=MAPPING_CG, attribute=WebDavMappingAttribute.CreationDate)
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

    @WebDavMapping(path=MAPPING_D1, creationDate="2005-01-01 00:00:00 GMT", attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.CreationDate, phrase="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').parse('2006-01-01 00:00:00 GMT')")
    })
    void test_D1X() throws ParseException {
    }
    @WebDavMetaMapping(path=MAPPING_D1)
    void test_D1(final MetaData metaData) throws ParseException {
        metaData.setCreationDate(DateTimeAdapter.parseDate("2007-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT));
    }
    @WebDavAttributeMapping(path=MAPPING_D1, attribute=WebDavMappingAttribute.CreationDate)
    Date test_D1() throws ParseException {
        return DateTimeAdapter.parseDate("2008-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }

    @WebDavMapping(path=MAPPING_D2, creationDate="2005-01-01 00:00:00 GMT", attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.CreationDate, phrase="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').parse('2006-01-01 00:00:00 GMT')")
    })
    void test_D2X() {
    }
    @WebDavMetaMapping(path=MAPPING_D2)
    void test_D2(final MetaData metaData) throws ParseException {
        metaData.setCreationDate(DateTimeAdapter.parseDate("2007-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT));
    }
    @WebDavAttributeMapping(path=MAPPING_D2, attribute=WebDavMappingAttribute.CreationDate)
    Date test_D2() throws ParseException {
        return DateTimeAdapter.parseDate("2008-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT);
    }

    @WebDavMapping(path=MAPPING_D3, creationDate="2005-01-01 00:00:00 GMT", attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.CreationDate, phrase="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').parse('2006-01-01 00:00:00 GMT')")
    })
    void test_D3X() {
    }
    @WebDavMetaMapping(path=MAPPING_D3)
    void test_D3(final MetaData metaData) throws ParseException {
        metaData.setCreationDate(DateTimeAdapter.parseDate("2007-01-01 00:00:00 GMT", DateTimeAdapter.DATETIME_FORMAT));
    }

    @WebDavMapping(path=MAPPING_D4, creationDate="2005-01-01 00:00:00 GMT", attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.CreationDate, phrase="new java.text.SimpleDateFormat('yyyy-MM-dd HH:mm:ss Z').parse('2006-01-01 00:00:00 GMT')")
    })
    void test_D4X() {
    }

    @WebDavMapping(path=MAPPING_D5, creationDate="2005-01-01 00:00:00 GMT")
    void test_D5X() {
    }

    @WebDavMapping(path=MAPPING_D6)
    void test_D6X() {
    }
}