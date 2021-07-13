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

import com.seanox.api.extras.LastModifiedTestController;
import com.seanox.test.AbstractApiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test the function of the LastModified attribute.
 *
 * LastModifiedTest 1.0.0 20210713
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210713
 */
public class LastModifiedTest extends AbstractApiTest {

    // Supported types of data definition:
    // - {@link ApiDavMapping}
    // - {@link ApiDavMetaMapping}
    // - {@link ApiDavAttribute}
    // Supported data types of definition:
    // - static value via {@link ApiDavMapping} + {@link ApiDavMetaMapping}
    // - Spring Expression Language via {@link ApiDavMapping}
    // - callback via {@link ApiDavAttribute}
    // Expected data type:
    // - Date, string in format yyyy-MM-dd hh:mm:ss only an effect in the annotations,
    //   the callbacks support only Date as return value.
    // - null is supported (suppresses output in Response header and PROPFIND response)
    // - empty strings and (convert) exception suppress output
    // - default: timestamp from the build of the application

    @Test
    void test_CX()
            throws Exception {
        Assertions.assertEquals("200/200/207 /extras/lastModified/c1.txt 301061 null/null", this.createAttributeFingeprint(LastModifiedTestController.MAPPING_C1, AttributeFingeprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/c2.txt 301061 null/null", this.createAttributeFingeprint(LastModifiedTestController.MAPPING_C2, AttributeFingeprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/c3.txt 301061 null/null", this.createAttributeFingeprint(LastModifiedTestController.MAPPING_C3, AttributeFingeprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/c4.txt 301061 null/null", this.createAttributeFingeprint(LastModifiedTestController.MAPPING_C4, AttributeFingeprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/c5.txt 301061 null/null", this.createAttributeFingeprint(LastModifiedTestController.MAPPING_C5, AttributeFingeprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/c6.txt 301061 null/null", this.createAttributeFingeprint(LastModifiedTestController.MAPPING_C6, AttributeFingeprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/c7.txt 301061 null/null", this.createAttributeFingeprint(LastModifiedTestController.MAPPING_C7, AttributeFingeprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/c8.txt 301061 null/null", this.createAttributeFingeprint(LastModifiedTestController.MAPPING_C8, AttributeFingeprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/c9.txt 301061 null/null", this.createAttributeFingeprint(LastModifiedTestController.MAPPING_C9, AttributeFingeprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/cA.txt 301361 Sun, 02 Jan 2456 03:04:05 GMT/Sun, 02 Jan 2456 03:04:05 GMT/Sun, 02 Jan 2456 03:04:05 GMT", this.createAttributeFingeprint(LastModifiedTestController.MAPPING_CA, AttributeFingeprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/cB.txt 301361 Mon, 02 Jan 1956 03:04:05 GMT/Mon, 02 Jan 1956 03:04:05 GMT/Mon, 02 Jan 1956 03:04:05 GMT", this.createAttributeFingeprint(LastModifiedTestController.MAPPING_CB, AttributeFingeprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/cC.txt 301361 Sun, 02 Jan 2456 03:04:05 GMT/Sun, 02 Jan 2456 03:04:05 GMT/Sun, 02 Jan 2456 03:04:05 GMT", this.createAttributeFingeprint(LastModifiedTestController.MAPPING_CC, AttributeFingeprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/cD.txt 301361 Mon, 02 Jan 1956 03:04:05 GMT/Mon, 02 Jan 1956 03:04:05 GMT/Mon, 02 Jan 1956 03:04:05 GMT", this.createAttributeFingeprint(LastModifiedTestController.MAPPING_CD, AttributeFingeprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/cE.txt 301361 Mon, 02 Jan 1956 03:04:05 GMT/Mon, 02 Jan 1956 03:04:05 GMT/Mon, 02 Jan 1956 03:04:05 GMT", this.createAttributeFingeprint(LastModifiedTestController.MAPPING_CE, AttributeFingeprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/cF.txt 301061 null/null", this.createAttributeFingeprint(LastModifiedTestController.MAPPING_CF, AttributeFingeprintType.LastModified));
        Assertions.assertEquals("200/200/207 /extras/lastModified/cG.txt 301061 null/null", this.createAttributeFingeprint(LastModifiedTestController.MAPPING_CG, AttributeFingeprintType.LastModified));
    }
}