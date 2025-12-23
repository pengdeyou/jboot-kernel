# JBoot Kernel BOM Module

## Overview

The `kernel-bom` module (Bill of Materials) is a special Maven module that provides centralized dependency version management for all JBoot Kernel modules. It allows users to import a single BOM dependency to manage versions of all JBoot Kernel components.

## Features

- Centralized version management for all JBoot Kernel modules
- Simplified dependency declaration in downstream projects
- Consistent versioning across all JBoot Kernel components
- Automatic alignment with Spring Boot and other framework versions

## Usage

To use the JBoot Kernel BOM in your project, add the following to your `pom.xml`:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.jboot</groupId>
            <artifactId>kernel-bom</artifactId>
            <version>4.7.1</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Once imported, you can declare JBoot Kernel dependencies without specifying versions:

```xml
<dependencies>
    <dependency>
        <groupId>org.jboot</groupId>
        <artifactId>kernel-core</artifactId>
    </dependency>
    <dependency>
        <groupId>org.jboot</groupId>
        <artifactId>kernel-web</artifactId>
    </dependency>
</dependencies>
```

## Managed Dependencies

The BOM manages versions for:

- All JBoot Kernel core modules
- Spring Boot dependencies (via spring-boot-dependencies)
- Spring Cloud dependencies
- Database-related dependencies (MyBatis, Hibernate, etc.)
- Common utility libraries

## Version Alignment

The BOM ensures all JBoot Kernel modules use compatible versions, particularly with:
- Spring Boot 3.5.6
- Spring Cloud Alibaba
- Java 17+ requirements

## Building the BOM

The BOM uses the `flatten-maven-plugin` to generate a flattened POM that includes all managed dependency versions:

```bash
mvn clean install
```

This creates a final BOM artifact that can be imported by other projects.