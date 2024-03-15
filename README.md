<p>
  <a href="https://github.com/seanox/spring-webdav/pulls"
      title="Development is waiting for new issues / requests / ideas"
    ><img src="https://img.shields.io/badge/development-passive-blue?style=for-the-badge"
  ></a>
  <a href="https://github.com/seanox/spring-webdav/issues"
    ><img src="https://img.shields.io/badge/maintenance-active-green?style=for-the-badge"
  ></a>
  <a href="http://seanox.de/contact"
    ><img src="https://img.shields.io/badge/support-active-green?style=for-the-badge"
  ></a>
</p>


# Description
WebDAV mapping for Spring Boot -- Use an API like a network drive, open data as
files, edit and save them. Why use a frontend when the data can be edited
directly in an Office application?

Seanox Spring WebDAV is a minimal implementation of WebDAV 1 + 2 for
integration into a Spring Boot based API. The implementation is based on a
virtual file system with virtual entities as an abstraction. The virtual file
system is created strictly via annotations in the managed beans and, like the
virtual entities, does not use any physical file structures. A user can use this
virtual file system as a network drive and has direct access to a Spring Boot
based API without an additional frontend.

<img src="https://github.com/seanox/spring-webdav/blob/master/manual/animation.gif?raw=true"/>


# Features
- Supported HTTP methods: `OPTIONS`, `PROPFIND`, `HEAD`, `GET`, `LOCK`, `PUT`,
  `PROPPATCH`, `UNLOCK`
- Declarative approach with annotations, also supports Spring Expression Language  
- Supports __WebDAV Class 1 + 2__ and thus also MS Office (Excel, Word, PowerPoint, ...)  
  but it does not create or transform Office documents automatically
- Supports use as a network drive
- Supports extended file attributes for Windows
- Supports Spring Boot 2 + 3


# Licence Agreement
Seanox Software Solutions ist ein Open-Source-Projekt, im Folgenden
Seanox Software Solutions oder kurz Seanox genannt.

Diese Software unterliegt der Version 2 der Apache License.

Copyright (C) 2024 Seanox Software Solutions

Licensed under the Apache License, Version 2.0 (the "License"); you may not use
this file except in compliance with the License. You may obtain a copy of the
License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR
CONDITIONS OF ANY KIND, either express or implied. See the License for the
specific language governing permissions and limitations under the License.


# System Requirement
- Java 11 or higher
- Spring Boot 2 or 3


# Downloads
https://mvnrepository.com/artifact/com.seanox/seanox-spring-webdav

for Spring Boot 2:  
https://mvnrepository.com/artifact/com.seanox/seanox-spring-webdav/2.5.0

```xml
<dependency>
    <groupId>com.seanox</groupId>
    <artifactId>seanox-spring-webdav</artifactId>
    <version>2.5.0</version>
</dependency>
```

for Spring Boot 3:  
https://mvnrepository.com/artifact/com.seanox/seanox-spring-webdav/3.5.0

```xml
<dependency>
    <groupId>com.seanox</groupId>
    <artifactId>seanox-spring-webdav</artifactId>
    <version>3.5.0</version>
</dependency>
```

# Manuals
[Getting Started](https://github.com/seanox/spring-webdav/blob/master/manual/getting-started.md)

# Changes 
## 1.5.0 20240313  
BF: Review: Optimization and corrections  
BF: Documentation: Update of content  
CR: WebDavFilter: Optimized access to the servlet API  

[Read more](https://raw.githubusercontent.com/seanox/spring-webdav/master/CHANGES)


# Contact
[Issues](https://github.com/seanox/spring-webdav/issues)  
[Requests](https://github.com/seanox/spring-webdav/pulls)  
[Mail](http://seanox.de/contact)
