package com.seanox.test.api;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
public class DeleteTest extends AbstractApiTest {

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