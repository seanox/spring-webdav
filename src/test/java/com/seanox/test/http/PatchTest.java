/**
 * LIZENZBEDINGUNGEN - Seanox Software Solutions ist ein Open-Source-Projekt,
 * im Folgenden Seanox Software Solutions oder kurz Seanox genannt.
 * Diese Software unterliegt der Version 2 der Apache License.
 *
 * WebDAV mapping for Spring Boot
 * Copyright (C) 2021 Seanox Software Solutions
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
package com.seanox.test.http;

import com.seanox.test.AbstractApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

/**
 * Test for HTTP methods PATCH.<br>
 * PATCH is not supported, it is an example for other unsupported methods.<br>
 * <br>
 *     <dir>Expectation:</dir>
 * If an Entry found in the SiteMap, the requests are responded with METHOD NOT ALLOWED
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210710
 */
public class PatchTest extends AbstractApiTest {

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
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request(method, URI.create(FILE_REDIRECT_URI)))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request(method, URI.create(FOLDER_URI)))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request(method, URI.create(FOLDER_REDIRECT_URI)))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request(method, URI.create(FOLDER_NOT_EXISTS_URI)))
                .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
    }
}