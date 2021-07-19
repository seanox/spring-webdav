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
 *     + {@link ApiDavMappingAttribute#ContentType} functions.
 *
 * ContentTypeTestController 1.0.0 20210719
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210719
 */
@Component
public class ContentTypeTestController {

    // TODO:

    void test_A() {
    }

    void test_B() {
    }

    // Test of {@link ApiDavAttribute}

    public static final String MAPPING_C1 = "/extras/contentType/c1.txt";
    public static final String MAPPING_C2 = "/extras/contentType/c2.txt";
    public static final String MAPPING_C3 = "/extras/contentType/c3.txt";
    public static final String MAPPING_C4 = "/extras/contentType/c4.txt";
    public static final String MAPPING_C5 = "/extras/contentType/c5.txt";
    public static final String MAPPING_C6 = "/extras/contentType/c6.txt";
    public static final String MAPPING_C7 = "/extras/contentType/c7.txt";
    public static final String MAPPING_C8 = "/extras/contentType/c8.txt";
    public static final String MAPPING_C9 = "/extras/contentType/c9.txt";

    @ApiDavMapping(path=MAPPING_C1)
    @ApiDavMapping(path=MAPPING_C2)
    @ApiDavMapping(path=MAPPING_C3)
    @ApiDavMapping(path=MAPPING_C4)
    @ApiDavMapping(path=MAPPING_C5)
    @ApiDavMapping(path=MAPPING_C6)
    @ApiDavMapping(path=MAPPING_C7)
    @ApiDavMapping(path=MAPPING_C8)
    @ApiDavMapping(path=MAPPING_C9)
    void test_CX() {
    }
    @ApiDavAttributeMapping(path=MAPPING_C1, attribute=ApiDavMappingAttribute.ContentType)
    String test_C1() {
        return null;
    }
    @ApiDavAttributeMapping(path=MAPPING_C2, attribute=ApiDavMappingAttribute.ContentType)
    String test_C2() {
        return "";
    }
    @ApiDavAttributeMapping(path=MAPPING_C3, attribute=ApiDavMappingAttribute.ContentType)
    String test_C3() {
        return " ";
    }
    @ApiDavAttributeMapping(path=MAPPING_C4, attribute=ApiDavMappingAttribute.ContentType)
    String test_C4() {
        return "a\t\r\nz";
    }
    @ApiDavAttributeMapping(path=MAPPING_C5, attribute=ApiDavMappingAttribute.ContentType)
    String test_C5() {
        return "Test C5";
    }
    @ApiDavAttributeMapping(path=MAPPING_C6, attribute=ApiDavMappingAttribute.ContentType)
    String test_C6() {
        return "a\u00A9z";
    }
    @ApiDavAttributeMapping(path=MAPPING_C7, attribute=ApiDavMappingAttribute.ContentType)
    Object test_C7() {
        return "Test C7";
    }
    @ApiDavAttributeMapping(path=MAPPING_C8, attribute=ApiDavMappingAttribute.ContentType)
    Exception test_C8() {
        return new Exception("Test C8");
    }
    @ApiDavAttributeMapping(path=MAPPING_C9, attribute=ApiDavMappingAttribute.ContentType)
    Integer test_C9() {
        throw new RuntimeException("Test C9");
    }
}