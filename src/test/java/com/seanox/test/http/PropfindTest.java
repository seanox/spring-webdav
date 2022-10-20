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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

/**
 * Test the sequence for PROPFIND for files and folders.
 *
 * @author  Seanox Software Solutions
 * @version 1.1.0 20210811
 */
@SuppressWarnings("boxing")
public class PropfindTest extends AbstractApiTest {

    @Test
    void test_file() throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(FILE_URI)
                        .contentType(CONTENT_TYPE_XLSX)
                        .content(AbstractApiTest.readTemplate(AbstractApiTest.TEMPLATES_COSTS_XLSX)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", URI.create(FILE_URI))
                        .header("Depth", "0"))
                .andExpect(MockMvcResultMatchers.status().is(207))
                .andExpect(MockMvcResultMatchers.header().doesNotExist("Last-Modified"))
                .andExpect(MockMvcResultMatchers.header().string("Content-Length", "959"))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_XML))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/displayname").string("costs.xlsx"))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/iscollection").booleanValue(Boolean.FALSE))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/creationdate").exists())
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getlastmodified").exists())
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getlastmodified").exists())
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/isreadonly").booleanValue(Boolean.FALSE))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/ishidden").booleanValue(Boolean.FALSE))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/issystem").booleanValue(Boolean.FALSE))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/isarchive").booleanValue(Boolean.FALSE))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/Win32FileAttributes").number(0d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getcontenttype").string(AbstractApiTest.CONTENT_TYPE_XLSX))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getcontentlength").number((double)AbstractApiTest.readTemplate(TEMPLATES_COSTS_XLSX).length))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getetag").exists())
                .andExpect(MockMvcResultMatchers.xpath("string-length(/multistatus/response/propstat/prop/getetag)").number(19d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/supportedlock/lockentry/lockscope/exclusive").exists())
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/supportedlock/lockentry/locktype/write").exists())
                .andExpect(MockMvcResultMatchers.xpath("count(/multistatus/response/propstat)").number(1d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/status").string("HTTP/1.1 200 Success"));
    }

    @Test
    void test_file_readonly() throws Exception {

        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", URI.create(FILE_READONLY_URI))
                        .header("Depth", "0"))
                .andExpect(MockMvcResultMatchers.status().is(207))
                .andExpect(MockMvcResultMatchers.header().doesNotExist("Last-Modified"))
                .andExpect(MockMvcResultMatchers.header().string("Content-Length", "975"))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_XML))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/displayname").string("sales.pptx"))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/iscollection").booleanValue(Boolean.FALSE))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/creationdate").exists())
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getlastmodified").exists())
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getlastmodified").exists())
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/isreadonly").booleanValue(Boolean.TRUE))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/ishidden").booleanValue(Boolean.FALSE))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/issystem").booleanValue(Boolean.FALSE))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/isarchive").booleanValue(Boolean.FALSE))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/Win32FileAttributes").number(1d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getcontenttype").string(AbstractApiTest.CONTENT_TYPE_PPTX))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getcontentlength").number((double)AbstractApiTest.readTemplate(TEMPLATE_SALES_PPTX).length))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/getetag").exists())
                .andExpect(MockMvcResultMatchers.xpath("string-length(/multistatus/response/propstat/prop/getetag)").number(19d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/supportedlock/lockentry/lockscope/exclusive").exists())
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/prop/supportedlock/lockentry/locktype/write").exists())
                .andExpect(MockMvcResultMatchers.xpath("count(/multistatus/response/propstat)").number(1d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response/propstat/status").string("HTTP/1.1 200 Success"));
    }

    @Test
    void test_file_not_exists() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", URI.create(FILE_NOT_EXISTS_URI))
                        .header("Depth", "0"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void test_file_redirect() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", URI.create(FILE_REDIRECT_URI))
                        .header("Depth", "0"))
                .andExpect(MockMvcResultMatchers.status().is(302));
    }

    @Test
    void test_folder() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", URI.create(FILE_PARENT_URI))
                        .header("Depth", "1"))
                .andExpect(MockMvcResultMatchers.status().is(207))
                .andExpect(MockMvcResultMatchers.header().doesNotExist("Last-Modified"))
                .andExpect(MockMvcResultMatchers.header().string("Content-Length", "2099"))
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_XML))
                .andExpect(MockMvcResultMatchers.xpath("count(/multistatus/response)").number(3d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[1]/propstat/prop/displayname").string("financial"))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[1]/propstat/prop/iscollection").booleanValue(Boolean.TRUE))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[1]/propstat/prop/isreadonly").booleanValue(Boolean.TRUE))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[1]/propstat/prop/Win32FileAttributes").number(11d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[2]/propstat/prop/displayname").string("costs.xlsx"))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[2]/propstat/prop/iscollection").booleanValue(Boolean.FALSE))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[2]/propstat/prop/isreadonly").booleanValue(Boolean.FALSE))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[2]/propstat/prop/Win32FileAttributes").number(0d))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[3]/propstat/prop/displayname").string("reports"))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[3]/propstat/prop/iscollection").booleanValue(Boolean.TRUE))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[3]/propstat/prop/isreadonly").booleanValue(Boolean.TRUE))
                .andExpect(MockMvcResultMatchers.xpath("/multistatus/response[3]/propstat/prop/Win32FileAttributes").number(11d));
    }

    @Test
    void test_folder_redirect() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", URI.create(FOLDER_REDIRECT_URI))
                        .header("Depth", "0"))
                .andExpect(MockMvcResultMatchers.status().is(302));
    }

    @Test
    void test_folder_not_exists() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", URI.create(FOLDER_NOT_EXISTS_URI))
                        .header("Depth", "0"))
                .andExpect(MockMvcResultMatchers.status().is(404));
    }
}