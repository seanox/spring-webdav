# Development
General notes, hints and comments for development.

## Contents Overview
- [Environment](environment)
- [Banner](#banner)
- [Version Number](#version-number)
- [Documentation](#documentation)
- [Exception Handling](#exception-handling)
- [Using final](#using-final)

## Environment
- Java 21

## Banner
https://patorjk.com/software/taag/#p=display&h=1&v=1&f=Standard  
https://patorjk.com/software/taag/#p=display&h=1&v=1&f=Standard&t=webDAV%20%20%20Example

| Parameter        | Value    |
| ---------------- | -------- |
| Font             | Standard |
| Character Width  | Fitted   |
| Character Height | Fitted   |

# Version Number
The version number for components is based on the version number of the release.
Thus, when a component is changed, it uses the version number of the release
where the changes were made. The procedure is easier if a release is created
over a long time and components are changed again and again.

# Documentation
The important things are the "why" and the public API. That is why comments can
be dispensed with in many places. It should only be understandable why one has
chosen a special form of implementation.

For complex code, a short description of how it works is helpful.

# Exception handling
Only catch exceptions where the code can meaningfully handle the exception,
otherwise keep throwing the exception.

# Using final
It is a helper during implementation. Too many variables and multiple assignment
can be a hint to better structure or split the code.

# Publish a release

## GitHub
- Start in the project directory
- Search and solve all TODO entries
- Check and if necessary update the version and time stamp of all components
  comparison with the tag from the last release.
- Maven: check and update of dependencies
- __Use Java 21__
- Final test (Junit only)  
  Call `mvn clean test`
- Final test (JUnit only + GPG)  
  Call `mvn clean verify`
- __Provided all tests are successful!__
- Finalize version in the classes
- Finalize version in CHANGES
- Final update of CHANGES / README.md / pom.xml  
  Call `ant -f ./development/build.xml release`
  this also includes updating the version in `pom.xml`  
- Final commit of the release  
  Release x.x.x
- Create a tag without comments  

## Maven Repository
- __Based on the previous step__
- Call `ant -f ./development/build.xml publish`  
  see also https://central.sonatype.com/artifact/com.seanox/seanox-spring-webdav  
  see also https://mvnrepository.com/artifact/com.seanox/seanox-spring-webdav
