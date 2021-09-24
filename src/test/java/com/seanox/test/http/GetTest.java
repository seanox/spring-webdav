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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test the sequence for GET for files and folders.<br>
 * <br>
 * GetTest 1.0.0 20210706<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210706
 */
public class GetTest extends AbstractApiTest {

    @Test
    void test_folder() throws Exception {

        // There are no file lists, so access to folders is not possible.
        // Unlike FORBIDDEN, 404 is used for things that are not allowed,
        // so that the folder structure cannot be explored without permission.

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(FOLDER_URI))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void test_folder_not_exists() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(FOLDER_NOT_EXISTS_URI))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void test_file_readonly() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(FILE_READONLY_URI))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void test_file() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(FILE_URI))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void test_file_etag() throws Exception {

        final MvcResult headResult1 = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(FILE_URI))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().exists("Etag"))
                .andReturn();
        final String etag1 = headResult1.getResponse().getHeader("Etag");

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(FILE_URI)
                        .contentType(CONTENT_TYPE_XLSX)
                        .content(AbstractApiTest.readTemplate(AbstractApiTest.TEMPLATES_COSTS_XLSX)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        final MvcResult headResult2 = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(FILE_URI))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().exists("Etag"))
                .andReturn();
        final String etag2 = headResult2.getResponse().getHeader("Etag");

        // If-None-Match: The ETag sent by the server must not match, otherwise 304/412

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(FILE_URI)
                        .header("If-None-Match", etag2))
                .andExpect(MockMvcResultMatchers.status().isNotModified());

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(FILE_URI)
                        .header("If-None-Match", etag1))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // If-Match: The ETag sent by the server must match, otherwise 304/412

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(FILE_URI)
                        .header("If-Match", etag2))
                .andExpect(MockMvcResultMatchers.status().isOk());

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(FILE_URI)
                        .header("If-Match", etag1))
                .andExpect(MockMvcResultMatchers.status().isPreconditionFailed());
    }

    @Test
    void test_file_not_exists() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(FILE_NOT_EXISTS_URI))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void test_file_redirect() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(FILE_REDIRECT_URI))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl(FILE_URI));
    }
}