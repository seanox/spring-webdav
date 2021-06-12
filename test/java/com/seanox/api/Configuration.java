package com.seanox.api;

import com.seanox.restdav.RestDavFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RestDavFilter());
        registration.setName(RestDavFilter.class.getName());
        registration.addUrlPatterns("/context/*", "/*");
        registration.setOrder(1);
        return registration;
    }
}