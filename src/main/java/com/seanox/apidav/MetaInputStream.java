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
import java.util.Objects;

// MetaInputStream can be limited with contentLengthMax.
// If the value of contentLengthMax greater than or equal to 0,
// only the so defined amount of data can be read from the data stream.
// Overwriting it will cause MetaInputStreamLimitException.
// Another special feature is the handling of an IOException that occur when reading in limited mode.
// An IOException is the permanent one, because it is not known how many bytes could be read, which can distort the limit.
// Without limiting, the data stream behaves normally.

@Builder(access=AccessLevel.PACKAGE)
public class MetaInputStream extends InputStream {

    private final HttpServletRequest request;

    @Getter(AccessLevel.PUBLIC) private final String contentType;
    @Getter(AccessLevel.PUBLIC) private final Long   contentLength;
    @Getter(AccessLevel.PUBLIC) private final Long   contentLengthMax;

    private InputStream input;

    private long limit;

    private IOException exception;

    @Override
    public int read()
            throws IOException {

        if (Objects.isNull(this.input))
            this.input = this.request.getInputStream();

        if (Objects.isNull(contentLengthMax)
                || contentLengthMax.longValue() < 0)
            return this.input.read();

        if (Objects.nonNull(this.exception))
            throw this.exception;

        try {
            final int digit = this.input.read();
            if (digit > 0)
                this.limit -= 1;
            return digit;
        } catch (IOException exception) {
            this.exception = exception;
            throw exception;
        } finally {
            if (this.limit < 0) {
                this.exception = new MetaInputStreamLimitException();
                throw this.exception;
            }
        }
    }

    @Override
    public int read(byte[] bytes)
            throws IOException {

        if (Objects.isNull(this.input))
            this.input = this.request.getInputStream();

        if (Objects.isNull(contentLengthMax)
                || contentLengthMax.longValue() < 0)
            return this.input.read(bytes);

        if (Objects.nonNull(this.exception))
            throw this.exception;

        try {
            final int total = this.input.read(bytes);
            if (total > 0)
                this.limit -= total;
            return total;
        } catch (IOException exception) {
            this.exception = exception;
            throw exception;
        } finally {
            if (this.limit < 0) {
                this.exception = new MetaInputStreamLimitException();
                throw this.exception;
            }
        }
    }

    @Override
    public int read(byte[] bytes, int offset, int length)
            throws IOException {

        if (Objects.isNull(this.input))
            this.input = this.request.getInputStream();

        if (Objects.isNull(contentLengthMax)
                || contentLengthMax.longValue() < 0)
            return this.input.read(bytes, offset, length);

        if (Objects.nonNull(this.exception))
            throw this.exception;

        try {
            final int total = this.input.read(bytes, offset, length);
            if (total > 0)
                this.limit -= total;
            return total;
        } catch (IOException exception) {
            this.exception = exception;
            throw exception;
        } finally {
            if (this.limit < 0) {
                this.exception = new MetaInputStreamLimitException();
                throw this.exception;
            }
        }
    }

    @Override
    public byte[] readAllBytes()
            throws IOException {

        if (Objects.isNull(this.input))
            this.input = this.request.getInputStream();

        if (Objects.isNull(contentLengthMax)
                || contentLengthMax.longValue() < 0)
            return this.input.readAllBytes();

        if (Objects.nonNull(this.exception))
            throw this.exception;

        try {return this.input.readNBytes((int) this.limit);
        } catch (IOException exception) {
            this.exception = exception;
            throw exception;
        } finally {
            this.limit = 0;
            if (this.input.read() >= 0) {
                this.exception = new MetaInputStreamLimitException();
                throw this.exception;
            }
        }
    }

    static class MetaInputStreamLimitException extends IOException {
        MetaInputStreamLimitException() {
            super();
        }
    }
}