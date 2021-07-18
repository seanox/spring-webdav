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
package com.seanox.apidav;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * Testing private parts and/or components visible only in the package requires
 * an adapter for access.
 *
 * Why are the tests not in com.seanox.apidav?
 * Spring Test is used for the tests. For this @ComponentScan must scan the
 * package. For the release version, however, it should be ensured that the
 * library com.seanox.apidav also works without @ComponentScan and therefore
 * another package is used for the tests of the package com.seanox.apidav.
 */
public class XmlWriterAdapter extends XmlWriter {

    public XmlWriterAdapter(final OutputStream output)
            throws IOException {
        super(output);
    }

    public XmlWriterAdapter(final OutputStream output, final Charset encoding)
            throws IOException {
        super(output, encoding);
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