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

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

/**
 * Test the sequence for OPTIONS for files and folders.
 *
 * OptionsTest 1.0.0 20210705
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210705
 */
public class OptionsTest extends AbstractApiTest {

    @Test
    void test_folder()
            throws Exception {

        // LOCK and UNLOCK for readonly files/folders are responded with status FORBIDDEN.
        // OPTIONS do not contain the LOCK and UNLOCK methods for readonly files/folders.

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(ROOT_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andExpect(MockMvcResultMatchers.content().string(""));

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(FOLDER_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andExpect(MockMvcResultMatchers.content().string(""));

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(FILE_PARENT_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andExpect(MockMvcResultMatchers.content().string(""));
    }

    @Test
    void test_folder_not_exists()
            throws Exception {

        // LOCK and UNLOCK for non-existing files/folders are responded with the status NOT FOUND.
        // OPTIONS do not contain the LOCK and UNLOCK methods for readonly files/folders.

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(ROOT_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"));

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(FOLDER_NOT_EXISTS_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"));
    }

    @Test
    void test_file_readonly()
            throws Exception {

        // LOCK and UNLOCK for readonly files/folders are responded with status FORBIDDEN.
        // OPTIONS do not contain the LOCK and UNLOCK methods for readonly files/folders.

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(ROOT_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"));

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(FILE_READONLY_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"));
    }

    @Test
    void test_file()
            throws Exception {

        // LOCK and UNLOCK for writable files must work.
        // OPTIONS contain the LOCK and UNLOCK methods for writable files.

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(ROOT_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"));

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(FILE_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND, LOCK, PUT, UNLOCK"));
    }

    @Test
    void test_file_not_exists()
            throws Exception {

        // LOCK and UNLOCK for non-existing files/folders are responded with the status NOT FOUND.
        // OPTIONS do not contain the LOCK and UNLOCK methods for readonly files/folders.

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(ROOT_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"));

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(FILE_NOT_EXISTS_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"));
    }
}