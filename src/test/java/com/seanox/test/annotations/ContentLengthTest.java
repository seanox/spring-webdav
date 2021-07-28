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

import com.seanox.api.extras.ContentLengthTestController;
import com.seanox.test.AbstractApiTest;
import com.seanox.webdav.WebDavMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test the function of the ContentLength attribute for
 * {@link WebDavMapping}.<br>
 * <br>
 * ContentLengthTest 1.0.0 20210728<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210728
 */
public class ContentLengthTest extends AbstractApiTest {

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
    // - Integer, int, string with int value
    // - null is supported (suppresses output in Response header and PROPFIND response)
    // - value less than 0, empty strings and (convert) exception suppress output
    // - default: null

    @Test
    void test_AX() throws Exception {
        Assertions.assertEquals("200/200/207 /extras/contentLength/a1.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_A1, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/a2.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_A2, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/a3.txt 331361 0/0/0", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_A3, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/a4.txt 331361 1/1/1", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_A4, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/a5.txt 331361 10/10/10", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_A5, AttributeFingerprintType.ContentLength));
    }

    @Test
    void test_BX() throws Exception {
        Assertions.assertEquals("200/200/207 /extras/contentLength/b1.txt 331361 110/110/110", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_B1, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/b2.txt 331361 120/120/120", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_B2, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/b3.txt 331361 130/130/130", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_B3, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/b4.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_B4, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/b5.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_B5, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/b6.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_B6, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/b7.txt 331361 0/0/0", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_B7, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/b8.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_B8, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/b9.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_B9, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/bA.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_BA, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/bB.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_BB, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/bC.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_BC, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/bD.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_BD, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/bE.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_BE, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/bF.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_BF, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/bD.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_BD, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/bG.txt 331361 0/0/0", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_BG, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/bH.txt 331361 1/1/1", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_BH, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/bI.txt 331361 10/10/10", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_BI, AttributeFingerprintType.ContentLength));
    }

    @Test
    void test_CX() throws Exception {
        Assertions.assertEquals("200/200/207 /extras/contentLength/c1.txt 331361 110/110/110", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_C1, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/c2.txt 331361 120/120/120", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_C2, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/c3.txt 331361 130/130/130", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_C3, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/c4.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_C4, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/c5.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_C5, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/c6.txt 331361 160/160/160", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_C6, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/c7.txt 331361 170/170/170", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_C7, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/c8.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_C8, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/c9.txt 331361 0/0/0", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_C9, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/cA.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_CA, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/cB.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_CB, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/cC.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_CC, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/cD.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_CD, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/cE.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_CE, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/cF.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_CF, AttributeFingerprintType.ContentLength));
    }

    @Test
    void test_DX() throws Exception {
        // Variations like 8/9/8 look strange, but they are correct.
        // Because MetaOutputStream::ContentLength is only used with GET and not with HEAD and PROPFIND.
        Assertions.assertEquals("200/200/207 /extras/contentLength/d1.txt 331361 8/9/8", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_D1, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/d2.txt 331361 8/8/8", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_D2, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/d3.txt 331361 7/7/7", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_D3, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/d4.txt 331361 6/6/6", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_D4, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/d5.txt 331361 5/5/5", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_D5, AttributeFingerprintType.ContentLength));
        Assertions.assertEquals("200/200/207 /extras/contentLength/d6.txt 301361 null/null", this.createAttributeFingerprint(ContentLengthTestController.MAPPING_D6, AttributeFingerprintType.ContentLength));
    }
}