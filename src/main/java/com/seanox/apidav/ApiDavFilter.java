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

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class ApiDavFilter extends HttpFilter {

    private FileSystem fileSystem;

    @Override
    public void init(final FilterConfig filterConfig)
            throws ServletException {

        this.fileSystem = new FileSystem();

        try {
            final ServletContext servletContext = filterConfig.getServletContext();
            final ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
            for (final String beanName : applicationContext.getBeanDefinitionNames()) {
                final Object object = applicationContext.getBean(beanName);
                // TODO: Why - MethodAnnotationsScanner was not configured?
                // final Reflections reflections = new Reflections(bean.getClass().getName(), new MethodAnnotationsScanner());
                // for (Method method : reflections.getMethodsAnnotatedWith(ApiDavMapping.class)) {
                // }
                final Method[] callbacks = Annotations.getMethods(object.getClass(), ApiDavMapping.class);
                for (final Method callback : callbacks) {
                    final ApiDavMapping mappingAnnotation = (ApiDavMapping)Annotations.getAnnotation(callback, ApiDavMapping.class);
                    this.fileSystem.map(mappingAnnotation, object, callback);
                }
            }

            // TODO: Logging: Output of the FileSystem for Level INFO
        } catch (Exception exception) {
            throw new ServletException(exception);
        }
    }

    // TODO: WebDav Win32FileAttributes: bit-mask readOnly 0x01, hidden 0x02

    private void doPropfind(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        // TODO:
    }

    private void doProppatch(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        // TODO:
    }

    private void doMkcol(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        // TODO:
    }

    private void doCopy(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        // TODO:
    }
    private void doMove(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        // TODO:
    }

    private void doPut(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        // TODO:
    }

    private void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        // TODO:
    }

    private void doOptions(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        // TODO:
    }

    private void doHead(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        // TODO:
    }

    private void doDelete(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        // TODO:
    }

    @Override
    protected void doFilter(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain)
            throws ServletException, IOException {
        super.doFilter(request, response, chain);
    }
}