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
package com.seanox.test.annotations;

import com.seanox.api.extras.ContentLengthMaxTestController;
import com.seanox.test.AbstractApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test the function of the ContentLengthMaxTest attribute.
 *
 * ContentLengthMaxTest 1.0.0 20210715
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210715
 */
public class ContentLengthMaxTest extends AbstractApiTest {

    // Supported types of data definition:
    // - {@link ApiDavInputMapping}
    // - {@link ApiDavInputMappingAttribute} + {@link ApiDavInputMappingAttributeExpression}
    // Supported data types of definition:
    // - static value via {@link ApiDavInputMapping}
    // - Spring Expression Language via {@link ApiDavInputMappingAttributeExpression}
    // Expected data type:
    // - long
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