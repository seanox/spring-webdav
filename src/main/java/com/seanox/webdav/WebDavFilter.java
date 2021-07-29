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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
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
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * <p>
 *   The WebDAV implementation is based on the WebDavFilter, which handles all
 *   requests to a URL pattern.
 * </p>
 * <p>
 *   For this purpose, the filter is registered in the Spring Boot application
 *   as follows:
 * </p>
 * <pre>
 *   &#64;SpringBootApplication
 *   public class Application extends SpringBootServletInitializer {
 *
 *       public static void main(final String... options) {
 *           final SpringApplication springApplication = new SpringApplication(Application.class);
 *           springApplication.setBannerMode(Banner.Mode.CONSOLE);
 *           springApplication.run(options);
 *       }
 *
 *       &#64;Bean
 *       public FilterRegistrationBean someFilterRegistration() {
 *           final FilterRegistrationBean registration = new FilterRegistrationBean();
 *           registration.setFilter(new WebDavFilter());
 *           registration.setOrder(1);
 *           registration.addUrlPatterns("/*");
 *           return registration;
 *       }
 *   }
 * </pre>
 * <p>
 *   With the exception of the URL pattern and the initialization order, there
 *   is no further configuration.
 * </p>
 * <p>
 *   About the behavior of the implementation the following is interesting:
 * </p>
 * <ul>
 *   <li>
 *     What is not permitted/allowed is handled as if it does not exist.
 *   </li>
 *   <li>
 *     PROPPATCH is not officially implemented and yet it is included. It is
 *     used in specific cases of MS Office / MS-WebDAV-MiniRedir. The method is
 *     not implemented in real, it just behaves like PROPFIND which is enough
 *     for the case.
 *   </li>
 *   <li>
 *     The implementation is based on throwing states. The request methods are
 *     not exited with the end or return , but a state exception is used to
 *     exit.
 *   </li>
 * </ul>
 * <br>
 * WebDavFilter 1.0.0 20210729<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210729
 */
public class WebDavFilter extends HttpFilter {

    private static final long serialVersionUID = 7637895578410477411L;

    private static final Logger LOGGER = LoggerFactory.getLogger(WebDavFilter.class);

    private Properties properties;

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

    /** Constructor, creates a new instance of WebDavFilter. */
    public WebDavFilter() {
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
                for (final Class<?> source : WebDavFilter.getClassHierarchy(object)) {
                    for (final Method method : source.getDeclaredMethods()) {
                        for (final java.lang.annotation.Annotation annotation : method.getDeclaredAnnotations()) {
                            if (annotation.annotationType().equals(WebDavAttributeMapping.class))
                                annotations.add(Annotation.Attribute.create((WebDavAttributeMapping)annotation, object, method));
                            else if (annotation.annotationType().equals(WebDavAttributeMapping.WebDavAttributeMappings.class))
                                for (final WebDavAttributeMapping webDavAttribute : ((WebDavAttributeMapping.WebDavAttributeMappings)annotation).value())
                                    annotations.add(Annotation.Attribute.create(webDavAttribute, object, method));
                            else if (annotation.annotationType().equals(WebDavInputMapping.class))
                                annotations.add(Annotation.Input.create((WebDavInputMapping)annotation, object, method));
                            else if (annotation.annotationType().equals(WebDavInputMapping.WebDavInputMappings.class))
                                for (final WebDavInputMapping webDavAttribute : ((WebDavInputMapping.WebDavInputMappings)annotation).value())
                                    annotations.add(Annotation.Input.create(webDavAttribute, object, method));
                            else if (annotation.annotationType().equals(WebDavMapping.class))
                                annotations.add(Annotation.Mapping.create((WebDavMapping)annotation, object, method));
                            else if (annotation.annotationType().equals(WebDavMapping.WebDavMappings.class))
                                for (final WebDavMapping webDavAttribute : ((WebDavMapping.WebDavMappings)annotation).value())
                                    annotations.add(Annotation.Mapping.create(webDavAttribute, object, method));
                            else if (annotation.annotationType().equals(WebDavMetaMapping.class))
                                annotations.add(Annotation.Meta.create((WebDavMetaMapping)annotation, object, method));
                            else if (annotation.annotationType().equals(WebDavMetaMapping.WebDavMetaMappings.class))
                                for (final WebDavMetaMapping webDavMeta : ((WebDavMetaMapping.WebDavMetaMappings)annotation).value())
                                    annotations.add(Annotation.Meta.create(webDavMeta, object, method));
                        }
                    }
                }
            }

