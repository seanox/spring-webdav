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

import com.seanox.test.AbstractApiTest;
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
 * Test bases on DeepHiddenFolderStructureController.
 * The directory structure shows only substructures with visible content.
 * If a directory has no visible content, this structure is not displayed.
 * However, the paths to the data exist.
 *     Expected behavior:
 * - Explorer displays only /extras/deep-hidden/c/c-1/c-2/c-3/c2.txt
 * - /extras/deep-hidden/a/a-1/a-2/a-3/a2.txt can be used, shows the file name
 * - /extras/deep-hidden/a/a-1/a-2/a-3 can be used, but is empty
 * - /extras/deep-hidden/a/a-1/a-1 can be used, but is empty
 * - /extras/deep-hidden/a/a-1/a-2/a-3/a2.txt can be used, shows the file name
 * - /extras/deep-hidden/b/b-1/b-2/b-3 can be used, but is empty
 * - /extras/deep-hidden/b/b-1/b-2/b-3/b1.txt can be used, shows the file name
 * - /extras/deep-hidden/c/c-1/c-2/c-3 can be used and is not empty
 *
 * HiddenTest 1.0.0 20210707
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210707
 */
public class HiddenTest extends AbstractApiTest {

    // Visible
    private static final String FILE_C2 = "/extras/deep-hidden/c/c-1/c-2/c-3/c2.txt";
    private static final String FOLDER_C3_REDIRECT = "/extras/deep-hidden/c/c-1/c-2/c-3";
    private static final String FOLDER_C3 = "/extras/deep-hidden/c/c-1/c-2/c-3/";

    // Hidden
    private static final String FILE_A2 = "/extras/deep-hidden/a/a-1/a-2/a-3/a2.txt";
    private static final String FOLDER_A3_REDIRECT = "/extras/deep-hidden/a/a-1/a-2/a-3";
    private static final String FOLDER_A3 = "/extras/deep-hidden/a/a-1/a-2/a-3/";
    private static final String FOLDER_A2_REDIRECT = "/extras/deep-hidden/a/a-1/a-2";
    private static final String FOLDER_A2 = "/extras/deep-hidden/a/a-1/a-2/";
    private static final String FOLDER_B3_REDIRECT = "/extras/deep-hidden/b/b-1/b-2/b-3";
    private static final String FOLDER_B3 = "/extras/deep-hidden/b/b-1/b-2/b-3/";
    private static final String FILE_B1 = "/extras/deep-hidden/b/b-1/b-2/b-3/b1.txt";

    PropfindResult propfind(final String uri)
            throws Exception {

        final MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders
                        .request("PROPFIND", new URI(uri))
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
        propfindResult.isFolder = Boolean.valueOf(xpath.compile("/multistatus/response/propstat/prop/iscollection").evaluate(xmlDocument));
        propfindResult.isFile = !propfindResult.isFolder;
        propfindResult.isHidden = Boolean.valueOf(xpath.compile("/multistatus/response/propstat/prop/ishidden").evaluate(xmlDocument));
        propfindResult.isEmpty = !propfindResult.isFile;
        propfindResult.childs = ((Number)xpath.compile("count(/multistatus/response) -1").evaluate(xmlDocument, XPathConstants.NUMBER)).intValue();
        return propfindResult;
    }

    private static class PropfindResult {
        String  uri;
        int     status;
        boolean isFile;
        boolean isFolder;
        boolean isHidden;
        boolean isEmpty;
        int     childs;
    }

    @Test
    void testFile()
            throws Exception {

        PropfindResult propfindResult;

        propfindResult = this.propfind(FILE_C2);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertEquals(true, propfindResult.isFile);
        Assertions.assertEquals(false, propfindResult.isHidden);

        propfindResult = this.propfind(FILE_A2);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertEquals(true, propfindResult.isFile);
        Assertions.assertEquals(true, propfindResult.isHidden);

        propfindResult = this.propfind(FILE_B1);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertEquals(true, propfindResult.isFile);
        Assertions.assertEquals(true, propfindResult.isHidden);
    }

    @Test
    void testFolder()
            throws Exception {

        PropfindResult propfindResult;

        Assertions.assertEquals(302, this.propfind(FOLDER_C3_REDIRECT).status);

        propfindResult = this.propfind(FOLDER_C3);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertEquals(true, propfindResult.isFolder);
        Assertions.assertEquals(false, propfindResult.isHidden);
        Assertions.assertEquals(1, propfindResult.childs);

        Assertions.assertEquals(302, this.propfind(FOLDER_A3_REDIRECT).status);
        propfindResult = this.propfind(FOLDER_A3);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertEquals(true, propfindResult.isFolder);
        Assertions.assertEquals(true, propfindResult.isHidden);
        Assertions.assertEquals(0, propfindResult.childs);

        Assertions.assertEquals(302, this.propfind(FOLDER_A2_REDIRECT).status);
        propfindResult = this.propfind(FOLDER_A2);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertEquals(true, propfindResult.isFolder);
        Assertions.assertEquals(true, propfindResult.isHidden);
        Assertions.assertEquals(0, propfindResult.childs);

        Assertions.assertEquals(302, this.propfind(FOLDER_B3_REDIRECT).status);
        propfindResult = this.propfind(FOLDER_B3);
        Assertions.assertEquals(207, propfindResult.status);
        Assertions.assertEquals(true, propfindResult.isFolder);
        Assertions.assertEquals(true, propfindResult.isHidden);
        Assertions.assertEquals(0, propfindResult.childs);
    }
}