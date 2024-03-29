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

import com.seanox.api.extras.AcceptTestController;
import com.seanox.test.AbstractApiTest;
import com.seanox.webdav.WebDavInputMapping;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test the function of the accept attribute for {@link WebDavInputMapping}.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210815
 */
class AcceptTest extends AbstractApiTest {

    // Supported types of data definition:
    // - {@link WebDavInputMapping}
    // - {@link WebDavInputMappingAttribute} + {@link WebDavInputMappingAttributeExpression}
    // Supported data types of definition:
    // - static value via {@link WebDavInputMapping}
    // - Spring Expression Language via {@link WebDavInputMappingAttributeExpression}
    // Expected data type:
    // - String pattern: */* mimetype/* mimetype/mimesubtype */mimesubtype
    // - empty string has effect like default (*/*)
    // - default: */*

    private static final String CONTENT_TYPE_TEXT_A = "TexT/A";
    private static final String CONTENT_TYPE_TEXT_B = "text/b";
    private static final String CONTENT_TYPE_TEXT_C = "TexT/C";

    private static final String CONTENT_TYPE_XXX_A = "XXX/a";
    private static final String CONTENT_TYPE_XXX_B = "XxX/B";
    private static final String CONTENT_TYPE_XXX_C = "XXX/c";

    private static final String CONTENT_TYPE_OTHER_A = "other/a";

    @Test
    void test_A1() throws Exception {
        final String mapping = AcceptTestController.MAPPING_A1;
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_A).content("TA"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_B).content("TB"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_C).content("TC"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_A).content("XA"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_B).content("XB"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_C).content("XC"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_OTHER_A).content("OA"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void test_A2() throws Exception {
        final String mapping = AcceptTestController.MAPPING_A2;
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_A).content("TA"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_B).content("TB"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_C).content("TC"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_A).content("XA"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_B).content("XB"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_C).content("XC"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_OTHER_A).content("OA"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void test_A3() throws Exception {
        final String mapping = AcceptTestController.MAPPING_A3;
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_A).content("TA"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_B).content("TB"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_C).content("TC"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_A).content("XA"))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_B).content("XB"))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_C).content("XC"))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_OTHER_A).content("OA"))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    void test_A4() throws Exception {
        final String mapping = AcceptTestController.MAPPING_A4;
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_A).content("TA"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_B).content("TB"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_C).content("TC"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_A).content("XA"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_B).content("XB"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_C).content("XC"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_OTHER_A).content("OA"))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    void test_A5() throws Exception {
        final String mapping = AcceptTestController.MAPPING_A5;
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_A).content("TA"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_B).content("TB"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_C).content("TC"))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_A).content("XA"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_B).content("XB"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_C).content("XC"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_OTHER_A).content("OA"))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

    @Test
    void test_A6() throws Exception {
        final String mapping = AcceptTestController.MAPPING_A6;
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_A).content("TA"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_A).content("TB"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_TEXT_C).content("TC"))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_A).content("XA"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_B).content("XB"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_XXX_C).content("XC"))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(mapping).contentType(CONTENT_TYPE_OTHER_A).content("OA"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}