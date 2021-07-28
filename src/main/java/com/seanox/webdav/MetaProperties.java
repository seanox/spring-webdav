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
package com.seanox.webdav;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;

import java.net.URI;
import java.util.Date;

/**
 * <p>
 *   MetaProperties, read-only collector with all attributes of the virtual
 *   entity.
 * </p>
 *
 * MetaProperties 1.0.0 20210728<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210728
 */
@Setter(AccessLevel.PACKAGE)
@Builder(access=AccessLevel.PACKAGE)
@AllArgsConstructor(access=AccessLevel.PACKAGE)
public class MetaProperties implements Cloneable {

    // NOTE: Lombok is awesome, but it doesn't create a usable JavaDoc
    // and then using Delombok isn't so great.

    private URI uri;
    private String contentType;
    private Integer contentLength;
    private Date creationDate;
    private Date lastModified;
    private boolean isReadOnly;
    private boolean isHidden;
    private boolean isAccepted;
    private boolean isPermitted;

    MetaProperties() {
    }

    /**
     * Returns the path of the virtual entity as URI.
     * @return the path of the virtual entity as URI
     */
    public URI getUri() {
        return this.uri;
    }

    /**
     * Returns the ContentType of the virtual entity.
     * @return the ContentType of the virtual entity
     */
    public String getContentType() {
        return this.contentType;
    }

    /**
     * Returns the ContentLength of the virtual entity.
     * @return the ContentLength of the virtual entity
     */
    public Integer getContentLength() {
        return this.contentLength;
    }

    /**
     * Returns the CreationDate of the virtual entity.
     * @return the CreationDate of the virtual entity
     */
    public Date getCreationDate() {
        return this.creationDate;
    }

    /**
     * Returns the LastModified of the virtual entity.
     * @return the LastModified of the virtual entity
     */
    public Date getLastModified() {
        return this.lastModified;
    }

    /**
     * Returns {@code true} when the virtual entity is read-only.
     * @return {@code true} when the virtual entity is read-only
     */
    public boolean isReadOnly() {
        return this.isReadOnly;
    }

    /**
     * Returns {@code true} when the virtual entity is hidden.
     * @return {@code true} when the virtual entity is hidden
     */
    public boolean isHidden() {
        return this.isHidden;
    }

    /**
     * Returns {@code true} when the virtual entity is accepted.
     * @return {@code true} when the virtual entity is accepted
     */
    public boolean isAccepted() {
        return this.isAccepted;
    }

    /**
     * Returns {@code true} when the virtual entity is permitted.
     * @return {@code true} when the virtual entity is permitted
     */
    public boolean isPermitted() {
        return this.isPermitted;
    }

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