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

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * <p>
 *   MetaInpoutStream is an {@link InputStream} with read-only meta
 *   information.
 * </p>
 * <p>
 *   MetaInputStream can be limited with {@code ContentLengthMax}. If the value
 *   of {@code ContentLengthMax} greater than or equal to {@code 0}, only the
 *   so defined amount of data can be read from the data stream. Overwriting it
 *   will cause {@link MetaInputStreamLimitException}.
 * </p>
 * <p>
 *   Another special feature is the handling of an {@link IOException} that
 *   occur when reading in limited mode. An {@link IOException} is the
 *   permanent one, because it is not known how many bytes could be read, which
 *   can distort the limit. Without limiting, the data stream behaves normally.
 * </p>
 *
 * MetaInputStream 1.0.0 20210728<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210728
 */
@Builder(access=AccessLevel.PACKAGE)
@AllArgsConstructor(access=AccessLevel.PACKAGE)
public class MetaInputStream extends InputStream {

    // NOTE: Lombok is awesome, but it doesn't create a usable JavaDoc
    // and then using Delombok isn't so great.

    private final HttpServletRequest request;

    private final String  contentType;
    private final Integer contentLength;
    private final Integer contentLengthMax;

    private InputStream input;

    private int limit;

    private IOException exception;

    /**
     * Return ContentType to the incoming data stream.
     * @return ContentType to the incoming data stream
     */
    public String getContentType() {
        return this.contentType;
    }

    /**
     * Return ContentLength to the incoming data stream.
     * @return ContentLength to the incoming data stream
     */
    public Integer getContentLength() {
        return this.contentLength;
    }

    /**
     * Return ContentLengthMax as limit for the incoming data stream.
     * @return ContentLengthMax as limit for the incoming data stream
     */
    public Integer getContentLengthMax() {
        return this.contentLengthMax;
    }

    private void advanceAccess()
            throws IOException {

        if (Objects.isNull(this.input)) {
            this.input = this.request.getInputStream();
            if (Objects.nonNull(this.contentLengthMax)
                    && this.contentLengthMax >= 0)
                this.limit = this.contentLengthMax;
        }

        if (Objects.nonNull(this.exception))
            throw this.exception;
    }

    @Override
    public int read()
            throws IOException {

        this.advanceAccess();

        if (Objects.isNull(this.contentLengthMax)
                || this.contentLengthMax < 0)
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
                || this.contentLengthMax < 0)
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
                || this.contentLengthMax < 0)
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
                || this.contentLengthMax < 0)
            return this.input.readAllBytes();

        try {return this.input.readNBytes(this.limit);
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