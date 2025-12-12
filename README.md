<p align="center">
  <img src="https://img.shields.io/badge/JDK-17+-green.svg" alt="JDK Version">
  <img src="https://img.shields.io/badge/license-LGPL%20v3-blue.svg" alt="License">
  <img src="https://img.shields.io/badge/Spring%20Cloud-2025-blue.svg" alt="Spring Cloud Version">
  <img src="https://img.shields.io/badge/Spring%20Boot-3.5-blue.svg" alt="Spring Boot Version">
  <a href="https://central.sonatype.com/artifact/org.jboot/kernel-bom/versions">
    <img src="https://img.shields.io/nexus/r/https/oss.sonatype.org/org.jboot/kernel-bom.svg?style=flat-square" alt="Maven Central">
  </a>
  <a href="https://oss.sonatype.org/content/repositories/snapshots/org/jboot/kernel-bom/">
    <img src="https://img.shields.io/nexus/s/https/oss.sonatype.org/org.jboot/kernel-bom.svg?style=flat-square" alt="Snapshots">
  </a>
  <br/>
  <a target="_blank" href="https://jboot.org">
    <img src="https://img.shields.io/badge/Author-Small%20Chill-ff69b4.svg" alt="Author">
  </a>
  <a target="_blank" href="https://jboot.org">
    <img src="https://img.shields.io/badge/Copyright%20-@JBoot Kernel-%23ff3f59.svg" alt="Copyright">
  </a>
</p>  

# JBoot微服务开发平台

JBoot Kernel是一个企业级微服务开发平台的底层核心框架，基于Spring Cloud和Spring Boot构建。它提供了高度封装的基础能力，适用于快速构建SaaS、物联网、AI等复杂业务系统。该框架已在生产环境中稳定运行六年，经历了多代技术演进。

## 🌟 核心特性

- **微服务治理**: 基于Spring Cloud Alibaba + Nacos实现服务注册发现与配置管理
- **安全认证**: 自研Secure模块，支持JWT Token认证，多终端权限隔离
- **多租户支持**: 极简封装SaaS多租户底层，支持数据级隔离
- **分布式事务**: 集成Seata实现分布式事务一致性
- **API文档**: 封装Swagger/Knife4j自动生成API接口文档
- **流量防护**: 集成Sentinel实现熔断降级、限流控制
- **代码生成**: 支持Vue/React模板的多前端代码生成
- **数据权限**: 行级数据权限控制(DataScope)
- **Excel处理**: 封装导入导出功能，支持POI与FastExcel
- **对象存储**: 支持多种云存储(OSS)抽象封装
- **国际化**: 提供I18n多语言支持
- **负载均衡**: 支持灰度发布与自定义负载策略


## 🛠️ 核心技术栈

| 技术栈 | 版本 |
|--------|------|
| Java | 17+ |
| NodeJS | 18+ |
| Spring | 6.2.11 |
| Spring Boot | 3.5.6 |
| Spring Cloud | 2025.0.0 |
| Spring Cloud Alibaba | 2023.0.3.3 |
| Nacos Alibaba | 3.1.0 |
| Mybatis Plus | 3.5.19 |

## 📁 工程结构

```
jboot-kernel
├── kernel-bom         -- Maven依赖管理模块
├── kernel-boot        -- 业务包综合模块，集成常用功能的快速启动包
├── kernel-cloud       -- Spring Cloud微服务封装模块 
├── kernel-launch      -- 基础启动模块，提供应用启动的核心功能
├── kernel-secure      -- 安全认证封装模块，JWT Token、权限控制等
├── kernel-test        -- 单元测试封装模块，集成测试工具和配置
├── kernel-tool        -- 核心工具封装模块，通用工具类、常量、配置等
├── kernel-api-crypto  -- API接口加密解密模块
├── kernel-cache       -- 缓存抽象封装模块
├── kernel-datascope   -- 数据权限封装模块，支持行级权限控制
├── kernel-develop     -- 代码生成封装模块，支持多种模板生成
├── kernel-excel       -- Excel导入导出封装模块
├── kernel-i18n        -- I18n国际化封装模块
├── kernel-loadbalancer -- 负载均衡与灰度发布封装模块
├── kernel-log         -- 日志封装模块，统一日志处理和存储 
├── kernel-mybatis     -- MyBatis Plus增强封装模块
├── kernel-oss         -- 对象存储封装模块，支持多种云存储
├── kernel-redis       -- Redis缓存封装模块，提供缓存操作工具
├── kernel-report      -- 报表封装模块，支持多种报表生成
├── kernel-social      -- 第三方登录封装模块，OAuth2社交登录
├── kernel-swagger     -- Swagger文档封装模块，API文档自动生成
├── kernel-tenant      -- 多租户封装模块，SaaS多租户支持
└── kernel-transaction -- 分布式事务封装模块，Seata分布式事务
```

## 🚀 快速开始

### Maven依赖

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.jboot</groupId>
            <artifactId>kernel-bom</artifactId>
            <version>${latest-version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

### 核心模块引用

```xml
<dependency>
    <groupId>org.jboot</groupId>
    <artifactId>kernel-boot</artifactId>
</dependency>
```

## 📞 官方信息

| 类别 | 内容 |
|------|------|
| 官网地址 | [https://jboot.org](https://jboot.org) |
| 会员计划 | [JBoot会员计划](https://gitee.com/pengdeyou/jboot/wikis/JBoot会员计划) |
