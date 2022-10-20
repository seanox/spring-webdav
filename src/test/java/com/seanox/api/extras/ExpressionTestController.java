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

import com.seanox.webdav.WebDavMapping;
import com.seanox.webdav.WebDavMappingAttribute;
import com.seanox.webdav.WebDavMappingAttributeExpression;
import com.seanox.webdav.MetaOutputStream;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Test of the expressions.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210726
 */
@Profile("test")
@Component
public class ExpressionTestController {

    public static final String MAPPING_A1 = "/extras/expression/a1.txt";
    public static final String MAPPING_A2 = "/extras/expression/a2.txt";
    public static final String MAPPING_A3 = "/extras/expression/a3.txt";
    public static final String MAPPING_A4 = "/extras/expression/a4.txt";
    public static final String MAPPING_A5 = "/extras/expression/a5.txt";
    public static final String MAPPING_A6 = "/extras/expression/a6.txt";

    @WebDavMapping(path=MAPPING_A1, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentType, phrase="")
    })
    @WebDavMapping(path=MAPPING_A2, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentType, phrase="a")
    })
    @WebDavMapping(path=MAPPING_A3, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentType, phrase="1a")
    })
    @WebDavMapping(path=MAPPING_A4, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentType, phrase="a1")
    })
    @WebDavMapping(path=MAPPING_A5, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentType, phrase="#a1")
    })
    @WebDavMapping(path=MAPPING_A6, attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentType, phrase="#a1(")
    })
    public void test_AX(MetaOutputStream outputStream) throws IOException {
        outputStream.write(String.valueOf(outputStream.getContentType()).getBytes());
    }
}