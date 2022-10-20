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
 * Test the sequence for OPTIONS for files and folders.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210710
 */
public class OptionsTest extends AbstractApiTest {

    @Test
    void test_folder() throws Exception {

        // LOCK and UNLOCK for readonly files/folders are responded with status FORBIDDEN.
        // OPTIONS do not contain the LOCK and UNLOCK methods for readonly files/folders.

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", URI.create(ROOT_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andExpect(MockMvcResultMatchers.content().string(""));

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", URI.create(FOLDER_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andExpect(MockMvcResultMatchers.content().string(""));

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", URI.create(FILE_PARENT_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void test_folder_not_exists() throws Exception {

        // LOCK and UNLOCK for non-existing files/folders are responded with the status NOT FOUND.
        // OPTIONS do not contain the LOCK and UNLOCK methods for readonly files/folders.

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", URI.create(ROOT_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"));

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", URI.create(FOLDER_NOT_EXISTS_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"));
    }

    @Test
    void test_file_readonly() throws Exception {

        // LOCK and UNLOCK for readonly files/folders are responded with status FORBIDDEN.
        // OPTIONS do not contain the LOCK and UNLOCK methods for readonly files/folders.

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", URI.create(ROOT_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"));

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", URI.create(FILE_READONLY_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"));
    }

    @Test
    void test_file() throws Exception {

        // LOCK and UNLOCK for writable files must work.
        // OPTIONS contain the LOCK and UNLOCK methods for writable files.

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", URI.create(ROOT_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"));

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", URI.create(FILE_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND, LOCK, PUT, UNLOCK"));
    }

    @Test
    void test_file_not_exists() throws Exception {

        // LOCK and UNLOCK for non-existing files/folders are responded with the status NOT FOUND.
        // OPTIONS do not contain the LOCK and UNLOCK methods for readonly files/folders.

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", URI.create(ROOT_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"));

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", URI.create(FILE_NOT_EXISTS_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"));
    }
}