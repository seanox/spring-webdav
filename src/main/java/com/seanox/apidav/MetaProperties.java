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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.util.Date;

/**
 * MetaProperties readonly collector as an alternative to single attributes.
 *
 * MetaProperties 1.0.0 20210715
 * Copyright (C) 2021 Seanox Software Solutions
 * Alle Rechte vorbehalten.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210715
 */
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PROTECTED)
@Builder(access=AccessLevel.PACKAGE)
@AllArgsConstructor(access=AccessLevel.PACKAGE)
public class MetaProperties implements Cloneable {

    private URI uri;
    private String contentType;
    private Integer contentLength;
    private Date creationDate;
    private Date lastModified;
    private boolean isReadOnly;
    private boolean isHidden;
    private boolean isAccepted;
    private boolean isPermitted;

    @Override
    public MetaProperties clone() {
        try {return (MetaProperties)super.clone();
        } catch (CloneNotSupportedException exception) {
            return MetaProperties.builder()
                    .uri(this.uri)
                    .contentType(this.contentType)
                    .contentLength(this.contentLength)
                    .creationDate(this.creationDate)
                    .lastModified(this.lastModified)
                    .isReadOnly(this.isReadOnly)
                    .isHidden(this.isHidden)
                    .isAccepted(this.isAccepted)
                    .isPermitted(this.isPermitted)
                    .build();
        }
    }
}