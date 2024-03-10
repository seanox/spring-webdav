package com.seanox.webdav;

import jakarta.servlet.ServletConnection;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor(access=AccessLevel.PRIVATE)
class WebDavConnect {

    private final ApplicationContext applicationContext;

    private final ServletContext servletContext;
    private final ServletConnection servletConnection;
    private final ServletRequest servletRequest;
    private final ServletResponse servletResponse;

    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;
    private final HttpSession httpSession;

    static WebDavConnect create(final HttpServletRequest request, final HttpServletResponse response) {
        return new WebDavConnect(
                WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext()),
                request.getServletContext(),
                request.getServletConnection(),
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
            this.put("servletConnection", WebDavConnect.this.servletConnection);
            this.put("servletRequest", WebDavConnect.this.servletRequest);
            this.put("servletResponse", WebDavConnect.this.servletResponse);
            this.put("httpServletRequest", WebDavConnect.this.httpServletRequest);
            this.put("httpServletResponse", WebDavConnect.this.httpServletResponse);
            this.put("httpSession", WebDavConnect.this.httpSession);
        }};
    }
}