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
 * ExpressionTestController 1.0.0 20210726<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
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