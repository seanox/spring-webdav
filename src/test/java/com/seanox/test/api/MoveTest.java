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
package com.seanox.test.api;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

/**
 * Test for HTTP method MOVE.
 * MOVE is not supported/allowed.
 *     Expectation:
 * If an Entry found in the SiteMap, the requests are responded with FORBIDDEN.
 *
 * MoveTest 1.0.0 20210704
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210704
 */
public class MoveTest extends AbstractApiTest {

    @Test
    void testRequest()
            throws Exception {
        final String method = this.getClass().getSimpleName().replaceAll("(?<=[a-z])[A-Z].*$", "").toUpperCase();
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head("/personal/budget.xlsx"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request(method, new URI("/personal/budget.xlsx")))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request(method, new URI("/personal/budget.xlsx/")))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/personal/budget.xlsx"));
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request(method, new URI("/personal/")))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request(method, new URI("/personal")))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/personal/"));
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request(method, new URI("/personal/ooo")))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}