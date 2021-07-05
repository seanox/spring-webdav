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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * Test the sequence for options for files and folders.
 *
 * OptionsTest 1.0.0 20210704
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210704
 */
public class OptionsTest extends AbstractApiTest {

    private final static String ROOT_URI = "/";
    private final static String FILE_URI = "/personal/budget.xlsx";
    private final static String FILE_READONLY_URI = "/personal/reports/sales.pptx";
    private final static String FILE_NOT_EXISTS_URI = "/personal/reports/nothing.pptx";
    private final static String FOLDER_URI = "/personal/";
    private final static String FOLDER_NOT_EXISTS_URI = "/personal/nothing/";

    @Test
    void test_folder()
            throws Exception {

        // LOCK and UNLOCK for readonly files/folders are responded with status Forbidden.
        // OPTIONS do not contain the LOCK and UNLOCK methods for readonly files/folders.

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(ROOT_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andReturn();

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(FOLDER_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andReturn();
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
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andReturn();

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(FOLDER_NOT_EXISTS_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andReturn();
    }

    @Test
    void test_file_readonly()
            throws Exception {

        // LOCK and UNLOCK for readonly files/folders are responded with status Forbidden.
        // OPTIONS do not contain the LOCK and UNLOCK methods for readonly files/folders.

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(ROOT_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andReturn();

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(FILE_READONLY_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andReturn();
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
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andReturn();

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(FILE_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND, LOCK, PUT, UNLOCK"))
                .andReturn();
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
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andReturn();

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(FILE_NOT_EXISTS_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andReturn();
    }
}