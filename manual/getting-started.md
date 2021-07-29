# Getting Started

## What is Seanox Spring-WebDAV?
Seanox Spring WebDAV is a minimal implementation of WebDAV 1 + 2 for
integration into a Spring Boot based API. The implementation is based on a
virtual file system with virtual entities as an abstraction. The virtual file
system is created strictly via annotations in the managed beans and, like the
virtual entities, does not use any physical file structures.

A user can use this virtual file system as a network drive and has direct
access to a Spring Boot based API without an additional Frontend.

## Features
- Supported HTTP methods: `OPTIONS`, `PROPFIND`, `HEAD`, `GET`, `LOCK`, `PUT`, `UNLOCK`
- Declarative approach with annotations, also supports Spring Expression Language
- Supports __WebDAV Class 1 + 2__ and thus also MS Office (Excel, Word, PowerPoint, ...)  
  but it does not create or transform Office documents automatically
- Supports use as a network drive
- Supports extended file attributes for Windows

## Contents Overview

* [Integration](#integration)
* [Registration of WebDAV filter](#registration-of-webdav-filter)
* [Definition of Sitemap](#definition-of-sitemap)
* [Attributes of the virtual entity](#attributes-of-the-virtual-entity)  
* [Starting the application](#starting-the-application)
* [Mapping from network drive](#)
* [Read-only access](#)
* [Read-write access](#)
* [Validation](#)
* [Permission ](#)
* [Maveb](#maven)  

## Integration
Create a new Spring Boot based project e.g. with https://start.spring.io or use
an existing one and add the dependencies to the project, e.g. in the `pom.xml`
for a Maven based project.

```
<dependency>
    <groupId>com.seanox</groupId>
    <artifactId>seanox-spring-webdav</artifactId>
    <version>1.0.0</version>
</dependency>
```

## Registration of WebDAV filter
To use the WebDAV implementation, the WebDavFilter must be registered, which is
very easy in Spring. At least a URL pattern is needed that defines the context
path of the WebDavFilter. Thus, the WebDAV implementation should always use its
own scope. Optionally, the order of filter can be specified, which is useful
when using filter chains.

Example of a easy registration, directly in an application:
```
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(final String... options) {
        final SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.setBannerMode(Banner.Mode.CONSOLE);
        springApplication.run(options);
    }

    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        final FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new WebDavFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
}
```

## Definition of Sitemap
The WebDAV implementation uses a virtual file system. This file system is built
per annotations in the sitemap. The sitemap cannot be accessed directly, it is
only built via the annotations. The annotations are applied directly in the
managed beans, e.g. in `Component`, `Controller`, `Service`, `RestController`,
...

The WebDAV implementation contains various annotations. The central component
is `WebDavMapping`. This defines the virtual entities of the Sitemap and thus
from the virtual file system. The `WebDavMapping` defines a virtual path for
this purpose, which is later used as a reference in other WebDav annotations.
The paths of the annotations are case-insensitive.

```
@RestController
public class ExampleController {

    @WebDavMapping(path="/example/file.txt")
    void getExampleEntity(final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }
    
    ...
}
```

The WebDAV relevant annotations always have a reference to methods which are
called by the WebDAV implementation. The methods generally have no fixed
signature and use the data types of used arguments as placeholders which are
then filled with the available data. Unknown placeholders are ignored and used
with `null`. Multiple used placeholders are filled with the same data
instances.

TODO:

## Attributes of the virtual entity
TODO:

## Starting the application
TODO:

## Mapping from network drive
TODO:

## Read-only access
TODO:

## Read-write access
TODO:

## Validation
TODO:

## Permission
TODO:

## Maven
`mvn clean install`  
Downloads the dependencies and then executes the tests.

`mvn clean install -DskipTests`  
Downloads the dependencies and skips the tests.

`mvn clean test`  
Rebuilds the project and executes the tests.

`mvn clean compile site`  
Rebuilds the project, starts the static code analysis and creates the report:  
`./spring-webdav/target/site/project-reports.html`

`mvn clean package -DskipTests`  
Rebuilds the project incl. JavaDoc but without tests.

`mvn clean package -DskipTests`  
`mvn -Dspring.profiles.active=demo -Dexec.mainClass=com.seanox.api.Application -Dexec.classpathScope=test exec:java`  
Rebuilds the project and starts the demo without test paths.  
`net use x: http://127.0.0.1:8080 /persistent:no`  
Maps the WebDAV as drive X: in Windows (not permanent).  
`net use x: /delete /YES`  
Remove the mapping for drive X: in Windows.

`mvn clean deploy`
Rebuilds the project and publish a release.
