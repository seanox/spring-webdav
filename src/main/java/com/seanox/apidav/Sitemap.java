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
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

// Rules (similar error behavior as mapping from RestController):
// - Ambiguous mapping causes SitemapException
// - Virtual paths must be unique (case insensitive)
// - Collisions (file + file / folder + folder / folder + file / file + folder) cause SitemapException

class Sitemap {

    private final TreeMap<String, Entry> tree;

    private static final Date CREATION_DATE = Sitemap.getBuildDate();

    Sitemap() {
        this.tree = new TreeMap<>();
    }

    private static Date getBuildDate() {

        final String source = Sitemap.class.getName().replaceAll("\\.", "/") + ".class";
        final URL url = Sitemap.class.getClassLoader().getResource(source);
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
        return Sitemap.probeContentType("none");
    }

    private static String recognizeContentType(final String extension) {
        if (Objects.isNull(extension)
                || extension.isBlank())
            return Sitemap.defaultContentType();
        return Sitemap.probeContentType("none." + extension);
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
            throws SitemapException {

        // Files cannot already exist, because none are created recursively.
        if (entry instanceof File
                && tree.containsKey(entry.getPath().toLowerCase()))
            throw new SitemapException("Ambiguous Mapping: " + entry.getPath());

        // Parent entries can only be folders.
        String parentPath = entry.getParent();
        if (tree.containsKey(parentPath.toLowerCase())
                && !(tree.get(parentPath.toLowerCase()) instanceof Folder))
            throw new SitemapException("Ambiguous Mapping: " + entry.getPath());

        Folder parentFolder = (Folder)tree.get(parentPath.toLowerCase());
        if (entry instanceof Folder
                && ((Folder)entry).isRoot()) {
            if (Objects.nonNull(parentFolder))
                return parentFolder;
            tree.put(entry.getPath().toLowerCase(), entry);
            return entry;
        }

        if (Objects.isNull(parentFolder))
            parentFolder = (Folder) Sitemap.add(tree, Folder.builder()
                    .path(parentPath)
                    .name(parentPath.replaceAll("^.*/(?=[^/]*$)", ""))
                    .creationDate(Sitemap.CREATION_DATE)
                    .lastModified(Sitemap.CREATION_DATE)
                    .collection(new HashSet<>())
                    .build());

        entry.path = parentFolder.getPath().replaceAll("/+$", "") + "/" + entry.getName();
        tree.put(entry.getPath().toLowerCase(), entry);
        parentFolder.getCollection().add(entry);

        return entry;
    }

    File map(final Callback... callbacks)
            throws SitemapException {

        final ApiDavMapping.MappingCallback mappingCallback = (ApiDavMapping.MappingCallback)Arrays.stream(callbacks)
                .filter(callback -> callback.getType().equals(Callback.Type.Mapping))
                .reduce((first, next) -> first).orElse(null);
        if (Objects.isNull(mappingCallback))
            throw new SitemapException("Mapping is missing");

        String path;
        try {path = Sitemap.normalizePath(mappingCallback.getPath());
        } catch (InvalidPathException exception) {
            if (Objects.isNull(exception.getReason())
                    || exception.getReason().isBlank())
                throw new SitemapException("Invalid mapping path");
            throw new SitemapException("Invalid mapping path: " + exception.getReason().trim());
        }

        path = path.replace('\\', '/');
        path = path.replaceAll("//+$", "").trim();
        if (path.isBlank()) {
            if (mappingCallback.getPath().isBlank())
                throw new SitemapException("Invalid mapping path");
            else throw new SitemapException("Invalid mapping path: " + mappingCallback.getPath().trim());
        }

        String name = path.replaceAll("^.*/(?=[^/]*$)", "");
        if (name.isBlank()) {
            if (mappingCallback.getPath().isBlank())
                throw new SitemapException("Invalid mapping path");
            else throw new SitemapException("Invalid mapping path: " + mappingCallback.getPath().trim());
        }

        final File file = File.builder()
                .path(path)
                .name(name)
                .creationDate(Sitemap.CREATION_DATE)
                .lastModified(mappingCallback.getLastModified())
                .contentLength(Math.max(-1, mappingCallback.getContentLength()))
                .contentType(mappingCallback.getContentType())
                .isReadOnly(mappingCallback.isReadOnly())
                .isHidden(mappingCallback.isHidden())
                .isPermitted(mappingCallback.isPermitted())
                .callbacks(new HashSet<>(Arrays.asList(callbacks)))
                .build();

        // First of all, the implementation is not thread-safe.
        // It is not required for WebDAV/apiDAV implementation.

        // To avoid adding unnecessary entries to the tree in case of errors, a
        // working copy is used. Only if no errors occur, the copy is merged
        // into the final tree.

        // The Add method creates recursive parent structures of folders, this
        // is not good in case of error when adding them to the final tree.
        // Sitemap can be used as an instance (which is not planned) and if
        // the map method is called and it generates a SitemapException, the
        // error can be caught and the instance from Sitemap will be used
        // further. In that case no automatically recursively created folders
        // should be added to the final tree.

        // PutAll to merge looks strange, but the TreeMap replaces eponymous
        // and so it works as expected.

        final TreeMap<String, Sitemap.Entry> treeWorkspace = (TreeMap<String, Entry>)this.tree.clone();
        final Entry entry = Sitemap.add(treeWorkspace, file);
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
                    .append(entry.getName())
                    .append(System.lineSeparator());
        }
        return builder.toString().trim();
    }

    @Getter(AccessLevel.PACKAGE)
    @AllArgsConstructor(access=AccessLevel.PRIVATE)
    abstract static class Entry {

        private String path;

        private final String name;
        private final Date creationDate;
        private final Date lastModified;
        private final boolean isHidden;
        private final boolean isReadOnly;

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

        private final long contentLength;
        private final String contentType;
        private final boolean isPermitted;
        private final Set<Callback> callbacks;

        @Builder(access=AccessLevel.PRIVATE)
        File(final String path, final String name,
                final long contentLength, final String contentType, final Date creationDate, final Date lastModified,
                final boolean isHidden, final boolean isReadOnly, final boolean isPermitted,
                final Set<Callback> callbacks) {
            super(path, name, creationDate, lastModified, isHidden, isReadOnly);

            this.contentLength = contentLength;
            this.contentType = contentType;
            this.isPermitted = isPermitted;

            this.callbacks = callbacks;
        }
    }
}