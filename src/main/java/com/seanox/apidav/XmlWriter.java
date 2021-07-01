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

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Writer for the output of XML data.<br>
 * <br>
 * XmlWriter 1.1.0 20210701<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * Alle Rechte vorbehalten.
 *
 * @author  Seanox Software Solutions
 * @version 1.1.0 20210701
 */
class XmlWriter implements Closeable {

    /** Output stream */
    private final OutputStream output;

    /** Encoding to be used */
    private final Charset encoding;

    /** Enumeration of element types */
    enum ElementType {

        /** opening element */
        OPENING("<", ">"),

        /** closing element */
        CLOSING("</", ">"),

        /** Closing element without content */
        EMPTY("<", "/>");

        /** Opening character sequence */
        private final String open;

        /** Closing character sequence */
        private final String close;

        /**
         * Constructor, creates the ElementType.
         * @param open  Opening character sequence
         * @param close Closing character sequence
         */
        ElementType(String open, String close) {
            this.open  = open;
            this.close = close;
        }
    }

    /**
     * Constructor, creates the XML writer with UTF-8 encoding.
     * @param output Output stream.
     */
    XmlWriter(final OutputStream output) {
        this(output, null);
    }

    /**
     * Constructor, creates the XML writer with the specified encoding.
     * @param output   Output stream.
     * @param encoding Encoding to be used
     */
    XmlWriter(final OutputStream output, final Charset encoding) {

        this.output   = output;
        this.encoding = Objects.isNull(encoding) ? StandardCharsets.UTF_8 : encoding;
    }

    /**
     * Writes an XML parameter into the data stream.
     * @param  namespace Namespace
     * @param  uri       Namespace URI
     * @param  name      Element name
     * @param  value     Element value
     * @throws IOException
     *     In case of faulty access to the data stream
     */
    void writeProperty(final String namespace, final String uri, final String name, final String value)
            throws IOException {

        this.writeElement(namespace, uri, name, ElementType.OPENING);
        this.writeText(value);
        this.writeElement(namespace, uri, name, ElementType.CLOSING);
    }

    /**
     * Writes an XML parameter into the data stream.
     * @param  namespace Namespace
     * @param  name      Element name
     * @param  value     Element value
     * @throws IOException
     *     In case of faulty access to the data stream
     */
    void writeProperty(final String namespace, final String name, final String value)
            throws IOException {

        this.writeElement(namespace, name, ElementType.OPENING);
        this.writeText(value);
        this.writeElement(namespace, name, ElementType.CLOSING);
    }

    /**
     * Writes an XML parameter as data segment into the data stream.
     * @param  namespace Namespace
     * @param  name      Element name
     * @param  value     Element value
     * @throws IOException
     *     In case of faulty access to the data stream
     */
    void writePropertyData(final String namespace, final String name, final String value)
            throws IOException {

        this.writeElement(namespace, name, ElementType.OPENING);
        this.writeText(String.format("<![CDATA[%s]]>", value));
        this.writeElement(namespace, name, ElementType.CLOSING);
    }

    /**
     * Writes an XML element into the data stream.
     * @param  namespace Namespace
     * @param  name      Element name
     * @throws IOException
     *     In case of faulty access to the data stream
     */
    void writeProperty(final String namespace, final String name)
            throws IOException {
        this.writeElement(namespace, name, ElementType.EMPTY);
    }

    /**
     * Writes an XML element into the data stream.
     * @param  namespace Namespace
     * @param  name      Element name
     * @param  type      Element type
     * @throws IOException
     *     In case of faulty access to the data stream
     */
    void writeElement(final String namespace, final String name, final ElementType type)
            throws IOException {
        this.writeElement(namespace, null, name, type);
    }

    /**
     * Writes an XML element into the data stream.
     * @param  namespace Namespace
     * @param  uri       Namespace URI
     * @param  name      Element name
     * @param  type      Element type
     * @throws IOException
     *     In case of faulty access to the data stream
     */
    void writeElement(final String namespace, final String uri, final String name, final ElementType type)
            throws IOException {

        this.writeText(Objects.nonNull(type) ? type.open : ElementType.EMPTY.open);
        if (namespace != null
                && !namespace.isBlank()) {
            this.writeText(String.format("%s:%s", namespace.trim(), name));
            if (uri != null
                    && !uri.isBlank())
                this.writeText(String.format(" xmlns:%s=\"%s\"", namespace.trim(), uri.trim()));
        } else this.writeText(name);
        this.writeText(Objects.nonNull(type) ? type.close : ElementType.EMPTY.close);
    }

    /**
     * Writes the passed string as text into the data stream.
     * @param  text Text
     * @throws IOException
     *     In case of faulty access to the data stream
     */
    void writeText(final String text)
            throws IOException {
        this.output.write(String.valueOf(text).getBytes(this.encoding));
    }

    /**
     * Writes the passed string as data segment into the data stream.
     * @param  data Content of the data segment
     * @throws IOException
     *     In case of faulty access to the data stream
     */
    void writeData(final String data)
            throws IOException {
        this.writeText(String.format("<![CDATA[%s]]>", data));
    }

    /**
     * Writes the XML header into the data stream.
     * @throws IOException
     *     In case of faulty access to the data stream
     */
    void writeXmlHeader()
            throws IOException {
        this.writeText(String.format("<?xml version=\"1.0\" encoding=\"%s\"?>", this.encoding.name()));
    }

    /**
     * Writes the data in the output buffer to the data stream.
     * @throws IOException
     *     In case of faulty access to the data stream
     */
    void flush()
            throws IOException {
        this.output.flush();
    }

    @Override
    public void close()
            throws IOException {
        this.output.close();
    }
}