/**
 * LIZENZBEDINGUNGEN - Seanox Software Solutions ist ein Open-Source-Projekt,
 * im Folgenden Seanox Software Solutions oder kurz Seanox genannt.
 * Diese Software unterliegt der Version 2 der GNU General Public License.
 *
 * WebDAV mapping for Spring Boot
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test the sequence for HEAD for files and folders.<br>
 * <br>
 * HeadTest 1.0.0 20210706<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210706
 */
public class HeadTest extends AbstractApiTest {

    @Test
    void test_folder() throws Exception {

        // There are no file lists, so access to folders is not possible.
        // Unlike FORBIDDEN, 404 is used for things that are not allowed,
        // so that the folder structure cannot be explored without permission.

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(FOLDER_URI))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void test_folder_not_exists() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(FOLDER_NOT_EXISTS_URI))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void test_file_readonly() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(FILE_READONLY_URI))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void test_file() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(FILE_URI))
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
                        .head(FILE_URI)
                        .header("If-None-Match", etag2))
                .andExpect(MockMvcResultMatchers.status().isNotModified());

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(FILE_URI)
                        .header("If-None-Match", etag1))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // If-Match: The ETag sent by the server must match, otherwise 304/412

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(FILE_URI)
                        .header("If-Match", etag2))
                .andExpect(MockMvcResultMatchers.status().isOk());

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(FILE_URI)
                        .header("If-Match", etag1))
                .andExpect(MockMvcResultMatchers.status().isPreconditionFailed());
    }

    @Test
    void test_file_not_exists() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(FILE_NOT_EXISTS_URI))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void test_file_redirect() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .head(FILE_REDIRECT_URI))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl(FILE_URI));
    }
}