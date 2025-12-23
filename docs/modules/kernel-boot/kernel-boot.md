# JBoot Kernel Boot Module

## Overview

The `kernel-boot` module is the core startup module of JBoot-Kernel, providing automatic configuration and basic functionality for JBoot applications. It integrates various core components and provides a unified entry point for application initialization.

## Features

- Automatic configuration of core components
- Integration with Spring Boot ecosystem
- Support for multi-environment configuration
- Built-in file upload and download functionality
- Request logging and monitoring
- Exception handling and unified response formatting
- Token-based authentication support

## Core Components

### Configuration Classes

- **JBootAutoConfiguration**: Main auto-configuration class that initializes core components
- **JWebMvcConfiguration**: Web MVC configuration for request handling and response formatting
- **RetryConfiguration**: Configuration for automatic retry mechanisms

### Controllers

- **JController**: Base controller class providing common functionality for all controllers

### File Handling

- **JFile**: File operation utility class for easy file upload/download
- **FileProxyManager**: Manages file storage proxies (local, cloud, etc.)
- **IFileProxy**: Interface for file storage implementations
- **JFileProxyFactory**: Factory for creating file storage proxies

### Request Handling

- **RequestLogAspect**: Aspect for logging request details and performance metrics
- **TokenArgumentResolver**: Resolves tokens from requests for authentication

## Usage

### Dependency Configuration

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.jboot</groupId>
    <artifactId>kernel-boot</artifactId>
    <version>4.7.1</version>
</dependency>
```

### Basic Usage

1. Create a Spring Boot application class that extends `JApplication`:

```java
@SpringBootApplication
public class MyApplication extends JApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }
}
```

2. Create a controller that extends `JController`:

```java
@RestController
@RequestMapping("/api/users")
public class UserController extends JController {
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public Result<List<User>> list() {
        return success(userService.list());
    }
    
    @PostMapping
    public Result<User> add(@RequestBody User user) {
        userService.save(user);
        return success(user);
    }
}
```

### File Upload

```java
@PostMapping("/upload")
public Result<String> upload(@RequestParam("file") MultipartFile file) {
    String url = JFileUtil.upload(file);
    return success(url);
}
```

### File Download

```java
@GetMapping("/download/{fileName}")
public void download(@PathVariable String fileName, HttpServletResponse response) {
    JFileUtil.download(fileName, response);
}
```

## Configuration Properties

### Application Configuration

```yaml
jboot:
  # Server configuration
  server:
    port: 8080
    context-path: /api
  
  # File upload configuration
  file:
    upload-path: /tmp/uploads
    max-size: 10MB
  
  # Logging configuration
  logging:
    request: true
    response: true
```

## Integration Points

The `kernel-boot` module integrates with several other JBoot-Kernel modules:

- **kernel-launch**: Application startup and lifecycle management
- **kernel-cloud**: Cloud-native features and microservice support
- **kernel-toolkit**: Utility classes and helper methods
- **kernel-secure**: Security and authentication features
- **kernel-cache**: Caching functionality
- **kernel-log**: Logging and monitoring
- **kernel-swagger**: API documentation
- **kernel-mp**: Database access with MyBatis Plus

## Advanced Features

### Custom File Storage

Implement the `IFileProxy` interface to create a custom file storage implementation:

```java
@Component
public class CustomFileProxy implements IFileProxy {
    
    @Override
    public String upload(MultipartFile file) {
        // Custom upload logic
        return "custom-url/" + file.getOriginalFilename();
    }
    
    @Override
    public void download(String fileName, HttpServletResponse response) {
        // Custom download logic
    }
}
```

### Request Logging

Enable request logging by adding the `@EnableRequestLog` annotation:

```java
@SpringBootApplication
@EnableRequestLog
public class MyApplication extends JApplication {
    // ...
}
```

### Token Authentication

Configure token authentication in your application.properties:

```properties
jboot.auth.token.header=Authorization
jboot.auth.token.prefix=Bearer
jboot.auth.token.secret=your-secret-key
jboot.auth.token.expiration=3600
```