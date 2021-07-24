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
import com.seanox.apidav.MetaData;
import com.seanox.apidav.MetaOutputStream;
import org.springframework.stereotype.Component;

/**
 * Test of the annotation {@link ApiDavAttributeMapping}
 *     + {@link ApiDavMappingAttribute#ContentType} functions.
 *
 * ContentTypeTestController 1.0.0 20210720
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210720
 */
@Component
public class ContentTypeTestController {

    // TODO: Test A for attributes + Variants of values (valid + invalid)
    // TODO: Test B for expressions + Variants of values (valid + invalid)
    // Test C for callbacks + Variants of values (valid + invalid)
    // Test D of priorities, what is used when -- (MetaOutputStream), Callback, Meta, Expression, Static, (Default)

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

    // Test of priorities:
    // (MetaOutputStream), Callback, Meta, Expression, Static, (Default)

    public static final String MAPPING_D1 = "/extras/contentType/d1.txt";
    public static final String MAPPING_D2 = "/extras/contentType/d2.txt";
    public static final String MAPPING_D3 = "/extras/contentType/d3.txt";
    public static final String MAPPING_D4 = "/extras/contentType/d4.txt";
    public static final String MAPPING_D5 = "/extras/contentType/d5.txt";
    public static final String MAPPING_D6 = "/extras/contentType/d6.txt";

    @ApiDavMapping(path=MAPPING_D1, contentType="5", attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentType, phrase="6")
    })
    void test_D1X(final MetaOutputStream outputStream) {
        outputStream.setContentType("9");
    }
    @ApiDavMetaMapping(path=MAPPING_D1)
    void test_D1(final MetaData metaData) {
        metaData.setContentType("7");
    }
    @ApiDavAttributeMapping(path=MAPPING_D1, attribute=ApiDavMappingAttribute.ContentType)
    String test_D1() {
        return "8";
    }

    @ApiDavMapping(path=MAPPING_D2, contentType="5", attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentType, phrase="6")
    })
    void test_D2X() {
    }
    @ApiDavMetaMapping(path=MAPPING_D2)
    void test_D2(final MetaData metaData) {
        metaData.setContentType("7");
    }
    @ApiDavAttributeMapping(path=MAPPING_D2, attribute=ApiDavMappingAttribute.ContentType)
    String test_D2() {
        return "8";
    }

    @ApiDavMapping(path=MAPPING_D3, contentType="5", attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentType, phrase="6")
    })
    void test_D3X() {
    }
    @ApiDavMetaMapping(path=MAPPING_D3)
    void test_D3(final MetaData metaData) {
        metaData.setContentType("7");
    }

    @ApiDavMapping(path=MAPPING_D4, contentType="5", attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentType, phrase="6")
    })
    void test_D4X() {
    }

    @ApiDavMapping(path=MAPPING_D5, contentType="5")
    void test_D5X() {
    }

    @ApiDavMapping(path=MAPPING_D6)
    void test_D6X() {
    }    
}