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
import lombok.NonNull;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

class FileSystem {

    private final TreeMap<String, FileSystem.Entry> tree;

    private final Date creationDate;

    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    FileSystem() {
        this.tree = new TreeMap<>();
        this.creationDate = FileSystem.getBuildDate();
    }

    private static Date getBuildDate() {

        final String source = FileSystem.class.getName().replaceAll("\\.", "/") + ".class";
        final URL url = FileSystem.class.getClassLoader().getResource(source);
        if (Objects.nonNull(url.getProtocol())) {
            if (url.getProtocol().equals("jar"))
                return new Date(new java.io.File(url.getFile().replaceAll("(?i)(^file:)|(!.*$)", "")).lastModified());
            if (url.getProtocol().equals("file"))
                return new Date(new java.io.File(url.getFile()).lastModified());
        }
        return null;
    }

    private static String probeContentType(final String file) {
        try {return Files.probeContentType(Path.of(file));
        } catch (IOException exception) {
            return "application/octet-stream";
        }
    }

    private static String defaultContentType() {
        return FileSystem.probeContentType("none");
    }

    private static String recognizeContentType(final String extension) {
        if (Objects.isNull(extension)
                || extension.isBlank())
            return FileSystem.defaultContentType();
        return FileSystem.probeContentType("none." + extension);
    }

    private static String normalizePath(String path) {

        if (Objects.isNull(path)
                || path.isBlank())
            path = "/";
        path = "/" + path.replace('\\', '/').trim();
        path = path.replaceAll("/+(?=/)", "");
        path = Paths.get(path).normalize().toString();
        path = path.replace('\\', '/');

        // Special characters only relevant for Windows (:*?"'<>|)
        if (path.matches("^.*[\\*\\?\"'<>\\|].*$"))
            throw new InvalidPathException(path, "Illegal character found: :*?\"'<>|");
        // Paths based only on spaces and dots are often a problem
        if (path.matches(".*/[\\s\\.]+(/.*)?$"))
            throw new InvalidPathException(path, "Illegal character sequence found");

        return path;
    }

    Entry map(final ApiDavMapping mappingAnnotation, final Object object, final Method callback)
            throws FileSystemException {

        String path = mappingAnnotation.path();
        path = path.replace('\\', '/');
        path = path.replaceAll("//+$", "").trim();
        if (path.isBlank()) {
            if (mappingAnnotation.path().isBlank())
                throw new FileSystemException("Invalid mapping path");
            else throw new FileSystemException("Invalid mapping path: " + path);
        }

        String name = path.replaceAll("[^/]+$", "");
        if (name.isBlank()) {
            if (mappingAnnotation.path().isBlank())
                throw new FileSystemException("Invalid mapping path");
            else throw new FileSystemException("Invalid mapping path: " + path);
        }

        Date creationDate = this.creationDate;

        Date lastModified = null;
        if (!mappingAnnotation.lastModified().isBlank()) {
            try {lastModified = new SimpleDateFormat(DATE_FORMAT).parse(mappingAnnotation.lastModified().trim());
            } catch (ParseException exception) {
                throw new FileSystemException("Invalid mapping lastModified: " + exception.getMessage());
            }
        }

        long contentLength = Math.max(-1, mappingAnnotation.contentLength());

        String contentType = null;
        if (!mappingAnnotation.contentType().isBlank())
            contentType = mappingAnnotation.contentType().trim();

        boolean isHidden = mappingAnnotation.isHidden();
        boolean isReadOnly = mappingAnnotation.isReadOnly();
        boolean isPermitted = mappingAnnotation.isPermitted();

        File file = File.builder()
                .path(path)
                .name(name)
                .creationDate(creationDate)
                .lastModified(lastModified)
                .contentLength(contentLength)
                .contentType(contentType)
                .isHidden(isHidden)
                .isHidden(isReadOnly)
                .isPermitted(isPermitted)
                .build();

        // TODO: callbacks

        return file;
    }

    @Getter
    @AllArgsConstructor
    abstract static class Entry {

        private final String path;
        private final String name;
        private final Date creationDate;
        private final Date lastModified;
        private final boolean isHidden;
        private final boolean isReadOnly;
    }

    @Getter @NonNull
    static class Folder extends Entry {

        private final Set<Entry> collection;

        @Builder(access=AccessLevel.PRIVATE)
        Folder(final String path, final String name,
                final Date creationDate, final Date lastModified, final boolean isHidden, final boolean isReadOnly,
                final Set<Entry> collection) {
            super(path, name, creationDate, lastModified, isHidden, isReadOnly);
            this.collection = collection;
        }
    }

    @Getter
    static class File extends Entry {

        private final long contentLength;
        private final String contentType;
        private final boolean isPermitted;
        private final Map<Class<? extends Annotation>, Callback> callbacks;

        @Builder(access=AccessLevel.PRIVATE)
        File(final String path, final String name,
                final long contentLength, final String contentType, final Date creationDate, final Date lastModified,
                final boolean isHidden, final boolean isReadOnly, final boolean isPermitted,
                final Map<Class<? extends Annotation>, Callback> callbacks) {
            super(path, name, creationDate, lastModified, isHidden, isReadOnly);

            this.contentLength = contentLength;
            this.contentType = contentType;
            this.isPermitted = isPermitted;

            this.callbacks = callbacks;
        }

        @Getter
        @Builder(access=AccessLevel.PRIVATE)
        private static class Callback<T extends ApiDavMapping & ApiDavProperty> {

            private final T type;
            private final Object object;
            private final Method method;
        }
    }
}