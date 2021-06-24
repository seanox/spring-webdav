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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;

@Builder(access=AccessLevel.PACKAGE)
public class MetaInputStream extends InputStream {

    private final HttpServletRequest request;

    @Getter(AccessLevel.PUBLIC) private String contentType;
    @Getter(AccessLevel.PUBLIC) private Long   contentLength;
    @Getter(AccessLevel.PUBLIC) private Date   lastModified;

    private InputStream input;

    @Override
    public int read()
            throws IOException {
        if (Objects.isNull(this.input))
            this.input = request.getInputStream();
        return this.input.read();
    }

    @Override
    public int read(byte[] bytes)
            throws IOException {
        if (Objects.isNull(this.input))
            this.input = request.getInputStream();
        return this.input.read(bytes);
    }

    @Override
    public int read(byte[] bytes, int offset, int lenght)
            throws IOException {
        if (Objects.isNull(this.input))
            this.input = request.getInputStream();
        return this.input.read(bytes, offset, lenght);
    }

    @Override
    public byte[] readAllBytes()
            throws IOException {
        if (Objects.isNull(this.input))
            this.input = request.getInputStream();
        return this.input.readAllBytes();
    }
}