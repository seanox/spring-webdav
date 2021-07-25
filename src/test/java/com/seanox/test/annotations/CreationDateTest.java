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

import com.seanox.api.extras.CreationDateTestController;
import com.seanox.apidav.DateTimeAdapter;
import com.seanox.test.AbstractApiTest;
import com.seanox.test.AbstractTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * Test the function of the CreationDate attribute
 * {@link com.seanox.apidav.ApiDavMapping}.<br>
 * <br>
 * CreationDateTest 1.0.0 20210721<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210721
 */
public class CreationDateTest extends AbstractApiTest {

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
    // - Date, string in format yyyy-MM-dd HH:mm:ss only an effect in the annotations,
    //   the callbacks support only Date as return value.
    // - null is supported (suppresses output in Response header and PROPFIND response)
    // - empty strings and (convert) exception suppress output
    // - default: timestamp from the build of the application

    @Test
    void test_CX() throws Exception {
        Assertions.assertEquals("200/200/207 /extras/creationDate/c1.txt 300061", this.createAttributeFingeprint(CreationDateTestController.MAPPING_C1, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/c2.txt 300061", this.createAttributeFingeprint(CreationDateTestController.MAPPING_C2, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/c3.txt 300061", this.createAttributeFingeprint(CreationDateTestController.MAPPING_C3, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/c4.txt 300061", this.createAttributeFingeprint(CreationDateTestController.MAPPING_C4, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/c5.txt 300061", this.createAttributeFingeprint(CreationDateTestController.MAPPING_C5, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/c6.txt 300061", this.createAttributeFingeprint(CreationDateTestController.MAPPING_C6, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/c7.txt 300061", this.createAttributeFingeprint(CreationDateTestController.MAPPING_C7, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/c8.txt 300061", this.createAttributeFingeprint(CreationDateTestController.MAPPING_C8, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/c9.txt 300061", this.createAttributeFingeprint(CreationDateTestController.MAPPING_C9, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/cA.txt 301361 2456-01-02T03:04:05Z", this.createAttributeFingeprint(CreationDateTestController.MAPPING_CA, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/cB.txt 301361 1956-01-02T03:04:05Z", this.createAttributeFingeprint(CreationDateTestController.MAPPING_CB, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/cC.txt 301361 2456-01-02T03:04:05Z", this.createAttributeFingeprint(CreationDateTestController.MAPPING_CC, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/cD.txt 301361 1956-01-02T03:04:05Z", this.createAttributeFingeprint(CreationDateTestController.MAPPING_CD, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/cE.txt 301361 1956-01-02T03:04:05Z", this.createAttributeFingeprint(CreationDateTestController.MAPPING_CE, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/cF.txt 300061", this.createAttributeFingeprint(CreationDateTestController.MAPPING_CF, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/cG.txt 300061", this.createAttributeFingeprint(CreationDateTestController.MAPPING_CG, AttributeFingeprintType.CreationDate));
    }

    @Test
    void test_DX() throws Exception {
        Assertions.assertEquals("200/200/207 /extras/creationDate/d1.txt 301361 2008-01-01T00:00:00Z", this.createAttributeFingeprint(CreationDateTestController.MAPPING_D1, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/d2.txt 301361 2008-01-01T00:00:00Z", this.createAttributeFingeprint(CreationDateTestController.MAPPING_D2, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/d3.txt 301361 2007-01-01T00:00:00Z", this.createAttributeFingeprint(CreationDateTestController.MAPPING_D3, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/d4.txt 301361 2006-01-01T00:00:00Z", this.createAttributeFingeprint(CreationDateTestController.MAPPING_D4, AttributeFingeprintType.CreationDate));
        Assertions.assertEquals("200/200/207 /extras/creationDate/d5.txt 301361 2004-12-31T23:00:00Z", this.createAttributeFingeprint(CreationDateTestController.MAPPING_D5, AttributeFingeprintType.CreationDate));
        String timestampD6 = this.createAttributeFingeprint(CreationDateTestController.MAPPING_D6, AttributeFingeprintType.CreationDate);
        timestampD6 = timestampD6.replaceAll("T.*$", "");
        Assertions.assertEquals(String.format("200/200/207 /extras/creationDate/d6.txt 301361 %1$tY-%1$tm-%1$td", DateTimeAdapter.getBuildDate()), timestampD6);
    }
}