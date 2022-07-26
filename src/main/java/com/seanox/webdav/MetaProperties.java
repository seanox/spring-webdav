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
import lombok.Setter;

import java.net.URI;
import java.util.Date;

/**
 * <p>
 *   MetaProperties, read-only collector with all attributes of the virtual
 *   entity.
 * </p>
 *
 * MetaProperties 1.0.0 20210729<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210729
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

    private MetaProperties() {
    }

    /**
     * Returns the path of the virtual entity as URI.
     * @return the path of the virtual entity as URI
     */
    public URI getUri() {
        return URI.create(this.uri.toString());
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