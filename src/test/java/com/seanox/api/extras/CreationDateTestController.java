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
import com.seanox.apidav.ApiDavMetaMapping;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Test of the annotation {@link ApiDavAttributeMapping}
 *     + {@link ApiDavMappingAttribute#CreationDate} functions.
 *
 * CreationDateTestController 1.0.0 20210711
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210711
 */
@Profile("test")
@Component
public class CreationDateTestController {

    // TODO:

    void test_A() {
    }

    void test_B() {
    }

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
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss Z").parse("2456-01-02 03:04:05 GMT");
    }
    @ApiDavAttributeMapping(path=MAPPING_CB, attribute=ApiDavMappingAttribute.CreationDate)
    Date test_CB() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss Z").parse("1956-01-02 03:04:05 GMT");
    }
    @ApiDavAttributeMapping(path=MAPPING_CC, attribute=ApiDavMappingAttribute.CreationDate)
    Object test_CC() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss Z").parse("2456-01-02 03:04:05 GMT");
    }
    @ApiDavAttributeMapping(path=MAPPING_CD, attribute=ApiDavMappingAttribute.CreationDate)
    Object test_CD() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss Z").parse("1956-01-02 03:04:05 GMT");
    }
    @ApiDavAttributeMapping(path=MAPPING_CE, attribute=ApiDavMappingAttribute.CreationDate)
    Object test_CE() throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss Z").parse("1956-01-02 03:04:05 GMT");
    }
    @ApiDavAttributeMapping(path=MAPPING_CF, attribute=ApiDavMappingAttribute.CreationDate)
    Exception test_CF() {
        return new Exception("Test C8");
    }
    @ApiDavAttributeMapping(path=MAPPING_CG, attribute=ApiDavMappingAttribute.CreationDate)
    Date test_CG() {
        throw new RuntimeException("2987-06-07 01:02");
    }
}