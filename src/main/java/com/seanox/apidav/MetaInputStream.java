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
package com.seanox.apidav;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * MetaInpoutStream is an {@link InputStream} with read-only meta information.<br>
 * <br>
 * MetaInputStream can be limited with {@code ContentLengthMax}.
 * If the value of {@code ContentLengthMax} greater than or equal to {@code 0},
 * only the so defined amount of data can be read from the data stream.
 * Overwriting it will cause {@link MetaInputStreamLimitException}.<br>
 * <br>
 * Another special feature is the handling of an {@link IOException} that occur
 * when reading in limited mode. An {@link IOException} is the permanent one,
 * because it is not known how many bytes could be read, which can distort the
 * limit. Without limiting, the data stream behaves normally.<br>
 * <br>
 * MetaInputStream 1.0.0 20210720<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210720
 */
@Builder(access=AccessLevel.PACKAGE)
public class MetaInputStream extends InputStream {

    private final HttpServletRequest request;

    @Getter(AccessLevel.PUBLIC) private final String  contentType;
    @Getter(AccessLevel.PUBLIC) private final Integer contentLength;
    @Getter(AccessLevel.PUBLIC) private final Integer contentLengthMax;

    private InputStream input;

    private int limit;

    private IOException exception;

    private void advanceAccess()
            throws IOException {

        if (Objects.isNull(this.input)) {
            this.input = this.request.getInputStream();
            if (Objects.nonNull(this.contentLengthMax)
                    && this.contentLengthMax.intValue() >= 0)
                this.limit = this.contentLengthMax.intValue();
        }

        if (Objects.nonNull(this.exception))
            throw this.exception;
    }

    @Override
    public int read()
            throws IOException {

        this.advanceAccess();

        if (Objects.isNull(this.contentLengthMax)
                || this.contentLengthMax.intValue() < 0)
            return this.input.read();

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
    public int read(final byte[] bytes)
            throws IOException {

        this.advanceAccess();

        if (Objects.isNull(this.contentLengthMax)
                || this.contentLengthMax.intValue() < 0)
            return this.input.read(bytes);

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
    public int read(final byte[] bytes, final int offset, final int length)
            throws IOException {

        this.advanceAccess();

        if (Objects.isNull(this.contentLengthMax)
                || this.contentLengthMax.intValue() < 0)
            return this.input.read(bytes, offset, length);

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

        this.advanceAccess();

        if (Objects.isNull(this.contentLengthMax)
                || this.contentLengthMax.intValue() < 0)
            return this.input.readAllBytes();

        try {return this.input.readNBytes((int)this.limit);
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

        private static final long serialVersionUID = 8486198748528435570L;

        MetaInputStreamLimitException() {
            super();
        }
    }
}