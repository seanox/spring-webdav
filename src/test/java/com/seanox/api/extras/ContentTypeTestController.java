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
import com.seanox.webdav.MetaData;
import com.seanox.webdav.MetaOutputStream;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Test the function of the ContentType attribute for
 * {@link WebDavMapping}.<br>
 * <br>
 * ContentTypeTestController 1.0.0 20210726<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210726
 */
@Profile("test")
@Component
public class ContentTypeTestController {

    // TODO: Test A for attributes + Variants of values (valid + invalid)
    // TODO: Test B for expressions + Variants of values (valid + invalid)
    // Test C for callbacks + Variants of values (valid + invalid)
    // Test D of priorities, what is used when -- (MetaOutputStream), Callback, Meta, Expression, Static, (Default)

    // Test of {@link WebDavAttribute}

    public static final String MAPPING_C1 = "/extras/contentType/c1.txt";
    public static final String MAPPING_C2 = "/extras/contentType/c2.txt";
    public static final String MAPPING_C3 = "/extras/contentType/c3.txt";
    public static final String MAPPING_C4 = "/extras/contentType/c4.txt";
    public static final String MAPPING_C5 = "/extras/contentType/c5.txt";
    public static final String MAPPING_C6 = "/extras/contentType/c6.txt";
    public static final String MAPPING_C7 = "/extras/contentType/c7.txt";
    public static final String MAPPING_C8 = "/extras/contentType/c8.txt";
    public static final String MAPPING_C9 = "/extras/contentType/c9.txt";

    @WebDavMapping(path=MAPPING_C1)
    @WebDavMapping(path=MAPPING_C2)
    @WebDavMapping(path=MAPPING_C3)
    @WebDavMapping(path=MAPPING_C4)
    @WebDavMapping(path=MAPPING_C5)
    @WebDavMapping(path=MAPPING_C6)
    @WebDavMapping(path=MAPPING_C7)
    @WebDavMapping(path=MAPPING_C8)
    @WebDavMapping(path=MAPPING_C9)
    void test_CX() {
    }
    @WebDavAttributeMapping(path=MAPPING_C1, attribute=WebDavMappingAttribute.ContentType)
    String test_C1() {
        return null;
    }
    @WebDavAttributeMapping(path=MAPPING_C2, attribute=WebDavMappingAttribute.ContentType)
    String test_C2() {
        return "";
    }
    @WebDavAttributeMapping(path=MAPPING_C3, attribute=WebDavMappingAttribute.ContentType)
    String test_C3() {
        return " ";
    }
    @WebDavAttributeMapping(path=MAPPING_C4, attribute=WebDavMappingAttribute.ContentType)
    String test_C4() {
        return "a\t\r\nz";
    }
    @WebDavAttributeMapping(path=MAPPING_C5, attribute=WebDavMappingAttribute.ContentType)
    String test_C5() {
        return "Test C5";
    }
    @WebDavAttributeMapping(path=MAPPING_C6, attribute=WebDavMappingAttribute.ContentType)
    String test_C6() {
        return "a\u00A9z";
    }
    @WebDavAttributeMapping(path=MAPPING_C7, attribute=WebDavMappingAttribute.ContentType)
    Object test_C7() {
        return "Test C7";
    }
    @WebDavAttributeMapping(path=MAPPING_C8, attribute=WebDavMappingAttribute.ContentType)
    Exception test_C8() {
        return new Exception("Test C8");
    }
    @WebDavAttributeMapping(path=MAPPING_C9, attribute=WebDavMappingAttribute.ContentType)
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

    @WebDavMapping(path=MAPPING_D1, contentType="5", attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentType, phrase="6")
    })
    void test_D1X(final MetaOutputStream outputStream) {
        outputStream.setContentType("9");
    }
    @WebDavMetaMapping(path=MAPPING_D1)
    void test_D1(final MetaData metaData) {
        metaData.setContentType("7");
    }
    @WebDavAttributeMapping(path=MAPPING_D1, attribute=WebDavMappingAttribute.ContentType)
    String test_D1() {
        return "8";
    }

    @WebDavMapping(path=MAPPING_D2, contentType="5", attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentType, phrase="6")
    })
    void test_D2X() {
    }
    @WebDavMetaMapping(path=MAPPING_D2)
    void test_D2(final MetaData metaData) {
        metaData.setContentType("7");
    }
    @WebDavAttributeMapping(path=MAPPING_D2, attribute=WebDavMappingAttribute.ContentType)
    String test_D2() {
        return "8";
    }

    @WebDavMapping(path=MAPPING_D3, contentType="5", attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentType, phrase="6")
    })
    void test_D3X() {
    }
    @WebDavMetaMapping(path=MAPPING_D3)
    void test_D3(final MetaData metaData) {
        metaData.setContentType("7");
    }

    @WebDavMapping(path=MAPPING_D4, contentType="5", attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentType, phrase="6")
    })
    void test_D4X() {
    }

    @WebDavMapping(path=MAPPING_D5, contentType="5")
    void test_D5X() {
    }

    @WebDavMapping(path=MAPPING_D6)
    void test_D6X() {
    }    
}