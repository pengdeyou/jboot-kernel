# JBoot Kernel Cloud Module

## Overview

The `kernel-cloud` module is a cloud-native enhancement module for JBoot-Kernel, providing advanced features for building microservice applications. It extends Spring Cloud functionality with additional capabilities for API versioning, Feign client enhancements, load balancing, and service governance.

## Features

- API versioning support (header-based and URL-based)
- Enhanced Feign client with automatic request header propagation
- Sentinel integration for service protection and circuit breaking
- Load balancing with custom routing rules
- Unified error handling for cloud applications
- HTTP client configuration with logging and monitoring
- Grayscale deployment support

## Core Components

### Configuration Classes

- **JCloudAutoConfiguration**: Main auto-configuration class for cloud-native features
- **JSentinelFilterConfiguration**: Configuration for Sentinel circuit breaker integration
- **RestTemplateConfiguration**: Configuration for enhanced RestTemplate with load balancing

### API Versioning

- **ApiVersion**: Annotation for header-based API versioning
- **UrlVersion**: Annotation for URL-based API versioning
- **VersionMapping**: Annotation for mapping different versions to the same API
- **JRequestMappingHandlerMapping**: Custom request mapping handler for versioned APIs

### Feign Enhancements

- **EnableFeign**: Annotation to enable enhanced Feign client functionality
- **JFeignSentinel**: Enhanced Feign client with Sentinel integration
- **JFeignFallback**: Base fallback implementation for Feign clients
- **JFallbackFactory**: Factory for creating Feign client fallbacks

### HTTP Client Enhancements

- **JHttpConfiguration**: Configuration for HTTP clients (RestTemplate, OkHttp)
- **JHttpProperties**: Properties for HTTP client configuration
- **LbRestTemplate**: Load-balanced RestTemplate implementation
- **RestTemplateHeaderInterceptor**: Interceptor for propagating headers between services

### Header Propagation

- **JFeignRequestHeaderInterceptor**: Interceptor for propagating headers in Feign requests
- **JHttpHeadersContextHolder**: Context holder for managing HTTP headers across threads
- **JAccountGetter**: Interface for retrieving account information from headers

### Sentinel Integration

- **JBlockExceptionHandler**: Custom exception handler for Sentinel block exceptions
- **JSentinelInvocationHandler**: Custom invocation handler for Sentinel proxies

## Usage

### Dependency Configuration

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>org.jboot</groupId>
    <artifactId>kernel-cloud</artifactId>
    <version>3.0.4-RELEASE</version>
</dependency>
```

### Enable Cloud Features

Enable cloud features in your application:

```java
@SpringBootApplication
@EnableFeign
public class MyCloudApplication extends JApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyCloudApplication.class, args);
    }
}
```

### API Versioning

#### Header-based Versioning

```java
@RestController
@RequestMapping("/api/users")
public class UserController extends JController {
    
    @ApiVersion("v1")
    @GetMapping
    public Result<List<User>> getUsersV1() {
        // V1 implementation
    }
    
    @ApiVersion("v2")
    @GetMapping
    public Result<List<UserDTO>> getUsersV2() {
        // V2 implementation with DTO
    }
}
```

#### URL-based Versioning

```java
@RestController
@RequestMapping("/api/users")
public class UserController extends JController {
    
    @UrlVersion("v1")
    @GetMapping
    public Result<List<User>> getUsersV1() {
        // V1 implementation
    }
    
    @UrlVersion("v2")
    @GetMapping
    public Result<List<UserDTO>> getUsersV2() {
        // V2 implementation with DTO
    }
}
```

### Enhanced Feign Client

Create a Feign client with Sentinel protection:

```java
@FeignClient(name = "user-service", fallback = UserServiceFallback.class)
public interface UserServiceClient {
    
    @GetMapping("/api/users/{id}")
    Result<User> getUserById(@PathVariable("id") String id);
    
    @PostMapping("/api/users")
    Result<User> createUser(@RequestBody User user);
}

@Component
public class UserServiceFallback implements UserServiceClient {
    @Override
    public Result<User> getUserById(String id) {
        return Result.error("User service is unavailable");
    }
    
    @Override
    public Result<User> createUser(User user) {
        return Result.error("User service is unavailable");
    }
}
```

### Load Balanced RestTemplate

```java
@RestController
@RequestMapping("/api/users")
public class UserController extends JController {
    
    @Autowired
    private LbRestTemplate restTemplate;
    
    @GetMapping("/external/{id}")
    public Result<User> getExternalUser(@PathVariable String id) {
        String url = "http://user-service/api/users/" + id;
        return restTemplate.getForObject(url, Result.class);
    }
}
```

## Configuration Properties

### Feign Configuration

```yaml
feign:
  sentinel:
    enabled: true
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 5000
  httpclient:
    enabled: true
```

### HTTP Client Configuration

```yaml
jboot:
  http:
    connect-timeout: 5000
    read-timeout: 5000
    max-connections: 100
    log-level: BASIC
```

### API Versioning Configuration

```yaml
jboot:
  cloud:
    version:
      header: "X-API-VERSION"
      url-prefix: "/api/v"
      default-version: "v1"
```

### Sentinel Configuration

```yaml
spring:
  cloud:
    sentinel:
      transport:
        dashboard: localhost:8080
      log:
        dir: ./logs/sentinel
      datasource:
        ds:
          nacos:
            server-addr: localhost:8848
            dataId: sentinel-rules
            groupId: DEFAULT_GROUP
            rule-type: flow
```

## Integration Points

The `kernel-cloud` module integrates with several other JBoot-Kernel modules:

- **kernel-boot**: Core application functionality and request handling
- **kernel-launch**: Application startup and lifecycle management
- **kernel-loadbalancer**: Custom load balancing strategies
- **kernel-toolkit**: Utility classes and helper methods
- **kernel-log**: Logging and monitoring
- **kernel-secure**: Security and authentication for cloud applications

## Advanced Features

### Grayscale Deployment

Configure grayscale deployment rules:

```yaml
jboot:
  loadbalancer:
    grayscale:
      enabled: true
      rules:
        - service: user-service
          weight: 30
          version: v2
        - service: user-service
          weight: 70
          version: v1
```

### Custom Feign Request Interceptors

```java
@Component
public class CustomFeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        // Add custom headers or modify request
        template.header("X-CUSTOM-HEADER", "custom-value");
    }
}
```

### Custom Sentinel Block Handler

```java
@Component
public class CustomBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException ex) throws Exception {
        // Custom block exception handling
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JSON.toJSONString(Result.error("Service is temporarily unavailable")));
    }
}
```
