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

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;

// Rules (similar error behavior as mapping from RestController):
// - Ambiguous mapping causes SitemapException
// - Virtual paths must be unique (case insensitive), otherwise SitemapException
// - Collisions (file + file / folder + folder / folder + file / file + folder) cause SitemapException
// - Paths must start with slash, otherwise SitemapException (TODO)
// - Not permitted (unauthorized) entries are not included in the sitemap (TODO)
// - Not permitted (unauthorized) entries are used as non-existent (TODO)
// - Empty folders are not included in the Sitemap, e.g. if files in substructure are not permitted (TODO)

class Sitemap {

    private final TreeMap<String, Entry> tree;
    private final Properties<Object> data;

    private static final Date CREATION_DATE = Sitemap.getBuildDate();

    Sitemap() {

        this.tree = new TreeMap<>();
        this.data = new Properties<>();
    }

    Sitemap share(Properties<Object> properties) {

        final Sitemap sitemap = new Sitemap();
        sitemap.tree.putAll((Map)this.tree.clone());
        sitemap.data.putAll(properties.clone());
        return sitemap;
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

    private Entry add(final TreeMap<String, Entry> tree, final Entry entry)
            throws SitemapException {

        // Files cannot already exist, because none are created recursively.
        // The existing and colliding path in the tree is used as the message.
        if (entry.isFile()
                && tree.containsKey(entry.getPathUnique()))
            throw new SitemapException("Ambiguous Mapping: " + tree.get(entry.getPathUnique()).getPath());

        // Parent entries can only be folders.
        // Caution, no parent entry must exist at this time, so use the path.
        // Or with ambiguous mapping, it doesn't have to be a folder.
        // The existing and colliding path in the tree is used as the message.
        String parentPathUnique = entry.getParentPathUnique();
        if (Objects.nonNull(parentPathUnique)
                && tree.containsKey(parentPathUnique)
                && !tree.get(parentPathUnique).isFolder())
            throw new SitemapException("Ambiguous Mapping: " + entry.getPath());

        Folder parentFolder = entry.getParent();
        if (Objects.isNull(parentFolder)
                && !entry.isRoot())
            parentFolder = (Folder)this.add(tree, new Folder(entry.getParentPath()));

        tree.put(entry.getPathUnique(), entry);
        if (Objects.nonNull(parentFolder)
                && !entry.isRoot())
            parentFolder.getCollection().add(entry);

        return entry;
    }

    File map(final Annotation... annotations)
            throws SitemapException {

        final ApiDavMapping.MappingAnnotation mappingAnnotation = (ApiDavMapping.MappingAnnotation)Arrays.stream(annotations)
                .filter(annotation -> annotation.getType().equals(Annotation.Type.Mapping)).findFirst().orElse(null);
        if (Objects.isNull(mappingAnnotation))
            throw new SitemapException("Mapping is missing");

        String path;
        try {path = Sitemap.normalizePath(mappingAnnotation.getPath());
        } catch (InvalidPathException exception) {
            if (Objects.isNull(exception.getReason())
                    || exception.getReason().isBlank())
                throw new SitemapException("Invalid mapping path");
            throw new SitemapException("Invalid mapping path: " + exception.getReason().trim());
        }

        if (path.isBlank()) {
            if (mappingAnnotation.getPath().isBlank())
                throw new SitemapException("Invalid mapping path");
            else throw new SitemapException("Invalid mapping path: " + mappingAnnotation.getPath().trim());
        }

        final String name = path.replaceAll("^.*/(?=[^/]*$)", "");
        if (name.isBlank()) {
            if (mappingAnnotation.getPath().isBlank())
                throw new SitemapException("Invalid mapping path");
            else throw new SitemapException("Invalid mapping path: " + mappingAnnotation.getPath().trim());
        }

        final File file = new File(path);

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
        final Entry entry = this.add(treeWorkspace, file);
        this.tree.putAll(treeWorkspace);
        return (File)entry;
    }

    Entry locate(final String path) {
        if (Objects.isNull(path))
            return null;
        return this.tree.get(Sitemap.normalizePath(path).toLowerCase());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (final Entry entry : this.tree.values()) {
            if (entry.isRoot())
                continue;
            builder.append(entry.getParentPath().replaceAll("/[^/]+", "  ").replaceAll("/+$", ""))
                    .append(entry.isFolder() ? "+" : "-")
                    .append(" ")
                    .append(entry.getName())
                    .append(System.lineSeparator());
        }
        return builder.toString().trim();
    }

    abstract class Entry {

        private final String parent;
        private final String path;
        private final String name;

        private Entry(final String path) {

            String parent = Sitemap.normalizePath(path);
            parent = parent.replaceAll("/+[^/]*$", "");
            if (!parent.isEmpty()) {
                if (Sitemap.this.tree.containsKey(parent.toLowerCase()))
                    parent = Sitemap.this.tree.get(parent.toLowerCase()).getPath();
                this.parent = parent;
            } else this.parent = "/";

            this.name = path.replaceAll("^.*/(?=[^/]*$)", "");
            this.path = parent.replaceAll("/+$", "") + "/" + this.name;
        }

        boolean isRoot() {
            return this.getPath().equals("/");
        }

        boolean isFolder() {
            return this instanceof Folder;
        }

        boolean isFile() {
            return this instanceof File;
        }

        Folder getParent() {
            if (Objects.isNull(this.parent))
                return null;
            return (Folder)Sitemap.this.tree.get(this.parent);
        }

        String getParentPath() {
            return this.parent;
        }

        String getParentPathUnique() {
            if (Objects.isNull(this.parent))
                return null;
            return this.parent.toLowerCase();
        }

        String getPath() {
            return this.path;
        }

        String getPathUnique() {
            return this.getPath().toLowerCase();
        }

        String getName() {
            return this.name;
        }

        Date getCreationDate() {
            return CREATION_DATE;
        }

        Date getLastModified() {
            return CREATION_DATE;
        }

        boolean isHidden() {
            return false;
        }

        boolean isReadOnly() {
            return true;
        }
    }

    class Folder extends Entry {

        private final Collection<Entry> collection;

        private Folder(final String path) {
            super(path);
            this.collection = new ArrayList<>();
        }

        Collection<Entry> getCollection() {
            return this.collection;
        }
    }

    class File extends Entry {

        private long contentLength;
        private String contentType;
        private boolean isPermitted;
        private Set<Attribute> attributes;

        private File(final String path, Attribute... attributes) {
            super(path);
        }

        String getContentType() {
            return contentType;
        }

        long getContentLength() {
            return contentLength;
        }
    }

    static abstract class Attribute {
    }
    static class Static extends Attribute {
    }
    static class Expression extends Attribute {
    }
    static class Callback extends Attribute {
    }
}