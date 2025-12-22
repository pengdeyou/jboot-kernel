# JBoot Kernel 发布指南

本文档介绍如何将 JBoot Kernel 发布到 Sonatype Maven 中央仓库。

## 发布配置

项目已配置了完整的 Sonatype 中央仓库发布支持：

### 1. Maven 配置
- `distributionManagement`: 配置了 Sonatype 中央仓库地址
- `release profile`: 包含 GPG 签名和 Nexus Staging 插件
- 支持自动构建、签名、部署

### 2. 关键配置信息
- **Server ID**: `jboot-ossrh-release` (发布仓库)
- **Snapshot Server ID**: `jboot-ossrh-snapshot` (快照仓库)
- **Nexus URL**: https://ossrh-staging-api.central.sonatype.com

## 前置条件

### 1. GPG 密钥配置
```bash
# 生成 GPG 密钥
gpg --gen-key

# 查看密钥 ID
gpg --list-secret-keys

# 上传公钥到密钥服务器
gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID
```

### 2. Maven 配置
将 `settings-release.xml` 复制为 `~/.m2/settings.xml`，并替换以下占位符：

```xml
<!-- Sonatype 认证信息 -->
<username>your-sonatype-username</username>
<password>your-sonatype-password</password>

<!-- GPG 配置 -->
<gpg.keyname>your-gpg-key-id</gpg.keyname>
<gpg.passphrase>your-gpg-passphrase</gpg.passphrase>
```

## 发布流程

### 方式一：使用发布脚本（推荐）

```bash
# 发布快照版本（版本号以 -SNAPSHOT 结尾）
./release.sh snapshot

# 发布正式版本
./release.sh release

# 自动释放暂存库到中央仓库
./release.sh auto-release
```

### 方式二：使用 Maven 命令

#### 发布快照版本
```bash
mvn clean deploy -DskipTests
```

#### 发布正式版本
```bash
# 1. 部署到暂存库
mvn clean deploy -P release -DskipTests

# 2. 释放到中央仓库（可选，也可手动在 Web UI 中操作）
mvn nexus-staging:release -P release
```

## 版本管理

### 快照版本
- 版本号格式：`4.7.1-SNAPSHOT`
- 自动发布到快照仓库
- 可重复发布，新版本会覆盖旧版本

### 正式版本
- 版本号格式：`4.7.1`
- 需要移除 `-SNAPSHOT` 后缀
- 需要手动释放暂存库到中央仓库
- 不可重复发布（需要升级版本号）

## 发布后检查

### 快照版本
检查地址：https://ossrh-staging-api.central.sonatype.com/content/repositories/snapshots/

### 正式版本
1. **暂存阶段**：https://ossrh-staging-api.central.sonatype.com/
2. **中央仓库**：https://repo1.maven.org/maven2/org/jboot/ （10-30分钟后同步）

## 故障排除

### 1. GPG 签名问题
```bash
# 检查 GPG 环境
gpg --version
gpg --list-secret-keys

# 如果出现 Inappropriate ioctl for device 错误
export GPG_TTY=$(tty)
```

### 2. 网络连接问题
```bash
# 测试连接到 Sonatype
curl -I https://ossrh-staging-api.central.sonatype.com/

# 如有网络问题，可配置 Maven 代理
```

### 3. 暂存库问题
如果部署成功但暂存库状态异常：
1. 登录 https://ossrh-staging-api.central.sonatype.com/
2. 检查暂存库状态
3. 如有问题，可 Drop 重新部署

### 4. 同步延迟
正式版本发布到中央仓库后可能需要 10-30 分钟同步时间。

## 发布清单

在发布前请确认：

- [ ] 项目版本号已正确设置
- [ ] 所有测试通过
- [ ] GPG 密钥已生成并上传
- [ ] Maven settings.xml 已配置正确
- [ ] 项目文档和示例已更新
- [ ] 变更日志已记录新版本内容

## 回滚策略

如果发现发布版本有问题：

1. **快照版本**：重新发布修复版本即可
2. **正式版本**：
   - 不要释放暂存库
   - 在 Web UI 中 Drop 有问题的暂存库
   - 修复问题后重新发布新版本号

## 最佳实践

1. **版本号管理**：遵循语义化版本规范
2. **测试覆盖**：确保所有核心功能测试通过
3. **文档更新**：同步更新 API 文档和使用指南
4. **发布检查**：使用发布脚本进行前置检查
5. **监控反馈**：发布后关注用户反馈和 issue

## 相关链接

- [Sonatype Central Repository](https://central.sonatype.org/)
- [Nexus Repository Manager](https://help.sonatype.com/repomanager2)
- [Maven Central Repository](https://search.maven.org/)
- [GPG 手册](https://www.gnupg.org/documentation/)