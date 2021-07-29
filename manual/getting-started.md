# Getting Started

## What is Seanox Spring-WebDAV?

## Features
- Supported HTTP methods: `OPTIONS`, `PROPFIND`, `HEAD`, `GET`, `LOCK`, `PUT`, `UNLOCK`
- Declarative approach with annotations, also supports Spring Expression Language
- Supports __WebDAV Class 1 + 2__ and thus also MS Office (Excel, Word, PowerPoint, ...)  
  but it does not create or transform Office documents automatically
- Supports use as a network drive
- Supports extended file attributes for Windows

## Contents Overview

## Integration

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
`mvn -Dspring.profiles.active=demo -Dexec.mainClass=com.seanox.api.Application -Dexec.classpathScope=test exec:java`  
Rebuilds the project and starts the demo without test paths.  
`net use x: http://127.0.0.1:8080 /persistent:no`  
Maps the WebDAV as drive X: in Windows (not permanent).  
`net use x: /delete /YES`  
Remove the mapping for drive X: in Windows.

`mvn clean deploy`
Rebuilds the project and publish a release.
