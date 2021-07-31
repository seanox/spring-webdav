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
* [Registration of WebDavFilter](#registration-of-webdavfilter)
* [Definition of Sitemap](#definition-of-sitemap)
* [Using WebDAV Annotations](#using-webdav-annotations)
* [Using Expressions](#using-expressions)
* [Mapping of Virtual Entity](#mapping-of-virtual-entity)  
* [Attributes of Virtual Entity](#attributes-of-virtual-entity)  
  * [Default value](#default-value-lowest-priority)
  * [Static value from annotation](#static-value-from-annotation)
  * [Dynamic value from the annotation as expression](#dynamic-value-from-the-annotation-as-expression)
  * [Dynamic value from the MetaData](#dynamic-value-from-the-metadata)
  * [Dynamic value from the attribute-method implementation](#dynamic-value-from-the-attribute-method-implementation-highest-priority)
* [Starting the Application](#starting-the-application)
* [Mapping from Network Drive](#mapping-from-network-drive)
* [Read-Only Access](#read-only-access)
* [Read-Write Access](#read-write-access)
* [Validation](#validation)
* [Permission ](#permission)
* [Demo and Examples](#demo-and-examples)  
* [Maven](#maven)  

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

## Registration of WebDavFilter
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
per annotations in the Sitemap. Direct access to the sitemap is not possible,
it is only created via the annotations. The annotations are applied directly in
the managed beans, e.g. in `Component`, `Controller`, `Service`,
`RestController`, ...

The WebDAV implementation contains various annotations. The central component
is `@WebDavMapping`. This defines the virtual entities of the Sitemap and thus
from the virtual file system. `@WebDavMapping` defines a virtual path for this
purpose, which is later used as a reference in other WebDav annotations. The
paths of the annotations are case-insensitive.

```java
@RestController
public class ExampleController {

    @WebDavMapping(path="/example/file.txt")
    void getExampleFile(final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }

    @WebDavMapping(path="/example/fileA.txt")
    @WebDavMapping(path="/example/fileB.txt")
    @WebDavMapping(path="/example/fileC.txt")
    void getExampleFiles(final URI uri, final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }    
    
    ...
}
```
_Example of single and multiple use of annotation for path definition_

The Sitemap supports folders and files based on the paths of virtual entities.
Where and in which managed beans the paths are defined does not matter. During
the initialization of the WebDavFilter all managed beans are scanned and the
information for the sitemap is collected.

The Sitemap is created with the initialization of the WebDavFilter. During this
process the paths are checked for validity, possible collisions and possible
conflicts. Similar to the Spring mapping, this causes an exception during the
initialization of the application and aborts the application.

## Using WebDAV Annotations
The WebDAV relevant annotations always have a reference to methods which are
called by the WebDAV implementation. The methods generally have no fixed
signature, no naming conventions and use the data types of used arguments as
placeholders which are then filled with the available data. Unknown
placeholders are ignored and used with `null`. Multiple used placeholders are
filled with the same data instances.

The annotations supports callbacks and expression, which will be described
later. If errors occur at runtime in callbacks, expressions or during automatic
conversion of return values, this causes an error log entry, but processing is
not interrupted. In this case the value `null` is used.

Why the exception behavior?

If one mapping is broken, all others entries in a folder will not work either.

## Using Expressions
Expressions are an important part of the WebDAV annotations for intgeration
into Spring based applications and therefore also use the
[Spring Expression Language](https://www.baeldung.com/spring-expression-language).

Because expressions use a special annotation, there is no need for the usual
`#{...}` syntax.

The expressions are interpreted in their own context. In this, all beans whos
name does not contain a dot, as well as `applicationContext`, `servletContext`,
`request` and `session` are available as variables.

If errors/exceptions occur during the interpretation or the optional
conversion, this will cause an error output in the logging, but the processing
will not be aborted, the WebDAV implementation will use the value `null` as
result.

Why the exception behavior?

If an mapping in the sitemap is incorrect, all other entries in a folder will
not work either.

Automatic conversion from data type: Usually the return value of expressions is
an object.However, the WebDAV implementation also uses primitive data types
internally and wants to support the use of text as value. Therefore, if the
data type of the return value does not match, the valueOf-method of the
expected data type are used for Integer, Boolean and String if necessary.

Possible errors during the conversion cause an error output as already
described, but not an abort and the value null is used.

## Mapping of Virtual Entity
The WebDAV implementation contains various annotations. The central component
is `@WebDavMapping`. This defines the virtual entities of the Sitemap and thus
from the virtual file system. `@WebDavMapping` defines a virtual path for this
purpose, which is later used as a reference in other WebDav annotations. The
paths of the annotations are case-insensitive.

In the context of HTTP, `@WebDavMapping` is the base for the GET method.

```java
@RestController
public class ExampleController {

    @WebDavMapping(path="/example/file.txt")
    void getExampleFile(final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }

    @WebDavMapping(path="/example/file.txt", lastModified="2000-01-01 00:00:00", attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentType, phrase="'text/plain'"),
            ...
    })
    void getExampleFile(final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }

    @WebDavMapping(path="/example/file.txt", lastModified="2000-01-01 00:00:00")
    void getExampleFile(final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }

    @WebDavMapping(path="/example/fileA.txt")
    @WebDavMapping(path="/example/fileB.txt")
    @WebDavMapping(path="/example/fileC.txt")
    void getExampleFiles(final URI uri, final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }    
    
    ...
}
```
_Example of single and multiple use of annotation_

__@WebDavMapping__ supports the following attributes:
- __path__ Path as a reference of the virtual entity
- __contentType__ Static value of ContentType
- __contentLength__ Static value of ContentLength
- __creationDate__ Static value of CreationDate (`yyyy-MM-dd HH:mm:ss`)
- __lastModified__ Static value of LastModified (`yyyy-MM-dd HH:mm:ss`)
- __readOnly__ Static value of flag ReadOnly
- __hidden__ Static value of flag Hidden
- __accepted__ Static value of flag Accepted
- __permitted__ Static value of flag Permitted
- __attributeExpressions__ Optionally to the static values, an array of dynamic
  expressions

The annotation can be used multiple times for different virtual entities
in one method. The method has no fixed signature and the data types of the
arguments are considered as placeholders and filled accordingly. If arguments
with the same data type are used multiple times, they are filled with the same
object multiple times. Unknown data types are filled with `null`.

__@WebDavMapping__ supports the following data types as arguments:
- __URI__ Path of the virtual entity.
- __Properties__ Collector with relevant runtime, request and meta information
  as a nested case-insensitive map.
- __MetaProperties__ MetaProperties, read-only collector with all attributes of
  the virtual entity.
- __MetaOutputStream__ OutputStream with meta information for the response header.

No return value is expected.

## Attributes of Virtual Entity
For WebDAV, file attributes are an important thing. Especially the last change
is important, because WebDAV is designed for versioning among other things,
even if it has no direct use in this WebDAV implementation.    

Following file attributes are supported:

- __ContentType__ indicates the media type of the entity.  
  This value can be determined automatically from the file extension from the
  virtual path and therefore usually does not need to be defined.


- __ContentLength__ is the size of the entity, in decimal number of bytes.  
  WebDAV does not need this value, but it is helpful in the representation of
  the virtual entity in File Explorer / File Manager. The value should be
  cached based on the LastModified value in the application.
  
    
- __CreationDate__ the last modification date of the entity, used to compare
  several versions of the same entity.  
  WebDAV does not need this value, but it is helpful in the representation of
  the virtual entity in File Explorer / File Manager. The default value uses
  the creation date of the application / jar and normally does not need to be
  defined.


- __LastModified__ is the last modification of the entity and the basis for the
  ETag, both for comparing different versions.  
  This value is very important for many WebDAV clients as it ensures that a
  virtual entity can be opened for editing and there is no version violation
  when saving.


- __ReadOnly__ indicates if the entity is read-only.  
  The default value is false and must be deliberately set to `true`. However,
  the value `true` requires that an input method has been annotated to receive
  the data.  

 
- __Hidden__ indicates if the entity is hidden but usable.  
  The value `true` has only a visual effect in File Explorer / File Manager
  where the virtual entity is then not displayed. Folder structures that are
  only created indirectly via the paths of the virtual entities are only
  displayed in File Explorer / File Manager if they contain at least one
  visible entity. The root folder is an exception, this is always displayed and
  would then be empty.

The following additional attributes are supported:

- __Accepted__ internal attribute that indicates if the request is valid for
  the entity.  
  If not the request will be responded with status 400.


- __Permitted__ internal attribute that specifies if the request is permitted
  for the entity.  
  If not, the request is responded with status 404.  
  Why status 404 instead of 401?  
  It should not be possible to scan the folder structure.

Permitted has the higher priority and both affect all methods except `OPTIONS`.  

Attributes can be defined in different ways with different priority (ascending).  
The priority is important when the same attribute is defined multiple times for
the same entity.

The value of attributes can suppress them. This means that they are not
included in the response header and not in the response XML from the `PROPFIND`.

### Default value (lowest priority)

Without further definition, the following default values are used:

| Attribute       | Default Value                       | Suppress Value                   |
| --------------- |------------------------------------ | -------------------------------- |
| `ContentType`   | `application/octet-stream`          | `null`, empty                    | 
| `ContentLength` | `-1`                                | `null`, empty, value less than 0 |
| `CreationDate`  | build date of the application / jar | `null`, empty                    |
| `LastModified`  | build date of the application / jar | `null`                           |
| `ReadOnly`      | `true`                              |                                  |
| `Hidden`        | `false`                             |                                  |
| `Accepted`      | `true`                              |                                  |
| `Permitted`     | `true`                              |                                  |

### Static value from annotation
In the easiest case the attributes can be defined as static values directly
with `@WebDavMapping`.

```java
@RestController
public class ExampleController {

    @WebDavMapping(path="/example/file.txt", lastModified="2000-01-01 00:00:00")
    void getExampleFile(final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }
    
    ...
}
```
_Example of usage_

### Dynamic value from the annotation as expression
Dynamic values with expressions are more flexible and consider the use case.

```java
@RestController
public class ExampleController {

    @WebDavMapping(path="/example/fileA.txt", attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()")
    })
    @WebDavMapping(path="/example/fileB.txt", attributeExpressions={
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.LastModified, phrase="new java.util.Date()"),
            @WebDavMappingAttributeExpression(attribute=WebDavMappingAttribute.ContentType, phrase="'text/plain'"),
            ...
    })
    void getExampleFile(final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }

    ...
}
```
_Example of single and multiple use of annotation_

### Dynamic value from the MetaData
Implementing a method to get the meta-data can provide all the information
about a virtual entity in one place. By using the supported parameters: `URI`,
`Properties`, `WebDavMappingAttribute` it can also be used for different
virtual entities.

```java
@RestController
public class ExampleController {

    private static final String MAPPING_FILE_TXT = "/example/file.txt";
    
    @WebDavMapping(path=MAPPING_FILE_TXT)
    void getExampleFile(final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }

    @WebDavMetaMapping(path=MAPPING_FILE_TXT)
    void getExampleFileMeta(final MetaData meta) {
        meta.setLastModified(new Date());
    }

    @WebDavMetaMapping(path="/example/fileA.txt")
    @WebDavMetaMapping(path="/example/fileB.txt")
    @WebDavMetaMapping(path="/example/fileC.txt")
    void getExampleFilesMeta(final MetaData meta) {
        meta.setLastModified(new Date());
    }
    
    ...
}
```
_Example of single and multiple use of annotation_

__@WebDavMetaMapping__ supports the following attributes:
- __path__ Path as a reference of the virtual entity.

__@WebDavMetaMapping__ supports the following data types as arguments:
- __URI__ Path of the virtual entity.
- __Properties__ Collector with relevant runtime, request and meta information
  as a nested case-insensitive map.
- __WebDavMappingAttribute__ The attribute requested with the method.
- __MetaData__ Writable collector containing all relevant attributes for a
  virtual entity.

No return value is expected.

### Dynamic value from the attribute-method implementation (highest priority)
Implementing a method to get the value for a specific attribute has the highest
priority. By using the supported parameters: `URI`, `Properties`,
`WebDavMappingAttribute` it can also be used for different virtual entities.

```java
@RestController
public class ExampleController {

    private static final String MAPPING_FILE_TXT = "/example/file.txt";
    
    @WebDavMapping(path=MAPPING_FILE_TXT)
    void getExampleFile(final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }

    @WebDavAttributeMapping(path=MAPPING_FILE_TXT, attribute=WebDavMappingAttribute.LastModified)
    Date getExampleFileLastModified() {
        return new Date();
    }

    @WebDavAttributeMapping(path="/example/fileA.txt", attribute=WebDavMappingAttribute.LastModified)
    @WebDavAttributeMapping(path="/example/fileB.txt", attribute=WebDavMappingAttribute.LastModified)
    @WebDavAttributeMapping(path="/example/fileC.txt", attribute=WebDavMappingAttribute.LastModified)
    Date getExampleFilesLastModified() {
        return new Date();
    }
    
    ...
}
```
_Example of single and multiple use of annotation_

__@WebDavAttributeMapping__ supports the following attributes:
- __path__ Path as a reference of the virtual entity.
- __attribute__ Referenced attribute.

__@WebDavAttributeMapping__ supports the following data types as arguments:
- __URI__ Path of the virtual entity.
- __Properties__ Collector with relevant runtime, request and meta information
  as a nested case-insensitive map.
- __WebDavMappingAttribute__ The attribute requested with the method.

The expected data type of the return value depends on the attribute:  
`Boolean`, `Integer`, `String`, `Date`

## Starting the Application
TODO:

## Mapping from Network Drive
TODO:

## Read-Only Access
TODO:

## Read-Write Access
TODO:

In the context of HTTP, `@WebDavInputMapping` is the base for the PUT method.

```java
@RestController
public class ExampleController {

    private static final String MAPPING_FILE_TXT = "/example/file.txt";
    
    @WebDavMapping(path=MAPPING_FILE_TXT)
    void getExampleFile(final MetaOutputStream outputStream) throws IOException {
        outputStream.write("Hello WebDAV!".getBytes());
    }
    
    @WebDavInputMapping(path=MAPPING_FILE_TXT, accept="text/*", contentLengthMax=1073741824)
    void putExampleFile(final MetaInputStream inputStream) throws IOException {
        ...
    }

    @WebDavInputMapping(path=MAPPING_FILE_TXT, attributeExpressions={
            @WebDavInputMappingAttributeExpression(attribute=WebDavInputMappingAttribute.Accept, phrase="'text/*'"),
            @WebDavInputMappingAttributeExpression(attribute=WebDavInputMappingAttribute.ContentLengthMax, phrase="1073741824")
    })
    void putExampleFile(final MetaInputStream inputStream) throws IOException {
        ...
    }

    @WebDavInputMapping(path="/example/fileA.txt")
    @WebDavInputMapping(path="/example/fileB.txt")
    @WebDavInputMapping(path="/example/fileC.txt")
    void putExampleFiles(final MetaInputStream inputStream) throws IOException {
        ...
    }

    @WebDavAttributeMapping(path=MAPPING_FILE_TXT, attribute=WebDavMappingAttribute.LastModified)
    Date getExampleFileLastModified() {
        return new Date();
    }
    
    ...
}
```
_Example of single and multiple use of annotation_

__@WebDavInputMapping__ supports the following attributes:
- __path__ Path as a reference of the virtual entity.
- __accept__ Based on the Accept HTTP header, a comma-separated list of
  expected MimeType / ContentType. The wildcard character is supported (`*/*`,
  `mime-type/*`, `mime-type/mime-subtype`, `*/mime-subtype`). If the attribute
  is used, requests that do not match the filter will be rejected with status
  406 / Not-Acceptable. Without specification or if empty, all content types
  are accepted.
- __contentLengthMax__ Limits the size of the request body in bytes. For this
  purpose the number of bytes read is evaluated. If the limit is exceeded,
  the requests are rejected with status 413 / Payload-Too-Large. Without
  specification or a value less than 0, the limit is ignored.
- __attributeExpressions__ Optionally to the static values, an array of dynamic
  expressions 

__@WebDavInputMapping__ supports the following data types as arguments:
- __URI__ Path of the virtual entity.
- __Properties__ Collector with relevant runtime, request and meta information
  as a nested case-insensitive map.
- __MetaProperties__ MetaProperties, read-only collector with all attributes
  of the virtual entity.
- __MetaInputStream__ InputStream with read-only meta information.

No return value is expected.

## Validation
TODO:

## Permission
TODO:

## Demo and Examples 
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
