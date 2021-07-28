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
package com.seanox.test.annotations;

import com.seanox.api.extras.ExpressionTestController;
import com.seanox.webdav.AnnotationAdapter;
import com.seanox.webdav.WebDavMapping;
import com.seanox.webdav.WebDavMappingAttribute;
import com.seanox.webdav.WebDavMappingAttributeExpression;
import com.seanox.test.AbstractApiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test of the expressions.
 *
 * ExpressionTest 1.0.0 20210728<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210728
 */
public class ExpressionTest extends AbstractApiTest {

    // Exceptions/syntax errors in the expressions have no effect for the mapping.

    @WebDavMapping(path = "/ignore_01", attributeExpressions = {
            @WebDavMappingAttributeExpression(attribute = WebDavMappingAttribute.ContentType, phrase = "")
    })
    @Test
    void parseTest_01() throws Exception {
        AnnotationAdapter.createAnnotation(this.collectApiAnnotationsFromCurrentMethod().stream().findFirst().orElseThrow());
    }

    @WebDavMapping(path = "/ignore_02", attributeExpressions = {
            @WebDavMappingAttributeExpression(attribute = WebDavMappingAttribute.ContentType, phrase = "a")
    })
    @Test
    void parseTest_02() throws Exception {
        AnnotationAdapter.createAnnotation(this.collectApiAnnotationsFromCurrentMethod().stream().findFirst().orElseThrow());
    }

    @WebDavMapping(path = "/ignore_03", attributeExpressions = {
            @WebDavMappingAttributeExpression(attribute = WebDavMappingAttribute.ContentType, phrase = "1a")
    })
    @Test
    void parseTest_03() throws Exception {
        AnnotationAdapter.createAnnotation(this.collectApiAnnotationsFromCurrentMethod().stream().findFirst().orElseThrow());
    }

    @WebDavMapping(path = "/ignore_04", attributeExpressions = {
            @WebDavMappingAttributeExpression(attribute = WebDavMappingAttribute.ContentType, phrase = "a1")
    })
    @Test
    void parseTest_04() throws Exception {
        AnnotationAdapter.createAnnotation(this.collectApiAnnotationsFromCurrentMethod().stream().findFirst().orElseThrow());
    }

    @WebDavMapping(path = "/ignore_05", attributeExpressions = {
            @WebDavMappingAttributeExpression(attribute = WebDavMappingAttribute.ContentType, phrase = "#a1")
    })
    @Test
    void parseTest_05() throws Exception {
        AnnotationAdapter.createAnnotation(this.collectApiAnnotationsFromCurrentMethod().stream().findFirst().orElseThrow());
    }

    @WebDavMapping(path = "/ignore_06", attributeExpressions = {
            @WebDavMappingAttributeExpression(attribute = WebDavMappingAttribute.ContentType, phrase = "#a1(")
    })
    @Test
    void parseTest_06() throws Exception {
        AnnotationAdapter.createAnnotation(this.collectApiAnnotationsFromCurrentMethod().stream().findFirst().orElseThrow());
    }

    // Exceptions/syntax errors in the expressions affect like the value null,
    // but there is an error output on the console

    @Test
    void test_CX() throws Exception {
        Assertions.assertEquals("200/200/207 /extras/expression/a1.txt 001361 null/null", this.createAttributeFingerprint(ExpressionTestController.MAPPING_A1, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/expression/a2.txt 001361 null/null", this.createAttributeFingerprint(ExpressionTestController.MAPPING_A2, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/expression/a3.txt 001361 null/null", this.createAttributeFingerprint(ExpressionTestController.MAPPING_A3, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/expression/a4.txt 001361 null/null", this.createAttributeFingerprint(ExpressionTestController.MAPPING_A4, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/expression/a5.txt 001361 null/null", this.createAttributeFingerprint(ExpressionTestController.MAPPING_A5, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/expression/a6.txt 001361 null/null", this.createAttributeFingerprint(ExpressionTestController.MAPPING_A6, AttributeFingerprintType.ContentType));
    }
}