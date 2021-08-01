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
package com.seanox.api;

import com.seanox.webdav.WebDavFilter;
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

/**
 * Example for the integration of webDAV in a Spring bases application.<br>
 * <br>
 * Normally @ComponentScan does not need to be used because Application.class
 * already sets the package com.seanox.api, but in this example there is still
 * a parallel com.seanox.test package. There are no classical UnitTests. Tests
 * are integrated in the application and are executed at startup. For this
 * purpose, the tests are integrated as @components. Therefore @ComponentScan
 * must be configured.<br>
 * <br>
 * Why are the tests not in com.seanox.webdav?<br>
 * Spring Test is used for the tests. For this @ComponentScan must scan the
 * package. For the release version, however, it should be ensured that the
 * library com.seanox.webdav also works without @ComponentScan and therefore
 * another package is used for the tests of the package com.seanox.webdav.<br>
 * <br>
 * SpringBootServletInitializer to prepare the application to deploy on
 * external servlet container/runner.<br>
 * <br>
 * Application 1.0.0 20210801<br>
 * Copyright (C) 2021 Seanox Software Solutions<br>
 * All rights reserved.
 *
 * @author  Seanox Software Solutions
 * @version 1.0.0 20210801
 */
@ComponentScan({"com.seanox.api", "com.seanox.test"})
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    public static void main(final String... options) {
        final SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setBannerMode(Banner.Mode.CONSOLE);
        springApplication.run(options);
    }

    @Bean
    public FilterRegistrationBean webDavFilterRegistration() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new WebDavFilter());
        registration.addUrlPatterns("/*");
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