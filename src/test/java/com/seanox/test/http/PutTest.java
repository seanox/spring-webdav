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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test the sequence for PUT file and folders.<br>
 * <br>
 * PutTest 1.0.0 20210706<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210706
 */
public class PutTest extends AbstractApiTest {

    @Test
    void test_folder() throws Exception {

        // PUT for folders are responded with status FORBIDDEN.
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(FOLDER_URI)
                        .contentType(CONTENT_TYPE_XLSX)
                        .content(AbstractApiTest.readTemplate(AbstractApiTest.TEMPLATES_COSTS_XLSX)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void test_folder_not_exists() throws Exception {

        // PUT for folders are responded with status FORBIDDEN.
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(FOLDER_NOT_EXISTS_URI)
                        .contentType(CONTENT_TYPE_XLSX)
                        .content(AbstractApiTest.readTemplate(AbstractApiTest.TEMPLATES_COSTS_XLSX)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void test_file_readonly() throws Exception {

        // PUT readonly files are responded with status FORBIDDEN.
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(FILE_READONLY_URI)
                        .contentType(CONTENT_TYPE_PPTX)
                        .content(AbstractApiTest.readTemplate(AbstractApiTest.TEMPLATE_STATISTIC_PPTX)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void test_file() throws Exception {

        final MvcResult headResult1 = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(FILE_URI))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().exists("Last-Modified"))
                .andExpect(MockMvcResultMatchers.header().exists("Content-Length"))
                .andReturn();

        Thread.sleep(1500);

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(FILE_URI)
                        .contentType(CONTENT_TYPE_XLSX)
                        .content(AbstractApiTest.readTemplate(AbstractApiTest.TEMPLATE_EMPTY_XLSX)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        final MvcResult headResult2 = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(FILE_URI))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().exists("Last-Modified"))
                .andExpect(MockMvcResultMatchers.header().exists("Content-Length"))
                .andReturn();

        Assertions.assertNotEquals(
                headResult1.getResponse().getHeader("Last-Modified"),
                headResult2.getResponse().getHeader("Last-Modified"));
        Assertions.assertEquals(
                AbstractApiTest.readTemplate(TEMPLATES_COSTS_XLSX).length,
                headResult1.getResponse().getContentLength());
        Assertions.assertNotEquals(
                headResult1.getResponse().getHeader("Content-Length"),
                headResult2.getResponse().getHeader("Content-Length"));
        Assertions.assertEquals(
                AbstractApiTest.readTemplate(TEMPLATE_EMPTY_XLSX).length,
                headResult2.getResponse().getContentLength());
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
                        .put(FILE_URI)
                        .header("If-None-Match", etag2)
                        .contentType(CONTENT_TYPE_XLSX)
                        .content(AbstractApiTest.readTemplate(AbstractApiTest.TEMPLATES_COSTS_XLSX)))
                .andExpect(MockMvcResultMatchers.status().isPreconditionFailed());

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(FILE_URI)
                        .header("If-None-Match", etag1)
                        .contentType(CONTENT_TYPE_XLSX)
                        .content(AbstractApiTest.readTemplate(AbstractApiTest.TEMPLATES_COSTS_XLSX)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        final MvcResult headResult3 = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(FILE_URI))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().exists("Etag"))
                .andReturn();
        final String etag3 = headResult3.getResponse().getHeader("Etag");

        // If-Match: The ETag sent by the server must match, otherwise 304/412

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(FILE_URI)
                        .header("If-Match", etag1)
                        .contentType(CONTENT_TYPE_XLSX)
                        .content(AbstractApiTest.readTemplate(AbstractApiTest.TEMPLATES_COSTS_XLSX)))
                .andExpect(MockMvcResultMatchers.status().isPreconditionFailed());

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(FILE_URI)
                        .header("If-Match", etag3)
                        .contentType(CONTENT_TYPE_XLSX)
                        .content(AbstractApiTest.readTemplate(AbstractApiTest.TEMPLATES_COSTS_XLSX)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void test_file_not_exists() throws Exception {

        // PUT for for non-existing files are responded with status FORBIDDEN.
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(FOLDER_NOT_EXISTS_URI)
                        .contentType(CONTENT_TYPE_XLSX)
                        .content(AbstractApiTest.readTemplate(AbstractApiTest.TEMPLATES_COSTS_XLSX)))
                .andExpect(MockMvcResultMatchers.status().isForbidden())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
}