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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ApiDavMapping {

    String path();
    long contentLength()  default -1;
    String contentType()  default "application/octet-stream";
    String lastModified() default "";
    String creationDate() default "";
    boolean isReadOnly()  default false;
    boolean isHidden()    default false;
    boolean isPermitted() default true;
    boolean isAccepted()  default true;

    @Getter(AccessLevel.PACKAGE)
    static class MappingCallback extends Callback {

        private long contentLength;
        private String contentType;
        private Date creationDate;
        private Date lastModified;
        private boolean isReadOnly;
        private boolean isHidden;
        private boolean isPermitted;
        private boolean isAccepted;

        @Builder(access=AccessLevel.PRIVATE)
        MappingCallback(final String path, final Type type, final Object object, final Method method,
                 final long contentLength, final String contentType, final Date creationDate, final Date lastModified,
                 final boolean isReadOnly, final boolean isHidden, final boolean isPermitted, final boolean isAccepted) {

            super(path, type, object, method);

            this.contentLength = contentLength;
            this.contentType   = contentType;
            this.creationDate  = creationDate;
            this.lastModified  = lastModified;
            this.isReadOnly    = isReadOnly;
            this.isHidden      = isHidden;
            this.isPermitted   = isPermitted;
            this.isAccepted    = isAccepted;
        }

        static MappingCallback create(final ApiDavMapping apiDavMapping, final Object object, final Method method)
                throws ParseException {

            return MappingCallback.builder()
                    .path(apiDavMapping.path())
                    .type(Type.Mapping)
                    .object(object)
                    .method(method)

                    .contentLength(apiDavMapping.contentLength())
                    .contentType(Callback.convertText(apiDavMapping.contentType()))
                    .creationDate(Callback.convertDateTime(apiDavMapping.creationDate()))
                    .lastModified(Callback.convertDateTime(apiDavMapping.lastModified()))
                    .isReadOnly(apiDavMapping.isReadOnly())
                    .isHidden(apiDavMapping.isHidden())
                    .isPermitted(apiDavMapping.isPermitted())
                    .isAccepted(apiDavMapping.isAccepted())
                    .build();
        }
    }
}