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
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ApiDavFilter extends HttpFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiDavFilter.class);

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

    private static final String SITEMAP_PROPERTY_SESSION_ATTRIBUTE  = "session.attribute";
    private static final String SITEMAP_PROPERTY_REQUEST_SESSION_ID = "request.session.id";
    private static final String SITEMAP_PROPERTY_REQUEST_HEADER     = "request.header";
    private static final String SITEMAP_PROPERTY_REQUEST_ATTRIBUTE  = "request.attribute";
    private static final String SITEMAP_PROPERTY_USER_REMOTE        = "user.remote";
    private static final String SITEMAP_PROPERTY_USER_PRINCIPAL     = "user.principal";

    private static final String HEADER_DEPTH = "Depth";

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

    /** Constant of the declaration of the XML namespace for WebDAV */
    private static final String WEBDAV_DEFAULT_XML_NAMESPACE_DECLARATION = " xmlns:" + WEBDAV_DEFAULT_XML_NAMESPACE + "=\"DAV:\"";

    private static final String DATETIME_FORMAT_CREATION_DATE = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String DATETIME_FORMAT_LAST_MODIFIED = "E, dd MMM yyyy HH:mm:ss z";

    private static Class<?>[] getClassHierarchy(Object source) {

        if (Objects.isNull(source))
            return null;

        if (!(source instanceof Class))
            source = source.getClass();

        final List<Class<?>> classes = new ArrayList<>();
        while (source != null) {
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
        final String filterName = this.getClass().getName();
        final Collection<String> filterUrlPatternMappings = filterConfig.getServletContext().getFilterRegistration(filterName).getUrlPatternMappings();
        if (filterUrlPatternMappings.isEmpty())
            throw new ServletException("Invalid URL pattern mapping for filter: " + this.getClass().getName());
        for (String urlPatternMapping : filterUrlPatternMappings)
            if (!urlPatternMapping.matches("^/(\\w+/){0,}\\*?$"))
                throw new ServletException("Invalid URL pattern mapping for filter: " + this.getClass().getName());

        try {
            final ServletContext servletContext = filterConfig.getServletContext();
            final ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
            final Collection<Annotation> annotations = new ArrayList<>();
            for (final String beanName : applicationContext.getBeanDefinitionNames()) {
                final Object object = applicationContext.getBean(beanName);
                for (final Class source: ApiDavFilter.getClassHierarchy(object)) {
                    for (final Method method : source.getDeclaredMethods()) {
                        for (final java.lang.annotation.Annotation annotation : method.getDeclaredAnnotations()) {
                            // TODO: Check expected methods signature, otherwise exception with more details
                            // TODO: Check for uniqueness of annotations, multiple occurrences cause exception
                            if (annotation.annotationType().equals(ApiDavAttribute.class))
                                annotations.add(Annotation.Attribute.create((ApiDavAttribute)annotation, object, method));
                            else if (annotation.annotationType().equals(ApiDavAttribute.ApiDavAttributes.class))
                                for (final ApiDavAttribute apiDavAttribute : ((ApiDavAttribute.ApiDavAttributes)annotation).value())
                                    annotations.add(Annotation.Attribute.create(apiDavAttribute, object, method));
                            else if (annotation.annotationType().equals(ApiDavInput.class))
                                annotations.add(Annotation.Input.create((ApiDavInput)annotation, object, method));
                            else if (annotation.annotationType().equals(ApiDavInput.ApiDavInputs.class))
                                for (final ApiDavInput apiDavAttribute : ((ApiDavInput.ApiDavInputs)annotation).value())
                                    annotations.add(Annotation.Input.create(apiDavAttribute, object, method));
                            else if (annotation.annotationType().equals(ApiDavMapping.class))
                                annotations.add(Annotation.Mapping.create((ApiDavMapping)annotation, object, method));
                            else if (annotation.annotationType().equals(ApiDavMapping.ApiDavMappings.class))
                                for (final ApiDavMapping apiDavAttribute : ((ApiDavMapping.ApiDavMappings)annotation).value())
                                    annotations.add(Annotation.Mapping.create(apiDavAttribute, object, method));
                            else if (annotation.annotationType().equals(ApiDavMeta.class))
                                annotations.add(Annotation.Meta.create((ApiDavMeta)annotation, object, method));
                            else if (annotation.annotationType().equals(ApiDavMeta.ApiDavMetas.class))
                                for (final ApiDavMeta apiDavMeta : ((ApiDavMeta.ApiDavMetas)annotation).value())
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
                if (!mappingPaths.contains(annotation.getPath()))
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

    private String locateRequestContextUrl(final HttpServletRequest request) {

        final String contextHostAddress = request.getRequestURL().toString().replaceAll("^(\\w+:/+[^/]+).*$", "$1");
        final String filterName = this.getClass().getName();
        final Collection<String> filterUrlMappings = request.getServletContext().getFilterRegistration(filterName).getUrlPatternMappings();
        for (String urlPatternMapping : filterUrlMappings) {
            urlPatternMapping = urlPatternMapping.replaceAll("/+\\**$", "");
            if (request.getRequestURI().startsWith(urlPatternMapping + "/")
                    || request.getRequestURI().equals(urlPatternMapping))
                return contextHostAddress + urlPatternMapping;
        }
        return "";
    }

    private String locateRequestContextPath(final HttpServletRequest request) {

        final String filterName = this.getClass().getName();
        final Collection<String> filterUrlMappings = request.getServletContext().getFilterRegistration(filterName).getUrlPatternMappings();
        for (String urlPatternMapping : filterUrlMappings) {
            urlPatternMapping = urlPatternMapping.replaceAll("/+\\**$", "");
            if (request.getRequestURI().startsWith(urlPatternMapping + "/")
                    || request.getRequestURI().equals(urlPatternMapping))
                return urlPatternMapping;
        }
        return "";
    }

    private String locateSitemapPath(final HttpServletRequest request) {

        final String requestURI =  URLDecoder.decode(request.getRequestURI(), StandardCharsets.UTF_8);
        final String filterName = this.getClass().getName();
        final Collection<String> filterUrlMappings = request.getServletContext().getFilterRegistration(filterName).getUrlPatternMappings();
        for (String urlPatternMapping : filterUrlMappings) {
            urlPatternMapping = urlPatternMapping.replaceAll("\\*?$", "");
            if (requestURI.startsWith(urlPatternMapping))
                return requestURI.substring(urlPatternMapping.length() -1);
            if ((requestURI + "/").equals(urlPatternMapping))
                return "/";
        }
        return null;
    }

    private Sitemap.Entry locateSitemapEntry(final Sitemap sitemap, final HttpServletRequest request) {
        return this.locateSitemapEntry(sitemap, request, false);
    }

    private Sitemap.Entry locateSitemapEntry(final Sitemap sitemap, final HttpServletRequest request, boolean stateful) {

        final String pathInfo = this.locateSitemapPath(request);
        if (Objects.isNull(pathInfo))
            if (stateful)
                throw new NotFoundState();
            else return null;

        if (stateful && pathInfo.endsWith("/")
                && !request.getRequestURI().endsWith("/"))
            throw new FoundState(request.getRequestURI() + "/");

        final Sitemap.Entry entry = sitemap.locate(pathInfo);
        if (stateful && Objects.isNull(entry))
            throw new NotFoundState();

        if (stateful && entry.isFolder()
                && !pathInfo.endsWith("/"))
            throw new FoundState(request.getRequestURI() + "/");
        if (stateful && entry.isFile()
                && pathInfo.endsWith("/"))
            throw new FoundState(request.getRequestURI().replaceAll("/+$", ""));

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

    private static Properties<String> getPropertiesFromXml(final Node node) {

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

        xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "response", XmlWriter.ElementType.OPENING);
        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "href", UriUtils.encodePath(contextUrl + entry.getPath(), StandardCharsets.UTF_8.name()));

        final String displayName = UriUtils.encodePath(entry.getName(), StandardCharsets.UTF_8.name());

        final String creationDate  = DateTime.formatDate(entry.getCreationDate(), DATETIME_FORMAT_CREATION_DATE);
        final String lastModified  = DateTime.formatDate(entry.getLastModified(), DATETIME_FORMAT_LAST_MODIFIED);
        final String contentType   = entry.isFolder() ? "" : ((Sitemap.File)entry).getContentType();
        final String contentLength = entry.isFolder() ? "" : String.valueOf(((Sitemap.File)entry).getContentLength());
        final String isCollection  = String.valueOf(entry.isFolder());

        final String isReadOnly = String.valueOf(entry.isReadOnly());
        final String isHidden   = String.valueOf(entry.isHidden());
        final String isSystem   = "false";
        final String isArchive  = "false";

        // Win32FileAttributes
        // see also https://docs.microsoft.com/de-de/windows/win32/api/fileapi/nf-fileapi-setfileattributesa?redirectedfrom=MSDN
        // readOnly: 0x01, hidden: 0x02, system: 0x04, directory: 0x10, achive: 0x20
        final String win32FileAttributes = Integer.toHexString(
                (entry.isReadOnly() ? 0x01 : 0)
                        | (entry.isHidden() ? 0x02 : 0)
                        | (entry.isFolder() ? 0x10 : 0));

        switch (type) {
            case WEBDAV_FIND_ALL_PROP:
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "propstat", XmlWriter.ElementType.OPENING);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "prop", XmlWriter.ElementType.OPENING);

                xmlWriter.writePropertyData(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "displayname", displayName);
                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "iscollection", isCollection);
                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "creationdate", creationDate);
                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "getlastmodified", lastModified);

                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "isreadonly", isReadOnly);
                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "ishidden",  isHidden);
                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "issystem", isSystem);
                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "isarchive", isArchive);

                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "Win32FileAttributes", win32FileAttributes);

                if (entry.isFolder()) {
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "resourcetype", XmlWriter.ElementType.OPENING);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "collection", XmlWriter.ElementType.EMPTY);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "resourcetype", XmlWriter.ElementType.CLOSING);
                } else {
                    xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "getcontenttype", contentType);
                    xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "getcontentlength", contentLength);
                }

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "prop", XmlWriter.ElementType.CLOSING);
                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "status", new SuccessState().getStatusLine());
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "propstat", XmlWriter.ElementType.CLOSING);

                break;

            case WEBDAV_FIND_PROPERTY_NAMES:

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "propstat", XmlWriter.ElementType.OPENING);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "prop", XmlWriter.ElementType.OPENING);

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "displayname", XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "iscollection", XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "creationdate", XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "getlastmodified", XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "getetag", XmlWriter.ElementType.EMPTY);

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "isreadonly", XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "ishidden", XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "issystem", XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "isarchive", XmlWriter.ElementType.EMPTY);

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "Win32FileAttributes", XmlWriter.ElementType.EMPTY);

                if (entry.isFolder()) {
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "resourcetype", XmlWriter.ElementType.EMPTY);
                } else {
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "getcontenttype", XmlWriter.ElementType.EMPTY);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "getcontentlength", XmlWriter.ElementType.EMPTY);
                }

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "prop", XmlWriter.ElementType.CLOSING);
                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "status", new SuccessState().getStatusLine());
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "propstat", XmlWriter.ElementType.CLOSING);

                break;

            case WEBDAV_FIND_BY_PROPERTY:

                List<String> list = new ArrayList<>();

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "propstat", XmlWriter.ElementType.OPENING);
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "prop", XmlWriter.ElementType.OPENING);

                for (String property : properties.keySet()) {

                    if (property.equals("displayname")) {
                        xmlWriter.writePropertyData(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "displayname", displayName);
                    } else if (property.equals("iscollection")) {
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "iscollection", isCollection);
                    } else if (property.equals("creationdate")) {
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "creationdate", creationDate);
                    } else if (property.equals("getlastmodified")) {
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "getlastmodified", lastModified);

                    } else if (property.equals("isreadonly")) {
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "isreadonly", isReadOnly);
                    } else if (property.equals("ishidden")) {
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "ishidden", isHidden);
                    } else if (property.equals("issystem")) {
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "issystem", isSystem);
                    } else if (property.equals("isarchive")) {
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "isarchive", isArchive);

                    } else if (property.equals("Win32FileAttributes")) {
                        xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "Win32FileAttributes", win32FileAttributes);
                    } else {
                        if (entry.isFolder()
                                && property.equals("resourcetype")) {
                            xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "resourcetype", XmlWriter.ElementType.OPENING);
                            xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "collection", XmlWriter.ElementType.EMPTY);
                            xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "resourcetype", XmlWriter.ElementType.CLOSING);
                        } else if (!entry.isFolder()
                                && property.equals("getcontentlength")) {
                            xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "getcontentlength", contentLength);
                        } else if (!entry.isFolder()
                                && property.equals("getcontenttype")) {
                            xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "getcontenttype", contentType);
                        } else list.add(property);
                    }
                }

                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "prop", XmlWriter.ElementType.CLOSING);
                xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "status", new SuccessState().getStatusLine());
                xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "propstat", XmlWriter.ElementType.CLOSING);

                Iterator<String> iterator = list.iterator();
                if (iterator.hasNext()) {
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "propstat", XmlWriter.ElementType.OPENING);
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "prop", XmlWriter.ElementType.OPENING);

                    while (iterator.hasNext())
                        xmlWriter.writeElement(null, iterator.next(), XmlWriter.ElementType.EMPTY);

                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "prop", XmlWriter.ElementType.CLOSING);
                    xmlWriter.writeProperty(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "status", new NotFoundState().getStatusLine());
                    xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "propstat", XmlWriter.ElementType.CLOSING);
                }

                break;
        }

        xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "response", XmlWriter.ElementType.CLOSING);
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

        final Sitemap.Entry entry = this.locateSitemapEntry(sitemap, request);

        response.setHeader("DAV", "1, 2");
        response.setHeader("MS-Author-Via", "DAV");
        response.setHeader("Allow", String.join(", ", ApiDavFilter.METHOD_OPTIONS, ApiDavFilter.METHOD_HEAD,
                ApiDavFilter.METHOD_GET, ApiDavFilter.METHOD_PROPFIND));
        if (Objects.nonNull(entry)
                && !entry.isReadOnly()) {
            response.setHeader("Allow", response.getHeader("Allow") + ", " + ApiDavFilter.METHOD_PUT);
        }

        throw new SuccessState();
    }

    private void doPropfind(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {

        final Sitemap.Entry entry = this.locateSitemapEntry(sitemap, request, true);
        final String contextPath = this.locateRequestContextPath(request);

        try {
            final Properties<String> properties = new Properties<>();
            final Document document = ApiDavFilter.readXmlRequest(request);
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            final XmlWriter xmlWriter = new XmlWriter(buffer);
            xmlWriter.writeXmlHeader();
            xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "multistatus" +  ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE_DECLARATION, XmlWriter.ElementType.OPENING);

            final int depth = ApiDavFilter.getDepth(request);

            if (document != null) {
                final Element root = document.getDocumentElement();
                final NodeList nodeList = root.getChildNodes();
                final Set<Node> nodeSet = IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item)
                        .filter(streamNode -> streamNode.getNodeType() == Node.ELEMENT_NODE).collect(Collectors.toSet());

                if (nodeSet.stream().anyMatch(streamNode -> streamNode.getLocalName().equalsIgnoreCase("allprop"))) {
                    ApiDavFilter.collectProperties(xmlWriter, contextPath, entry, WEBDAV_FIND_ALL_PROP, properties, depth);
                } else if (nodeSet.stream().anyMatch(streamNode -> streamNode.getLocalName().equalsIgnoreCase("prop"))) {
                    final Node propNode = nodeSet.stream().filter(streamNode -> streamNode.getLocalName().equalsIgnoreCase("prop")).findFirst().get();
                    properties.putAll(ApiDavFilter.getPropertiesFromXml(propNode));
                    ApiDavFilter.collectProperties(xmlWriter, contextPath, entry, WEBDAV_FIND_BY_PROPERTY, properties, depth);
                } else if (nodeSet.stream().anyMatch(streamNode -> streamNode.getLocalName().equalsIgnoreCase("propname"))) {
                    ApiDavFilter.collectProperties(xmlWriter, contextPath, entry, WEBDAV_FIND_PROPERTY_NAMES, properties, depth);
                } else {
                    ApiDavFilter.collectProperties(xmlWriter, contextPath, entry, WEBDAV_FIND_ALL_PROP, properties, depth);
                }

            } else ApiDavFilter.collectProperties(xmlWriter, contextPath, entry, WEBDAV_FIND_ALL_PROP, properties, depth);

            xmlWriter.writeElement(ApiDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "multistatus", XmlWriter.ElementType.CLOSING);
            xmlWriter.flush();

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

    private void doHead(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final Sitemap.Entry entry = this.locateSitemapEntry(sitemap, request, true);

        if (entry.isFolder()) {
            // TODO:
            throw new SuccessState();
        }

        // TODO:
        throw new SuccessState();
    }

    private void doGet(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        final Sitemap.Entry entry = this.locateSitemapEntry(sitemap, request, true);

        if (entry.isFolder()) {
            // TODO:
            throw new SuccessState();
        }

        final Sitemap.File file = ((Sitemap.File)entry);
        final Sitemap.Callback readCallback = file.getReadCallback();
        final MetaOutputStream metaOutputStream = MetaOutputStream.builder()
                .response(response)
                .contentType(file.getContentType())
                .contentLength(file.getContentLength())
                .lastModified(file.getLastModified())
                .build();
        try {readCallback.invoke(metaOutputStream);
        } catch (Exception exception) {
            while (exception instanceof InvocationTargetException)
                exception = (Exception)((InvocationTargetException)exception).getTargetException();
            LOGGER.error("GET callback failed", exception);
            throw new InternalServerError();
        }
        throw new SuccessState();
    }

    private void doPut(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        final Sitemap.Entry entry = this.locateSitemapEntry(sitemap, request, false);

        if (entry.isFolder()
                || entry.isReadOnly())
            throw new ForbiddenState();

        final Sitemap.File file = ((Sitemap.File)entry);
        final Sitemap.Callback writeCallback = file.getWriteCallback();
        final MetaInputStream metaInputStream = MetaInputStream.builder()
                .request(request)
                .contentType(file.getContentType())
                .contentLength(file.getContentLength())
                .build();
        try {writeCallback.invoke(metaInputStream);
        } catch (Exception exception) {
            while (exception instanceof InvocationTargetException)
                exception = (Exception)((InvocationTargetException)exception).getTargetException();
            LOGGER.error("PUT callback failed", exception);
            throw new InternalServerError();
        }
        throw new NoContentState();
    }

    private void doLock(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response) {
        this.locateSitemapEntry(sitemap, request, true);
        // MS Office is satisfied with the very simple lock -- Thanks guys :-)
        response.addHeader("Lock-Token", "<opaquelocktoken:0123456789>");
        throw new SuccessState();
    }

    private void doUnlock(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response) {
        this.locateSitemapEntry(sitemap, request, true);
        throw new NoContentState();
    }

    @Override
    protected void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws ServletException, IOException {

        final long timing = System.currentTimeMillis();

        // The sitemap is used as a request-based shared instance.
        // The values of the annotations can contain values, expressions and
        // callbacks, which must then be processed in relation to the request.

        final Properties<Object> properties = new Properties<>();
        final HttpSession session = request.getSession(false);
        if (Objects.nonNull(session))
            session.getAttributeNames() .asIterator()
                    .forEachRemaining(attribute -> properties.put(SITEMAP_PROPERTY_SESSION_ATTRIBUTE + "." + attribute, session.getAttribute(attribute)));
        if (Objects.nonNull(request.getRequestedSessionId()))
            properties.put(SITEMAP_PROPERTY_REQUEST_SESSION_ID, request.getRequestedSessionId());
        request.getHeaderNames().asIterator()
                .forEachRemaining(header -> properties.put(SITEMAP_PROPERTY_REQUEST_HEADER + "." + header, request.getHeader(header)));
        request.getAttributeNames().asIterator()
                .forEachRemaining(attribute -> properties.put(SITEMAP_PROPERTY_REQUEST_ATTRIBUTE + "." + attribute, request.getAttribute(attribute)));
        if (Objects.nonNull(request.getRemoteUser()))
            properties.put(SITEMAP_PROPERTY_USER_REMOTE, request.getRemoteUser());
        if (Objects.nonNull(request.getUserPrincipal()))
            properties.put(SITEMAP_PROPERTY_USER_PRINCIPAL, request.getUserPrincipal());
        final Sitemap sitemap = this.sitemap.share(properties);

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
            }

            throw new MethodNotAllowedState(ApiDavFilter.METHOD_OPTIONS, ApiDavFilter.METHOD_PROPFIND,
                    ApiDavFilter.METHOD_HEAD, ApiDavFilter.METHOD_GET, ApiDavFilter.METHOD_PUT,
                    ApiDavFilter.METHOD_LOCK, ApiDavFilter.METHOD_UNLOCK);

        } catch (State state) {
            try {state.forceResponseStatus(response);
            } finally {
                LOGGER.info("{} {} {} ({} ms)", state.getStatusCode(),
                        request.getMethod(), request.getRequestURI(),
                        System.currentTimeMillis() -timing);
            }
        }
    }

    private abstract static class State extends RuntimeException {

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
        @Override
        int getStatusCode() {
            return HttpServletResponse.SC_OK;
        }
    }

    private static class NoContentState extends State {
        @Override
        int getStatusCode() {
            return HttpServletResponse.SC_NO_CONTENT;
        }
    }

    private static class MultiStatusState extends State {
        @Override
        int getStatusCode() {
            return 207;
        }
    }

    private static class FoundState extends State {

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
            response.setHeader("Location", this.location);
            super.forceResponseStatus(response);
        }
    }

    private static class ForbiddenState extends State {
        @Override
        int getStatusCode() {
            return HttpServletResponse.SC_FORBIDDEN;
        }
    }

    private static class NotFoundState extends State {
        @Override
        int getStatusCode() {
            return HttpServletResponse.SC_NOT_FOUND;
        }
    }

    private static class MethodNotAllowedState extends State {

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
            response.setHeader("Allow", String.join(", ", this.allow));
            super.forceResponseStatus(response);
        }
    }

    private static class NotAcceptableState extends State {
        @Override
        int getStatusCode() {
            return HttpServletResponse.SC_NOT_ACCEPTABLE;
        }
    }

    private static class UnprocessableEntityState extends State {
        @Override
        int getStatusCode() {
            return 422;
        }
    }

    private static class LockedState extends State {
        @Override
        int getStatusCode() {
            return 423;
        }
    }

    private static class InternalServerError extends State {
        @Override
        int getStatusCode() {
            return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }
    }
}