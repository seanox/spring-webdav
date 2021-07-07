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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * General implementation for the execution of API tests.
 *
 * AbstractApiTest 1.0.0 20210705
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210705
 */
@SpringBootTest(classes= Application.class)
public abstract class AbstractApiTest extends AbstractTest {

    public final static String ROOT_URI = "/";
    public final static String FILE_URI = "/personal/budget.xlsx";
    public final static String FILE_READONLY_URI = "/personal/reports/sales.pptx";
    public final static String FILE_NOT_EXISTS_URI = "/personal/reports/nothing.pptx";
    public final static String FOLDER_URI = "/personal/";
    public final static String FOLDER_NOT_EXISTS_URI = "/personal/nothing/";

    public final static String FILE_REDIRECT_URI = "/personal/budget.xlsx/";
    public final static String FILE_PARENT_URI = "/personal/";
    public final static String FOLDER_REDIRECT_URI = "/personal";

    public final static String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public final static String CONTENT_TYPE_PPTX = "application/vnd.openxmlformats-officedocument.presentationml.presentation";

    public final static String TEMPLATE_BUDGET_XLSX = "/templates/budget.xlsx";
    public final static String TEMPLATE_EMPTY_XLSX = "/templates/empty.xlsx";
    public final static String TEMPLATE_STATISTIC_PPTX = "/templates/statistic.pptx";
    public final static String TEMPLATE_SALES_PPTX = "/templates/sales.pptx";

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @BeforeEach
    protected void startApi()
            throws ServletException {
        final MockFilterConfig mockFilterConfig = new MockFilterConfig(webApplicationContext.getServletContext());
        final ApiDavFilter apiDavFilter = new ApiDavFilter();
        apiDavFilter.init(mockFilterConfig);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(apiDavFilter)
                .build();
    }

    protected static byte[] readTemplate(String template)
            throws IOException {
        return AbstractApiTest.class.getResourceAsStream(template).readAllBytes();
    }
}