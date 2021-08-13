<p>
  <a href="https://github.com/seanox/spring-webdav/pulls"
      title="Development is waiting for new issues / requests / ideas">
    <img src="https://img.shields.io/badge/development-passive-blue?style=for-the-badge">
  </a>
  <a href="https://github.com/seanox/spring-webdav/issues">
    <img src="https://img.shields.io/badge/maintenance-active-green?style=for-the-badge">
  </a>
  <a href="http://seanox.de/contact">
    <img src="https://img.shields.io/badge/support-active-green?style=for-the-badge">
  </a>
</p>


# Description
WebDAV mapping for Spring Boot.  
Use an API like a network drive, open data as files, edit and save them.  
Why use a frontend when the data can be edited directly in an Office application?

Seanox Spring WebDAV is a minimal implementation of WebDAV 1 + 2 for
integration into a Spring Boot based API. The implementation is based on a
virtual file system with virtual entities as an abstraction. The virtual file
system is created strictly via annotations in the managed beans and, like the
virtual entities, does not use any physical file structures.

A user can use this virtual file system as a network drive and has direct
access to a Spring Boot based API without an additional frontend.

<img src="https://github.com/seanox/spring-webdav/raw/main/manual/animation.gif"/>


# Features
- Supported HTTP methods: `OPTIONS`, `PROPFIND`, `HEAD`, `GET`, `LOCK`, `PUT`, `UNLOCK`
- Declarative approach with annotations, also supports Spring Expression Language  
- Supports __WebDAV Class 1 + 2__ and thus also MS Office (Excel, Word, PowerPoint, ...)  
  but it does not create or transform Office documents automatically
- Supports use as a network drive
- Supports extended file attributes for Windows


# Licence Agreement
Seanox Software Solutions ist ein Open-Source-Projekt, im Folgenden
Seanox Software Solutions oder kurz Seanox genannt.

Diese Software unterliegt der Version 2 der GNU General Public License.

Copyright (C) 2021 Seanox Software Solutions

This program is free software; you can redistribute it and/or modify it under
the terms of version 2 of the GNU General Public License as published by the
Free Software Foundation.

This program is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with
this program; if not, write to the Free Software Foundation, Inc., 51 Franklin
Street, Fifth Floor, Boston, MA 02110-1301, USA.


# System Requirement
- Java 11 or higher
- Spring Boot


# Downloads
https://mvnrepository.com/artifact/com.seanox/seanox-spring-webdav
```xml
<dependency>
    <groupId>com.seanox</groupId>
    <artifactId>seanox-spring-webdav</artifactId>
    <version>1.0.0</version>
</dependency>
```


# Manuals
[Getting Started](https://github.com/seanox/spring-webdav/blob/master/manual/getting-started.md)

# Changes (Change Log)
## 1.1.0 20210813 (summary of the current version)
BF: Sitemap: Optimization of accepted/permitted  
BF: Sitemap: Correction of compute if no fallback exists  
BF: Sitemap: Correction of path normalization for non Windows  
CR: Sitemap: Optimization of compute when using data types  
CR: Sitemap: MetaData based on the initial values of WebDavMapping  
CR: Sitemap: Optimization from CreationDate, based on the build date of the application  
CR: WebDavFilter: More tolerant URL pattern  
CR: WebDavFilter: Optimization of the determination from the context path  
CR: WebDavFilter: Optimization from CreationDate, based on the build date of the application  

[Read more](https://raw.githubusercontent.com/seanox/spring-webdav/master/CHANGES)


# Contact
[Issues](https://github.com/seanox/spring-webdav/issues)  
[Requests](https://github.com/seanox/spring-webdav/pulls)  
[Mail](http://seanox.de/contact)


# Thanks!
<img src="https://raw.githubusercontent.com/seanox/seanox/master/sources/resources/images/thanks.png">

[JetBrains](https://www.jetbrains.com/?from=seanox)  
Sven Lorenz  
Andreas Mitterhofer  
[novaObjects GmbH](https://www.novaobjects.de)  
Leo Pelillo  
Gunter Pfannm&uuml;ller  
Annette und Steffen Pokel  
Edgar R&ouml;stle  
Michael S&auml;mann  
Markus Schlosneck  
[T-Systems International GmbH](https://www.t-systems.com)