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
package com.seanox.test;

import com.seanox.api.Application;
import com.seanox.apidav.ApiDavFilter;
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

import javax.servlet.ServletException;
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
 * AbstractApiTest 1.0.0 20210717
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210717
 */
@SpringBootTest(classes= Application.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractApiTest extends AbstractTest {

    protected static final String ROOT_URI = "/";
    protected static final String FILE_URI = "/personal/budget.xlsx";
    protected static final String FILE_READONLY_URI = "/personal/reports/sales.pptx";
    protected static final String FILE_NOT_EXISTS_URI = "/personal/reports/nothing.pptx";
    protected static final String FOLDER_URI = "/personal/";
    protected static final String FOLDER_NOT_EXISTS_URI = "/personal/nothing/";

    protected static final String FILE_REDIRECT_URI = "/personal/budget.xlsx/";
    protected static final String FILE_PARENT_URI = "/personal/";
    protected static final String FOLDER_REDIRECT_URI = "/personal";

    protected static final String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    protected static final String CONTENT_TYPE_PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";

    protected static final String TEMPLATE_BUDGET_XLSX = "/templates/budget.xlsx";
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
        final ApiDavFilter apiDavFilter = new ApiDavFilter();
        apiDavFilter.init(mockFilterConfig);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .addFilters(apiDavFilter)
                .build();
    }

    protected static byte[] readTemplate(String template)
            throws IOException {
        return AbstractApiTest.class.getResourceAsStream(template).readAllBytes();
    }

    protected String createAttributeFingeprint(final String uri)
            throws Exception {
        return this.createAttributeFingeprint(uri, null);
    }

    protected String createAttributeFingeprint(final String uri, AttributeFingeprintType type)
            throws Exception {

        final AttributeFingeprint attributeFingeprint = new AttributeFingeprint();
        attributeFingeprint.uri = uri;

        final MvcResult mvcResultHead = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(uri))
                .andReturn();
        attributeFingeprint.status = String.valueOf(mvcResultHead.getResponse().getStatus());
        if (mvcResultHead.getResponse().containsHeader("Content-Type"))
            attributeFingeprint.contentTypeCount++;
        if (mvcResultHead.getResponse().containsHeader("Content-Length"))
            attributeFingeprint.contentLengthCount++;
        if (mvcResultHead.getResponse().containsHeader("Last-Modified"))
            attributeFingeprint.lastModifiedCount++;

        final MvcResult mvcResultGet = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(uri))
                .andReturn();
        attributeFingeprint.status += "/" + mvcResultGet.getResponse().getStatus();
        if (mvcResultGet.getResponse().containsHeader("Content-Type"))
            attributeFingeprint.contentTypeCount++;
        if (mvcResultGet.getResponse().containsHeader("Content-Length"))
            attributeFingeprint.contentLengthCount++;
        if (mvcResultGet.getResponse().containsHeader("Last-Modified"))
            attributeFingeprint.lastModifiedCount++;

        final MvcResult mvcResultPropfind = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", URI.create(uri)))
                .andReturn();
        attributeFingeprint.status += "/" + mvcResultPropfind.getResponse().getStatus();

        String propfindContentType = "";
        String propfindContentLength = "";
        String propfindCreationDate = "";
        String propfindLastModified = "";

        if (mvcResultPropfind.getResponse().getStatus() == 207) {
            final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = builderFactory.newDocumentBuilder();
            final Document xmlDocument = builder.parse(new ByteArrayInputStream(mvcResultPropfind.getResponse().getContentAsString(StandardCharsets.UTF_8).getBytes(StandardCharsets.UTF_8)));
            final XPath xpath = XPathFactory.newInstance().newXPath();

            attributeFingeprint.contentTypeCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/getcontenttype)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
            attributeFingeprint.contentLengthCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/getcontentlength)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
            attributeFingeprint.creationDateCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/creationdate)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
            attributeFingeprint.lastModifiedCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/getlastmodified)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
            attributeFingeprint.isReadOnlyCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/isreadonly)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
            attributeFingeprint.isReadOnlyCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/isreadonly[text()='true'])").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue() *5;
            attributeFingeprint.isHiddenCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/ishidden)").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
            attributeFingeprint.isHiddenCount += ((Number)xpath.compile("count(/multistatus/response/propstat/prop/ishidden[text()='true'])").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue() *5;

            propfindContentType = xpath.compile("/multistatus/response/propstat/prop/getcontenttype").evaluate(xmlDocument);
            propfindContentLength = xpath.compile("/multistatus/response/propstat/prop/getcontentlength").evaluate(xmlDocument);
            propfindCreationDate = xpath.compile("/multistatus/response/propstat/prop/creationdate").evaluate(xmlDocument);
            propfindLastModified = xpath.compile("/multistatus/response/propstat/prop/getlastmodified").evaluate(xmlDocument);
        }

        String fingeprint = attributeFingeprint.toString();
        if (AttributeFingeprintType.Meta.equals(type)
                || AttributeFingeprintType.ContentType.equals(type)) {
            fingeprint += " " + mvcResultHead.getResponse().getHeader("Content-Type");
            fingeprint += "/" + mvcResultGet.getResponse().getHeader("Content-Type");
            if (!propfindContentType.isBlank())
                fingeprint += "/" + propfindContentType;
        }
        if (AttributeFingeprintType.Meta.equals(type)
                || AttributeFingeprintType.ContentLength.equals(type)) {
            fingeprint += " " + mvcResultHead.getResponse().getHeader("Content-Length");
            fingeprint += "/" + mvcResultGet.getResponse().getHeader("Content-Length");
            if (!propfindContentLength.isBlank())
                fingeprint += "/" + propfindContentLength;
        }
        if (AttributeFingeprintType.Meta.equals(type)
                || AttributeFingeprintType.CreationDate.equals(type)) {
            if (!propfindCreationDate.isBlank())
                fingeprint += " " + propfindCreationDate;
        }
        if (AttributeFingeprintType.Meta.equals(type)
                || AttributeFingeprintType.LastModified.equals(type)) {
            fingeprint += " " + mvcResultHead.getResponse().getHeader("Last-Modified");
            fingeprint += "/" + mvcResultGet.getResponse().getHeader("Last-Modified");
            if (!propfindLastModified.isBlank())
                fingeprint += "/" + propfindLastModified;
        }

        return fingeprint;
    }

    private static class AttributeFingeprint {

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

    public enum AttributeFingeprintType {
        Meta,
        ContentType,
        ContentLength,
        CreationDate,
        LastModified
    }
}