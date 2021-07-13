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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.UriUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// What is not allowed is handled as if it does not exist.

public class ApiDavFilter extends HttpFilter {

    private static final long serialVersionUID = 7637895578410477411L;

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiDavFilter.class);

    private Properties<Object> properties;

    private Sitemap sitemap;

    private static final String METHOD_OPTIONS   = "OPTIONS";
    private static final String METHOD_PROPFIND  = "PROPFIND";
    private static final String METHOD_PROPPATCH = "PROPPATCH";
    private static final String METHOD_HEAD      = "HEAD";
    private static final String METHOD_GET       = "GET";
    private static final String METHOD_MKCOL     = "MKCOL";
    private static final String METHOD_COPY      = "COPY";
    private static final String METHOD_MOVE      = "MOVE";
    private static final String METHOD_LOCK      = "LOCK";
    private static final String METHOD_PUT       = "PUT";
    private static final String METHOD_UNLOCK    = "UNLOCK";
    private static final String METHOD_DELETE    = "DELETE";

    private static final String HEADER_ALLOW            = "Allow";
    private static final String HEADER_CONTENT_LOCATION = "Content-Location";
    private static final String HEADER_DAV              = "DAV";
    private static final String HEADER_DEPTH            = "Depth";
    private static final String HEADER_ETAG             = "Etag";
    private static final String HEADER_IF               = "If";
    private static final String HEADER_IF_MATCH         = "If-Match";
    private static final String HEADER_IF_NONE_MATCH    = "If-None-Match";
    private static final String HEADER_LAST_MODIFIED    = "Last-Modified";
    private static final String HEADER_LOCATION         = "Location";
    private static final String HEADER_LOCK_TOKEN       = "Lock-Token";
    private static final String HEADER_MS_AUTHOR_VIA    = "MS-Author-Via";
    private static final String HEADER_TIMEOUT          = "Timeout";

    private static final String XML_ACTIVELOCK          = "activelock";
    private static final String XML_ALLPROP             = "allprop";
    private static final String XML_COLLECTION          = "collection";
    private static final String XML_CREATIONDATE        = "creationdate";
    private static final String XML_DEPTH               = "depth";
    private static final String XML_DISPLAYNAME         = "displayname";
    private static final String XML_EXCLUSIVE           = "exclusive";
    private static final String XML_GETCONTENTLENGTH    = "getcontentlength";
    private static final String XML_GETCONTENTTYPE      = "getcontenttype";
    private static final String XML_GETETAG             = "getetag";
    private static final String XML_GETLASTMODIFIED     = "getlastmodified";
    private static final String XML_HREF                = "href";
    private static final String XML_ISARCHIVE           = "isarchive";
    private static final String XML_ISCOLLECTION        = "iscollection";
    private static final String XML_ISHIDDEN            = "ishidden";
    private static final String XML_ISREADONLY          = "isreadonly";
    private static final String XML_ISSYSTEM            = "issystem";
    private static final String XML_LOCKDISCOVERY       = "lockdiscovery";
    private static final String XML_LOCKENTRY           = "lockentry";
    private static final String XML_LOCKROOT            = "lockroot";
    private static final String XML_LOCKSCOPE           = "lockscope";
    private static final String XML_LOCKTOKEN           = "locktoken";
    private static final String XML_LOCKTYPE            = "locktype";
    private static final String XML_MULTISTATUS         = "multistatus";
    private static final String XML_PROP                = "prop";
    private static final String XML_PROPNAME            = "propname";
    private static final String XML_PROPSTAT            = "propstat";
    private static final String XML_RESOURCETYPE        = "resourcetype";
    private static final String XML_RESPONSE            = "response";
    private static final String XML_STATUS              = "status";
    private static final String XML_SUPPORTEDLOCK       = "supportedlock";
    private static final String XML_TIMEOUT             = "timeout";
    private static final String XML_WIN32FILEATTRIBUTES = "Win32FileAttributes";
    private static final String XML_WRITE               = "write";

    /**
     * Constant for max. depth with infinite processing depth
     * Caution: More than 1 works, but Windows Explorer seems to have problems
     * and then displays everything deeper 1 in the same folder level.
     */
    private static final int WEBDAV_INFINITY = 1;

    /** Constant for PROPFIND to display all properties */
    private static final int WEBDAV_FIND_ALL_PROP = 1;

    /** Constant for  PROPFIND to specify a property mask */
    private static final int WEBDAV_FIND_BY_PROPERTY = 0;

    /** Constant for PROPFIND to find property names */
    private static final int WEBDAV_FIND_PROPERTY_NAMES = 2;

    /** Constant of the default XML namespace for WebDAV */
    private static final String WEBDAV_DEFAULT_XML_NAMESPACE = "d";

    /** Constant of the default XML namespace URI for WebDAV */
    private static final String WEBDAV_DEFAULT_XML_NAMESPACE_URI = "DAV:";

    private static final String DATETIME_FORMAT_CREATION_DATE = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String DATETIME_FORMAT_LAST_MODIFIED = "E, dd MMM yyyy HH:mm:ss z";

    private final Collection<String> filterUrlPatternMappings;

    public ApiDavFilter() {
        this.filterUrlPatternMappings = new ArrayList<>();
    }

    private static Class<?>[] getClassHierarchy(Object source) {

        if (Objects.isNull(source))
            return null;

        if (!(source instanceof Class))
            source = source.getClass();

        final List<Class<?>> classes = new ArrayList<>();
        while (Objects.nonNull(source)) {
            classes.add((Class<?>)source);
            source = ((Class<?>)source).getSuperclass();
        }

        Collections.reverse(classes);

        return classes.toArray(new Class[0]);
    }

    @Override
    public void init(final FilterConfig filterConfig)
            throws ServletException {

        this.sitemap = new Sitemap();

        // Validation of URL pattern mappings, only patterns that start with a
        // slash, optionally contain word-character sequences separated by
        // slashes and if the patterns finally end with a slash or slash with
        // asterisk are supported.
        final String filterName = filterConfig.getFilterName();
        final FilterRegistration filterRegistration = filterConfig.getServletContext().getFilterRegistration(filterName);
        if (Objects.nonNull(filterRegistration))
            this.filterUrlPatternMappings.addAll(filterRegistration.getUrlPatternMappings());
        for (String urlPatternMapping : this.filterUrlPatternMappings)
            if (!urlPatternMapping.matches("^/(\\w+/){0,}\\*?$"))
                throw new ServletException("Invalid URL pattern mapping for filter: " + this.getClass().getName());

        try {
            final ServletContext servletContext = filterConfig.getServletContext();
            final ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
            final Collection<Annotation> annotations = new ArrayList<>();
            for (final String beanName : applicationContext.getBeanDefinitionNames()) {
                final Object object = applicationContext.getBean(beanName);
                for (final Class<?> source: ApiDavFilter.getClassHierarchy(object)) {
                    for (final Method method : source.getDeclaredMethods()) {
                        for (final java.lang.annotation.Annotation annotation : method.getDeclaredAnnotations()) {
                            // TODO: Check for uniqueness of annotations, multiple occurrences cause exception
                            if (annotation.annotationType().equals(ApiDavAttributeMapping.class))
                                annotations.add(Annotation.Attribute.create((ApiDavAttributeMapping)annotation, object, method));
                            else if (annotation.annotationType().equals(ApiDavAttributeMapping.ApiDavAttributeMappings.class))
                                for (final ApiDavAttributeMapping apiDavAttribute : ((ApiDavAttributeMapping.ApiDavAttributeMappings)annotation).value())
                                    annotations.add(Annotation.Attribute.create(apiDavAttribute, object, method));
                            else if (annotation.annotationType().equals(ApiDavInputMapping.class))
                                annotations.add(Annotation.Input.create((ApiDavInputMapping)annotation, object, method));
                            else if (annotation.annotationType().equals(ApiDavInputMapping.ApiDavInputMappings.class))
                                for (final ApiDavInputMapping apiDavAttribute : ((ApiDavInputMapping.ApiDavInputMappings)annotation).value())
                                    annotations.add(Annotation.Input.create(apiDavAttribute, object, method));
                            else if (annotation.annotationType().equals(ApiDavMapping.class))
                                annotations.add(Annotation.Mapping.create((ApiDavMapping)annotation, object, method));
                            else if (annotation.annotationType().equals(ApiDavMapping.ApiDavMappings.class))
                                for (final ApiDavMapping apiDavAttribute : ((ApiDavMapping.ApiDavMappings)annotation).value())
                                    annotations.add(Annotation.Mapping.create(apiDavAttribute, object, method));
                            else if (annotation.annotationType().equals(ApiDavMetaMapping.class))
                                annotations.add(Annotation.Meta.create((ApiDavMetaMapping)annotation, object, method));
                            else if (annotation.annotationType().equals(ApiDavMetaMapping.ApiDavMetaMappings.class))
                                for (final ApiDavMetaMapping apiDavMeta : ((ApiDavMetaMapping.ApiDavMetaMappings)annotation).value())
                                    annotations.add(Annotation.Meta.create(apiDavMeta, object, method));
                        }
                    }
                }
            }

            // Validation that a mapping annotation exists for all paths.
            // The check at the startup avoids unwanted effects at runtime.
            // Because without mapping other apiDav annotations have no effect.
            final Collection<String> mappingPaths = new ArrayList<>();
            annotations.stream()
                    .filter(annotation -> annotation.getType().equals(Annotation.AnnotationType.Mapping))
                    .sorted((annotation, compare) -> annotation.getPath().compareToIgnoreCase(compare.getPath()))
                    .forEach(annotation -> mappingPaths.add(annotation.getPath().toLowerCase()));
            for (final Annotation annotation : annotations)
                if (!mappingPaths.contains(annotation.getPath().toLowerCase()))
                    throw new AnnotationException("Mapping annotation missing for path: " + annotation.getPath());

            for (final String mappingPath : mappingPaths)
                try {this.sitemap.map(annotations.stream().filter(annotation -> annotation.getPath().equalsIgnoreCase(mappingPath)).toArray(Annotation[]::new));
                } catch (SitemapException exception) {
                    throw new AnnotationException(exception.getMessage());
                }

            LOGGER.info(this.getClass().getSimpleName() + " was established");
            // TODO: Output of endpoints
            LOGGER.info("Sitemap");
            LOGGER.info("---");
            Arrays.stream(this.sitemap.toString().split("\\R")).forEach(LOGGER::info);

        } catch (Exception exception) {
            throw new ServletException(exception);
        }
    }

    private String locateRequestContextPath(final HttpServletRequest request) {

        final String requestURI = URLDecoder.decode(request.getRequestURI(), StandardCharsets.UTF_8);
        for (String urlPatternMapping : this.filterUrlPatternMappings) {
            urlPatternMapping = urlPatternMapping.replaceAll("/+\\**$", "");
            if (requestURI.startsWith(urlPatternMapping + "/")
                    || requestURI.equals(urlPatternMapping))
                return urlPatternMapping;
        }
        return "";
    }

    private String locateSitemapPath(final HttpServletRequest request) {

        final String requestURI = URLDecoder.decode(request.getRequestURI(), StandardCharsets.UTF_8);
        if (this.filterUrlPatternMappings.isEmpty())
            return requestURI;
        for (String urlPatternMapping : this.filterUrlPatternMappings) {
            urlPatternMapping = urlPatternMapping.replaceAll("\\*?$", "");
            if (requestURI.startsWith(urlPatternMapping))
                return requestURI.substring(urlPatternMapping.length() -1);
            if ((requestURI + "/").equals(urlPatternMapping))
                return "/";
        }
        return null;
    }

    private Sitemap.Entry locateSitemapEntry(final Sitemap sitemap, final HttpServletRequest request) {

        final String pathInfo = this.locateSitemapPath(request);
        if (Objects.isNull(pathInfo))
            throw new NotFoundState();

        if (pathInfo.endsWith("/")
                && !request.getRequestURI().endsWith("/"))
            throw new FoundState(request.getRequestURI() + "/");

        final String method = request.getMethod().toUpperCase();

        final Sitemap.Entry entry = sitemap.locate(pathInfo);
        if (Objects.isNull(entry)) {
            if (METHOD_PUT.equals(method))
                throw new ForbiddenState();
            throw new NotFoundState();
        }

        if (entry.isFolder()
                && !pathInfo.endsWith("/"))
            throw new FoundState(request.getRequestURI() + "/");
        if (entry.isFile()
                && pathInfo.endsWith("/"))
            throw new FoundState(request.getRequestURI().replaceAll("/+$", ""));

        if (!Arrays.asList(METHOD_HEAD, METHOD_GET, METHOD_LOCK, METHOD_PUT).contains(method))
            return entry;

        // If-None-Match / If-Match is only supported for: GET / HEAD / PUT / LOCK

        final String identifier = entry.getIdentifier();
        if (Objects.nonNull(identifier)) {
            final String ifNoneMatch = request.getHeader(HEADER_IF_NONE_MATCH);
            if (Objects.nonNull(ifNoneMatch)
                    && !ifNoneMatch.isBlank()) {
                if (Arrays.asList(ifNoneMatch.split("\\s*,\\s*")).contains("\"" + identifier + "\"")) {
                    if (METHOD_HEAD.equals(method)
                            || METHOD_GET.equals(method))
                        throw new NotModifiedState(identifier);
                    if (METHOD_PUT.equals(method)
                            || METHOD_LOCK.equals(method))
                        throw new PreconditionFailedState();
                }
            }
            final String ifMatch = request.getHeader(HEADER_IF_MATCH);
            if (Objects.nonNull(ifMatch)
                    && !ifMatch.isBlank()) {
                if (!Arrays.asList(ifMatch.split("\\s*,\\s*")).contains("\"" + identifier + "\"")) {
                    if (METHOD_PUT.equals(method)
                            || METHOD_LOCK.equals(method)
                            || METHOD_HEAD.equals(method)
                            || METHOD_GET.equals(method))
                        throw new PreconditionFailedState();
                }
            }
        }

        return entry;
    }

    private static int getDepth(final HttpServletRequest request) {

        final String depth = request.getHeader(HEADER_DEPTH);
        if (Objects.isNull(depth)
                || (!depth.equals("0") && depth.equals("1")))
            return WEBDAV_INFINITY;
        return request.getIntHeader(HEADER_DEPTH);
    }

    private static Document readXmlRequest(final HttpServletRequest request)
            throws IOException, SAXException, ParserConfigurationException {

        if (request.getContentLength() <= 0)
            return null;

        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        return factory.newDocumentBuilder().parse(request.getInputStream());
    }

    private static Properties<String> getPropertiesFromNode(final Node node) {

        final Properties<String> properties = new Properties<>();
        final NodeList nodeList = node.getChildNodes();
        IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item)
                .filter(streamNode -> streamNode.getNodeType() == Node.ELEMENT_NODE)
                .forEach(streamNode -> {
                    String streamNodeValue = streamNode.getNodeValue();
                    if (Objects.isNull(streamNodeValue))
                        streamNodeValue = streamNode.getTextContent();
                    properties.put(streamNode.getLocalName(), streamNodeValue);
                });
        return properties;
    }

    private static void collectProperties(final XmlWriter xmlWriter, final String contextUrl, final Sitemap.Entry entry,
                  final int type, final Properties<String> properties)
            throws IOException {

        xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_RESPONSE, XmlWriter.ElementType.OPENING);
        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_HREF, UriUtils.encodePath(contextUrl + entry.getPath(), StandardCharsets.UTF_8.name()));

        final String displayName = UriUtils.encodePath(entry.getName(), StandardCharsets.UTF_8.name());

        final String creationDate  = Objects.nonNull(entry.getCreationDate()) ? DateTime.formatDate(entry.getCreationDate(), DATETIME_FORMAT_CREATION_DATE) : null;
        final String lastModified  = Objects.nonNull(entry.getLastModified()) ? DateTime.formatDate(entry.getLastModified(), DATETIME_FORMAT_LAST_MODIFIED) : null;
        final String contentType   = entry.isFile() && Objects.nonNull(((Sitemap.File)entry).getContentType()) ? ((Sitemap.File)entry).getContentType() : null;
        final String contentLength = entry.isFile() && Objects.nonNull(((Sitemap.File)entry).getContentLength()) ? ((Sitemap.File)entry).getContentLength().toString() : null;
        final String isCollection  = String.valueOf(entry.isFolder());

        final String isReadOnly = String.valueOf(entry.isReadOnly());
        final String isHidden   = String.valueOf(entry.isHidden());
        final String isSystem   = Boolean.FALSE.toString();
        final String isArchive  = Boolean.FALSE.toString();

        final String etag = Objects.nonNull(entry.getIdentifier()) ? "\"" + entry.getIdentifier() + "\"" : "";

        // Win32FileAttributes
        // see also https://docs.microsoft.com/de-de/windows/win32/api/fileapi/nf-fileapi-setfileattributesa?redirectedfrom=MSDN
        // readOnly: 0x01, hidden: 0x02, system: 0x04, directory: 0x10, achive: 0x20
        final String win32FileAttributes = Integer.toHexString(
                (entry.isReadOnly() ? 0x01 : 0)
                        | (entry.isHidden() ? 0x02 : 0)
                        | (entry.isFolder() ? 0x10 : 0));

        switch (type) {
            case WEBDAV_FIND_ALL_PROP:
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROPSTAT, XmlWriter.ElementType.OPENING);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.OPENING);

                xmlWriter.writePropertyData(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_DISPLAYNAME, displayName);
                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISCOLLECTION, isCollection);
                if (Objects.nonNull(creationDate))
                    xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_CREATIONDATE, creationDate);
                if (Objects.nonNull(lastModified))
                    xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETLASTMODIFIED, lastModified);

                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISREADONLY, isReadOnly);
                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISHIDDEN,  isHidden);
                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISSYSTEM, isSystem);
                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISARCHIVE, isArchive);

                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_WIN32FILEATTRIBUTES, win32FileAttributes);

                if (entry.isFolder()) {
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_RESOURCETYPE, XmlWriter.ElementType.OPENING);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_COLLECTION, XmlWriter.ElementType.EMPTY);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_RESOURCETYPE, XmlWriter.ElementType.CLOSING);
                } else {
                    if (Objects.nonNull(contentType))
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETCONTENTTYPE, contentType);
                    if (Objects.nonNull(contentLength))
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETCONTENTLENGTH, contentLength);
                    xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETETAG, etag);

                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_SUPPORTEDLOCK, XmlWriter.ElementType.OPENING);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "DAV:", XML_LOCKENTRY, XmlWriter.ElementType.OPENING);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKSCOPE, XmlWriter.ElementType.OPENING);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_EXCLUSIVE, XmlWriter.ElementType.EMPTY);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKSCOPE, XmlWriter.ElementType.CLOSING);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKTYPE, XmlWriter.ElementType.OPENING);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_WRITE, XmlWriter.ElementType.EMPTY);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKTYPE, XmlWriter.ElementType.CLOSING);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKENTRY, XmlWriter.ElementType.CLOSING);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_SUPPORTEDLOCK, XmlWriter.ElementType.CLOSING);
                }

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.CLOSING);
                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_STATUS, new SuccessState().getStatusLine());
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROPSTAT, XmlWriter.ElementType.CLOSING);

                break;

            case WEBDAV_FIND_PROPERTY_NAMES:

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROPSTAT, XmlWriter.ElementType.OPENING);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.OPENING);

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_DISPLAYNAME, XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISCOLLECTION, XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_CREATIONDATE, XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETLASTMODIFIED, XmlWriter.ElementType.EMPTY);

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISREADONLY, XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISHIDDEN, XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISSYSTEM, XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISARCHIVE, XmlWriter.ElementType.EMPTY);

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_WIN32FILEATTRIBUTES, XmlWriter.ElementType.EMPTY);

                if (entry.isFolder()) {
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_RESOURCETYPE, XmlWriter.ElementType.EMPTY);
                } else {
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETCONTENTTYPE, XmlWriter.ElementType.EMPTY);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETCONTENTLENGTH, XmlWriter.ElementType.EMPTY);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETETAG, XmlWriter.ElementType.EMPTY);
                }

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.CLOSING);
                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_STATUS, new SuccessState().getStatusLine());
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROPSTAT, XmlWriter.ElementType.CLOSING);

                break;

            case WEBDAV_FIND_BY_PROPERTY:

                List<String> list = new ArrayList<>();

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROPSTAT, XmlWriter.ElementType.OPENING);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.OPENING);

                for (String property : properties.keySet()) {

                    if (property.equals(XML_DISPLAYNAME)) {
                        xmlWriter.writePropertyData(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_DISPLAYNAME, displayName);
                    } else if (property.equals(XML_ISCOLLECTION)) {
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISCOLLECTION, isCollection);
                    } else if (property.equals(XML_CREATIONDATE)
                            && Objects.nonNull(creationDate)) {
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_CREATIONDATE, creationDate);
                    } else if (property.equals(XML_GETLASTMODIFIED)
                            && Objects.nonNull(lastModified)) {
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETLASTMODIFIED, lastModified);

                    } else if (property.equals(XML_ISREADONLY)) {
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISREADONLY, isReadOnly);
                    } else if (property.equals(XML_ISHIDDEN)) {
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISHIDDEN, isHidden);
                    } else if (property.equals(XML_ISSYSTEM)) {
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISSYSTEM, isSystem);
                    } else if (property.equals(XML_ISARCHIVE)) {
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISARCHIVE, isArchive);

                    } else if (property.equals(XML_WIN32FILEATTRIBUTES)) {
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_WIN32FILEATTRIBUTES, win32FileAttributes);
                    } else {
                        if (entry.isFolder()
                                && property.equals(XML_RESOURCETYPE)) {
                            xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_RESOURCETYPE, XmlWriter.ElementType.OPENING);
                            xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_COLLECTION, XmlWriter.ElementType.EMPTY);
                            xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_RESOURCETYPE, XmlWriter.ElementType.CLOSING);
                        } else if (!entry.isFolder()
                                && property.equals(XML_GETCONTENTLENGTH)
                                && Objects.nonNull(contentLength)) {
                            xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETCONTENTLENGTH, contentLength);
                        } else if (!entry.isFolder()
                                && property.equals(XML_GETCONTENTTYPE)
                                && Objects.nonNull(contentType)) {
                            xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETCONTENTTYPE, contentType);
                        } else if (!entry.isFolder()
                                && property.equals(XML_GETETAG)) {
                            xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETETAG, etag);
                        } else list.add(property);
                    }
                }

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.CLOSING);
                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_STATUS, new SuccessState().getStatusLine());
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROPSTAT, XmlWriter.ElementType.CLOSING);

                Iterator<String> iterator = list.iterator();
                if (iterator.hasNext()) {
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROPSTAT, XmlWriter.ElementType.OPENING);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.OPENING);

                    while (iterator.hasNext())
                        xmlWriter.writeElement(null, iterator.next(), XmlWriter.ElementType.EMPTY);

                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.CLOSING);
                    xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_STATUS, new NotFoundState().getStatusLine());
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROPSTAT, XmlWriter.ElementType.CLOSING);
                }

                break;
        }

        xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_RESPONSE, XmlWriter.ElementType.CLOSING);
    }

    private static void collectProperties(final XmlWriter xmlWriter, final String contextUrl, final Sitemap.Entry entry,
                    final int type, final Properties<String> properties, final int depth)
            throws IOException {

        ApiDavFilter.collectProperties(xmlWriter, contextUrl, entry, type, properties);
        if (entry.isFile()
                || depth <= 0)
            return;
        for (Sitemap.Entry folderEntry : ((Sitemap.Folder)entry).getCollection()) {
            if (folderEntry.isHidden())
                continue;
            ApiDavFilter.collectProperties(xmlWriter, contextUrl, folderEntry, type, properties, depth -1);
        }
    }

    private void doOptions(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response) {

        Sitemap.Entry entry = null;
        try {entry = this.locateSitemapEntry(sitemap, request);
        } catch (State state) {
        }

        response.setHeader(HEADER_DAV, "1, 2");
        response.setHeader(HEADER_MS_AUTHOR_VIA, "DAV");
        response.setHeader(HEADER_ALLOW, String.join(", ",
                ApiDavFilter.METHOD_OPTIONS,
                ApiDavFilter.METHOD_HEAD,
                ApiDavFilter.METHOD_GET,
                ApiDavFilter.METHOD_PROPFIND));
        if (Objects.nonNull(entry)
                && !entry.isReadOnly()) {
            response.setHeader(HEADER_ALLOW, response.getHeader(HEADER_ALLOW)
                    + ", " + String.join(", ",
                    ApiDavFilter.METHOD_LOCK,
                    ApiDavFilter.METHOD_PUT,
                    ApiDavFilter.METHOD_UNLOCK));
        }

        throw new SuccessState();
    }

    private void doPropfind(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {

        final Sitemap.Entry entry = this.locateSitemapEntry(sitemap, request);
        final String contextPath = this.locateRequestContextPath(request);

        try {
            final Properties<String> properties = new Properties<>();
            final Document document = ApiDavFilter.readXmlRequest(request);
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            try (final XmlWriter xmlWriter = new XmlWriter(buffer)) {
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE_URI,XML_MULTISTATUS, XmlWriter.ElementType.OPENING);

                final int depth = ApiDavFilter.getDepth(request);

                if (Objects.nonNull(document)) {
                    final Element root = document.getDocumentElement();
                    final NodeList nodeList = root.getChildNodes();
                    final Set<Node> nodeSet = IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item)
                            .filter(streamNode -> streamNode.getNodeType() == Node.ELEMENT_NODE).collect(Collectors.toSet());

                    if (nodeSet.stream().anyMatch(streamNode -> streamNode.getLocalName().equalsIgnoreCase(XML_ALLPROP))) {
                        ApiDavFilter.collectProperties(xmlWriter, contextPath, entry, WEBDAV_FIND_ALL_PROP, properties, depth);
                    } else if (nodeSet.stream().anyMatch(streamNode -> streamNode.getLocalName().equalsIgnoreCase(XML_PROP))) {
                        final Node propNode = nodeSet.stream().filter(streamNode -> streamNode.getLocalName().equalsIgnoreCase(XML_PROP)).findFirst().get();
                        properties.putAll(ApiDavFilter.getPropertiesFromNode(propNode));
                        ApiDavFilter.collectProperties(xmlWriter, contextPath, entry, WEBDAV_FIND_BY_PROPERTY, properties, depth);
                    } else if (nodeSet.stream().anyMatch(streamNode -> streamNode.getLocalName().equalsIgnoreCase(XML_PROPNAME))) {
                        ApiDavFilter.collectProperties(xmlWriter, contextPath, entry, WEBDAV_FIND_PROPERTY_NAMES, properties, depth);
                    } else {
                        ApiDavFilter.collectProperties(xmlWriter, contextPath, entry, WEBDAV_FIND_ALL_PROP, properties, depth);
                    }

                } else ApiDavFilter.collectProperties(xmlWriter, contextPath, entry, WEBDAV_FIND_ALL_PROP, properties, depth);

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_MULTISTATUS, XmlWriter.ElementType.CLOSING);
                xmlWriter.flush();
            }

            response.setStatus(new MultiStatusState().getStatusCode());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.TEXT_XML_VALUE);
            response.setContentLength(buffer.size());
            response.getOutputStream().write(buffer.toByteArray());

        } catch (SAXException | ParserConfigurationException exception) {
            throw new UnprocessableEntityState();
        }

        throw new MultiStatusState();
    }

    private void doHead(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response) {

        final Sitemap.Entry entry = this.locateSitemapEntry(sitemap, request);

        if (entry.isFolder())
            throw new NotFoundState();

        final Sitemap.File file = (Sitemap.File)entry;
        if (Objects.nonNull(file.getIdentifier()))
            response.setHeader(HEADER_ETAG, "\"" + file.getIdentifier() + "\"");
        if (Objects.nonNull(file.getLastModified()))
            response.setDateHeader(HEADER_LAST_MODIFIED, file.getLastModified().getTime());
        if (Objects.nonNull(file.getContentLength()))
            response.setContentLengthLong(file.getContentLength());
        if (Objects.nonNull(file.getContentType()))
            response.setContentType(file.getContentType());
        throw new SuccessState();
    }

    private void doGet(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {

        final Sitemap.Entry entry = this.locateSitemapEntry(sitemap, request);

        if (entry.isFolder())
            throw new NotFoundState();

        final Sitemap.File file = ((Sitemap.File)entry);
        final Sitemap.Callback readCallback = file.getReadCallback();

        try (final MetaOutputStream metaOutputStream = MetaOutputStream.builder()
                .response(response)
                .contentType(file.getContentType())
                .contentLength(file.getContentLength())
                .lastModified(file.getLastModified())
                .build()) {
            try {readCallback.invoke(URI.create(file.getPath()), file.getProperties(), metaOutputStream);
            } catch (Exception exception) {
                while (exception instanceof InvocationTargetException)
                    exception = (Exception)((InvocationTargetException)exception).getTargetException();
                LOGGER.error("GET callback failed", exception);
                throw new InternalServerErrorState();
            }
        }
        throw new SuccessState();
    }

    private void doPut(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response) {

        final Sitemap.Entry entry = this.locateSitemapEntry(sitemap, request);

        if (entry.isReadOnly())
            throw new ForbiddenState();

        response.setHeader(HEADER_CONTENT_LOCATION, this.locateSitemapPath(request));

        final Sitemap.File file = ((Sitemap.File)entry);
        final Sitemap.Callback writeCallback = file.getWriteCallback();
        final MetaInputStream metaInputStream = MetaInputStream.builder()
                .request(request)
                .contentType(file.getContentType())
                .contentLength(file.getContentLength())
                .build();
        try {writeCallback.invoke(URI.create(file.getPath()), file.getProperties(), metaInputStream);
        } catch (Exception exception) {
            while (exception instanceof InvocationTargetException)
                exception = (Exception)((InvocationTargetException)exception).getTargetException();
            LOGGER.error("PUT callback failed", exception);
            throw new InternalServerErrorState();
        }
        throw new NoContentState();
    }

    private void doLock(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {

        final Sitemap.Entry entry = this.locateSitemapEntry(sitemap, request);
        if (entry.isReadOnly())
            throw new ForbiddenState();

        // Lock and token are more a simulation not real.
        // Only Exclusive Write Locks are supported.
        // It  based on an echo of the If-OpaqueLockToken.
        // Timeout headers are ignored.

        String token = request.getHeader(HEADER_IF);
        if (Objects.nonNull(token)
                && token.matches("(?i)^<([a-z0-9]+(?:-[a-z0-9]+)+)>$"))
            token = token.replaceAll("(?i)^<([a-z0-9]+(?:-[a-z0-9]+)+>)$", "$1");
        else token = UUID.randomUUID().toString();

        String timeout = request.getHeader(HEADER_TIMEOUT);
        if (Objects.isNull(timeout)
                || !timeout.matches("(?i)^[a-z]-\\d+"))
            timeout = String.format("Second-%s", 60 *60 *24 *7);

        final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try (final XmlWriter xmlWriter = new XmlWriter(buffer)) {
            xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, WEBDAV_DEFAULT_XML_NAMESPACE_URI, XML_PROP, XmlWriter.ElementType.OPENING);
                xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKDISCOVERY, XmlWriter.ElementType.OPENING);
                    xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_ACTIVELOCK, XmlWriter.ElementType.OPENING);
                        xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKTYPE, XmlWriter.ElementType.OPENING);
                            xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_WRITE, XmlWriter.ElementType.EMPTY);
                        xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKTYPE, XmlWriter.ElementType.CLOSING);
                        xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKSCOPE, XmlWriter.ElementType.OPENING);
                            xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_EXCLUSIVE, XmlWriter.ElementType.EMPTY);
                        xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKSCOPE, XmlWriter.ElementType.CLOSING);
                        xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_DEPTH, XmlWriter.ElementType.OPENING);
                            xmlWriter.writeText("Infinity");
                        xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_DEPTH, XmlWriter.ElementType.CLOSING);
    
                        xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_TIMEOUT, XmlWriter.ElementType.OPENING);
                            xmlWriter.writeText(timeout);
                        xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_TIMEOUT, XmlWriter.ElementType.CLOSING);
    
                        xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKTOKEN, XmlWriter.ElementType.OPENING);
                            xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_HREF, XmlWriter.ElementType.OPENING);
                                xmlWriter.writeText(token);
                            xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_HREF, XmlWriter.ElementType.CLOSING);
                        xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKTOKEN, XmlWriter.ElementType.CLOSING);
    
                        xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKROOT, XmlWriter.ElementType.OPENING);
                            xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_HREF, XmlWriter.ElementType.OPENING);
                                xmlWriter.writeText(this.locateSitemapPath(request));
                            xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_HREF, XmlWriter.ElementType.CLOSING);
                        xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKROOT, XmlWriter.ElementType.CLOSING);
                    xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_ACTIVELOCK, XmlWriter.ElementType.CLOSING);
                xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKDISCOVERY, XmlWriter.ElementType.CLOSING);
            xmlWriter.writeElement(WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.CLOSING);
            xmlWriter.flush();
        }

        final String markup = buffer.toString();

        response.addHeader(HEADER_LOCK_TOKEN, "<" + token + ">");
        response.setContentType(MediaType.TEXT_XML.toString());
        response.setContentLength(markup.length());
        response.getOutputStream().write(markup.getBytes());
        throw new SuccessState();
    }

    private void doUnlock(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response) {

        final Sitemap.Entry entry = this.locateSitemapEntry(sitemap, request);
        if (entry.isReadOnly())
            throw new ForbiddenState();
        throw new NoContentState();
    }

    @Override
    protected void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws ServletException, IOException {

        final long timing = System.currentTimeMillis();

        // The sitemap is used as a request-based shared instance.
        // The values of the annotations can contain values, expressions and
        // callbacks, which must then be processed in relation to the request.

        if (Objects.isNull(this.properties)) {
            synchronized (this) {
                if (Objects.isNull(this.properties)) {

                    // Beans are cached so that not every request has to scan.
                    // Only beans with simple name without dot(s). The others
                    // should be internal things, but this assumption is not
                    // valid. Order of initialization is unknown, this is done
                    // with the first request.

                    final ServletContext servletContext = request.getServletContext();
                    final ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);

                    this.properties = new Properties<>();
                    for (final String beanName : Arrays.stream(applicationContext.getBeanDefinitionNames())
                            .filter(Predicate.not(entry -> entry.contains(".")))
                            .collect(Collectors.toSet()))
                        this.properties.put(beanName, applicationContext.getBean(beanName));

                    final Map<String, Object> contextMap = new HashMap<>();
                    this.properties.put("context", contextMap);
                    if (Objects.nonNull(request.getRemoteUser()))
                        contextMap.put("servlet", servletContext);
                    if (Objects.nonNull(request.getUserPrincipal()))
                        contextMap.put("application", applicationContext);
                }
            }
        }

        final Properties<Object> properties = this.properties.clone();

        final HttpSession session = request.getSession(false);
        if (Objects.nonNull(session)) {
            final Map<String, Object> sessionMap = new HashMap<>();
            properties.put("session", sessionMap);
            sessionMap.put("id", session.getId());
            if (session.getAttributeNames().hasMoreElements()) {
                final Map<String, Object> sessionAttributeMap = new HashMap<>();
                sessionMap.put("attributes", sessionAttributeMap);
                session.getAttributeNames().asIterator()
                        .forEachRemaining(attribute -> sessionAttributeMap.put(attribute, session.getAttribute(attribute)));
            }
        }

        final Map<String, Object> requestMap = new HashMap<>();
        properties.put("request", requestMap);
        if (Objects.nonNull(request.getRequestedSessionId())) {
            final Map<String, Object> requestSessionMap = new HashMap<>();
            requestMap.put("session", requestSessionMap);
            requestSessionMap.put("id", request.getRequestedSessionId());
        }
        if (request.getHeaderNames().hasMoreElements()) {
            final Map<String, Object> requestHeaderMap = new HashMap<>();
            requestMap.put("headers", requestHeaderMap);
            request.getHeaderNames().asIterator()
                    .forEachRemaining(header -> requestHeaderMap.put(header, request.getHeader(header)));
        }
        if (request.getAttributeNames().hasMoreElements()) {
            final Map<String, Object> requestAttributeMap = new HashMap<>();
            requestMap.put("attributes", requestAttributeMap);
            request.getAttributeNames().asIterator()
                    .forEachRemaining(attribute -> requestAttributeMap.put(attribute, request.getAttribute(attribute)));
        }

        if (Objects.nonNull(request.getRemoteUser())
                || Objects.nonNull(request.getUserPrincipal())) {
            final Map<String, Object> userMap = new HashMap<>();
            properties.put("user", userMap);
            if (Objects.nonNull(request.getRemoteUser()))
                userMap.put("remote", request.getRemoteUser());
            if (Objects.nonNull(request.getUserPrincipal()))
                userMap.put("principal", request.getUserPrincipal());
        }

        final Sitemap sitemap;
        try {sitemap = this.sitemap.share(properties);
        } catch (SitemapException exception) {
            throw new ServletException(exception);
        }

        // Short help to the often asked question where the path comes from:
        // PathInfo is not available, so the path has to be determined
        // laboriously from RequestURI and UrlPatternMappings of the filter.

        // There is no break and no return.
        // The methods all end with a state exception.
        // Thus the methods can be left simply without complex if-constructs
        // and the type of the return value is more flexible.

        try {
            switch (request.getMethod().toUpperCase()) {
                case ApiDavFilter.METHOD_OPTIONS:
                    this.doOptions(sitemap, request, response);
                case ApiDavFilter.METHOD_PROPFIND:
                    this.doPropfind(sitemap, request, response);
                case ApiDavFilter.METHOD_HEAD:
                    this.doHead(sitemap, request, response);
                case ApiDavFilter.METHOD_GET:
                    this.doGet(sitemap, request, response);
                case ApiDavFilter.METHOD_LOCK:
                    this.doLock(sitemap, request, response);
                case ApiDavFilter.METHOD_PUT:
                    this.doPut(sitemap, request, response);
                case ApiDavFilter.METHOD_UNLOCK:
                    this.doUnlock(sitemap, request, response);
                case ApiDavFilter.METHOD_PROPPATCH:
                case ApiDavFilter.METHOD_MKCOL:
                case ApiDavFilter.METHOD_COPY:
                case ApiDavFilter.METHOD_MOVE:
                case ApiDavFilter.METHOD_DELETE:
                    this.locateSitemapEntry(sitemap, request);
                    throw new ForbiddenState();
            }

            throw new MethodNotAllowedState(ApiDavFilter.METHOD_OPTIONS, ApiDavFilter.METHOD_PROPFIND,
                    ApiDavFilter.METHOD_HEAD, ApiDavFilter.METHOD_GET, ApiDavFilter.METHOD_PUT,
                    ApiDavFilter.METHOD_LOCK, ApiDavFilter.METHOD_UNLOCK);

        } catch (Exception exception) {
            if (!(exception instanceof State)) {
                LOGGER.error("Unexpected exception has occurred", exception);
                exception = new InternalServerErrorState();
            }
            final State state = (State)exception;
            try {state.forceResponseStatus(response);
            } finally {
                LOGGER.info("{} {} {} ({} ms)", state.getStatusCode(),
                        request.getMethod(), request.getRequestURI(),
                        System.currentTimeMillis() -timing);
            }
        }
    }

    private abstract static class State extends RuntimeException {

        private static final long serialVersionUID = -3285926540309442308L;

        abstract int getStatusCode();

        String getStatusLine() {
            final String message = this.getClass().getSimpleName().replaceAll("State$", "").replaceAll("(?<=[a-z])(?=[A-Z])", " ");
            return "HTTP/1.1 " + this.getStatusCode() + " " + message;
        }

        void forceResponseStatus(final HttpServletResponse response)
                throws IOException {
            if (response.isCommitted())
                return;
            response.setStatus(this.getStatusCode());
            response.flushBuffer();
            response.getOutputStream().close();
        }
    }

    private static class SuccessState extends State {
        
        private static final long serialVersionUID = -1771577635432317281L;

        @Override
        int getStatusCode() {
            return HttpServletResponse.SC_OK;
        }
    }

    private static class NoContentState extends State {

        private static final long serialVersionUID = 3712271737420787168L;

        @Override
        int getStatusCode() {
            return HttpServletResponse.SC_NO_CONTENT;
        }
    }

    private static class MultiStatusState extends State {

        private static final long serialVersionUID = 384634503420067272L;

        @Override
        int getStatusCode() {
            return 207;
        }
    }

    private static class FoundState extends State {

        private static final long serialVersionUID = 7992253504529221105L;

        final String location;

        FoundState(String location) {
            this.location = location;
        }

        @Override
        int getStatusCode() {
            return HttpServletResponse.SC_FOUND;
        }

        @Override
        void forceResponseStatus(final HttpServletResponse response)
                throws IOException {
            if (response.isCommitted())
                return;
            response.setHeader(HEADER_LOCATION, this.location);
            super.forceResponseStatus(response);
        }
    }

    private static class NotModifiedState extends State {

        private static final long serialVersionUID = -6789559753321086790L;

        final String identifier;

        @Override
        int getStatusCode() {
            return HttpServletResponse.SC_NOT_MODIFIED;
        }

        NotModifiedState(String identifier) {
            this.identifier = identifier;
        }

        @Override
        void forceResponseStatus(final HttpServletResponse response)
                throws IOException {
            if (response.isCommitted())
                return;
            response.setHeader(HEADER_ETAG, "\"" + this.identifier + "\"");
            super.forceResponseStatus(response);
        }
    }

    private static class ForbiddenState extends State {

        private static final long serialVersionUID = 2028565954023402465L;

        @Override
        int getStatusCode() {
            return HttpServletResponse.SC_FORBIDDEN;
        }
    }

    private static class NotFoundState extends State {

        private static final long serialVersionUID = -3676678322498170899L;

        @Override
        int getStatusCode() {
            return HttpServletResponse.SC_NOT_FOUND;
        }
    }

    private static class MethodNotAllowedState extends State {

        private static final long serialVersionUID = 7242353961612090064L;

        final String[] allow;

        MethodNotAllowedState(String... allow) {
            this.allow = allow;
        }

        @Override
        int getStatusCode() {
            return HttpServletResponse.SC_METHOD_NOT_ALLOWED;
        }

        @Override
        void forceResponseStatus(final HttpServletResponse response)
                throws IOException {
            if (response.isCommitted())
                return;
            response.setHeader(HEADER_ALLOW, String.join(", ", this.allow));
            super.forceResponseStatus(response);
        }
    }

    private static class NotAcceptableState extends State {

        private static final long serialVersionUID = -3888539558816130805L;

        @Override
        int getStatusCode() {
            return HttpServletResponse.SC_NOT_ACCEPTABLE;
        }
    }

    private static class PreconditionFailedState extends State {

        private static final long serialVersionUID = -4196618688975724595L;

        @Override
        int getStatusCode() {
            return HttpServletResponse.SC_PRECONDITION_FAILED;
        }
    }

    private static class UnprocessableEntityState extends State {

        private static final long serialVersionUID = -6604759761612191353L;

        @Override
        int getStatusCode() {
            return 422;
        }
    }

    private static class LockedState extends State {

        private static final long serialVersionUID = 836006924606414263L;

        @Override
        int getStatusCode() {
            return 423;
        }
    }

    private static class InternalServerErrorState extends State {

        private static final long serialVersionUID = 3019539164953304989L;

        @Override
        int getStatusCode() {
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
    }
}