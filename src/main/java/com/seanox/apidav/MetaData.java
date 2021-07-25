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
import lombok.Getter;

import java.net.URI;
import java.util.Date;

/**
 * MetaData, a writable collector containing all relevant attributes for a
 * virtual entity.<br>
 * <br>
 * MetaData 1.0.0 20210717<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210717
 */
@Getter(AccessLevel.PUBLIC)
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