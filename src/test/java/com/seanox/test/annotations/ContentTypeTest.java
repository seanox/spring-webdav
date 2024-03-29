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

import com.seanox.api.extras.ContentTypeTestController;
import com.seanox.test.AbstractApiTest;
import com.seanox.webdav.WebDavMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test the function of the ContentType attribute for {@link WebDavMapping}.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210815
 */
class ContentTypeTest extends AbstractApiTest {

    // Supported types of data definition:
    // - {@link WebDavMapping}
    // - {@link WebDavMapping} + {@link WebDavMappingAttributeExpression}
    // - {@link WebDavMetaMapping}
    // - {@link WebDavAttribute}
    // Supported data types of definition:
    // - static value via {@link WebDavMapping}, {@link WebDavMetaMapping}
    // - Spring Expression Language via {@link WebDavMappingAttributeExpression}
    // - callback via {@link WebDavAttribute}
    // Expected data type:
    // - String
    // - null is supported (suppresses output in Response header and PROPFIND response)
    // - empty strings and (convert) exception suppress output
    // - default: application/octet-stream

    @Test
    void test_CX() throws Exception {
        Assertions.assertEquals("200/200/207 /extras/contentType/c1.txt 001361 null/null", this.createAttributeFingerprint(ContentTypeTestController.MAPPING_C1, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/c2.txt 001361 null/null", this.createAttributeFingerprint(ContentTypeTestController.MAPPING_C2, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/c3.txt 001361 null/null", this.createAttributeFingerprint(ContentTypeTestController.MAPPING_C3, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/c4.txt 301361 a\t\r\nz/a\t\r\nz/a\t\nz", this.createAttributeFingerprint(ContentTypeTestController.MAPPING_C4, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/c5.txt 301361 Test C5/Test C5/Test C5", this.createAttributeFingerprint(ContentTypeTestController.MAPPING_C5, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/c6.txt 301361 a\u00A9z/a\u00A9z/a\u00A9z", this.createAttributeFingerprint(ContentTypeTestController.MAPPING_C6, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/c7.txt 301361 Test C7/Test C7/Test C7", this.createAttributeFingerprint(ContentTypeTestController.MAPPING_C7, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/c8.txt 001361 null/null", this.createAttributeFingerprint(ContentTypeTestController.MAPPING_C8, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/c9.txt 001361 null/null", this.createAttributeFingerprint(ContentTypeTestController.MAPPING_C9, AttributeFingerprintType.ContentType));
    }

    @Test
    void test_DX() throws Exception {
        // Variations like 8/9/8 look strange, but they are correct.
        // Because MetaOutputStream::ContentLength is only used with GET and not with HEAD and PROPFIND.
        Assertions.assertEquals("200/200/207 /extras/contentType/d1.txt 301361 8/9/8", this.createAttributeFingerprint(ContentTypeTestController.MAPPING_D1, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/d2.txt 301361 8/8/8", this.createAttributeFingerprint(ContentTypeTestController.MAPPING_D2, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/d3.txt 301361 7/7/7", this.createAttributeFingerprint(ContentTypeTestController.MAPPING_D3, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/d4.txt 301361 6/6/6", this.createAttributeFingerprint(ContentTypeTestController.MAPPING_D4, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/d5.txt 301361 5/5/5", this.createAttributeFingerprint(ContentTypeTestController.MAPPING_D5, AttributeFingerprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/d6.txt 301361 text/plain/text/plain/text/plain", this.createAttributeFingerprint(ContentTypeTestController.MAPPING_D6, AttributeFingerprintType.ContentType));
    }
}