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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

// Rules (similar error behavior as mapping from RestController):
// - Ambiguous mapping causes FileSystemException
// - Virtual paths must be unique (case insensitive)
// - Collisions (file + file / folder + folder / folder + file / file + folder) cause FileSystemException

class FileSystem {

    private final TreeMap<String, Entry> tree;

    private static final Date CREATION_DATE = FileSystem.getBuildDate();

    private static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";

    FileSystem() {
        this.tree = new TreeMap<>();
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
            throw new InvalidPathException(path, "Illegal character found");
        // Paths based only on spaces and dots are often a problem
        if (path.matches(".*/[\\s\\.]+(/.*)?$"))
            throw new InvalidPathException(path, "Illegal character sequence found");

        return path;
    }

    private static Entry add(final TreeMap<String, Entry> tree, final Entry entry)
            throws FileSystemException {

        // Files cannot already exist, because none are created recursively.
        if (entry instanceof File
                && tree.containsKey(entry.getPath().toLowerCase()))
            throw new FileSystemException("Ambiguous Mapping: " + entry.getPath());

        // Parent entries can only be folders.
        String parentPath = entry.getParent();
        if (tree.containsKey(parentPath.toLowerCase())
                && !(tree.get(parentPath.toLowerCase()) instanceof Folder))
            throw new FileSystemException("Ambiguous Mapping: " + entry.getPath());

        Folder parentFolder = (Folder)tree.get(parentPath.toLowerCase());
        if (entry instanceof Folder
                && ((Folder)entry).isRoot()) {
            if (Objects.nonNull(parentFolder))
                return parentFolder;
            tree.put(entry.getPath().toLowerCase(), entry);
            return entry;
        }

        if (Objects.isNull(parentFolder))
            parentFolder = (Folder)FileSystem.add(tree, Folder.builder()
                    .path(parentPath)
                    .name(parentPath.replaceAll("^.*/(?=[^/]*$)", ""))
                    .creationDate(FileSystem.CREATION_DATE)
                    .lastModified(FileSystem.CREATION_DATE)
                    .collection(new HashSet<>())
                    .build());

        entry.path = parentFolder.getPath().replaceAll("/+$", "") + "/" + entry.getName();
        tree.put(entry.getPath().toLowerCase(), entry);
        parentFolder.getCollection().add(entry);

        return entry;
    }

    File map(final ApiDavMapping mappingAnnotation, final Callback... callback)
            throws FileSystemException {

        String path = mappingAnnotation.path();
        try {path = FileSystem.normalizePath(path);
        } catch (InvalidPathException exception) {
            String message = "Invalid mapping path";
            if (Objects.isNull(exception.getReason())
                    || exception.getReason().isBlank())
                throw new FileSystemException("Invalid mapping path");
            throw new FileSystemException("Invalid mapping path: " + exception.getReason().trim());
        }

        path = path.replace('\\', '/');
        path = path.replaceAll("//+$", "").trim();
        if (path.isBlank()) {
            if (mappingAnnotation.path().isBlank())
                throw new FileSystemException("Invalid mapping path");
            else throw new FileSystemException("Invalid mapping path: " + path);
        }

        String name = path.replaceAll("^.*/(?=[^/]*$)", "");
        if (name.isBlank()) {
            if (mappingAnnotation.path().isBlank())
                throw new FileSystemException("Invalid mapping path");
            else throw new FileSystemException("Invalid mapping path: " + path);
        }

        Date creationDate = this.CREATION_DATE;

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

        final File file = File.builder()
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
        // TODO: add/set callbacks

        // First of all, the implementation is not thread-safe.
        // It is not required for WebDAV/apiDAV implementation.

        // To avoid adding unnecessary entries to the tree in case of errors, a
        // working copy is used. Only if no errors occur, the copy is merged
        // into the final tree.

        // The Add method creates recursive parent structures of folders, this
        // is not good in case of error when adding them to the final tree.
        // FileSystem can be used as an instance (which is not planned) and if
        // the map method is called and it generates a FileSystemException, the
        // error can be caught and the instance from FileSystem will be used
        // further. In that case no automatically recursively created folders
        // should be added to the final tree.

        // PutAll to merge looks strange, but the TreeMap replaces eponymous
        // and so it works as expected.

        final TreeMap<String, FileSystem.Entry> treeWorkspace = (TreeMap<String, Entry>)this.tree.clone();
        final Entry entry = FileSystem.add(treeWorkspace, file);
        this.tree.putAll(treeWorkspace);
        return (File)entry;
    }

    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder();
        for (final Entry entry : this.tree.values()) {
            if (entry instanceof Folder
                    && ((Folder)entry).isRoot())
                continue;
            builder.append(entry.getParent().replaceAll("/[^/]+", "  ").replaceAll("/+$", ""))
                    .append(entry instanceof Folder ? "+" : "-")
                    .append(" ")
                    .append(entry.getName() + System.lineSeparator());
        }
        return builder.toString().trim();
    }

    @Getter(AccessLevel.PACKAGE)
    @AllArgsConstructor
    abstract static class Entry {

        private String path;
        private String name;
        private Date creationDate;
        private Date lastModified;
        private boolean isHidden;
        private boolean isReadOnly;

        String getParent() {
            return this.path.replaceAll("((?<=.)/[^/]+$)|([^/]+$)", "");
        }
    }

    @Getter(AccessLevel.PACKAGE) @NonNull
    static class Folder extends Entry {

        private final Set<Entry> collection;

        @Builder(access=AccessLevel.PRIVATE)
        Folder(final String path, final String name,
                final Date creationDate, final Date lastModified, final boolean isHidden, final boolean isReadOnly,
                final Set<Entry> collection) {
            super(path, name, creationDate, lastModified, isHidden, isReadOnly);
            this.collection = collection;
        }

        boolean isRoot() {
            return this.getPath().equals("/");
        }
    }

    @Getter(AccessLevel.PACKAGE)
    static class File extends Entry {

        private long contentLength;
        private String contentType;
        private boolean isPermitted;
        private Map<Class<? extends Annotation>, Callback> callbacks;

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
    }
}