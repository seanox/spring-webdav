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

import java.net.URI;
import java.util.Date;

/**
 * MetaData, a writable collector containing all relevant attributes for a
 * virtual entity.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210728
 */
public class MetaData extends MetaProperties {

    MetaData(final URI uri, final String contentType, final Integer contentLength,
            final Date creationDate, final Date lastModified,
            final boolean isReadOnly, final boolean isHidden, final boolean isAccepted, final boolean isPermitted) {
        super(uri, contentType, contentLength, creationDate, lastModified, isReadOnly, isHidden, isAccepted, isPermitted);
    }

    @Override
    public void setContentType(final String contentType) {
        super.setContentType(contentType);
    }

    @Override
    public void setContentLength(final Integer contentLength) {
        super.setContentLength(contentLength);
    }

    @Override
    public void setCreationDate(final Date creationDate) {
        super.setCreationDate(creationDate);
    }

    @Override
    public void setLastModified(final Date lastModified) {
        super.setLastModified(lastModified);
    }

    @Override
    public void setReadOnly(final boolean isReadOnly) {
        super.setReadOnly(isReadOnly);
    }

    @Override
    public void setHidden(final boolean isHidden) {
        super.setHidden(isHidden);
    }

    @Override
    public void setAccepted(final boolean isAccepted) {
        super.setAccepted(isAccepted);
    }

    @Override
    public void setPermitted(final boolean isPermitted) {
        super.setPermitted(isPermitted);
    }

    @Override
    public MetaData clone() {
        return (MetaData)super.clone();
    }
}