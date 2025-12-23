# 模块概览

JBoot-Kernel 采用模块化设计，将不同的功能拆分为独立的模块，便于按需引入和使用。以下是各个模块的简要介绍：

## 核心模块

- **kernel-bom**: Bill of Materials，统一管理项目依赖版本
- **kernel-boot**: 启动模块，提供自动配置和基础功能
- **kernel-cloud**: 云原生支持，提供微服务相关功能
- **kernel-launch**: 应用启动器，简化应用启动配置
- **kernel-toolkit**: 工具类模块，提供丰富的工具方法

## 功能模块

- **kernel-crypto**: 加密解密模块，提供 API 加密和数据安全功能
- **kernel-datascope**: 数据权限模块，实现数据级别的权限控制
- **kernel-develop**: 开发工具模块，提供代码生成和开发辅助功能
- **kernel-excel**: Excel 操作模块，支持 Excel 导入导出
- **kernel-i18n**: 国际化模块，支持多语言切换
- **kernel-loadbalancer**: 负载均衡模块，提供自定义负载均衡策略
- **kernel-log**: 日志模块，统一日志处理
- **kernel-mp**: MyBatis 增强模块，简化数据库操作

## 集成模块

- **kernel-secure**: 安全模块，提供权限管理和安全认证
- **kernel-test**: 测试模块，简化单元测试和集成测试
- **kernel-oss**: 对象存储模块，支持多种云存储服务
- **kernel-redis**: Redis 模块，提供 Redis 操作封装
- **kernel-report**: 报表模块，提供报表生成功能
- **kernel-social**: 社交登录模块，支持第三方登录
- **kernel-swagger**: API 文档模块，自动生成接口文档
- **kernel-transaction**: 事务模块，增强事务管理

## 扩展模块

- **kernel-cache**: 缓存模块，提供缓存管理功能
- **kernel-tenant**: 多租户模块，支持多租户隔离

点击左侧菜单查看各个模块的详细使用说明。