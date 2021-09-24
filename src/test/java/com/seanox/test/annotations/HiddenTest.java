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

import com.seanox.api.extras.HiddenTestController;
import com.seanox.test.AbstractApiTest;
import com.seanox.webdav.WebDavMapping;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.net.URI;

/**
 * Test the function of the hidden attribute for
 * {@link WebDavMapping}.<br>
 * <br>
 * The directory structure shows only substructures with visible content.
 * If a directory has no visible content, this structure is not displayed.
 * However, the paths to the data exist.
 *     Expected behavior:
 * - Explorer displays only /extras/hidden/c/c-1/c-2/c-3/c2.txt
 * - /extras/hidden/a/a-1/a-2/a-3/a2.txt can be used, shows the file name
 * - /extras/hidden/a/a-1/a-2/a-3 can be used, but is empty
 * - /extras/hidden/a/a-1/a-1 can be used, but is empty
 * - /extras/hidden/a/a-1/a-2/a-3/a2.txt can be used, shows the file name
 * - /extras/hidden/b/b-1/b-2/b-3 can be used, but is empty
 * - /extras/hidden/b/b-1/b-2/b-3/b1.txt can be used, shows the file name
 * - /extras/hidden/c/c-1/c-2/c-3 can be used and is not empty
 *
 * HiddenTest 1.0.0 20210815<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210815
 */
class HiddenTest extends AbstractApiTest {

    // Visible
    // HiddenTestController.MAPPING_A_FILE_C2
    // HiddenTestController.MAPPING_A_FOLDER_C3_REDIRECT
    // HiddenTestController.MAPPING_A_FOLDER_C3

    // Hidden
    // HiddenTestController.MAPPING_A_FILE_A2
    // HiddenTestController.MAPPING_A_FOLDER_A3_REDIRECT
    // HiddenTestController.MAPPING_A_FOLDER_A3
    // HiddenTestController.MAPPING_A_FOLDER_A2_REDIRECT
    // HiddenTestController.MAPPING_A_FOLDER_A2
    // HiddenTestController.MAPPING_A_FOLDER_B3_REDIRECT
    // HiddenTestController.MAPPING_A_FOLDER_B3
    // HiddenTestController.MAPPING_A_FILE_B1

    PropfindResult propfind(final String uri) throws Exception {

        final MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", URI.create(uri))
                        .header("Depth", "1"))
                .andReturn();

        if (mvcResult.getResponse().getStatus() != 200
                && mvcResult.getResponse().getStatus() != 207) {
            final PropfindResult propfindResult = new PropfindResult();
            propfindResult.uri = uri;
            propfindResult.status = mvcResult.getResponse().getStatus();
            return propfindResult;
        }

        final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder builder = builderFactory.newDocumentBuilder();
        final Document xmlDocument = builder.parse(new ByteArrayInputStream(mvcResult.getResponse().getContentAsString().getBytes()));
        final XPath xpath = XPathFactory.newInstance().newXPath();

        final PropfindResult propfindResult = new PropfindResult();
        propfindResult.uri = xpath.compile("/multistatus/response/href").evaluate(xmlDocument);
        propfindResult.status = mvcResult.getResponse().getStatus();
        propfindResult.isFolder = Boolean.valueOf(xpath.compile("/multistatus/response/propstat/prop/iscollection").evaluate(xmlDocument)).booleanValue();
        propfindResult.isFile = !propfindResult.isFolder;
        propfindResult.isHidden = Boolean.valueOf(xpath.compile("/multistatus/response/propstat/prop/ishidden").evaluate(xmlDocument)).booleanValue();
        propfindResult.isEmpty = !propfindResult.isFile;
        propfindResult.childs = ((Number)xpath.compile("count(/multistatus/response) -1").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
        return propfindResult;
    }

    static class PropfindResult {
        String  uri;
        int     status;
        boolean isFile;
        boolean isFolder;
        boolean isHidden;
        boolean isEmpty;
        int     childs;
    }

    @Test
    void testFile_A1() throws Exception {
        final PropfindResult propfindResult = this.propfind(HiddenTestController.MAPPING_A_FILE_C2);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertTrue(propfindResult.isFile);
        Assertions.assertFalse(propfindResult.isHidden);
    }
    @Test
    void testFile_A2() throws Exception {
        final PropfindResult propfindResult = this.propfind(HiddenTestController.MAPPING_A_FILE_A2);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertTrue(propfindResult.isFile);
        Assertions.assertTrue(propfindResult.isHidden);
    }
    @Test
    void testFile_A3() throws Exception {
        final PropfindResult propfindResult = this.propfind(HiddenTestController.MAPPING_A_FILE_B1);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertTrue(propfindResult.isFile);
        Assertions.assertTrue(propfindResult.isHidden);
    }

    @Test
    void testFolder_A1() throws Exception {
        Assertions.assertEquals(302, this.propfind(HiddenTestController.MAPPING_A_FOLDER_C3_REDIRECT).status);
        final PropfindResult propfindResult = this.propfind(HiddenTestController.MAPPING_A_FOLDER_C3);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertTrue(propfindResult.isFolder);
        Assertions.assertFalse(propfindResult.isHidden);
        Assertions.assertEquals(1, propfindResult.childs);
    }
    @Test
    void testFolder_A2() throws Exception {
        Assertions.assertEquals(302, this.propfind(HiddenTestController.MAPPING_A_FOLDER_A3_REDIRECT).status);
        final PropfindResult propfindResult = this.propfind(HiddenTestController.MAPPING_A_FOLDER_A3);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertTrue(propfindResult.isFolder);
        Assertions.assertTrue(propfindResult.isHidden);
        Assertions.assertEquals(0, propfindResult.childs);
    }
    @Test
    void testFolder_A3() throws Exception {
        Assertions.assertEquals(302, this.propfind(HiddenTestController.MAPPING_A_FOLDER_A2_REDIRECT).status);
        final PropfindResult propfindResult = this.propfind(HiddenTestController.MAPPING_A_FOLDER_A2);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertTrue(propfindResult.isFolder);
        Assertions.assertTrue(propfindResult.isHidden);
        Assertions.assertEquals(0, propfindResult.childs);
    }
    @Test
    void testFolder_A4() throws Exception {
        Assertions.assertEquals(302, this.propfind(HiddenTestController.MAPPING_A_FOLDER_B3_REDIRECT).status);
        final PropfindResult propfindResult = this.propfind(HiddenTestController.MAPPING_A_FOLDER_B3);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertTrue(propfindResult.isFolder);
        Assertions.assertTrue(propfindResult.isHidden);
        Assertions.assertEquals(0, propfindResult.childs);
    }
}