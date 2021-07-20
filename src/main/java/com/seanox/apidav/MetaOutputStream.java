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

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Objects;

@Builder(access=AccessLevel.PACKAGE)
public class MetaOutputStream extends OutputStream {

    private final HttpServletResponse response;

    @Getter(AccessLevel.PUBLIC) private String  contentType;
    @Getter(AccessLevel.PUBLIC) private Integer contentLength;
    @Getter(AccessLevel.PUBLIC) private Date    lastModified;

    private OutputStream output;

    public void setContentLength(final Integer contentLength) {
        if (this.response.isCommitted())
            return;
        this.contentLength = contentLength;
    }

    public void setContentType(final String contentType) {
        if (this.response.isCommitted())
            return;
        this.contentType = contentType;
    }

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