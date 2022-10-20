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

import com.seanox.api.extras.LastModifiedTestController;
import com.seanox.webdav.DateTimeAdapter;
import com.seanox.test.AbstractApiTest;
import com.seanox.webdav.WebDavMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Locale;

/**
 * Test the function of the LastModified attribute for {@link WebDavMapping}.
 *
 * @author  Seanox Software Solutions
 * @version 1.1.0 20210815
 */
class LastModifiedTest extends AbstractApiTest {

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
    // - Date, string in format yyyy-MM-dd HH:mm:ss only an effect in the annotations,
    //   the callbacks support only Date as return value.
    // - null is supported (suppresses output in Response header and PROPFIND response)
    // - empty strings and (convert) exception suppress output
    // - default: timestamp from the build of the application

    @Test
    void test_CX() throws Exception {
        Assertions.assertEquals("200/200/207 /extras/lastModified/c1.txt 301061 null/null", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_C1, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/c2.txt 301061 null/null", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_C2, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/c3.txt 301061 null/null", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_C3, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/c4.txt 301061 null/null", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_C4, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/c5.txt 301061 null/null", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_C5, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/c6.txt 301061 null/null", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_C6, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/c7.txt 301061 null/null", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_C7, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/c8.txt 301061 null/null", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_C8, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/c9.txt 301061 null/null", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_C9, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/cA.txt 301361 Sun, 02 Jan 2456 03:04:05 GMT/Sun, 02 Jan 2456 03:04:05 GMT/Sun, 02 Jan 2456 03:04:05 GMT", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_CA, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/cB.txt 301361 Mon, 02 Jan 1956 03:04:05 GMT/Mon, 02 Jan 1956 03:04:05 GMT/Mon, 02 Jan 1956 03:04:05 GMT", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_CB, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/cC.txt 301361 Sun, 02 Jan 2456 03:04:05 GMT/Sun, 02 Jan 2456 03:04:05 GMT/Sun, 02 Jan 2456 03:04:05 GMT", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_CC, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/cD.txt 301361 Mon, 02 Jan 1956 03:04:05 GMT/Mon, 02 Jan 1956 03:04:05 GMT/Mon, 02 Jan 1956 03:04:05 GMT", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_CD, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/cE.txt 301361 Mon, 02 Jan 1956 03:04:05 GMT/Mon, 02 Jan 1956 03:04:05 GMT/Mon, 02 Jan 1956 03:04:05 GMT", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_CE, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/cF.txt 301061 null/null", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_CF, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/cG.txt 301061 null/null", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_CG, AttributeFingerprintType.LastModified));
    }

    @Test
    void test_DX() throws Exception {
        // Variations like 2008/2009/2008 look strange, but they are correct.
        // Because MetaOutputStream::ContentLength is only used with GET and not with HEAD and PROPFIND.
        Assertions.assertEquals("200/200/207 /extras/lastModified/d1.txt 301361 Tue, 01 Jan 2008 00:00:00 GMT/Thu, 01 Jan 2009 00:00:00 GMT/Tue, 01 Jan 2008 00:00:00 GMT", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_D1, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/d2.txt 301361 Tue, 01 Jan 2008 00:00:00 GMT/Tue, 01 Jan 2008 00:00:00 GMT/Tue, 01 Jan 2008 00:00:00 GMT", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_D2, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/d3.txt 301361 Mon, 01 Jan 2007 00:00:00 GMT/Mon, 01 Jan 2007 00:00:00 GMT/Mon, 01 Jan 2007 00:00:00 GMT", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_D3, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/d4.txt 301361 Sun, 01 Jan 2006 00:00:00 GMT/Sun, 01 Jan 2006 00:00:00 GMT/Sun, 01 Jan 2006 00:00:00 GMT", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_D4, AttributeFingerprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/d5.txt 301361 Fri, 31 Dec 2004 23:00:00 GMT/Fri, 31 Dec 2004 23:00:00 GMT/Fri, 31 Dec 2004 23:00:00 GMT", this.createAttributeFingerprint(LastModifiedTestController.MAPPING_D5, AttributeFingerprintType.LastModified));
        String timestampD6 = this.createAttributeFingerprint(LastModifiedTestController.MAPPING_D6, AttributeFingerprintType.LastModified);
        timestampD6 = timestampD6.replaceAll("\\s+\\d\\d:.*$", "");
        Assertions.assertEquals(String.format(Locale.US, "200/200/207 /extras/lastModified/d6.txt 301361 %1$ta, %1$td %1$tb %1$tY", DateTimeAdapter.getApplicationBuildDate()), timestampD6);
    }
}