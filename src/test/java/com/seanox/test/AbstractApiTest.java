/**
 * LIZENZBEDINGUNGEN - Seanox Software Solutions ist ein Open-Source-Projekt,
 * im Folgenden Seanox Software Solutions oder kurz Seanox genannt.
 * Diese Software unterliegt der Version 2 der Apache License.
 *
 * WebDAV mapping for Spring Boot
 * Copyright (C) 2024 Seanox Software Solutions
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
package com.seanox.test;

import com.seanox.api.Application;
import com.seanox.webdav.WebDavFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.w3c.dom.Document;

import jakarta.servlet.ServletException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * General implementation for the execution of API tests.
 *
 * @author  Seanox Software Solutions
 * @version 1.3.0 20240103
 */
@SpringBootTest(classes=Application.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractApiTest extends AbstractTest {

    protected static final String ROOT_URI = "/";
    protected static final String FILE_URI = "/financial/costs.xlsx";
    protected static final String FILE_READONLY_URI = "/financial/reports/sales.pptx";
    protected static final String FILE_NOT_EXISTS_URI = "/financial/reports/nothing.pptx";
    protected static final String FOLDER_URI = "/financial/";
    protected static final String FOLDER_NOT_EXISTS_URI = "/financial/nothing/";

    protected static final String FILE_REDIRECT_URI = "/financial/costs.xlsx/";
    protected static final String FILE_PARENT_URI = "/financial/";
    protected static final String FOLDER_REDIRECT_URI = "/financial";

    protected static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    protected static final String CONTENT_TYPE_PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";

    protected static final String TEMPLATES_COSTS_XLSX = "/templates/costs.xlsx";
    protected static final String TEMPLATE_EMPTY_XLSX = "/templates/empty.xlsx";
    protected static final String TEMPLATE_STATISTIC_PPTX = "/templates/statistic.pptx";
    protected static final String TEMPLATE_SALES_PPTX = "/templates/sales.pptx";

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @BeforeEach
    protected void startApi()
            throws ServletException {
        if (Objects.nonNull(this.mockMvc))
            return;
        final MockFilterConfig mockFilterConfig = new MockFilterConfig(this.webApplicationContext.getServletContext());
        final WebDavFilter webDavFilter = new WebDavFilter();
        webDavFilter.init(mockFilterConfig);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .addFilters(webDavFilter)
                .build();
    }

    protected static byte[] readTemplate(final String template)
            throws IOException {
        return AbstractApiTest.class.getResourceAsStream(template).readAllBytes();
    }

    protected String createAttributeFingerprint(final String uri)
            throws Exception {
        return this.createAttributeFingerprint(uri, null);
    }

    protected String createAttributeFingerprint(final String uri, final AttributeFingerprintType type)
            throws Exception {

        final AttributeFingerprint attributeFingerprint = new AttributeFingerprint();
        attributeFingerprint.uri = uri;

        final MvcResult mvcResultHead = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(uri))
                .andReturn();
        attributeFingerprint.status = String.valueOf(mvcResultHead.getResponse().getStatus());
        if (mvcResultHead.getResponse().containsHeader("Content-Type"))
            attributeFingerprint.contentTypeCount++;
        if (mvcResultHead.getResponse().containsHeader("Content-Length"))
            attributeFingerprint.contentLengthCount++;
        if (mvcResultHead.getResponse().containsHeader("Last-Modified"))
            attributeFingerprint.lastModifiedCount++;

        final MvcResult mvcResultGet = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(uri))
                .andReturn();
        attributeFingerprint.status += "/" + mvcResultGet.getResponse().getStatus();
        if (mvcResultGet.getResponse().containsHeader("Content-Type"))
            attributeFingerprint.contentTypeCount++;
        if (mvcResultGet.getResponse().containsHeader("Content-Length"))
            attributeFingerprint.contentLengthCount++;
        if (mvcResultGet.getResponse().containsHeader("Last-Modified"))
            attributeFingerprint.lastModifiedCount++;

        final MvcResult mvcResultPropfind = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", URI.create(uri)))
                .andReturn();
        attributeFingerprint.status += "/" + mvcResultPropfind.getResponse().getStatus();

        String propfindContentType = "";
        String propfindContentLength = "";
        String propfindCreationDate = "";
        String propfindLastModified = "";

        if (mvcResultPropfind.getResponse().getStatus() == 207) {
            final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = builderFactory.newDocumentBuilder();
            final Document xmlDocument = builder.parse(new ByteArrayInputStream(mvcResultPropfind.getResponse().getContentAsString(StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8)));
            final XPath xpath = XPathFactory.newInstance().newXPath();

            attributeFingerprint.contentTypeCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/getcontenttype)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
            attributeFingerprint.contentLengthCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/getcontentlength)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
            attributeFingerprint.creationDateCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/creationdate)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
            attributeFingerprint.lastModifiedCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/getlastmodified)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
            attributeFingerprint.isReadOnlyCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/isreadonly)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
            attributeFingerprint.isReadOnlyCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/isreadonly[text()='true'])").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue() *5;
            attributeFingerprint.isHiddenCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/ishidden)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
            attributeFingerprint.isHiddenCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/ishidden[text()='true'])").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue() *5;

            propfindContentType = xpath.compile("/multistatus/response/propstat/prop/getcontenttype").evaluate(xmlDocument);
            propfindContentLength = xpath.compile("/multistatus/response/propstat/prop/getcontentlength").evaluate(xmlDocument);
            propfindCreationDate = xpath.compile("/multistatus/response/propstat/prop/creationdate").evaluate(xmlDocument);
            propfindLastModified = xpath.compile("/multistatus/response/propstat/prop/getlastmodified").evaluate(xmlDocument);
        }

        String fingerprint = attributeFingerprint.toString();
        if (AttributeFingerprintType.Meta.equals(type)
                || AttributeFingerprintType.ContentType.equals(type)) {
            fingerprint += " " + mvcResultHead.getResponse().getHeader("Content-Type");
            fingerprint += "/" + mvcResultGet.getResponse().getHeader("Content-Type");
            if (!propfindContentType.isBlank())
                fingerprint += "/" + propfindContentType;
        }
        if (AttributeFingerprintType.Meta.equals(type)
                || AttributeFingerprintType.ContentLength.equals(type)) {
            fingerprint += " " + mvcResultHead.getResponse().getHeader("Content-Length");
            fingerprint += "/" + mvcResultGet.getResponse().getHeader("Content-Length");
            if (!propfindContentLength.isBlank())
                fingerprint += "/" + propfindContentLength;
        }
        if (AttributeFingerprintType.Meta.equals(type)
                || AttributeFingerprintType.CreationDate.equals(type)) {
            if (!propfindCreationDate.isBlank())
                fingerprint += " " + propfindCreationDate;
        }
        if (AttributeFingerprintType.Meta.equals(type)
                || AttributeFingerprintType.LastModified.equals(type)) {
            fingerprint += " " + mvcResultHead.getResponse().getHeader("Last-Modified");
            fingerprint += "/" + mvcResultGet.getResponse().getHeader("Last-Modified");
            if (!propfindLastModified.isBlank())
                fingerprint += "/" + propfindLastModified;
        }

        return fingerprint;
    }

    private static class AttributeFingerprint {

        private String uri;
        private String status;
        private int contentTypeCount;
        private int contentLengthCount;
        private int creationDateCount;
        private int lastModifiedCount;
        private int isReadOnlyCount;
        private int isHiddenCount;

        @Override
        public String toString() {
            return this.status
                    + " " + this.uri + " "
                    + this.contentTypeCount
                    + this.contentLengthCount
                    + this.creationDateCount
                    + this.lastModifiedCount
                    + this.isReadOnlyCount
                    + this.isHiddenCount;
        }
    }

    public enum AttributeFingerprintType {
        Meta,
        ContentType,
        ContentLength,
        CreationDate,
        LastModified
    }
}