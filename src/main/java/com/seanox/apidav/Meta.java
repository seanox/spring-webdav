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
import lombok.Setter;

import java.util.Date;

/**
 * Meta data collector as an alternative to single attributes.
 *
 * Meta 1.1.0 20210708<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * Alle Rechte vorbehalten.
 *
 * @author  Seanox Software Solutions
 * @version 1.1.0 20210708
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@Builder(access=AccessLevel.PACKAGE)
public final class Meta implements Cloneable {

    private final Long contentLength;
    private final String contentType;
    private final Date lastModified;
    private final Date creationDate;
    private final boolean isReadOnly;
    private final boolean isHidden;
    private final boolean isPermitted;

    @Override
    protected Meta clone() {
        try {return (Meta)super.clone();
        } catch (CloneNotSupportedException exception) {
            return Meta.builder()
                    .contentLength(this.contentLength)
                    .contentType(this.contentType)
                    .lastModified(this.lastModified)
                    .creationDate(this.creationDate)
                    .isReadOnly(this.isReadOnly)
                    .isHidden(this.isHidden)
                    .isPermitted(this.isPermitted)
                    .build();
        }
    }
}