# Getting Started

## What is Seanox Spring-WebDAV?
Seanox Spring WebDAV is a minimal implementation of WebDAV 1 + 2 for
integration into a Spring Boot based API. The implementation is based on a
virtual file system with virtual entities as an abstraction. The virtual file
system is created strictly via annotations in the managed beans and, like the
virtual entities, does not use any physical file structures.

A user can use this virtual file system as a network drive and has direct
access to a Spring Boot based API without an additional frontend.

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
  * [Default value](#default-value-lowest-priority)
  * [Static value from annotation](#static-value-from-annotation)
  * [Dynamic value from the annotation as expression](#dynamic-value-from-the-annotation-as-expression)
  * [Dynamic value from the meta-method implementation](#dynamic-value-from-the-meta-method-implementation)
  * [Dynamic value from the attribute-method implementation](#dynamic-value-from-the-attribute-method-implementation-highest-priority)
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

```xml
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

```java
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

```java
@RestController
public class ExampleController {

    @WebDavMapping(path="/example/file.txt")
    void getExampleEntity(final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }

    @WebDavMapping(path="/example/fileA.txt")
    @WebDavMapping(path="/example/fileB.txt")
    @WebDavMapping(path="/example/fileC.txt")
    void getExampleEntities(final URI uri, final MetaOutputStream outputStream) throws IOException {
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
For WebDAV, file attributes are an important thing. Especially the last change
is important, because WebDAV is designed for versioning among other things,
even if it has no direct use in this WebDAV implementation.    

Following file attributes are supported:
- __ContentType__ indicates the media type of the entity
- __ContentLength__ is the size of the entity, in decimal number of bytes 
- __CreationDate__ the last modification date of the entity, used to compare
  several versions of the same entity
- __LastModified__ is the last modification of the entity and the basis for the
  ETag, both for comparing different versions
- __ReadOnly__ indicates if the entity is read-only.
- __Hidden__ indicates if the entity is hidden but usable

The following additional attributes are supported:
- __Accepted__ internal attribute that indicates if the request is valid for
  the entity; if not the request will be responded with status 400
- __Permitted__ internal attribute that specifies if the request is permitted
  for the entity; if not, the request is responded with status 404

Both affect all methods except `OPTIONS`.

Why status 404 instead of 401?  
It should not be possible to scan the folder structure.

Attributes can be defined in different ways with different priority (ascending).  
The priority is important when the same attribute is defined multiple times for
the same entity.

### Default value (lowest priority)

Without further definition, the following default values are used:

| Attribute       | Default Value                       |
| --------------- |------------------------------------ |
| `ContentType`   | `application/octet-stream`          |
| `ContentLength` | `-1`                                |
| `CreationDate`  | build date of the application / jar |
| `LastModified`  | build date of the application / jar |
| `ReadOnly`      | `true`                              |
| `Hidden`        | `false`                             |
| `Accepted`      | `true`                              |
| `Permitted`     | `true`                              |

### Static value from annotation
TODO:

```java
@RestController
public class ExampleController {

    @WebDavMapping(path="/example/file.txt", lastModified="2000-01-01 00:00:00")
    void getExampleEntity(final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }
    
    ...
}
```

### Dynamic value from the annotation as expression
TODO:

```java
@RestController
public class ExampleController {

    @WebDavMapping(path="/example/file.txt", attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")
    })
    void getExampleEntity(final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }
    
    ...
}
```

### Dynamic value from the meta-method implementation
TODO:

```java
@RestController
public class ExampleController {

    private static final String MAPPING_FILE_TXT = "/example/file.txt";
    
    @WebDavMapping(path=MAPPING_FILE_TXT)
    void getExampleEntity(final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }

    @WebDavMetaMapping(path=MAPPING_FILE_TXT)
    void getExampleEntityMeta(final MetaData meta) {
        meta.setLastModified(new Date());
    }

    @WebDavMetaMapping(path="/example/fileA.txt")
    @WebDavMetaMapping(path="/example/fileB.txt")
    @WebDavMetaMapping(path="/example/fileC.txt")
    void getExampleEntitiesMeta(final MetaData meta) {
        meta.setLastModified(new Date());
    }
    
    ...
}
```

### Dynamic value from the attribute-method implementation (highest priority)
TODO:

```java
@RestController
public class ExampleController {

    private static final String MAPPING_FILE_TXT = "/example/file.txt";
    
    @WebDavMapping(path=MAPPING_FILE_TXT)
    void getExampleEntity(final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }

    @WebDavAttributeMapping(path=MAPPING_FILE_TXT, attribute=WebDavMappingAttribute.LastModified)
    Date getExampleEntityLastModified() {
        return new Date();
    }

    @WebDavAttributeMapping(path="/example/fileA.txt", attribute=WebDavMappingAttribute.LastModified)
    @WebDavAttributeMapping(path="/example/fileB.txt", attribute=WebDavMappingAttribute.LastModified)
    @WebDavAttributeMapping(path="/example/fileC.txt", attribute=WebDavMappingAttribute.LastModified)
    Date getExampleEntitiesLastModified() {
        return new Date();
    }
    
    ...
}
```

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
