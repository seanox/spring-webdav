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

import com.seanox.api.extras.CreationDateTestController;
import com.seanox.webdav.DateTimeAdapter;
import com.seanox.test.AbstractApiTest;
import com.seanox.webdav.WebDavMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test the function of the CreationDate attribute for {@link WebDavMapping}.
 *
 * @author  Seanox Software Solutions
 * @version 1.1.0 20210815
 */
class CreationDateTest extends AbstractApiTest {

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
        Assertions.assertEquals("200/200/207 /extras/creationDate/c1.txt 300061", this.createAttributeFingerprint(CreationDateTestController.MAPPING_C1, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/c2.txt 300061", this.createAttributeFingerprint(CreationDateTestController.MAPPING_C2, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/c3.txt 300061", this.createAttributeFingerprint(CreationDateTestController.MAPPING_C3, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/c4.txt 300061", this.createAttributeFingerprint(CreationDateTestController.MAPPING_C4, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/c5.txt 300061", this.createAttributeFingerprint(CreationDateTestController.MAPPING_C5, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/c6.txt 300061", this.createAttributeFingerprint(CreationDateTestController.MAPPING_C6, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/c7.txt 300061", this.createAttributeFingerprint(CreationDateTestController.MAPPING_C7, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/c8.txt 300061", this.createAttributeFingerprint(CreationDateTestController.MAPPING_C8, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/c9.txt 300061", this.createAttributeFingerprint(CreationDateTestController.MAPPING_C9, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/cA.txt 301361 2456-01-02T03:04:05Z", this.createAttributeFingerprint(CreationDateTestController.MAPPING_CA, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/cB.txt 301361 1956-01-02T03:04:05Z", this.createAttributeFingerprint(CreationDateTestController.MAPPING_CB, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/cC.txt 301361 2456-01-02T03:04:05Z", this.createAttributeFingerprint(CreationDateTestController.MAPPING_CC, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/cD.txt 301361 1956-01-02T03:04:05Z", this.createAttributeFingerprint(CreationDateTestController.MAPPING_CD, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/cE.txt 301361 1956-01-02T03:04:05Z", this.createAttributeFingerprint(CreationDateTestController.MAPPING_CE, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/cF.txt 300061", this.createAttributeFingerprint(CreationDateTestController.MAPPING_CF, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/cG.txt 300061", this.createAttributeFingerprint(CreationDateTestController.MAPPING_CG, AttributeFingerprintType.CreationDate));
    }

    @Test
    void test_DX() throws Exception {
        Assertions.assertEquals("200/200/207 /extras/creationDate/d1.txt 301361 2008-01-01T00:00:00Z", this.createAttributeFingerprint(CreationDateTestController.MAPPING_D1, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/d2.txt 301361 2008-01-01T00:00:00Z", this.createAttributeFingerprint(CreationDateTestController.MAPPING_D2, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/d3.txt 301361 2007-01-01T00:00:00Z", this.createAttributeFingerprint(CreationDateTestController.MAPPING_D3, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/d4.txt 301361 2006-01-01T00:00:00Z", this.createAttributeFingerprint(CreationDateTestController.MAPPING_D4, AttributeFingerprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/d5.txt 301361 2004-12-31T23:00:00Z", this.createAttributeFingerprint(CreationDateTestController.MAPPING_D5, AttributeFingerprintType.CreationDate));
        String timestampD6 = this.createAttributeFingerprint(CreationDateTestController.MAPPING_D6, AttributeFingerprintType.CreationDate);
        timestampD6 = timestampD6.replaceAll("T.*$", "");
        Assertions.assertEquals(String.format("200/200/207 /extras/creationDate/d6.txt 301361 %1$tY-%1$tm-%1$td", DateTimeAdapter.getApplicationBuildDate()), timestampD6);
    }
}