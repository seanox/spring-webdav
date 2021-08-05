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

import com.seanox.api.extras.MetaTestController;
import com.seanox.webdav.WebDavMetaMapping;
import com.seanox.test.AbstractApiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.net.URI;

/**
 * Test of the annotation {@link WebDavMetaMapping}.<br>
 * <br>
 * MetaTest 1.1.0 20210805<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.1.0 20210805
 */
public class MetaTest extends AbstractApiTest {

    MetaTest.PropfindResult propfind(final String uri) throws Exception {

        final MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", URI.create(uri))
                        .header("Depth", "1"))
                .andReturn();

        if (mvcResult.getResponse().getStatus() != 200
                && mvcResult.getResponse().getStatus() != 207) {
            final MetaTest.PropfindResult propfindResult = new MetaTest.PropfindResult();
            propfindResult.status = mvcResult.getResponse().getStatus();
            return propfindResult;
        }

        final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = builderFactory.newDocumentBuilder();
        final Document xmlDocument = builder.parse(new ByteArrayInputStream(mvcResult.getResponse().getContentAsString().getBytes()));
        final XPath xpath = XPathFactory.newInstance().newXPath();

        final MetaTest.PropfindResult propfindResult = new MetaTest.PropfindResult();
        propfindResult.status = mvcResult.getResponse().getStatus();
        propfindResult.contentType = xpath.compile("/multistatus/response/propstat/prop/getcontenttype").evaluate(xmlDocument);
        propfindResult.contentTypeCount = ((Number)xpath.compile("count(/multistatus/response/propstat/prop/getcontenttype)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
        return propfindResult;
    }

    private static class PropfindResult {
        int    status;
        String contentType;
        int    contentTypeCount;
    }

    // ContentType is set by meta callback.
    // After that, it must be possible to retrieve the value via different ways.

    @Test
    void test_A1() throws Exception {

        final MetaTest.PropfindResult propfindResult = this.propfind(MetaTestController.MAPPING_A1);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertEquals("m1", propfindResult.contentType);
        Assertions.assertEquals(1, propfindResult.contentTypeCount);

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MetaTestController.MAPPING_A1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().exists("Content-Type"))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "m1"))
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_A1 + " m1"));
    }

    @Test
    void test_A2() throws Exception {

        final MetaTest.PropfindResult propfindResult = this.propfind(MetaTestController.MAPPING_A2);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertEquals("m2", propfindResult.contentType);
        Assertions.assertEquals(1, propfindResult.contentTypeCount);

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MetaTestController.MAPPING_A2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().exists("Content-Type"))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "m2"))
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_A2 + " null"));
    }

    @Test
    void test_A3() throws Exception {

        final MetaTest.PropfindResult propfindResult = this.propfind(MetaTestController.MAPPING_A3);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertEquals("m3", propfindResult.contentType);
        Assertions.assertEquals(1, propfindResult.contentTypeCount);

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MetaTestController.MAPPING_A3))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().exists("Content-Type"))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "m3"))
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_A3 + " m3"));
    }

    @Test
    void test_A4() throws Exception {

        final MetaTest.PropfindResult propfindResult = this.propfind(MetaTestController.MAPPING_A4);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertEquals("", propfindResult.contentType);
        Assertions.assertEquals(0, propfindResult.contentTypeCount);

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MetaTestController.MAPPING_A4))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().doesNotExist("Content-Type"))
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_A4 + " null"));
    }

    @Test
    void test_A5() throws Exception {

        final MetaTest.PropfindResult propfindResult = this.propfind(MetaTestController.MAPPING_A5);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertEquals("", propfindResult.contentType);
        Assertions.assertEquals(0, propfindResult.contentTypeCount);

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MetaTestController.MAPPING_A5))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().doesNotExist("Content-Type"))
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_A5 + " null"));
    }

    @Test
    void test_A6() throws Exception {

        final MetaTest.PropfindResult propfindResult = this.propfind(MetaTestController.MAPPING_A6);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertEquals("text/plain", propfindResult.contentType);
        Assertions.assertEquals(1, propfindResult.contentTypeCount);

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MetaTestController.MAPPING_A6))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().exists("Content-Type"))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "text/plain"))
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_A6 + " text/plain"));
    }

    // Following placeholders for methods parameters are supported: Properties, URI, MetaData
    // Order and number are not fixed.

    @Test
    void test_B1() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MetaTestController.MAPPING_B1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_B1 + " true true true"));
    }

    @Test
    void test_B2() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MetaTestController.MAPPING_B2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_B2 + " true"));
    }

    @Test
    void test_B3() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MetaTestController.MAPPING_B3))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_B3 + " true true true"));
    }

    @Test
    void test_B4() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MetaTestController.MAPPING_B4))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_B4 + " true true true true true true true true true true"));
    }

    @Test
    void test_B5() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MetaTestController.MAPPING_B5))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_B5 + " true"));
    }

    // Function of all attributes is tested.
    // Test by the way of multiple @WebDavMapping annotation.

    @Test
    void test_CX() throws Exception {
        Assertions.assertEquals("404/404/404 /extras/meta/c1.txt 000000 null/null null/null null/null", this.createAttributeFingerprint(MetaTestController.MAPPING_C1, AttributeFingerprintType.Meta));
        Assertions.assertEquals("200/200/207 /extras/meta/c2.txt 300061 TesT/TesT/TesT null/null null/null", this.createAttributeFingerprint(MetaTestController.MAPPING_C2, AttributeFingerprintType.Meta));
        Assertions.assertEquals("200/200/207 /extras/meta/c3.txt 000061 null/null null/null null/null", this.createAttributeFingerprint(MetaTestController.MAPPING_C3, AttributeFingerprintType.Meta));
        Assertions.assertEquals("200/200/207 /extras/meta/c4.txt 000061 null/null null/null null/null", this.createAttributeFingerprint(MetaTestController.MAPPING_C4, AttributeFingerprintType.Meta));
        Assertions.assertEquals("200/200/207 /extras/meta/c5.txt 030061 null/null 0/0/0 null/null", this.createAttributeFingerprint(MetaTestController.MAPPING_C5, AttributeFingerprintType.Meta));
        Assertions.assertEquals("200/200/207 /extras/meta/c6.txt 030061 null/null 1/1/1 null/null", this.createAttributeFingerprint(MetaTestController.MAPPING_C6, AttributeFingerprintType.Meta));
        Assertions.assertEquals("200/200/207 /extras/meta/c7.txt 030061 null/null 100/100/100 null/null", this.createAttributeFingerprint(MetaTestController.MAPPING_C7, AttributeFingerprintType.Meta));
        Assertions.assertEquals("200/200/207 /extras/meta/c8.txt 001061 null/null null/null 2000-01-01T00:00:00Z null/null", this.createAttributeFingerprint(MetaTestController.MAPPING_C8, AttributeFingerprintType.Meta));
        Assertions.assertEquals("200/200/207 /extras/meta/c9.txt 000361 null/null null/null Sat, 01 Jan 2000 00:00:00 GMT/Sat, 01 Jan 2000 00:00:00 GMT/Sat, 01 Jan 2000 00:00:00 GMT", this.createAttributeFingerprint(MetaTestController.MAPPING_C9, AttributeFingerprintType.Meta));
        Assertions.assertEquals("200/200/207 /extras/meta/cA.txt 000061 null/null null/null null/null", this.createAttributeFingerprint(MetaTestController.MAPPING_CA, AttributeFingerprintType.Meta));
        Assertions.assertEquals("200/200/207 /extras/meta/cB.txt 000066 null/null null/null null/null", this.createAttributeFingerprint(MetaTestController.MAPPING_CB, AttributeFingerprintType.Meta));
        Assertions.assertEquals("200/200/207 /extras/meta/cC.txt 000061 null/null null/null null/null", this.createAttributeFingerprint(MetaTestController.MAPPING_CC, AttributeFingerprintType.Meta));
    }

    @Test
    void test_D1() throws Exception {
        this.mockMvc.perform(
                        MockMvcRequestBuilders
                                .get(MetaTestController.MAPPING_D1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_D1 + " false-true"));
    }
}