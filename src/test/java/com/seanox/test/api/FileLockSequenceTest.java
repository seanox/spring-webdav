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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * Test the sequence for locked and unlocked a file.
 * There are no real locks, but the behavior is supposed to be correct.
 *
 * FileLockSequenceTest 1.0.0 20210704
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210704
 */
public class FileLockSequenceTest extends AbstractApiTest {

    private final static String rootURI = "/";
    private final static String targetURI = "/personal/reports/sales.pptx";

    @Test
    void test()
            throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(rootURI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andReturn();

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("OPTIONS", new URI(targetURI)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.header().string("Allow", "OPTIONS, HEAD, GET, PROPFIND"))
                .andReturn();

        final MvcResult mvcResult3 = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", new URI(targetURI))
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
                        .string("/personal/reports/sales.pptx"))
                .andReturn();
        final String lockToken3 = mvcResult3.getResponse().getContentAsString()
                .replaceAll("^.*<d:locktoken><d:href>([A-Za-z0-9-]+).*$", "$1");
        Assertions.assertTrue(lockToken3.matches("^[A-Za-z0-9]+(-[A-Za-z0-9]+)+$"));
        final String lockTokenHeader3 = mvcResult3.getResponse().getHeader("Lock-Token");
        Assertions.assertEquals(lockTokenHeader3, "<" + lockToken3 + ">");

        final MvcResult mvcResult4 = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", new URI(targetURI))
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
                        .string("/personal/reports/sales.pptx"))
                .andReturn();
        final String lockToken4 = mvcResult4.getResponse().getContentAsString()
                .replaceAll("^.*<d:locktoken><d:href>([A-Za-z0-9-]+).*$", "$1");
        Assertions.assertTrue(lockToken4.matches("^[A-Za-z0-9]+(-[A-Za-z0-9]+)+$"));
        Assertions.assertNotEquals(lockToken3, lockToken4);

        final MvcResult mvcResult5 = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("LOCK", new URI(targetURI))
                        .header("If", "<" + lockToken4 + ">"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers
                        .xpath("/prop/lockdiscovery/activelock/lockroot/href")
                        .string("/personal/reports/sales.pptx"))
                .andReturn();
        final String lockToken5 = mvcResult5.getResponse().getContentAsString()
                .replaceAll("^.*<d:locktoken><d:href>([A-Za-z0-9-]+).*$", "$1");
        Assertions.assertTrue(lockToken4.matches("^[A-Za-z0-9]+(-[A-Za-z0-9]+)+$"));
        Assertions.assertEquals(lockToken4, lockToken5);

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("UNLOCK", new URI(targetURI))
                        .header("Lock-Token", "<" + lockToken4 + ">"))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.header().doesNotExist("Lock-Token"))
                .andReturn();
    }
}