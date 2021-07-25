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

import com.seanox.api.extras.PermittedTestController;
import com.seanox.test.AbstractApiTest;
import com.seanox.webdav.WebDavInputMapping;
import com.seanox.webdav.WebDavMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test the function of the permitted attribute.
 * {@link WebDavMapping} and
 * {@link WebDavInputMapping}.<br>
 * <br>
 * PermittedTest 1.0.0 20210724<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210724
 */
public class PermittedTest extends AbstractApiTest {

    @Test
    void test_DX1() throws Exception {
        Assertions.assertEquals("404/404/404 /extras/permitted/d1.txt 000000", this.createAttributeFingeprint(PermittedTestController.MAPPING_D1));
        Assertions.assertEquals("200/200/207 /extras/permitted/d2.txt 301361", this.createAttributeFingeprint(PermittedTestController.MAPPING_D2));
        Assertions.assertEquals("200/200/207 /extras/permitted/d3.txt 301361", this.createAttributeFingeprint(PermittedTestController.MAPPING_D3));
        Assertions.assertEquals("200/200/207 /extras/permitted/d4.txt 301361", this.createAttributeFingeprint(PermittedTestController.MAPPING_D4));
        Assertions.assertEquals("200/200/207 /extras/permitted/d5.txt 301361", this.createAttributeFingeprint(PermittedTestController.MAPPING_D5));
        Assertions.assertEquals("200/200/207 /extras/permitted/d6.txt 301361", this.createAttributeFingeprint(PermittedTestController.MAPPING_D6));
    }

    @Test
    void test_DX2() throws Exception {
        Assertions.assertEquals("200/200/207 /extras/permitted/dA.txt 301361", this.createAttributeFingeprint(PermittedTestController.MAPPING_DA));
        Assertions.assertEquals("404/404/404 /extras/permitted/dB.txt 000000", this.createAttributeFingeprint(PermittedTestController.MAPPING_DB));
        Assertions.assertEquals("404/404/404 /extras/permitted/dC.txt 000000", this.createAttributeFingeprint(PermittedTestController.MAPPING_DC));
        Assertions.assertEquals("404/404/404 /extras/permitted/dD.txt 000000", this.createAttributeFingeprint(PermittedTestController.MAPPING_DD));
        Assertions.assertEquals("404/404/404 /extras/permitted/dE.txt 000000", this.createAttributeFingeprint(PermittedTestController.MAPPING_DE));
        Assertions.assertEquals("200/200/207 /extras/permitted/dF.txt 301361", this.createAttributeFingeprint(PermittedTestController.MAPPING_DF));
    }
}