            // Validation that a mapping annotation exists for all paths.
            // The check at the startup avoids unwanted effects at runtime.
            // Because without mapping other webDav annotations have no effect.
            final Collection<String> mappingPaths = new ArrayList<>();
            annotations.stream()
                    .filter(annotation -> annotation.getType().equals(Annotation.AnnotationType.Mapping))
                    .sorted((annotation, compare) -> annotation.getPath().compareToIgnoreCase(compare.getPath()))
                    .forEach(annotation -> mappingPaths.add(annotation.getPath().toLowerCase()));
            for (final Annotation annotation : annotations)
                if (!mappingPaths.contains(annotation.getPath().toLowerCase()))
                    throw new AnnotationException("Mapping annotation missing for path: " + annotation.getPath());

            // Check for uniqueness of annotations, multiple occurrences lead to an AnnotationException:
            //     e.g. @WebDavAttributeMapping(path=/example/file.txt, attribute=WebDavMappingAttribute.ContentType)
            //          methodA...
            //          @WebDavAttributeMapping(path=/example/file.txt, attribute=WebDavMappingAttribute.ContentType)
            //          methodB...
            // The attribute mapping for a path and attribute for different callbacks.
            // It would always be random which method the VM uses.

            final List<String> register = new ArrayList<>();
            for (Annotation annotation : annotations) {
                final List<String> collector = new ArrayList<>();
                collector.add(annotation.getClass().getSimpleName());
                collector.add(annotation.getPath().toLowerCase());
                if (annotation instanceof Annotation.Attribute)
                    collector.add(((Annotation.Attribute)annotation).attributeType.name());
                final String fingerprint = String.join(":", collector.toArray(new String[0]));
                if (register.contains(fingerprint))
                    if (Annotation.AnnotationType.Mapping.equals(annotation.getType()))
                        throw new AnnotationException("Ambiguous " + annotation.getType() + ": " + annotation.getPath());
                    else throw new AnnotationException("Ambiguous " + annotation.getType() + " Mapping: " + annotation.getPath());
                register.add(fingerprint);
            }

            for (final String mappingPath : mappingPaths)
                try {this.sitemap.map(annotations.stream().filter(annotation -> annotation.getPath().equalsIgnoreCase(mappingPath)).toArray(Annotation[]::new));
                } catch (SitemapException exception) {
                    throw new AnnotationException(exception.getMessage());
                }

            LOGGER.info(this.getClass().getSimpleName() + " was established");
            if (this.sitemap.toString().length() > 0) {
                LOGGER.info("Sitemap");
                LOGGER.info("---");
                Arrays.stream(this.sitemap.toString().split("\\R")).forEach(LOGGER::info);
            } else LOGGER.warn("Sitemap is empty");

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

