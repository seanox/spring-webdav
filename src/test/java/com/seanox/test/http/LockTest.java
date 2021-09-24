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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * Test the sequence for LOCK and UNLOCK file and folders.<br>
 * There are no real locks, but the behavior is supposed to be correct.<br>
 * <br>
 * LockTest 1.0.0 20210710<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210710
 */
public class LockTest extends AbstractApiTest {

    @Test
    void test_folder() throws Exception {

        // LOCK and UNLOCK for readonly files/folders are responded with status FORBIDDEN.
        // OPTIONS do not contain the LOCK and UNLOCK methods for readonly files/folders.

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", URI.create(ROOT_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"));

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", URI.create(FOLDER_URI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"));

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FOLDER_URI))
                        .contentType(MediaType.TEXT_XML)
                        .characterEncoding(StandardCharsets.UTF_8.toString())
                        .content("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                                + "<D:lockinfo xmlns:D=\"DAV:\">"
                                + "<D:lockscope><D:exclusive/></D:lockscope>"
                                + "<D:locktype><D:write/></D:locktype>"
                                + "<D:owner><D:href>WXX\\seanox</D:href></D:owner>"
                                + "</D:lockinfo>"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FOLDER_URI))
                        .contentType(MediaType.TEXT_XML)
                        .characterEncoding(StandardCharsets.UTF_8.toString())
                        .content("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                                + "<D:lockinfo xmlns:D=\"DAV:\">"
                                + "<D:lockscope><D:exclusive/></D:lockscope>"
                                + "<D:locktype><D:write/></D:locktype>"
                                + "<D:owner><D:href>WXX\\seanox</D:href></D:owner>"
                                + "</D:lockinfo>"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        // Even with an apparently valid token, the function remains forbidden.
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FOLDER_URI))
                        .header("If", "<0000-0000-0000-0000>"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        // Even with an apparently valid token, the function remains forbidden.
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("UNLOCK", URI.create(FOLDER_URI))
                        .header("If", "<0000-0000-0000-0000>"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
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

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FOLDER_NOT_EXISTS_URI))
                        .contentType(MediaType.TEXT_XML)
                        .characterEncoding(StandardCharsets.UTF_8.toString())
                        .content("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                                + "<D:lockinfo xmlns:D=\"DAV:\">"
                                + "<D:lockscope><D:exclusive/></D:lockscope>"
                                + "<D:locktype><D:write/></D:locktype>"
                                + "<D:owner><D:href>WXX\\seanox</D:href></D:owner>"
                                + "</D:lockinfo>"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FOLDER_NOT_EXISTS_URI))
                        .contentType(MediaType.TEXT_XML)
                        .characterEncoding(StandardCharsets.UTF_8.toString())
                        .content("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                                + "<D:lockinfo xmlns:D=\"DAV:\">"
                                + "<D:lockscope><D:exclusive/></D:lockscope>"
                                + "<D:locktype><D:write/></D:locktype>"
                                + "<D:owner><D:href>WXX\\seanox</D:href></D:owner>"
                                + "</D:lockinfo>"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        // Even with an apparently valid token, the function remains not found.
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FOLDER_NOT_EXISTS_URI))
                        .header("If", "<0000-0000-0000-0000>"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        // Even with an apparently valid token, the function remains forbidden.
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("UNLOCK", URI.create(FOLDER_NOT_EXISTS_URI))
                        .header("If", "<0000-0000-0000-0000>"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
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

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FILE_READONLY_URI))
                        .contentType(MediaType.TEXT_XML)
                        .characterEncoding(StandardCharsets.UTF_8.toString())
                        .content("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                                + "<D:lockinfo xmlns:D=\"DAV:\">"
                                + "<D:lockscope><D:exclusive/></D:lockscope>"
                                + "<D:locktype><D:write/></D:locktype>"
                                + "<D:owner><D:href>WXX\\seanox</D:href></D:owner>"
                                + "</D:lockinfo>"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FILE_READONLY_URI))
                        .contentType(MediaType.TEXT_XML)
                        .characterEncoding(StandardCharsets.UTF_8.toString())
                        .content("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                                + "<D:lockinfo xmlns:D=\"DAV:\">"
                                + "<D:lockscope><D:exclusive/></D:lockscope>"
                                + "<D:locktype><D:write/></D:locktype>"
                                + "<D:owner><D:href>WXX\\seanox</D:href></D:owner>"
                                + "</D:lockinfo>"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        // Even with an apparently valid token, the function remains forbidden.
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FILE_READONLY_URI))
                        .header("If", "<0000-0000-0000-0000>"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());

        // Even with an apparently valid token, the function remains forbidden.
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("UNLOCK", URI.create(FILE_READONLY_URI))
                        .header("If", "<0000-0000-0000-0000>"))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
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

        // New lock request is responded with a new lock + token,
        // but there is no real lock.
        final MvcResult mvcResult3 = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FILE_URI))
                        .contentType(MediaType.TEXT_XML)
                        .characterEncoding(StandardCharsets.UTF_8.toString())
                        .content("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                                + "<D:lockinfo xmlns:D=\"DAV:\">"
                                + "<D:lockscope><D:exclusive/></D:lockscope>"
                                + "<D:locktype><D:write/></D:locktype>"
                                + "<D:owner><D:href>WXX\\seanox</D:href></D:owner>"
                                + "</D:lockinfo>"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .xpath("/prop/lockdiscovery/activelock/lockroot/href")
                        .string(FILE_URI))
                .andReturn();
        final String lockToken3 = mvcResult3.getResponse().getContentAsString()
                .replaceAll("^.*<d:locktoken><d:href>([A-Za-z0-9-]+).*$", "$1");
        Assertions.assertTrue(lockToken3.matches("^[A-Za-z0-9]+(-[A-Za-z0-9]+)+$"));
        final String lockTokenHeader3 = mvcResult3.getResponse().getHeader("Lock-Token");
        Assertions.assertEquals(lockTokenHeader3, "<" + lockToken3 + ">");

        // New lock request despite existing one is responded with a new lock + token,
        // because there is no real lock.
        final MvcResult mvcResult4 = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FILE_URI))
                        .contentType(MediaType.TEXT_XML)
                        .characterEncoding(StandardCharsets.UTF_8.toString())
                        .content("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                                + "<D:lockinfo xmlns:D=\"DAV:\">"
                                + "<D:lockscope><D:exclusive/></D:lockscope>"
                                + "<D:locktype><D:write/></D:locktype>"
                                + "<D:owner><D:href>WXX\\seanox</D:href></D:owner>"
                                + "</D:lockinfo>"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .xpath("/prop/lockdiscovery/activelock/lockroot/href")
                        .string(FILE_URI))
                .andReturn();
        final String lockToken4 = mvcResult4.getResponse().getContentAsString()
                .replaceAll("^.*<d:locktoken><d:href>([A-Za-z0-9-]+).*$", "$1");
        Assertions.assertTrue(lockToken4.matches("^[A-Za-z0-9]+(-[A-Za-z0-9]+)+$"));
        Assertions.assertNotEquals(lockToken3, lockToken4);

        // Refresh/update of the token with the If header.
        // The response must be the correct lock with the value on the If header.
        // The token is only checked for the correct format.
        final MvcResult mvcResult5 = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FILE_URI))
                        .header("If", "<" + lockToken4 + ">"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .xpath("/prop/lockdiscovery/activelock/lockroot/href")
                        .string(FILE_URI))
                .andReturn();
        final String lockToken5 = mvcResult5.getResponse().getContentAsString()
                .replaceAll("^.*<d:locktoken><d:href>([A-Za-z0-9-]+).*$", "$1");
        Assertions.assertTrue(lockToken4.matches("^[A-Za-z0-9]+(-[A-Za-z0-9]+)+$"));
        Assertions.assertEquals(lockToken4, lockToken5);

        // The file is unlocked, but the token does not matter.
        // Simple response 204 No Content
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("UNLOCK", URI.create(FILE_URI))
                        .header("Lock-Token", "<" + lockToken4 + ">"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.header().doesNotExist("Lock-Token"));
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
                        .request("LOCK", URI.create(FILE_URI))
                        .header("If-None-Match", etag2))
                .andExpect(MockMvcResultMatchers.status().isPreconditionFailed());

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FILE_URI))
                        .header("If-None-Match", etag1))
                .andExpect(MockMvcResultMatchers.status().isOk());

        // If-Match: The ETag sent by the server must match, otherwise 304/412

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FILE_URI))
                        .header("If-Match", etag2))
                .andExpect(MockMvcResultMatchers.status().isOk());

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FILE_URI))
                        .header("If-Match", etag1))
                .andExpect(MockMvcResultMatchers.status().isPreconditionFailed());
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

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FILE_NOT_EXISTS_URI))
                        .contentType(MediaType.TEXT_XML)
                        .characterEncoding(StandardCharsets.UTF_8.toString())
                        .content("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                                + "<D:lockinfo xmlns:D=\"DAV:\">"
                                + "<D:lockscope><D:exclusive/></D:lockscope>"
                                + "<D:locktype><D:write/></D:locktype>"
                                + "<D:owner><D:href>WXX\\seanox</D:href></D:owner>"
                                + "</D:lockinfo>"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FILE_NOT_EXISTS_URI))
                        .contentType(MediaType.TEXT_XML)
                        .characterEncoding(StandardCharsets.UTF_8.toString())
                        .content("<?xml version=\"1.0\" encoding=\"utf-8\"?>"
                                + "<D:lockinfo xmlns:D=\"DAV:\">"
                                + "<D:lockscope><D:exclusive/></D:lockscope>"
                                + "<D:locktype><D:write/></D:locktype>"
                                + "<D:owner><D:href>WXX\\seanox</D:href></D:owner>"
                                + "</D:lockinfo>"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        // Even with an apparently valid token, the function remains not found.
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", URI.create(FILE_NOT_EXISTS_URI))
                        .header("If", "<0000-0000-0000-0000>"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        // Even with an apparently valid token, the function remains forbidden.
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("UNLOCK", URI.create(FILE_NOT_EXISTS_URI))
                        .header("If", "<0000-0000-0000-0000>"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}