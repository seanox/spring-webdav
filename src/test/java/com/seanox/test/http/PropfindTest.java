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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

/**
 * Test the sequence for PROPFIND for files and folders.
 *
 * PropfindTest 1.0.0 20210705
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210705
 */
public class PropfindTest extends AbstractApiTest {

    @Test
    void test_file()
            throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", new URI(FILE_URI))
                        .header("Depth", "0"))
                .andExpect(MockMvcResultMatchers.status().is(207))
                .andExpect(MockMvcResultMatchers.header().doesNotExist("Last-Modified"))
                .andExpect(MockMvcResultMatchers.header().string("Content-Length", "951"))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_XML))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/displayname").string("budget.xlsx"))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/iscollection").booleanValue(false))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/creationdate").exists())
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getlastmodified").exists())
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getlastmodified").exists())
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/isreadonly").booleanValue(false))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/ishidden").booleanValue(false))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/issystem").booleanValue(false))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/isarchive").booleanValue(false))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/Win32FileAttributes").number(0d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getcontenttype").string(AbstractApiTest.CONTENT_TYPE_XLSX))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getcontentlength").number((double)AbstractApiTest.readTemplate(TEMPLATE_BUDGET_XLSX).length))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getetag").exists())
                .andExpect(MockMvcResultMatchers.xpath("string-length(/multistatus/response/propstat/prop/getetag)").number(10d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/supportedlock/lockentry/lockscope/exclusive").exists())
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/supportedlock/lockentry/locktype/write").exists())
                .andExpect(MockMvcResultMatchers.xpath("count(/multistatus/response/propstat)").number(1d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/status").string("HTTP/1.1 200 Success"));
    }

    @Test
    void test_file_readonly()
            throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", new URI(FILE_READONLY_URI))
                        .header("Depth", "0"))
                .andExpect(MockMvcResultMatchers.status().is(207))
                .andExpect(MockMvcResultMatchers.header().doesNotExist("Last-Modified"))
                .andExpect(MockMvcResultMatchers.header().string("Content-Length", "965"))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_XML))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/displayname").string("sales.pptx"))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/iscollection").booleanValue(false))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/creationdate").exists())
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getlastmodified").exists())
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getlastmodified").exists())
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/isreadonly").booleanValue(true))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/ishidden").booleanValue(false))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/issystem").booleanValue(false))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/isarchive").booleanValue(false))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/Win32FileAttributes").number(1d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getcontenttype").string(AbstractApiTest.CONTENT_TYPE_PPTX))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getcontentlength").number((double)AbstractApiTest.readTemplate(TEMPLATE_SALES_PPTX).length))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getetag").exists())
                .andExpect(MockMvcResultMatchers.xpath("string-length(/multistatus/response/propstat/prop/getetag)").number(10d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/supportedlock/lockentry/lockscope/exclusive").exists())
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/supportedlock/lockentry/locktype/write").exists())
                .andExpect(MockMvcResultMatchers.xpath("count(/multistatus/response/propstat)").number(1d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/status").string("HTTP/1.1 200 Success"));
    }

    @Test
    void test_file_not_exists()
            throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", new URI(FILE_NOT_EXISTS_URI))
                        .header("Depth", "0"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void test_folder()
            throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", new URI(FILE_PARENT_URI))
                        .header("Depth", "1"))
                .andExpect(MockMvcResultMatchers.status().is(207))
                .andExpect(MockMvcResultMatchers.header().doesNotExist("Last-Modified"))
                .andExpect(MockMvcResultMatchers.header().string("Content-Length", "2088"))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_XML))
                .andExpect(MockMvcResultMatchers.xpath("count(/multistatus/response)").number(3d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[1]/propstat/prop/displayname").string("personal"))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[1]/propstat/prop/iscollection").booleanValue(true))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[1]/propstat/prop/isreadonly").booleanValue(true))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[1]/propstat/prop/Win32FileAttributes").number(11d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[2]/propstat/prop/displayname").string("budget.xlsx"))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[2]/propstat/prop/iscollection").booleanValue(false))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[2]/propstat/prop/isreadonly").booleanValue(false))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[2]/propstat/prop/Win32FileAttributes").number(0d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[3]/propstat/prop/displayname").string("reports"))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[3]/propstat/prop/iscollection").booleanValue(true))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[3]/propstat/prop/isreadonly").booleanValue(true))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[3]/propstat/prop/Win32FileAttributes").number(11d));
    }

    @Test
    void test_folder_not_exists()
            throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", new URI(FOLDER_NOT_EXISTS_URI))
                        .header("Depth", "0"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }
}