        if (entry.isFile()
                && !((Sitemap.File)entry).isAccepted())
            throw new BadRequestState();

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
                    && !Arrays.asList(ifMatch.split("\\s*,\\s*")).contains("\"" + identifier + "\"")) {
                if (METHOD_PUT.equals(method)
                        || METHOD_LOCK.equals(method)
                        || METHOD_HEAD.equals(method)
                        || METHOD_GET.equals(method))
                    throw new PreconditionFailedState();
            }
        }

        return entry;
    }

    private static int getDepth(final HttpServletRequest request) {
        final String depth = request.getHeader(HEADER_DEPTH);
        if (Objects.isNull(depth)
                || !depth.matches("[01]"))
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

    private static Properties getPropertiesFromNode(final Node node) {

        final Properties properties = new Properties();
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
                  final int type, final Properties properties)
            throws IOException {

        xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_RESPONSE, XmlWriter.ElementType.OPENING);
        xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_HREF, UriUtils.encodePath(contextUrl + entry.getPath(), StandardCharsets.UTF_8.name()));

        final String displayName = UriUtils.encodePath(entry.getName(), StandardCharsets.UTF_8.name());

        final String creationDate  = Objects.nonNull(entry.getCreationDate()) ? DateTime.formatDate(entry.getCreationDate(), DATETIME_FORMAT_CREATION_DATE) : null;
        final String lastModified  = Objects.nonNull(entry.getLastModified()) ? DateTime.formatDate(entry.getLastModified(), DATETIME_FORMAT_LAST_MODIFIED) : null;
        final String contentType   = entry.isFile() && Objects.nonNull(((Sitemap.File)entry).getContentType()) ? ((Sitemap.File)entry).getContentType() : null;
        final String contentLength = entry.isFile() && Objects.nonNull(((Sitemap.File)entry).getContentLength()) ? ((Sitemap.File)entry).getContentLength().toString() : null;

        final String isCollection = String.valueOf(entry.isFolder());
        final String isReadOnly   = String.valueOf(entry.isReadOnly());
        final String isHidden     = String.valueOf(entry.isHidden());
        final String isSystem     = Boolean.FALSE.toString();
        final String isArchive    = Boolean.FALSE.toString();

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
                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROPSTAT, XmlWriter.ElementType.OPENING);
                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.OPENING);

                xmlWriter.writePropertyData(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_DISPLAYNAME, displayName);
                xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISCOLLECTION, isCollection);
                if (Objects.nonNull(creationDate))
                    xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_CREATIONDATE, creationDate);
                if (Objects.nonNull(lastModified))
                    xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETLASTMODIFIED, lastModified);

                xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISREADONLY, isReadOnly);
                xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISHIDDEN, isHidden);
                xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISSYSTEM, isSystem);
                xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISARCHIVE, isArchive);

                xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_WIN32FILEATTRIBUTES, win32FileAttributes);

                if (entry.isFolder()) {
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_RESOURCETYPE, XmlWriter.ElementType.OPENING);
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_COLLECTION, XmlWriter.ElementType.EMPTY);
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_RESOURCETYPE, XmlWriter.ElementType.CLOSING);
                } else {
                    if (Objects.nonNull(contentType))
                        xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETCONTENTTYPE, contentType);
                    if (Objects.nonNull(contentLength))
                        xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETCONTENTLENGTH, contentLength);
                    xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETETAG, etag);

                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_SUPPORTEDLOCK, XmlWriter.ElementType.OPENING);
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, "DAV:", XML_LOCKENTRY, XmlWriter.ElementType.OPENING);
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKSCOPE, XmlWriter.ElementType.OPENING);
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_EXCLUSIVE, XmlWriter.ElementType.EMPTY);
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKSCOPE, XmlWriter.ElementType.CLOSING);
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKTYPE, XmlWriter.ElementType.OPENING);
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_WRITE, XmlWriter.ElementType.EMPTY);
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKTYPE, XmlWriter.ElementType.CLOSING);
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_LOCKENTRY, XmlWriter.ElementType.CLOSING);
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_SUPPORTEDLOCK, XmlWriter.ElementType.CLOSING);
                }

                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.CLOSING);
                xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_STATUS, new SuccessState().getStatusLine());
                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROPSTAT, XmlWriter.ElementType.CLOSING);

                break;

            case WEBDAV_FIND_PROPERTY_NAMES:

                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROPSTAT, XmlWriter.ElementType.OPENING);
                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.OPENING);

                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_DISPLAYNAME, XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISCOLLECTION, XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_CREATIONDATE, XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETLASTMODIFIED, XmlWriter.ElementType.EMPTY);

                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISREADONLY, XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISHIDDEN, XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISSYSTEM, XmlWriter.ElementType.EMPTY);
                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISARCHIVE, XmlWriter.ElementType.EMPTY);

                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_WIN32FILEATTRIBUTES, XmlWriter.ElementType.EMPTY);

                if (entry.isFolder()) {
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_RESOURCETYPE, XmlWriter.ElementType.EMPTY);
                } else {
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETCONTENTTYPE, XmlWriter.ElementType.EMPTY);
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETCONTENTLENGTH, XmlWriter.ElementType.EMPTY);
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETETAG, XmlWriter.ElementType.EMPTY);
                }

                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.CLOSING);
                xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_STATUS, new SuccessState().getStatusLine());
                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROPSTAT, XmlWriter.ElementType.CLOSING);

                break;

            case WEBDAV_FIND_BY_PROPERTY:

                List<String> list = new ArrayList<>();

                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROPSTAT, XmlWriter.ElementType.OPENING);
                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.OPENING);

                for (String property : properties.keySet()) {

                    if (property.equals(XML_DISPLAYNAME)) {
                        xmlWriter.writePropertyData(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_DISPLAYNAME, displayName);
                    } else if (property.equals(XML_ISCOLLECTION)) {
                        xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISCOLLECTION, isCollection);
                    } else if (property.equals(XML_CREATIONDATE)
                            && Objects.nonNull(creationDate)) {
                        xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_CREATIONDATE, creationDate);
                    } else if (property.equals(XML_GETLASTMODIFIED)
                            && Objects.nonNull(lastModified)) {
                        xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETLASTMODIFIED, lastModified);

                    } else if (property.equals(XML_ISREADONLY)) {
                        xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISREADONLY, isReadOnly);
                    } else if (property.equals(XML_ISHIDDEN)) {
                        xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISHIDDEN, isHidden);
                    } else if (property.equals(XML_ISSYSTEM)) {
                        xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISSYSTEM, isSystem);
                    } else if (property.equals(XML_ISARCHIVE)) {
                        xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_ISARCHIVE, isArchive);

                    } else if (property.equals(XML_WIN32FILEATTRIBUTES)) {
                        xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_WIN32FILEATTRIBUTES, win32FileAttributes);
                    } else {
                        if (entry.isFolder()
                                && property.equals(XML_RESOURCETYPE)) {
                            xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_RESOURCETYPE, XmlWriter.ElementType.OPENING);
                            xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_COLLECTION, XmlWriter.ElementType.EMPTY);
                            xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_RESOURCETYPE, XmlWriter.ElementType.CLOSING);
                        } else if (!entry.isFolder()
                                && property.equals(XML_GETCONTENTLENGTH)
                                && Objects.nonNull(contentLength)) {
                            xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETCONTENTLENGTH, contentLength);
                        } else if (!entry.isFolder()
                                && property.equals(XML_GETCONTENTTYPE)
                                && Objects.nonNull(contentType)) {
                            xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETCONTENTTYPE, contentType);
                        } else if (!entry.isFolder()
                                && property.equals(XML_GETETAG)) {
                            xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_GETETAG, etag);
                        } else list.add(property);
                    }
                }

                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.CLOSING);
                xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_STATUS, new SuccessState().getStatusLine());
                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROPSTAT, XmlWriter.ElementType.CLOSING);

                Iterator<String> iterator = list.iterator();
                if (iterator.hasNext()) {
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROPSTAT, XmlWriter.ElementType.OPENING);
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.OPENING);

                    while (iterator.hasNext())
                        xmlWriter.writeElement(null, iterator.next(), XmlWriter.ElementType.EMPTY);

                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROP, XmlWriter.ElementType.CLOSING);
                    xmlWriter.writeProperty(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_STATUS, new NotFoundState().getStatusLine());
                    xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_PROPSTAT, XmlWriter.ElementType.CLOSING);
                }

                break;

            default:
        }

        xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_RESPONSE, XmlWriter.ElementType.CLOSING);
    }

    private static void collectProperties(final XmlWriter xmlWriter, final String contextUrl, final Sitemap.Entry entry,
                    final int type, final Properties properties, final int depth)
            throws IOException {

        WebDavFilter.collectProperties(xmlWriter, contextUrl, entry, type, properties);
        if (entry.isFile()
                || depth <= 0)
            return;
        for (Sitemap.Entry folderEntry : ((Sitemap.Folder)entry).getCollection()) {
            if (folderEntry.isHidden())
                continue;
            WebDavFilter.collectProperties(xmlWriter, contextUrl, folderEntry, type, properties, depth -1);
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
                WebDavFilter.METHOD_OPTIONS,
                WebDavFilter.METHOD_HEAD,
                WebDavFilter.METHOD_GET,
                WebDavFilter.METHOD_PROPFIND));
        if (Objects.nonNull(entry)
                && !entry.isReadOnly()) {
            response.setHeader(HEADER_ALLOW, response.getHeader(HEADER_ALLOW)
                    + ", " + String.join(", ",
                    WebDavFilter.METHOD_LOCK,
                    WebDavFilter.METHOD_PUT,
                    WebDavFilter.METHOD_UNLOCK));
        }

        throw new SuccessState();
    }

    private void doPropfind(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {

        final Sitemap.Entry entry = this.locateSitemapEntry(sitemap, request);
        final String contextPath = this.locateRequestContextPath(request);

        try {
            final Properties properties = new Properties();
            final Document document = WebDavFilter.readXmlRequest(request);
            final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            try (final XmlWriter xmlWriter = new XmlWriter(buffer)) {
                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE_URI, XML_MULTISTATUS, XmlWriter.ElementType.OPENING);

                final int depth = WebDavFilter.getDepth(request);

                if (Objects.nonNull(document)) {
                    final Element root = document.getDocumentElement();
                    final NodeList nodeList = root.getChildNodes();
                    final Set<Node> nodeSet = IntStream.range(0, nodeList.getLength()).mapToObj(nodeList::item)
                            .filter(streamNode -> streamNode.getNodeType() == Node.ELEMENT_NODE).collect(Collectors.toSet());

                    if (nodeSet.stream().anyMatch(streamNode -> streamNode.getLocalName().equalsIgnoreCase(XML_ALLPROP))) {
                        WebDavFilter.collectProperties(xmlWriter, contextPath, entry, WEBDAV_FIND_ALL_PROP, properties, depth);
                    } else if (nodeSet.stream().anyMatch(streamNode -> streamNode.getLocalName().equalsIgnoreCase(XML_PROP))) {
                        final Node propNode = nodeSet.stream().filter(streamNode -> streamNode.getLocalName().equalsIgnoreCase(XML_PROP)).findFirst().get();
                        properties.putAll(WebDavFilter.getPropertiesFromNode(propNode));
                        WebDavFilter.collectProperties(xmlWriter, contextPath, entry, WEBDAV_FIND_BY_PROPERTY, properties, depth);
                    } else if (nodeSet.stream().anyMatch(streamNode -> streamNode.getLocalName().equalsIgnoreCase(XML_PROPNAME))) {
                        WebDavFilter.collectProperties(xmlWriter, contextPath, entry, WEBDAV_FIND_PROPERTY_NAMES, properties, depth);
                    } else {
                        WebDavFilter.collectProperties(xmlWriter, contextPath, entry, WEBDAV_FIND_ALL_PROP, properties, depth);
                    }

                } else WebDavFilter.collectProperties(xmlWriter, contextPath, entry, WEBDAV_FIND_ALL_PROP, properties, depth);

                xmlWriter.writeElement(WebDavFilter.WEBDAV_DEFAULT_XML_NAMESPACE, XML_MULTISTATUS, XmlWriter.ElementType.CLOSING);
                xmlWriter.flush();
            }

            response.setStatus(new MultiStatusState().getHttpStatus().value());
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.TEXT_XML_VALUE);
            response.setContentLength(buffer.size());
            response.getOutputStream().write(buffer.toByteArray());

        } catch (SAXException | ParserConfigurationException exception) {
            throw new UnprocessableEntityState();
        }

        throw new MultiStatusState();
    }

    private void doProppatch(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {

        // PROPPATCH is not officially supported, it doesn't make sense.
        // But MS Office / MS-WebDAV-MiniRedir does it sometimes and wants to
        // change timestamps and file attributes.
        // Lazy magic -- here simply PROPFIND is used and the requests are
        // supplied with meta data. Not nice but works.

        this.doPropfind(sitemap, request, response);
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
            response.setContentLength(file.getContentLength());
        if (Objects.nonNull(file.getContentType()))
            response.setContentType(file.getContentType());
        throw new SuccessState();
    }

    private void doGet(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response)
            throws IOException {

        final Sitemap.Entry entry = this.locateSitemapEntry(sitemap, request);

        if (entry.isFolder())
            throw new NotFoundState();

        final Sitemap.File file = (Sitemap.File)entry;
        final Sitemap.Callback outputCallback = file.getOutputCallback();

        try (final MetaOutputStream metaOutputStream = MetaOutputStream.builder()
                .response(response)
                .contentType(file.getContentType())
                .contentLength(file.getContentLength())
                .lastModified(file.getLastModified())
                .build()) {
            try {outputCallback.invoke(URI.create(file.getPath()), file.getProperties(), metaOutputStream);
            } catch (Exception exception) {
                while (exception instanceof InvocationTargetException)
                    exception = (Exception)((InvocationTargetException)exception).getTargetException();
                LOGGER.error("GET callback failed", exception);
                throw new InternalServerErrorState();
            }
        }
        throw new SuccessState();
    }

    private static void acceptContentType(final String accept, final String contentType) {

        if (Objects.isNull(accept)
                || accept.isBlank())
            return;

        final List<String> accepts = Arrays.asList(accept.trim().toLowerCase().split("\\s*,\\s*"));

        if (Objects.isNull(contentType)
                || contentType.isBlank()) {
            if (accepts.contains("*/*"))
                return;
            throw new NotAcceptableState();
        }

        final String mimeTypePattern = "^\\s*([\\w-]+)\\s*/\\s*([\\w.-]+)\\s*(;.*)?$";
        if (!contentType.matches(mimeTypePattern))
            throw new NotAcceptableState();
        final String mimeType = contentType.replaceAll(mimeTypePattern, "$1").toLowerCase();
        final String mimeSubtype = contentType.replaceAll(mimeTypePattern, "$2").toLowerCase();
        if (!accepts.contains("*/*")
                && !accepts.contains(mimeType + "/*")
                && !accepts.contains(mimeType + "/" + mimeSubtype)
                && !accepts.contains("*/" + mimeSubtype))
            throw new NotAcceptableState();
    }

    private void doPut(final Sitemap sitemap, final HttpServletRequest request, final HttpServletResponse response) {

        final Sitemap.Entry entry = this.locateSitemapEntry(sitemap, request);

        if (entry.isReadOnly())
            throw new ForbiddenState();

        response.setHeader(HEADER_CONTENT_LOCATION, this.locateSitemapPath(request));

        final Sitemap.File file = (Sitemap.File)entry;
        WebDavFilter.acceptContentType(file.getAccept(), request.getContentType());
        final Sitemap.Callback inputCallback = file.getInputCallback();
        final MetaInputStream metaInputStream = MetaInputStream.builder()
                .request(request)
                .contentType(file.getContentType())
                .contentLength(file.getContentLength())
                .contentLengthMax(file.getContentLengthMax())
                .build();
        try {inputCallback.invoke(URI.create(file.getPath()), file.getProperties(), metaInputStream);
        } catch (Exception exception) {
            while (exception instanceof InvocationTargetException)
                exception = (Exception)((InvocationTargetException)exception).getTargetException();
            if (exception instanceof MetaInputStream.MetaInputStreamLimitException)
                throw new PayloadTooLargeState();
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

        final String markup = buffer.toString(StandardCharsets.UTF_8);

        response.addHeader(HEADER_LOCK_TOKEN, "<" + token + ">");
        response.setContentType(MediaType.TEXT_XML.toString());
        response.setContentLength(markup.length());
        response.getOutputStream().write(markup.getBytes(StandardCharsets.UTF_8));
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

        final ServletContext servletContext = request.getServletContext();
        final ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);

        // The sitemap is used as a request-based shared instance.
        // The values of the annotations can contain values, expressions and
        // callbacks, which must then be processed in relation to the request.

        if (Objects.isNull(this.properties)) {

            // Beans are cached so that not every request has to scan.
            // Only beans with simple name without dot(s). The others should be
            // internal things, but this assumption is not valid. Order of
            // initialization is unknown, this is done with the first request.

            final Properties properties = new Properties();
            for (final String beanName : Arrays.stream(applicationContext.getBeanDefinitionNames())
                    .filter(Predicate.not(entry -> entry.contains(".")))
                    .collect(Collectors.toSet()))
                properties.put(beanName, applicationContext.getBean(beanName));
            if (Objects.isNull(this.properties))
                this.properties = properties;
        }

        final Properties properties = this.properties.clone();
        properties.put("applicationContext", applicationContext);
        properties.put("servletContext", servletContext);
        properties.put("request", request);
        final HttpSession session = request.getSession(false);
        if (Objects.nonNull(session))
            properties.put("session", session);

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
                case WebDavFilter.METHOD_OPTIONS:
                    this.doOptions(sitemap, request, response);
                case WebDavFilter.METHOD_PROPFIND:
                    this.doPropfind(sitemap, request, response);
                case WebDavFilter.METHOD_HEAD:
                    this.doHead(sitemap, request, response);
                case WebDavFilter.METHOD_GET:
                    this.doGet(sitemap, request, response);
                case WebDavFilter.METHOD_LOCK:
                    this.doLock(sitemap, request, response);
                case WebDavFilter.METHOD_PUT:
                    this.doPut(sitemap, request, response);
                case WebDavFilter.METHOD_UNLOCK:
                    this.doUnlock(sitemap, request, response);
                case WebDavFilter.METHOD_PROPPATCH:
                    this.doProppatch(sitemap, request, response);
                case WebDavFilter.METHOD_MKCOL:
                case WebDavFilter.METHOD_COPY:
                case WebDavFilter.METHOD_MOVE:
                case WebDavFilter.METHOD_DELETE:
                    this.locateSitemapEntry(sitemap, request);
                    throw new ForbiddenState();
                default:
            }

            throw new MethodNotAllowedState(WebDavFilter.METHOD_OPTIONS, WebDavFilter.METHOD_PROPFIND,
                    WebDavFilter.METHOD_HEAD, WebDavFilter.METHOD_GET, WebDavFilter.METHOD_PUT,
                    WebDavFilter.METHOD_LOCK, WebDavFilter.METHOD_UNLOCK);

        } catch (Exception exception) {
            if (!(exception instanceof State)) {
                LOGGER.error("Unexpected exception has occurred", exception);
                exception = new InternalServerErrorState();
            }
            final State state = (State)exception;
            try {state.forceResponseStatus(response);
            } finally {
                LOGGER.info("{} {} {} ({} ms)", state.getHttpStatus(),
                        request.getMethod(), request.getRequestURI(),
                        System.currentTimeMillis() -timing);
            }
        }
    }

    private abstract static class State extends RuntimeException {

        private static final long serialVersionUID = -3285926540309442308L;

        abstract HttpStatus getHttpStatus();

        String getStatusLine() {
            final String message = this.getClass().getSimpleName().replaceAll("State$", "").replaceAll("(?<=[a-z])(?=[A-Z])", " ");
            return "HTTP/1.1 " + this.getHttpStatus().value() + " " + message;
        }

        void forceResponseStatus(final HttpServletResponse response)
                throws IOException {
            if (response.isCommitted())
                return;
            response.setStatus(this.getHttpStatus().value());
            response.flushBuffer();
            response.getOutputStream().close();
        }
    }

    private static class SuccessState extends State {
        
        private static final long serialVersionUID = -1771577635432317281L;

        @Override
        HttpStatus getHttpStatus() {
            return HttpStatus.OK;
        }
    }

    private static class NoContentState extends State {

        private static final long serialVersionUID = 3712271737420787168L;

        @Override
        HttpStatus getHttpStatus() {
            return HttpStatus.NO_CONTENT;
        }
    }

    private static class MultiStatusState extends State {

        private static final long serialVersionUID = 384634503420067272L;

        @Override
        HttpStatus getHttpStatus() {
            return HttpStatus.MULTI_STATUS;
        }
    }

    private static class FoundState extends State {

        private static final long serialVersionUID = 7992253504529221105L;

        final String location;

        FoundState(String location) {
            this.location = location;
        }

        @Override
        HttpStatus getHttpStatus() {
            return HttpStatus.FOUND;
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
        HttpStatus getHttpStatus() {
            return HttpStatus.NOT_MODIFIED;
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

    private static class BadRequestState extends State {

        private static final long serialVersionUID = 6236121705930514520L;

        @Override
        HttpStatus getHttpStatus() {
            return HttpStatus.BAD_REQUEST;
        }
    }

    private static class ForbiddenState extends State {

        private static final long serialVersionUID = 2028565954023402465L;

        @Override
        HttpStatus getHttpStatus() {
            return HttpStatus.FORBIDDEN;
        }
    }

    private static class NotFoundState extends State {

        private static final long serialVersionUID = -3676678322498170899L;

        @Override
        HttpStatus getHttpStatus() {
            return HttpStatus.NOT_FOUND;
        }
    }

    private static class MethodNotAllowedState extends State {

        private static final long serialVersionUID = 7242353961612090064L;

        final String[] allow;

        MethodNotAllowedState(String... allow) {
            this.allow = allow;
        }

        @Override
        HttpStatus getHttpStatus() {
            return HttpStatus.METHOD_NOT_ALLOWED;
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
        HttpStatus getHttpStatus() {
            return HttpStatus.NOT_ACCEPTABLE;
        }
    }

    private static class PreconditionFailedState extends State {

        private static final long serialVersionUID = -4196618688975724595L;

        @Override
        HttpStatus getHttpStatus() {
            return HttpStatus.PRECONDITION_FAILED;
        }
    }

    private static class PayloadTooLargeState extends State {

        private static final long serialVersionUID = 1616244187380236731L;

        @Override
        HttpStatus getHttpStatus() {
            return HttpStatus.PAYLOAD_TOO_LARGE;
        }
    }

    private static class UnprocessableEntityState extends State {

        private static final long serialVersionUID = -6604759761612191353L;

        @Override
        HttpStatus getHttpStatus() {
            return HttpStatus.UNPROCESSABLE_ENTITY;
        }
    }

    private static class LockedState extends State {

        private static final long serialVersionUID = 836006924606414263L;

        @Override
        HttpStatus getHttpStatus() {
            return HttpStatus.LOCKED;
        }
    }

    private static class InternalServerErrorState extends State {

        private static final long serialVersionUID = 3019539164953304989L;

        @Override
        HttpStatus getHttpStatus() {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}