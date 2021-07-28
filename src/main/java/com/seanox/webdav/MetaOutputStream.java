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
package com.seanox.webdav;

import lombok.AccessLevel;
import lombok.Builder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 *   MetaOutputStream is an {@link OutputStream} with meta information for the
 *   response header. The meta information is already pre-filled with the data
 *   determined by the framework and can be changed until the first byte is
 *   written to the data output stream.
 * </p>
 *
 * MetaOutputStream 1.0.0 20210728<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210728
 */
@Builder(access=AccessLevel.PACKAGE)
public class MetaOutputStream extends OutputStream {

    // NOTE: Lombok is awesome, but it doesn't create a usable JavaDoc
    // and then using Delombok isn't so great.

    private final HttpServletResponse response;

    private String  contentType;
    private Integer contentLength;
    private Date    lastModified;

    private OutputStream output;

    MetaOutputStream() {
        throw new RuntimeException();
    }

    /**
     * Return ContentType to the outgoing data stream.
     * @return ContentType to the outgoing data stream
     */
    public String getContentType() {
        return this.contentType;
    }

    /**
     * Set ContentType for the outgoing data stream.
     * @param contentType for the outgoing data stream
     */
    public void setContentType(final String contentType) {
        if (this.response.isCommitted())
            return;
        this.contentType = contentType;
    }

    /**
     * Return ContentLength to the outgoing data stream.
     * @return contentLength to the outgoing data stream
     */
    public Integer getContentLength() {
        return this.contentLength;
    }

    /**
     * Set ContentLength for the outgoing data stream.
     * @param contentLength for the outgoing data stream
     */
    public void setContentLength(final Integer contentLength) {
        if (this.response.isCommitted())
            return;
        this.contentLength = contentLength;
    }

    /**
     * Return LastModified to the outgoing data stream.
     * @return LastModified to the outgoing data stream
     */
    public Date getLastModified() {
        return this.lastModified;
    }

    /**
     * Set LastModified for the outgoing data stream.
     * @param lastModified for the outgoing data stream
     */
    public void setLastModified(final Date lastModified) {
        if (this.response.isCommitted())
            return;
        this.lastModified = lastModified;
    }

    @Override
    public void write(final int digit)
            throws IOException {
        this.flush();
        this.output.write(digit);
    }

    @Override
    public void write(final byte[] bytes)
            throws IOException {
        this.flush();
        this.output.write(bytes);
    }

    @Override
    public void write(final byte[] bytes, final int offset, final int length)
            throws IOException {
        this.flush();
        this.output.write(bytes, offset, length);
    }

    @Override
    public void flush()
            throws IOException {
        if (!this.response.isCommitted()) {
            this.response.setStatus(HttpServletResponse.SC_OK);
            if (Objects.nonNull(this.contentType)
                    && !this.contentType.isBlank())
                this.response.setContentType(this.contentType);
            if (Objects.nonNull(this.contentLength)
                    && this.contentLength >= 0)
                this.response.setContentLength(this.contentLength);
            if (Objects.nonNull(this.lastModified))
                this.response.setHeader("Last-Modified", DateTime.formatDate(this.lastModified, "E, dd MMM yyyy HH:mm:ss z"));
            final Date lastModified = this.getLastModified();
            if (Objects.nonNull(lastModified))
                this.response.setHeader("Etag", "\"" + Long.toString(lastModified.getTime(), 36).toUpperCase() + "\"");
            this.response.flushBuffer();
        }
        if (Objects.isNull(this.output))
            this.output = this.response.getOutputStream();
    }

    @Override
    public void close()
            throws IOException {
        this.flush();
        super.close();
    }
}