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
package com.seanox.test.annotations;

import com.seanox.api.extras.ContentTypeTestController;
import com.seanox.test.AbstractApiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test the function of the ContentType attribute.
 *
 * ContentTypeTest 1.0.0 20210721
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210721
 */
public class ContentTypeTest extends AbstractApiTest {

    // Supported types of data definition:
    // - {@link ApiDavMapping}
    // - {@link ApiDavMapping} + {@link ApiDavMappingAttributeExpression}
    // - {@link ApiDavMetaMapping}
    // - {@link ApiDavAttribute}
    // Supported data types of definition:
    // - static value via {@link ApiDavMapping}, {@link ApiDavMetaMapping}
    // - Spring Expression Language via {@link ApiDavMappingAttributeExpression}
    // - callback via {@link ApiDavAttribute}
    // Expected data type:
    // - String
    // - null is supported (suppresses output in Response header and PROPFIND response)
    // - empty strings and (convert) exception suppress output
    // - default: application/octet-stream

    // TODO:

    @Test
    void test_CX() throws Exception {
        Assertions.assertEquals("200/200/207 /extras/contentType/c1.txt 001361 null/null", this.createAttributeFingeprint(ContentTypeTestController.MAPPING_C1, AttributeFingeprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/c2.txt 001361 null/null", this.createAttributeFingeprint(ContentTypeTestController.MAPPING_C2, AttributeFingeprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/c3.txt 001361 null/null", this.createAttributeFingeprint(ContentTypeTestController.MAPPING_C3, AttributeFingeprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/c4.txt 301361 a\t\r\nz/a\t\r\nz/a\t\nz", this.createAttributeFingeprint(ContentTypeTestController.MAPPING_C4, AttributeFingeprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/c5.txt 301361 Test C5/Test C5/Test C5", this.createAttributeFingeprint(ContentTypeTestController.MAPPING_C5, AttributeFingeprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/c6.txt 301361 a\u00A9z/a\u00A9z/a\u00A9z", this.createAttributeFingeprint(ContentTypeTestController.MAPPING_C6, AttributeFingeprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/c7.txt 301361 Test C7/Test C7/Test C7", this.createAttributeFingeprint(ContentTypeTestController.MAPPING_C7, AttributeFingeprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/c8.txt 001361 null/null", this.createAttributeFingeprint(ContentTypeTestController.MAPPING_C8, AttributeFingeprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/c9.txt 001361 null/null", this.createAttributeFingeprint(ContentTypeTestController.MAPPING_C9, AttributeFingeprintType.ContentType));
    }

    @Test
    void test_DX() throws Exception {
        // Variations like 8/9/8 look strange, but they are correct.
        // Because MetaOutputstream::ContentLength is only used with GET and not with HEAD and PROPFIND.
        Assertions.assertEquals("200/200/207 /extras/contentType/d1.txt 301361 8/9/8", this.createAttributeFingeprint(ContentTypeTestController.MAPPING_D1, AttributeFingeprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/d2.txt 301361 8/8/8", this.createAttributeFingeprint(ContentTypeTestController.MAPPING_D2, AttributeFingeprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/d3.txt 301361 7/7/7", this.createAttributeFingeprint(ContentTypeTestController.MAPPING_D3, AttributeFingeprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/d4.txt 301361 6/6/6", this.createAttributeFingeprint(ContentTypeTestController.MAPPING_D4, AttributeFingeprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/d5.txt 301361 5/5/5", this.createAttributeFingeprint(ContentTypeTestController.MAPPING_D5, AttributeFingeprintType.ContentType));
        Assertions.assertEquals("200/200/207 /extras/contentType/d6.txt 301361 text/plain/text/plain/text/plain", this.createAttributeFingeprint(ContentTypeTestController.MAPPING_D6, AttributeFingeprintType.ContentType));
    }
}