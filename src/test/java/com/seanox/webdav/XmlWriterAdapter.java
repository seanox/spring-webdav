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
package com.seanox.webdav;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Testing private parts and/or components visible only in the package requires
 * an adapter for access.<br>
 * <br>
 * Why are the tests not in com.seanox.webdav?<br>
 * Spring Test is used for the tests. For this @ComponentScan must scan the
 * package. For the release version, however, it should be ensured that the
 * library com.seanox.webdav also works without @ComponentScan and therefore
 * another package is used for the tests of the package com.seanox.webdav.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210728
 */
public class XmlWriterAdapter extends XmlWriter {

    public XmlWriterAdapter(final OutputStream output)
            throws IOException {
        super(output);
    }

    public enum ElementType {

        OPENING(XmlWriter.ElementType.OPENING),
        CLOSING(XmlWriter.ElementType.CLOSING),
        EMPTY(XmlWriter.ElementType.EMPTY);

        final XmlWriter.ElementType elementType;

        ElementType(XmlWriter.ElementType elementType) {
            this.elementType = elementType;
        }
    }

    @Override
    public void writeProperty(final String space, final String note, final String name, final String value)
            throws IOException {
        super.writeProperty(space, note, name, value);
    }

    @Override
    public void writeProperty(final String space, final String name, final String value)
            throws IOException {
        super.writeProperty(space, name, value);
    }

    @Override
    public void writePropertyData(final String space, final String name, final String value)
            throws IOException {
        super.writePropertyData(space, name, value);
    }

    @Override
    public void writeProperty(final String space, final String name)
            throws IOException {
        super.writeProperty(space, name);
    }

    public void writeElement(final String space, final String name, final ElementType type)
            throws IOException {
        super.writeElement(space, name, type.elementType);
    }

    public void writeElement(final String space, final String note, final String name, final ElementType type)
            throws IOException {
        super.writeElement(space, note, name, type.elementType);
    }

    @Override
    public void writeText(final String text)
            throws IOException {
        super.writeText(text);
    }

    @Override
    public void writeData(final String data)
            throws IOException {
        super.writeData(data);
    }

    @Override
    public void flush()
            throws IOException {
        super.flush();
    }
}