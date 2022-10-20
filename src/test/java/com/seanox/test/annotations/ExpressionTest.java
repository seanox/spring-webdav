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
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210815
 */
class ExpressionTest extends AbstractApiTest {

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