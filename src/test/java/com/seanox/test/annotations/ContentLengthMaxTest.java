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
package com.seanox.test.annotations;

import com.seanox.api.extras.ContentLengthMaxTestController;
import com.seanox.test.AbstractApiTest;
import com.seanox.webdav.WebDavInputMapping;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test the function of the ContentLengthMaxTest attribute for
 * {@link WebDavInputMapping}.<br>
 * <br>
 * ContentLengthMaxTest 1.0.0 20210815<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210815
 */
class ContentLengthMaxTest extends AbstractApiTest {

    // Supported types of data definition:
    // - {@link WebDavInputMapping}
    // - {@link WebDavInputMappingAttribute} + {@link WebDavInputMappingAttributeExpression}
    // Supported data types of definition:
    // - static value via {@link WebDavInputMapping}
    // - Spring Expression Language via {@link WebDavInputMappingAttributeExpression}
    // Expected data type:
    // - int
    // - value less than 0 has effect like default (null)

    private static final String CONTENT_0  = "";
    private static final String CONTENT_1  = "A";
    private static final String CONTENT_10 = "ABCDEFGHIJ";
    private static final String CONTENT_11 = "ABCDEFGHIJK";
    private static final String CONTENT_50 = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWX";
    private static final String CONTENT_51 = "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXY";

    @Test
    void test_A1() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(ContentLengthMaxTestController.MAPPING_A1)
                        .content(CONTENT_51))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(ContentLengthMaxTestController.MAPPING_A1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("51"));
    }

    @Test
    void test_A2() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(ContentLengthMaxTestController.MAPPING_A2)
                        .content(CONTENT_0))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(ContentLengthMaxTestController.MAPPING_A2)
                        .content(CONTENT_1))
                .andExpect(MockMvcResultMatchers.status().isPayloadTooLarge());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(ContentLengthMaxTestController.MAPPING_A2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("0"));
    }

    @Test
    void test_A3() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(ContentLengthMaxTestController.MAPPING_A3)
                        .content(CONTENT_10))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(ContentLengthMaxTestController.MAPPING_A3)
                        .content(CONTENT_11))
                .andExpect(MockMvcResultMatchers.status().isPayloadTooLarge());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(ContentLengthMaxTestController.MAPPING_A3))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("10"));
    }

    @Test
    void test_A4() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(ContentLengthMaxTestController.MAPPING_A4)
                        .content(CONTENT_50))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(ContentLengthMaxTestController.MAPPING_A4)
                        .content(CONTENT_51))
                .andExpect(MockMvcResultMatchers.status().isPayloadTooLarge());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(ContentLengthMaxTestController.MAPPING_A4))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("50"));
    }

    @Test
    void test_A5() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .put(ContentLengthMaxTestController.MAPPING_A5)
                        .content(CONTENT_51))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders
                        .get(ContentLengthMaxTestController.MAPPING_A5))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("51"));
    }
}