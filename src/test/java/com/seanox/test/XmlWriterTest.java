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
package com.seanox.test;

import com.seanox.webdav.XmlWriterAdapter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Test of the XmlWriter functions.<br>
 * <br>
 * XmlWriterTest 1.0.0 20210815<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210815
 */
class XmlWriterTest extends AbstractTest {

    @Test
    void test_1() throws IOException {

        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (final XmlWriterAdapter xmlWriter = new XmlWriterAdapter(buffer)) {
            xmlWriter.writeData("writeData1-\u00F6");

            xmlWriter.writeElement("space1", "name1", XmlWriterAdapter.ElementType.OPENING);
            xmlWriter.writeText("writeText1-\u00F6");
            xmlWriter.writeElement("space1", "name1", XmlWriterAdapter.ElementType.CLOSING);
            xmlWriter.writeElement("space2", "note2", "name2", XmlWriterAdapter.ElementType.OPENING);
            xmlWriter.writeText("writeText2-\u00F6");
            xmlWriter.writeElement("space2", "note2", "name2", XmlWriterAdapter.ElementType.CLOSING);
            xmlWriter.writeElement("space3", "name3", XmlWriterAdapter.ElementType.EMPTY);
            xmlWriter.writeElement("space4", "note4", "name4", XmlWriterAdapter.ElementType.EMPTY);

            xmlWriter.writeProperty("space5", "name5");
            xmlWriter.writeProperty("space6", "note6", "name6");
            xmlWriter.writeProperty("space7", "note7", "name7", "value7");

            xmlWriter.writePropertyData("space8", "name8", "value8");

            xmlWriter.close();

            final String output = buffer.toString().replaceAll("<(?!/)", "\n<").trim();
            final String pattern = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<![CDATA[writeData1-\u00c3\u00b6]]>\n" +
                    "<space1:name1>writeText1-\u00c3\u00b6</space1:name1>\n" +
                    "<space2:name2 xmlns:space2=\"note2\">writeText2-\u00c3\u00b6</space2:name2 xmlns:space2=\"note2\">\n" +
                    "<space3:name3/>\n" +
                    "<space4:name4 xmlns:space4=\"note4\"/>\n" +
                    "<space5:name5/>\n" +
                    "<space6:note6>name6</space6:note6>\n" +
                    "<space7:name7 xmlns:space7=\"note7\">value7</space7:name7 xmlns:space7=\"note7\">\n" +
                    "<space8:name8>\n" +
                    "<![CDATA[value8]]></space8:name8>";
            Assertions.assertEquals(pattern, output);
        }
    }
}