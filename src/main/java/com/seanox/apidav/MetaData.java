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

import java.util.Date;

/**
 * MetaData writable collector as an alternative to single attributes.
 *
 * MetaData 1.0.0 20210710<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * Alle Rechte vorbehalten.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210710
 */
@Getter(AccessLevel.PUBLIC)
public class MetaData extends MetaProperties implements Cloneable {

    MetaData(final String path, final String contentType, final Long contentLength,
            final Date creationDate, final Date lastModified,
            final boolean isReadOnly, final boolean isHidden, final boolean isPermitted) {
        super(path, contentType, contentLength, creationDate, lastModified, isReadOnly, isHidden, isPermitted);
    }

    @Override
    public void setContentType(final String contentType) {
        super.setContentType(contentType);
    }

    @Override
    public void setContentLength(final Long contentLength) {
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
    public void setPermitted(final boolean isPermitted) {
        super.setPermitted(isPermitted);
    }

    @Override
    public MetaData clone() {
        return new MetaData(
                this.getPath(),
                this.getContentType(),
                this.getContentLength(),
                this.getCreationDate(),
                this.getLastModified(),
                this.isReadOnly(),
                this.isHidden(),
                this.isPermitted());
    }
}