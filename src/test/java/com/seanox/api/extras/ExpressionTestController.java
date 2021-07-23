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

import com.seanox.apidav.AnnotationAdapter;
import com.seanox.apidav.ApiDavAttributeMapping;
import com.seanox.apidav.ApiDavMapping;
import com.seanox.apidav.ApiDavMappingAttribute;
import com.seanox.apidav.ApiDavMappingAttributeExpression;
import com.seanox.apidav.MetaOutputStream;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Test of the expressions.
 *
 * ExpressionTestController 1.0.0 20210723
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210723
 */
@Component
public class ExpressionTestController {

    public static final String MAPPING_A1 = "/extras/expression/a1.txt";
    public static final String MAPPING_A2 = "/extras/expression/a2.txt";
    public static final String MAPPING_A3 = "/extras/expression/a3.txt";
    public static final String MAPPING_A4 = "/extras/expression/a4.txt";
    public static final String MAPPING_A5 = "/extras/expression/a5.txt";
    public static final String MAPPING_A6 = "/extras/expression/a6.txt";

    @ApiDavMapping(path=MAPPING_A1, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentType, phrase="")
    })
    @ApiDavMapping(path=MAPPING_A2, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentType, phrase="a")
    })
    @ApiDavMapping(path=MAPPING_A3, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentType, phrase="1a")
    })
    @ApiDavMapping(path=MAPPING_A4, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentType, phrase="a1")
    })
    @ApiDavMapping(path=MAPPING_A5, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentType, phrase="#a1")
    })
    @ApiDavMapping(path=MAPPING_A6, attributeExpressions={
            @ApiDavMappingAttributeExpression(attribute=ApiDavMappingAttribute.ContentType, phrase="#a1(")
    })
    public void test_AX(MetaOutputStream outputStream) throws IOException {
        outputStream.write(String.valueOf(outputStream.getContentType()).getBytes());
    }
}