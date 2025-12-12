# Spring Boot Version Compatibility Matrix

## Spring Boot 4.0 Requirements

### Minimum Requirements
- **Java**: 17 minimum, 21+ recommended
- **Spring Framework**: 7.0.x
- **Jakarta EE**: 11
- **Servlet**: 6.1

### Compatible Versions

| Component | Spring Boot 3.5.x | Spring Boot 4.0.x |
|-----------|-------------------|-------------------|
| Java | 17+ | 17+ (21+ recommended) |
| Spring Framework | 6.2.x | 7.0.x |
| Spring Cloud | 2023.0.x | 2025.0.x |
| Jakarta EE | 9/10 | 11 |
| Tomcat | 10.1.x | 12.0.x |
| Jetty | 11.x | 12.x |

## Dependency Upgrade Path

### Core Spring Ecosystem
```xml
<!-- Spring Boot 4.0 -->
<spring.boot.version>4.0.0</spring.boot.version>
<spring.version>7.0.0</spring.version>
<spring.cloud.version>2025.0.0</spring.cloud.version>
```

### Jakarta EE Dependencies
```xml
<!-- Jakarta EE 11 -->
<jakarta.servlet.version>6.1.0</jakarta.servlet.version>
<jakarta.validation.version>3.1.0</jakarta.validation.version>
<jakarta.persistence.version>3.2.0</jakarta.persistence.version>
<jakarta.xml.bind.version>4.0.2</jakarta.xml.bind.version>
```

## Migration Considerations

### High Risk Dependencies
- **MyBatis**: Ensure compatibility with Spring Boot 4.0
- **Druid**: Check for Jakarta EE compatibility
- **Swagger**: Use springdoc-openapi 2.x+
- **JWT Libraries**: Verify Jakarta compatibility

### Low Risk Dependencies  
- **Guava**: Usually compatible
- **OkHttp**: Framework independent
- **Apache Commons**: Generally compatible

## Testing Strategy

### Phase 1: Preparation
1. Backup current project
2. Run full test suite to establish baseline
3. Document current functionality

### Phase 2: Incremental Upgrade
1. Upgrade to Spring Boot 3.5.latest
2. Fix any compatibility issues
3. Test thoroughly

### Phase 3: Major Upgrade
1. Apply javax → jakarta migration
2. Update to Spring Boot 4.0
3. Resolve any new compatibility issues
4. Full regression testing

## Common Issues and Solutions

### javax/jakarta Conflicts
```xml
<!-- Exclude old javax dependencies -->
<dependency>
    <groupId>com.some.library</groupId>
    <artifactId>some-artifact</artifactId>
    <exclusions>
        <exclusion>
            <groupId>javax.persistence</groupId>
            <artifactId>persistence-api</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

### Build Plugin Updates
```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <version>4.0.0</version>
</plugin>
```