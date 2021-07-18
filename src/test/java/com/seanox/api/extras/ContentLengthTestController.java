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
import org.springframework.stereotype.Component;

/**
 * Test of the annotation {@link ApiDavAttributeMapping}
 *     + {@link ApiDavMappingAttribute#ContentLength} functions.
 *
 * ContentLengthTestController 1.0.0 20210711
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210711
 */
@Component
@SuppressWarnings("boxing")
public class ContentLengthTestController {

    // TODO:

    void test_A() {
    }

    void test_B() {
    }

    // Test of {@link ApiDavAttribute}

    public static final String MAPPING_C1 = "/extras/contentLength/c1.txt";
    public static final String MAPPING_C2 = "/extras/contentLength/c2.txt";
    public static final String MAPPING_C3 = "/extras/contentLength/c3.txt";
    public static final String MAPPING_C4 = "/extras/contentLength/c4.txt";
    public static final String MAPPING_C5 = "/extras/contentLength/c5.txt";
    public static final String MAPPING_C6 = "/extras/contentLength/c6.txt";
    public static final String MAPPING_C7 = "/extras/contentLength/c7.txt";
    public static final String MAPPING_C8 = "/extras/contentLength/c8.txt";
    public static final String MAPPING_C9 = "/extras/contentLength/c9.txt";
    public static final String MAPPING_CA = "/extras/contentLength/cA.txt";
    public static final String MAPPING_CB = "/extras/contentLength/cB.txt";
    public static final String MAPPING_CC = "/extras/contentLength/cC.txt";
    public static final String MAPPING_CD = "/extras/contentLength/cD.txt";
    public static final String MAPPING_CE = "/extras/contentLength/cE.txt";
    public static final String MAPPING_CF = "/extras/contentLength/cF.txt";

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
    void test_CX() {
    }
    @ApiDavAttributeMapping(path=MAPPING_C1, attribute=ApiDavMappingAttribute.ContentLength)
    Long test_C1() {
        return 110L;
    }
    @ApiDavAttributeMapping(path=MAPPING_C2, attribute=ApiDavMappingAttribute.ContentLength)
    long test_C2() {
        return 120L;
    }
    @ApiDavAttributeMapping(path=MAPPING_C3, attribute=ApiDavMappingAttribute.ContentLength)
    String test_C3() {
        return "130";
    }
    @ApiDavAttributeMapping(path=MAPPING_C4, attribute=ApiDavMappingAttribute.ContentLength)
    String test_C4() {
        return "-140";
    }
    @ApiDavAttributeMapping(path=MAPPING_C5, attribute=ApiDavMappingAttribute.ContentLength)
    String test_C5() {
        return "wrong text value";
    }
    @ApiDavAttributeMapping(path=MAPPING_C6, attribute=ApiDavMappingAttribute.ContentLength)
    Object test_C6() {
        return 160L;
    }
    @ApiDavAttributeMapping(path=MAPPING_C7, attribute=ApiDavMappingAttribute.ContentLength)
    Object test_C7() {
        return "170";
    }
    @ApiDavAttributeMapping(path=MAPPING_C8, attribute=ApiDavMappingAttribute.ContentLength)
    Long test_C8() {
        return null;
    }
    @ApiDavAttributeMapping(path=MAPPING_C9, attribute=ApiDavMappingAttribute.ContentLength)
    Long test_C9() {
        return 0L;
    }
    @ApiDavAttributeMapping(path=MAPPING_CA, attribute=ApiDavMappingAttribute.ContentLength)
    Long test_CA() {
        return -1L;
    }
    @ApiDavAttributeMapping(path=MAPPING_CB, attribute=ApiDavMappingAttribute.ContentLength)
    Long test_CB() {
        return -100L;
    }
    @ApiDavAttributeMapping(path=MAPPING_CC, attribute=ApiDavMappingAttribute.ContentLength)
    Exception test_CC() {
        return new Exception("Test CC");
    }
    @ApiDavAttributeMapping(path=MAPPING_CD, attribute=ApiDavMappingAttribute.ContentLength)
    Long test_CD() {
        throw new RuntimeException("Test CD");
    }
    @ApiDavAttributeMapping(path=MAPPING_CE, attribute=ApiDavMappingAttribute.ContentLength)
    String test_CE() {
        return "";
    }
    @ApiDavAttributeMapping(path=MAPPING_CF, attribute=ApiDavMappingAttribute.ContentLength)
    String test_CF() {
        return " ";
    }
}