/**
 * LIZENZBEDINGUNGEN - Seanox Software Solutions ist ein Open-Source-Projekt,
 * im Folgenden Seanox Software Solutions oder kurz Seanox genannt.
 * Diese Software unterliegt der Version 2 der GNU General Public License.
 *
 * WebDAV mapping for Spring Boot
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

import com.seanox.api.extras.AcceptTestController;
import com.seanox.test.AbstractApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test the function of the accept attribute for
 * {@link com.seanox.apidav.ApiDavInputMapping}.<br>
 * <br>
 * AcceptTest 1.0.0 20210715<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210715
 */
public class AcceptTest extends AbstractApiTest {

    // Supported types of data definition:
    // - {@link ApiDavInputMapping}
    // - {@link ApiDavInputMappingAttribute} + {@link ApiDavInputMappingAttributeExpression}
    // Supported data types of definition:
    // - static value via {@link ApiDavInputMapping}
    // - Spring Expression Language via {@link ApiDavInputMappingAttributeExpression}
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