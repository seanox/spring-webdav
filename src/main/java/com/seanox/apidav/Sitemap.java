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
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Sitemap for mapping a virtual file system.
 *
 * Rules (similar error behavior as mapping from RestController):
 * <ul>
 *   <li>Ambiguous mapping causes SitemapException</li>
 *   <li>Path must be unique (case insensitive), otherwise SitemapException</li>
 *   <li>Path must start with slash, otherwise SitemapException</li>
 *   <li>Not permitted (unauthorized) entries are not included in the sitemap</li>
 *   <li>Not permitted (unauthorized) entries are used as non-existent as 404</li>
 *   <li>Empty folders are hidden, e.g. if included files are not allowed or hidden/li>
 * </ul>
 *
 * Sitemap 1.0.0 20210708
 * Copyright (C) 2021 Seanox Software Solutions
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210708
 */
class Sitemap {

    private final TreeMap<String, Entry> tree;
    private final Properties<Object> data;
    private final Properties<Object> meta;
    private final List<Annotation[]> trace;

    private static final Date CREATION_DATE = Sitemap.getBuildDate();

    Sitemap() {

        this.tree  = new TreeMap<>();
        this.data  = new Properties<>();
        this.meta  = new Properties<>();
        this.trace = new ArrayList<>();
    }

    Sitemap share(Properties<Object> properties)
            throws SitemapException {

        // The trace is the basis for sharing, which is very complex due to the
        // reference to the parent sitemap instance. That's why a trace is
        // created to build the sitemap, so that for sharing a new instance can
        // be easily created based on the existing source information.

        final Sitemap sitemap = new Sitemap();
        for (final Annotation[] annotations : this.trace)
            sitemap.map(annotations);
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
            return Defaults.contentType;
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

        final Annotation.Mapping mappingAnnotation = (Annotation.Mapping)Arrays.stream(annotations)
                .filter(annotation -> annotation.getType().equals(Annotation.AnnotationType.Mapping)).findFirst().orElse(null);
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
            throw new SitemapException("Invalid mapping path: " + mappingAnnotation.getPath().trim());
        }

        if (!path.startsWith("/")) {
            if (mappingAnnotation.getPath().isBlank())
                throw new SitemapException("Invalid mapping path");
            throw new SitemapException("Invalid mapping path: " + mappingAnnotation.getPath().trim());
        }

        final String name = path.replaceAll("^.*/(?=[^/]*$)", "");
        if (name.isBlank()) {
            if (mappingAnnotation.getPath().isBlank())
                throw new SitemapException("Invalid mapping path");
            throw new SitemapException("Invalid mapping path: " + mappingAnnotation.getPath().trim());
        }

        final File file = new File(path, annotations);

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

        // The trace is the basis for sharing, which is very complex due to the
        // reference to the parent sitemap instance. That's why a trace is
        // created to build the sitemap, so that for sharing a new instance can
        // be easily created based on the existing source information.
        this.trace.add(annotations);

