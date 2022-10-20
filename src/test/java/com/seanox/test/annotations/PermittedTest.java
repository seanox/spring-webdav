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

import com.seanox.api.extras.PermittedTestController;
import com.seanox.test.AbstractApiTest;
import com.seanox.webdav.WebDavInputMapping;
import com.seanox.webdav.WebDavMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test the function of the permitted attribute {@link WebDavMapping} and
 * {@link WebDavInputMapping}.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210815
 */
class PermittedTest extends AbstractApiTest {

    @Test
    void test_DX1() throws Exception {
        Assertions.assertEquals("404/404/404 /extras/permitted/d1.txt 000000", this.createAttributeFingerprint(PermittedTestController.MAPPING_D1));
        Assertions.assertEquals("200/200/207 /extras/permitted/d2.txt 301361", this.createAttributeFingerprint(PermittedTestController.MAPPING_D2));
        Assertions.assertEquals("200/200/207 /extras/permitted/d3.txt 301361", this.createAttributeFingerprint(PermittedTestController.MAPPING_D3));
        Assertions.assertEquals("200/200/207 /extras/permitted/d4.txt 301361", this.createAttributeFingerprint(PermittedTestController.MAPPING_D4));
        Assertions.assertEquals("200/200/207 /extras/permitted/d5.txt 301361", this.createAttributeFingerprint(PermittedTestController.MAPPING_D5));
        Assertions.assertEquals("200/200/207 /extras/permitted/d6.txt 301361", this.createAttributeFingerprint(PermittedTestController.MAPPING_D6));
    }

    @Test
    void test_DX2() throws Exception {
        Assertions.assertEquals("200/200/207 /extras/permitted/dA.txt 301361", this.createAttributeFingerprint(PermittedTestController.MAPPING_DA));
        Assertions.assertEquals("404/404/404 /extras/permitted/dB.txt 000000", this.createAttributeFingerprint(PermittedTestController.MAPPING_DB));
        Assertions.assertEquals("404/404/404 /extras/permitted/dC.txt 000000", this.createAttributeFingerprint(PermittedTestController.MAPPING_DC));
        Assertions.assertEquals("404/404/404 /extras/permitted/dD.txt 000000", this.createAttributeFingerprint(PermittedTestController.MAPPING_DD));
        Assertions.assertEquals("404/404/404 /extras/permitted/dE.txt 000000", this.createAttributeFingerprint(PermittedTestController.MAPPING_DE));
        Assertions.assertEquals("200/200/207 /extras/permitted/dF.txt 301361", this.createAttributeFingerprint(PermittedTestController.MAPPING_DF));
    }
}