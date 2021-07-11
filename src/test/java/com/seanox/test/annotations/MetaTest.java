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

import com.seanox.api.extras.MetaTestController;
import com.seanox.apidav.ApiDavMetaMapping;
import com.seanox.test.AbstractApiTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.w3c.dom.Document;

import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.net.URI;

/**
 * Test of the annotation {@link ApiDavMetaMapping} functions.
 *
 * MetaTest 1.0.0 20210711
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210711
 */
public class MetaTest extends AbstractApiTest {

    MetaTest.PropfindResult propfind(final String uri)
            throws Exception {

        final MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", URI.create(uri))
                        .header("Depth", "1"))
                .andReturn();

        if (mvcResult.getResponse().getStatus() != 200
                && mvcResult.getResponse().getStatus() != 207) {
            final MetaTest.PropfindResult propfindResult = new MetaTest.PropfindResult();
            propfindResult.uri = uri;
            propfindResult.status = mvcResult.getResponse().getStatus();
            return propfindResult;
        }

        final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = builderFactory.newDocumentBuilder();
        final Document xmlDocument = builder.parse(new ByteArrayInputStream(mvcResult.getResponse().getContentAsString().getBytes()));
        final XPath xpath = XPathFactory.newInstance().newXPath();

        final MetaTest.PropfindResult propfindResult = new MetaTest.PropfindResult();
        propfindResult.uri = xpath.compile("/multistatus/response/href").evaluate(xmlDocument);
        propfindResult.status = mvcResult.getResponse().getStatus();
        propfindResult.contentType = xpath.compile("/multistatus/response/propstat/prop/getcontenttype").evaluate(xmlDocument);
        propfindResult.contentTypeCount = ((Number)xpath.compile("count(/multistatus/response/propstat/prop/getcontenttype)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
        return propfindResult;
    }

    private static class PropfindResult {
        String uri;
        int    status;
        String contentType;
        int    contentTypeCount;
    }

    @Test
    void testMeta_A1()
            throws Exception {

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
    void testMeta_A2()
            throws Exception {

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
    void testMeta_A3()
            throws Exception {

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
    void testMeta_A4()
            throws Exception {

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
    void testMeta_A5()
            throws Exception {

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
    void testMeta_A6()
            throws Exception {

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
    void testMeta_B1()
            throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MetaTestController.MAPPING_B1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_B1 + " true true true"));
    }

    @Test
    void testMeta_B2()
            throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MetaTestController.MAPPING_B2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_B2 + " true"));
    }

    @Test
    void testMeta_B3()
            throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MetaTestController.MAPPING_B3))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_B3 + " true true true"));
    }

    @Test
    void testMeta_B4()
            throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MetaTestController.MAPPING_B4))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_B4 + " true true true true true true true true true true"));
    }

    @Test
    void testMeta_B5()
            throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(MetaTestController.MAPPING_B5))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(MetaTestController.MAPPING_B5 + " true"));
    }

    String createMetaFingeprint(String uri)
            throws Exception {

        final MvcResult mvcResultGet = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(uri))
                .andReturn();
        final MetaFingeprint metaFingeprint = new MetaFingeprint();
        metaFingeprint.uri = uri;
        metaFingeprint.statusGet = mvcResultGet.getResponse().getStatus();
        if (mvcResultGet.getResponse().getStatus() != HttpServletResponse.SC_OK)
            return metaFingeprint.toString();
        if (mvcResultGet.getResponse().containsHeader("Content-Type"))
            metaFingeprint.contentTypeCount++;
        if (mvcResultGet.getResponse().containsHeader("Content-Length"))
            metaFingeprint.contentLengthCount++;
        if (mvcResultGet.getResponse().containsHeader("Last-Modified"))
            metaFingeprint.lastModifiedCount++;

        final MvcResult mvcResultPropfind = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", URI.create(uri)))
                .andReturn();

        final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = builderFactory.newDocumentBuilder();
        final Document xmlDocument = builder.parse(new ByteArrayInputStream(mvcResultPropfind.getResponse().getContentAsString().getBytes()));
        final XPath xpath = XPathFactory.newInstance().newXPath();

        metaFingeprint.statusPropfind = mvcResultPropfind.getResponse().getStatus();
        metaFingeprint.contentTypeCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/getcontenttype)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
        metaFingeprint.contentTypeCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/getcontenttype[text()='TesT'])").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue() *5;
        metaFingeprint.contentLengthCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/getcontentlength)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
        metaFingeprint.creationDateCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/creationdate)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
        metaFingeprint.lastModifiedCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/getlastmodified)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
        metaFingeprint.isReadOnlyCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/isreadonly)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
        metaFingeprint.isReadOnlyCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/isreadonly[text()='true'])").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue() *5;
        metaFingeprint.isHiddenCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/ishidden)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
        metaFingeprint.isHiddenCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/ishidden[text()='true'])").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue() *5;

        String fingeprint = metaFingeprint.toString() + " ";
        fingeprint += xpath.compile("/multistatus/response/propstat/prop/getcontentlength").evaluate(xmlDocument);
        fingeprint += xpath.compile("/multistatus/response/propstat/prop/creationdate").evaluate(xmlDocument);
        fingeprint += xpath.compile("/multistatus/response/propstat/prop/getlastmodified").evaluate(xmlDocument);

        return fingeprint.trim();
    }

    static class MetaFingeprint {

        private String uri;
        private int statusGet;
        private int statusPropfind;
        private int contentTypeCount;
        private int contentLengthCount;
        private int creationDateCount;
        private int lastModifiedCount;
        private int isReadOnlyCount;
        private int isHiddenCount;

        @Override
        public String toString() {
            return statusGet + "/" + statusPropfind + ":" + uri + " "
                    + contentTypeCount
                    + contentLengthCount
                    + creationDateCount
                    + lastModifiedCount
                    + isReadOnlyCount
                    + isHiddenCount;
        }
    }

    @Test
    void testMeta_C1()
            throws Exception {
        Assertions.assertEquals("404/0:/extras/meta/c1.txt 000000", this.createMetaFingeprint(MetaTestController.MAPPING_C1));
    }

    @Test
    void testMeta_C2()
            throws Exception {
        Assertions.assertEquals("200/207:/extras/meta/c2.txt 700061", this.createMetaFingeprint(MetaTestController.MAPPING_C2));
    }

    @Test
    void testMeta_C3()
            throws Exception {
        Assertions.assertEquals("200/207:/extras/meta/c3.txt 000061", this.createMetaFingeprint(MetaTestController.MAPPING_C3));
    }

    @Test
    void testMeta_C4()
            throws Exception {
        Assertions.assertEquals("200/207:/extras/meta/c4.txt 000061", this.createMetaFingeprint(MetaTestController.MAPPING_C4));
    }

    @Test
    void testMeta_C5()
            throws Exception {
        Assertions.assertEquals("200/207:/extras/meta/c5.txt 020061 0", this.createMetaFingeprint(MetaTestController.MAPPING_C5));
    }

    @Test
    void testMeta_C6()
            throws Exception {
        Assertions.assertEquals("200/207:/extras/meta/c6.txt 020061 1", this.createMetaFingeprint(MetaTestController.MAPPING_C6));
    }

    @Test
    void testMeta_C7()
            throws Exception {
        Assertions.assertEquals("200/207:/extras/meta/c7.txt 020061 100", this.createMetaFingeprint(MetaTestController.MAPPING_C7));
    }

    @Test
    void testMeta_C8()
            throws Exception {
        Assertions.assertEquals("200/207:/extras/meta/c8.txt 001061 2000-01-01T00:00:00Z", this.createMetaFingeprint(MetaTestController.MAPPING_C8));
    }

    @Test
    void testMeta_C9()
            throws Exception {
        Assertions.assertEquals("200/207:/extras/meta/c9.txt 000261 Sat, 01 Jan 2000 00:00:00 GMT", this.createMetaFingeprint(MetaTestController.MAPPING_C9));
    }

    @Test
    void testMeta_CA()
            throws Exception {
        Assertions.assertEquals("200/207:/extras/meta/cA.txt 000061", this.createMetaFingeprint(MetaTestController.MAPPING_CA));
    }

    @Test
    void testMeta_CB()
            throws Exception {
        Assertions.assertEquals("200/207:/extras/meta/cB.txt 000066", this.createMetaFingeprint(MetaTestController.MAPPING_CB));
    }

    @Test
    void testMeta_CC()
            throws Exception {
        Assertions.assertEquals("200/207:/extras/meta/cC.txt 000061", this.createMetaFingeprint(MetaTestController.MAPPING_CC));
    }
}