        return (File)entry;
    }

    Entry locate(final String path) {

        if (Objects.isNull(path))
            return null;

        final Entry entry = this.tree.get(Sitemap.normalizePath(path).toLowerCase());
        if (Objects.isNull(entry))
            return null;

        if (entry.isFile()) {
            final File file = (File)entry;
            if (file.isPermitted())
                return file;
            return null;
        }

        return entry;
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

    static class Defaults {
        static final Long    contentLength = Long.valueOf(-1);
        static final String  contentType   = "application/octet-stream";
        static final Date    lastModified  = Sitemap.getBuildDate();
        static final Date    creationDate  = Sitemap.getBuildDate();
        static final Boolean isReadOnly    = Boolean.TRUE;
        static final Boolean isHidden      = Boolean.FALSE;
        static final Boolean isPermitted   = Boolean.TRUE;
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

        String getIdentifier() {
            Date date = this.getLastModified();
            if (Objects.isNull(date))
                date = this.getCreationDate();
            if (Objects.isNull(date))
                return null;
            return Long.toString(this.getLastModified().getTime(), 36).toUpperCase();
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

        @Override
        boolean isHidden() {
            for (final Entry entry : this.getCollection())
                if (!entry.isHidden())
                    return false;
            return !this.isRoot();
        }

        Collection<Entry> getCollection() {
            return this.collection;
        }
    }

    class File extends Entry {

        private Attribute accept;
        private Attribute contentLength;
        private Attribute contentLengthMax;
        private Attribute contentType;
        private Attribute lastModified;
        private Attribute creationDate;
        private Attribute isReadOnly;
        private Attribute isHidden;
        private Attribute isPermitted;
        private Attribute isAccepted;

        @Getter(AccessLevel.PACKAGE) private Callback readCallback;
        @Getter(AccessLevel.PACKAGE) private Callback writeCallback;
        @Getter(AccessLevel.PACKAGE) private Callback metaCallback;

        private File(final String path, Annotation... annotations) {

            super(path);

            for (final Annotation annotation : annotations) {
                if (annotation instanceof Annotation.Attribute) {
                    final Annotation.Attribute attributeAnnotation = (Annotation.Attribute)annotation;
                    final Callback callback = new Callback(attributeAnnotation.getObject(), attributeAnnotation.getMethod());
                    final Annotation.Attribute.AttributeType attributeType = attributeAnnotation.attributeType;

                    if (Annotation.Attribute.AttributeType.ReadOnly.equals(attributeType))
                        this.isReadOnly = callback;
                    else if (Annotation.Attribute.AttributeType.Hidden.equals(attributeType))
                        this.isHidden = callback;
                    else if (Annotation.Attribute.AttributeType.Permitted.equals(attributeType))
                        this.isPermitted = callback;

                    else if (Annotation.Attribute.AttributeType.ContentType.equals(attributeType))
                        this.contentType = callback;
                    else if (Annotation.Attribute.AttributeType.ContentLength.equals(attributeType))
                        this.contentLength = callback;
                    else if (Annotation.Attribute.AttributeType.CreationDate.equals(attributeType))
                        this.creationDate = callback;
                    else if (Annotation.Attribute.AttributeType.LastModified.equals(attributeType))
                        this.lastModified = callback;

                    else if (Annotation.Attribute.AttributeType.Accept.equals(attributeType))
                        this.accept = callback;
                    else if (Annotation.Attribute.AttributeType.ContentLengthMax.equals(attributeType))
                        this.contentLengthMax = callback;
                    else if (Annotation.Attribute.AttributeType.Accepted.equals(attributeType))
                        this.isAccepted = callback;

                } else if (annotation instanceof Annotation.Input) {
                    final Annotation.Input inputAnnotation = (Annotation.Input)annotation;
                    this.writeCallback = new Callback(inputAnnotation.getObject(), inputAnnotation.getMethod());

                    if (Objects.nonNull(inputAnnotation.getAccept())
                            && !inputAnnotation.getAccept().isBlank())
                        this.accept = new Static(inputAnnotation.getAccept());
                    if (inputAnnotation.getContentLengthMax() >= 0)
                        this.contentLengthMax = new Static(Long.valueOf(inputAnnotation.getContentLengthMax()));

                    if (Objects.nonNull(inputAnnotation.getExpressions())) {
                        for (final Annotation.Attribute.AttributeExpression attributeExpression : inputAnnotation.getExpressions()) {
                            final Expression expression = new Expression(attributeExpression.expression);
                            if (Annotation.Attribute.AttributeType.Accept.equals(attributeExpression.type))
                                this.accept = expression;
                            if (Annotation.Attribute.AttributeType.ContentLengthMax.equals(attributeExpression.type))
                                this.contentLengthMax = expression;
                            if (Annotation.Attribute.AttributeType.Accepted.equals(attributeExpression.type))
                                this.isAccepted = expression;
                        }
                    }

                } else if (annotation instanceof Annotation.Mapping) {
                    final Annotation.Mapping mappingAnnotation = (Annotation.Mapping)annotation;
                    this.readCallback = new Callback(mappingAnnotation.getObject(), mappingAnnotation.getMethod());

                    if (mappingAnnotation.getContentLength() >= 0)
                        this.contentLengthMax = new Static(Long.valueOf(mappingAnnotation.getContentLength()));
                    if (Objects.nonNull(mappingAnnotation.getContentType())
                            && !mappingAnnotation.getContentType().isBlank())
                        this.contentType = new Static(mappingAnnotation.getContentType());
                    if (Objects.nonNull(mappingAnnotation.getCreationDate()))
                        this.creationDate = new Static(mappingAnnotation.getCreationDate());
                    if (Objects.nonNull(mappingAnnotation.getLastModified()))
                        this.lastModified = new Static(mappingAnnotation.getLastModified());
                    this.isReadOnly = new Static(Boolean.valueOf(mappingAnnotation.isReadOnly()));
                    this.isHidden = new Static(Boolean.valueOf(mappingAnnotation.isHidden()));
                    this.isPermitted = new Static(Boolean.valueOf(mappingAnnotation.isPermitted()));

                    if (Objects.nonNull(mappingAnnotation.getExpressions())) {
                        for (final Annotation.Attribute.AttributeExpression attributeExpression : mappingAnnotation.getExpressions()) {
                            final Expression expression = new Expression(attributeExpression.expression);
                            if (Annotation.Attribute.AttributeType.ContentType.equals(attributeExpression.type))
                                this.contentType = expression;
                            if (Annotation.Attribute.AttributeType.ContentLength.equals(attributeExpression.type))
                                this.contentLength = expression;
                            if (Annotation.Attribute.AttributeType.CreationDate.equals(attributeExpression.type))
                                this.creationDate = expression;
                            if (Annotation.Attribute.AttributeType.LastModified.equals(attributeExpression.type))
                                this.lastModified = expression;
                            if (Annotation.Attribute.AttributeType.ReadOnly.equals(attributeExpression.type))
                                this.isReadOnly = expression;
                            if (Annotation.Attribute.AttributeType.Hidden.equals(attributeExpression.type))
                                this.isHidden = expression;
                            if (Annotation.Attribute.AttributeType.Permitted.equals(attributeExpression.type))
                                this.isPermitted = expression;
                        }
                    }

                } else if (annotation instanceof Annotation.Meta) {
                    final Annotation.Meta metaAnnotation = (Annotation.Meta)annotation;
                    this.metaCallback = new Callback(metaAnnotation.getObject(), metaAnnotation.getMethod());
                }
            }
        }

        // The attributes use a sitemap instance related (and thus request
        // related) attribute cache. This avoids that expressions and callbacks
        // are called more than once by the getter. The construct may not be
        // self-explanatory, therefore a short explanation. Each request uses
        // its own sitemap instance. Sitemap::data contains the request-related
        // properties, which are then used for the expressions, among other
        // things. Sitemap::meta is a cache of attributes for a sitemap
        // instance. After calling the share method a new sitemap instance is
        // created and Sitemap::data as well as Sitemap::meta are then empty
        // and filled with the usage.
        //
        // For the use of the attributes the following priority exists:
        //     1. Dynamic value from the attribute-method implementation
        //     2. Dynamic value from the meta-method implementation
        //     3. Dynamic value from the annotation expression
        //     4. Static value from annotation
        //     5. Default value from the class
        //
        // A lot of logic can be called and therefore the cache for the
        // attributes.

        private <T> T eval(final Annotation.Attribute.AttributeType attributeType, final Attribute attribute, final T fallback) {

            if (Objects.isNull(attribute))
                return fallback;

            if (!Sitemap.this.meta.containsKey(this.getPathUnique()))
                Sitemap.this.meta.put(this.getPathUnique(), new HashMap<>());
            final HashMap<Object, Object> metaMap = (HashMap<Object, Object>)Sitemap.this.meta.get(this.getPathUnique());
            if (metaMap.containsKey(attribute))
                return (T)metaMap.get(attribute);

            Object result = fallback;
            if (attribute instanceof Callback) {
                try {result = ((Callback)attribute).invoke(attributeType.attribute, Sitemap.File.this);
                } catch (Exception exception) {
                    while (exception instanceof InvocationTargetException)
                        exception = (Exception)((InvocationTargetException)exception).getTargetException();
                    throw new SitemapCallbackException(exception);
                }
            } else if (Objects.nonNull(this.metaCallback)) {
                if (!metaMap.containsKey(Meta.class))
                    try {metaMap.put(Meta.class, this.metaCallback.invoke(attributeType.attribute, Sitemap.File.this));
                    } catch (Exception exception) {
                        while (exception instanceof InvocationTargetException)
                            exception = (Exception)((InvocationTargetException)exception).getTargetException();
                        throw new SitemapCallbackException(exception);
                    }
                final Meta metaAttributes = (Meta)metaMap.get(Meta.class);
                if (Objects.nonNull(metaAttributes)) {
                    if (Annotation.Attribute.AttributeType.ContentLength.equals(attributeType))
                        result = metaAttributes.getContentLength();
                    else if (Annotation.Attribute.AttributeType.ContentType.equals(attributeType))
                        result = metaAttributes.getContentType();
                    else if (Annotation.Attribute.AttributeType.CreationDate.equals(attributeType))
                        result = metaAttributes.getCreationDate();
                    else if (Annotation.Attribute.AttributeType.LastModified.equals(attributeType))
                        result = metaAttributes.getLastModified();
                    else if (Annotation.Attribute.AttributeType.Hidden.equals(attributeType))
                        result = Boolean.valueOf(metaAttributes.isHidden());
                    else if (Annotation.Attribute.AttributeType.ReadOnly.equals(attributeType))
                        result = Boolean.valueOf(metaAttributes.isReadOnly());
                    else if (Annotation.Attribute.AttributeType.Permitted.equals(attributeType))
                        result = Boolean.valueOf(metaAttributes.isPermitted());
                    // TODO: And in the other cases (e.g. for input)?
                }
            } else if (attribute instanceof Expression) {
                result = ((Expression)attribute).eval();
            } else if (attribute instanceof Static) {
                result = ((Static)attribute).value;
            }

            if (Objects.nonNull(result)
                    && !fallback.getClass().equals(result.getClass())) {

                Class<?> type = result.getClass();
                try {type = (Class<?>)type.getDeclaredField("TYPE").get(null);
                } catch (Exception exception) {
                }

                final Class<?> source = fallback.getClass();
                try {result = source.getMethod("valueOf", type).invoke(null, result);
                } catch (Exception exception) {
                    while (exception instanceof InvocationTargetException)
                        exception = (Exception)((InvocationTargetException)exception).getTargetException();
                    throw new SitemapConverterException(exception);
                }
            }

            metaMap.put(attribute, result);

            return (T)result;
        }

        String getContentType() {
            return this.eval(Annotation.Attribute.AttributeType.ContentType, this.contentType, Sitemap.recognizeContentType(this.getName()));
        }

        Long getContentLength() {
            return this.eval(Annotation.Attribute.AttributeType.ContentLength, this.contentLength, Defaults.contentLength);
        }

        @Override
        Date getCreationDate() {
            return this.eval(Annotation.Attribute.AttributeType.CreationDate, this.creationDate, Defaults.creationDate);
        }

        @Override
        Date getLastModified() {
            return this.eval(Annotation.Attribute.AttributeType.LastModified, this.lastModified, Defaults.lastModified);
        }

        @Override
        boolean isReadOnly() {
            if (!this.isPermitted()
                    || Objects.isNull(this.writeCallback))
                return true;
            final Boolean result = this.eval(Annotation.Attribute.AttributeType.ReadOnly, this.isReadOnly, Defaults.isReadOnly);
            return Objects.nonNull(result) && result.booleanValue();
        }

        @Override
        boolean isHidden() {
            if (!this.isPermitted())
                return true;
            final Boolean result = this.eval(Annotation.Attribute.AttributeType.Hidden, this.isHidden, Defaults.isHidden);
            return Objects.nonNull(result) && result.booleanValue();
        }

        boolean isPermitted() {
            final Boolean result = this.eval(Annotation.Attribute.AttributeType.Permitted, this.isPermitted, Defaults.isPermitted);
            return Objects.nonNull(result) && result.booleanValue();
        }
    }

    abstract class Attribute {
    }

    class Static extends Attribute {

        private final Object value;

        Static(final Object value) {
            this.value = value;
        }
    }

    class Expression extends Attribute {

        private final org.springframework.expression.Expression expression;

        Expression(final org.springframework.expression.Expression expression) {
            this.expression = expression;
        }

        Object eval() {
            StandardEvaluationContext context = new StandardEvaluationContext();
            Sitemap.this.data.forEach(context::setVariable);
            return this.expression.getValue(context);
        }
    }

    class Callback extends Attribute {

        @Getter(AccessLevel.PACKAGE) private final Object object;
        @Getter(AccessLevel.PACKAGE) private final Method method;

        Callback(Object object, Method method) {
            this.object = object;
            this.method = method;
        }

        private Object[] composeArguments(Object... arguments) {

            final Map<Class<?>, Object> placeholder = new HashMap<>();
            Arrays.stream(arguments).forEach(argument -> {
                if (Objects.nonNull(argument))
                    placeholder.put(argument.getClass(), argument);
            });
            final List<Object> compose = new ArrayList<>();
            Arrays.stream(this.method.getParameterTypes()).forEach(parameterType ->
                compose.add(placeholder.get(parameterType))
            );
            return compose.toArray(new Object[0]);
        }

        Object invoke(Object... arguments)
                throws InvocationTargetException, IllegalAccessException {
            this.method.setAccessible(true);
            final List<Object> argumentList = new ArrayList<>(Arrays.asList(arguments));
            argumentList.add(Sitemap.this.data.clone());
            return this.method.invoke(this.object, this.composeArguments(argumentList.toArray(new Object[0])));
        }
    }
}