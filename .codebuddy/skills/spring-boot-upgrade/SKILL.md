name: spring-boot-upgrade
description: This skill should be used when users need to upgrade Spring Boot projects, particularly for major version upgrades like from 3.x to 4.0. It provides automated workflows for dependency updates, javax to jakarta migration, and compatibility validation.

# Spring Boot Upgrade Skill

## Overview
This skill automates the process of upgrading Spring Boot projects between major versions, with special focus on Spring Boot 4.0 migration including javax to jakarta package migration.

## When to Use
- User asks to upgrade Spring Boot version
- User asks about Spring Boot 4.0 compatibility
- User needs javax to jakarta migration
- User wants to assess upgrade feasibility

## Automated Workflows

### 1. Upgrade Assessment
Analyze current project structure and dependencies to determine upgrade complexity:
```bash
# Check current Spring Boot version
grep -r "spring-boot" pom.xml kernel-*/pom.xml

# Identify javax dependencies
find . -name "*.java" -exec grep -l "javax\." {} \;

# Check Jakarta EE readiness
find . -name "*.java" -exec grep -l "jakarta\." {} \;
```

### 2. Dependency Version Updates
Automatically update pom.xml files with compatible versions:
```xml
<!-- Spring Boot 4.0 target versions -->
<spring.boot.version>4.0.0</spring.boot.version>
<spring.version>7.0.0</spring.version>
```

### 3. Package Migration (javax → jakarta)
Replace deprecated javax imports with jakarta equivalents:

**Key Migrations:**
- `javax.xml.bind.*` → `jakarta.xml.bind.*`
- `javax.servlet.*` → `jakarta.servlet.*`
- `javax.persistence.*` → `jakarta.persistence.*`
- `javax.activation.*` → `jakarta.activation.*`

**No Change Required:**
- `javax.crypto.*` → keep as is
- `javax.net.ssl.*` → keep as is
- Other JDK standard library packages

### 4. Validation Steps
- Update build plugins to compatible versions
- Add Jakarta EE dependencies where needed
- Remove deprecated dependencies

## Usage Instructions

1. **Run upgrade assessment**: Execute assessment workflow first to understand project complexity
2. **Backup project**: Always create backup before major upgrades
3. **Incremental upgrades**: Upgrade to latest 3.x before jumping to 4.0
4. **Test each module**: Validate functionality after each major change

## Risk Mitigation

- Maintain backward compatibility dependencies where possible
- Use transformer tools for automatic migration
- Validate build before and after each step
- Test critical business logic thoroughly

## Expected Timeline

- Simple upgrades (few dependencies): 2-3 days
- Medium complexity (10+ modules): 1-2 weeks
- Complex projects (many custom dependencies): 2-4 weeks