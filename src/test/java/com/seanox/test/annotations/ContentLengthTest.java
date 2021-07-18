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

import com.seanox.api.extras.ContentLengthTestController;
import com.seanox.test.AbstractApiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test the function of the ContentLength attribute.
 *
 * ContentLengthTest 1.0.0 20210718
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210718
 */
public class ContentLengthTest extends AbstractApiTest {

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
    // - Long, long, string with long value
    // - null is supported (suppresses output in Response header and PROPFIND response)
    // - value less than 0, empty strings and (convert) exception suppress output
    // - default: null

    @Test
    void test_AX() throws Exception {
        Assertions.assertEquals("200/200/207 /extras/contentLength/a1.txt 301361 null/null", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_A1, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/a2.txt 301361 null/null", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_A2, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/a3.txt 331361 0/0/0", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_A3, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/a4.txt 331361 1/1/1", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_A4, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/a5.txt 331361 10/10/10", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_A5, AttributeFingeprintType.ContentLength));
    }

    // TODO:

    @Test
    void test_CX() throws Exception {
        Assertions.assertEquals("200/200/207 /extras/contentLength/c1.txt 331361 110/110/110", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_C1, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/c2.txt 331361 120/120/120", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_C2, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/c3.txt 331361 130/130/130", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_C3, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/c4.txt 301361 null/null", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_C4, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/c5.txt 301361 null/null", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_C5, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/c6.txt 331361 160/160/160", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_C6, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/c7.txt 331361 170/170/170", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_C7, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/c8.txt 301361 null/null", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_C8, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/c9.txt 331361 0/0/0", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_C9, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/cA.txt 301361 null/null", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_CA, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/cB.txt 301361 null/null", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_CB, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/cC.txt 301361 null/null", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_CC, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/cD.txt 301361 null/null", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_CD, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/cE.txt 301361 null/null", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_CE, AttributeFingeprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/cF.txt 301361 null/null", this.createAttributeFingeprint(ContentLengthTestController.MAPPING_CF, AttributeFingeprintType.ContentLength));
    }
}