package com.seanox.test.http;

import com.seanox.api.Application;
import com.seanox.apidav.ApiDavFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletException;

/**
 * Test for HTTP method DELETE.
 * DELETE is not supported/allowed.
 *     Expectation:
 * If an Entry found in the SiteMap, the requests are responded with FORBIDDEN.
 *
 * DeleteTest 1.0.0 20210704
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210704
 */
@SpringBootTest(classes=Application.class)
public class DeleteTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup()
            throws ServletException {
        final MockFilterConfig mockFilterConfig = new MockFilterConfig(webApplicationContext.getServletContext());
        final ApiDavFilter apiDavFilter = new ApiDavFilter();
        apiDavFilter.init(mockFilterConfig);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .addFilters(apiDavFilter)
                .build();
    }

    @Test
    void testRequest()
            throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head("/personal/budget.xlsx"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/personal/budget.xlsx"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/personal/budget.xlsx/"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/personal/budget.xlsx"));
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/personal/"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/personal"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/personal/"));
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/personal/ooo"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}