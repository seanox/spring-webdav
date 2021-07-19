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
package com.seanox.test.http;

import com.seanox.test.AbstractApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

/**
 * Test for HTTP method PROPPATCH.
 * PROPPATCH is not officially supported/allowed.
 * But for MS Office it is answered with 207 PROPFIND.
 *     Expectation:
 * If an Entry found in the SiteMap, the requests are responded with 207 PROPFIND.
 *
 * ProppatchTest 1.0.0 20210719
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210719
 */
public class ProppatchTest extends AbstractApiTest {

    @Test
    void testRequest() throws Exception {
        final String method = this.getClass().getSimpleName().replaceAll("(?<=[a-z])[A-Z].*$", "").toUpperCase();
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(FILE_URI))
                .andExpect(MockMvcResultMatchers.status().isOk());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request(method, URI.create(FILE_URI)))
                .andExpect(MockMvcResultMatchers.status().isMultiStatus());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request(method, URI.create(FILE_REDIRECT_URI)))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl(FILE_URI));
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request(method, URI.create(FOLDER_URI)))
                .andExpect(MockMvcResultMatchers.status().isMultiStatus());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request(method, URI.create(FOLDER_REDIRECT_URI)))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl(FOLDER_URI));
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request(method, URI.create(FOLDER_NOT_EXISTS_URI)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}