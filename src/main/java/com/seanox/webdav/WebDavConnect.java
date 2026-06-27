package com.seanox.webdav;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
class WebDavConnect {

    private final ApplicationContext applicationContext;

    private final ServletContext servletContext;
    private final ServletRequest servletRequest;
    private final ServletResponse servletResponse;

    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;
    private final HttpSession httpSession;

    static WebDavConnect create(final HttpServletRequest request, final HttpServletResponse response) {
        return new WebDavConnect(
                WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()),
                request.getServletContext(),
                request,
                response,
                request,
                response,
                request.getSession(false)
        );
    }

    Map<String, Object> toMap() {
        return new HashMap<>() {{
            this.put("applicationContext", WebDavConnect.this.applicationContext);
            this.put("servletContext", WebDavConnect.this.servletContext);
            this.put("servletRequest", WebDavConnect.this.servletRequest);
            this.put("servletResponse", WebDavConnect.this.servletResponse);
            this.put("httpServletRequest", WebDavConnect.this.httpServletRequest);
            this.put("httpServletResponse", WebDavConnect.this.httpServletResponse);
            this.put("httpSession", WebDavConnect.this.httpSession);
        }};
    }
}