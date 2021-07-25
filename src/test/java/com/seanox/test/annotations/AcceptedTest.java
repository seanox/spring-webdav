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

import com.seanox.api.extras.AcceptedTestController;
import com.seanox.test.AbstractApiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test the function of the Accepted attribute.
 *
 * AcceptedTest 1.0.0 20210724
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210724
 */
public class AcceptedTest extends AbstractApiTest {

    @Test
    void test_DX1() throws Exception {
        Assertions.assertEquals("400/400/400 /extras/accepted/d1.txt 000000", this.createAttributeFingeprint(AcceptedTestController.MAPPING_D1));
        Assertions.assertEquals("200/200/207 /extras/accepted/d2.txt 301361", this.createAttributeFingeprint(AcceptedTestController.MAPPING_D2));
        Assertions.assertEquals("200/200/207 /extras/accepted/d3.txt 301361", this.createAttributeFingeprint(AcceptedTestController.MAPPING_D3));
        Assertions.assertEquals("200/200/207 /extras/accepted/d4.txt 301361", this.createAttributeFingeprint(AcceptedTestController.MAPPING_D4));
        Assertions.assertEquals("200/200/207 /extras/accepted/d5.txt 301361", this.createAttributeFingeprint(AcceptedTestController.MAPPING_D5));
        Assertions.assertEquals("200/200/207 /extras/accepted/d6.txt 301361", this.createAttributeFingeprint(AcceptedTestController.MAPPING_D6));
    }

    @Test
    void test_DX2() throws Exception {
        Assertions.assertEquals("200/200/207 /extras/accepted/dA.txt 301361", this.createAttributeFingeprint(AcceptedTestController.MAPPING_DA));
        Assertions.assertEquals("400/400/400 /extras/accepted/dB.txt 000000", this.createAttributeFingeprint(AcceptedTestController.MAPPING_DB));
        Assertions.assertEquals("400/400/400 /extras/accepted/dC.txt 000000", this.createAttributeFingeprint(AcceptedTestController.MAPPING_DC));
        Assertions.assertEquals("400/400/400 /extras/accepted/dD.txt 000000", this.createAttributeFingeprint(AcceptedTestController.MAPPING_DD));
        Assertions.assertEquals("400/400/400 /extras/accepted/dE.txt 000000", this.createAttributeFingeprint(AcceptedTestController.MAPPING_DE));
        Assertions.assertEquals("200/200/207 /extras/accepted/dF.txt 301361", this.createAttributeFingeprint(AcceptedTestController.MAPPING_DF));
    }
}