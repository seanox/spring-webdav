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

import com.seanox.api.extras.SignaturesTestController;
import com.seanox.test.AbstractApiTest;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * Test the function of the method signatures.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210815
 */
class SignaturesTest extends AbstractApiTest {

    // Methods do not have a fixed signature, the data type of the parameters
    // is used as a placeholder and filled when called.

    @Test
    void test_A1() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(SignaturesTestController.MAPPING_A1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(""));
    }
    @Test
    void test_A2() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(SignaturesTestController.MAPPING_A2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("A2-ABCD---"));
    }
    @Test
    void test_A3() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(SignaturesTestController.MAPPING_A3))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("A3----ABCD"));
    }
    @Test
    void test_A4() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(SignaturesTestController.MAPPING_A4))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("A4-AaBbCc-c-Dab"));
    }

    // {@link WebDavMapping}
    // URI, Properties, MetaProperties, MetaOutputStream, Annotation.AnnotationType.Mapping
    // expected data type from return value: void
    // - without
    // - complete +3 more object
    // - complete +3 more object, but sequence reversed
    // - complete all double, the first two repeat at the end

    @Test
    void test_B1() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(SignaturesTestController.MAPPING_B1))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(SignaturesTestController.MAPPING_B1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("B1"));
    }
    @Test
    void test_B2() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(SignaturesTestController.MAPPING_B2))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(SignaturesTestController.MAPPING_B2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("B2-ABCD---"));
    }
    @Test
    void test_B3() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(SignaturesTestController.MAPPING_B3))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(SignaturesTestController.MAPPING_B3))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("B3----ABCD"));
    }
    @Test
    void test_B4() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.put(SignaturesTestController.MAPPING_B4))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(SignaturesTestController.MAPPING_B4))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("B4-AaBbCc-cD-ab"));
    }

    // {@link WebDavMetaMapping}
    // URI, Properties, MetaData, Annotation.AnnotationType.Meta (not public)
    // expected data type from return value: void
    // - without
    // - complete +3 more object
    // - complete +3 more object, but sequence reversed
    // - complete all double, the first two repeat at the end

    @Test
    void test_C1() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(SignaturesTestController.MAPPING_C1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("C1"));
    }
    @Test
    void test_C2() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(SignaturesTestController.MAPPING_C2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("C2-ABC---"));
    }
    @Test
    void test_C3() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(SignaturesTestController.MAPPING_C3))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("C3----ABC"));
    }
    @Test
    void test_C4() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(SignaturesTestController.MAPPING_C4))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("C4-AaBbCcc---ab"));
    }

    // {@link WebDavAttributeMapping}
    // URI, Properties, WebDavMappingAttribute
    // expected data type from return value: depending on the attributes - Boolean, Integer, String, Date
    // - without
    // - complete +3 more object
    // - complete +3 more object, but sequence reversed
    // - complete all double, the first two repeat at the end

    @Test
    void test_D1() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(SignaturesTestController.MAPPING_D1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("D1"));
    }
    @Test
    void test_D2() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(SignaturesTestController.MAPPING_D2))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("D2-ABC---"));
    }
    @Test
    void test_D3() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(SignaturesTestController.MAPPING_D3))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("D3----ABC"));
    }
    @Test
    void test_D4() throws Exception {
        this.mockMvc.perform(
                MockMvcRequestBuilders.get(SignaturesTestController.MAPPING_D4))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("D4-AaBbCc----ab"));
    }
}