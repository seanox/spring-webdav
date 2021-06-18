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
package com.seanox.api;

import com.seanox.apidav.ApiDavFilter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// Normally @ComponentScan does not need to be used because Application.class
// already sets the package com.seanox.api, but in this example there is still
// a parallel com.seanox.test package. There are no classical UnitTests. Tests
// are integrated in the application and are executed at startup. For this
// purpose, the tests are integrated as @components. Therefore @ComponentScan
// must be configured.

// Why are the tests not in com.seanox.apidav?
// Spring Test is used for the tests. For this @ComponentScan must scan the
// package. For the release version, however, it should be ensured that the
// library com.seanox.apidav also works without @ComponentScan and therefore
// another package is used for the tests of the package com.seanox.apidav.

// SpringBootServletInitializer to prepare the application to deploy on
// external servlet container/runner.

@ComponentScan({"com.seanox.api", "com.seanox.test"})
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    public static void main(String... options) {
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setBannerMode(Banner.Mode.CONSOLE);
        springApplication.setBanner((environment, sourceClass, output) -> output.println("A Fallback Banner...!"));
        springApplication.run(options);
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {

        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new ApiDavFilter());
        registration.setName(ApiDavFilter.class.getName());
        registration.addUrlPatterns("/*", "/context/*");
        registration.setOrder(1);
        return registration;
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix="example")
    @EnableConfigurationProperties
    public static class ApplicationConfiguration {
        // Example of the configuration as an inner class in combination with lombok
    }
}