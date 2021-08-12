# 简介

Spring Security, 这是一种基于 Spring AOP 和 Servlet 过滤器的安全的安全框架。 它提供全面的安全性解决方案，同时在 Web 请求级和方法级调用级处理 身份确认和授权。

# 2、特征

* 对身份验证和授权全面、可扩展
* 可防止攻击，如点击劫持、跨站点请求伪造、
* servlet 集成
* 可以选择与 Spring Web MVC集成

# 3.先决条件

需要JDK8或JDK8以上的运行环境

# 具体依赖

```xml

<dependencies>
    <!-- ... other dependency elements ... -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-security</artifactId>
    </dependency>
</dependencies>

```

如果要使用快照版本，需要引入maven仓库:

```xml

<repositories>
    <!-- ... possibly other repository elements ... -->
    <repository>
        <id>spring-snapshot</id>
        <name>Spring Snapshot Repository</name>
        <url>https://repo.spring.io/snapshot</url>
    </repository>
</repositories>
```

# Authentication

### 架构组件

* SecurityContextHolder - Spring Security 存储身份验证详细信息的地方
* SecurityContext - 从 SecurityContextHolder 中获得，包含当前经过身份验证的用户的身份验证
* Authentication - 身份验证，可以是AuthenticationManager的输入，以提供用户已提供用于身份验证的凭据，也可以是 SecurityContext中的当前用户
* GrantedAuthority - 在身份验证中授予主体的权限(即角色、范围等)。
* AuthenticationManager - 定义Spring Security filter如何执行身份认证的API
* ProviderManager - AuthenticationManager最常见的实现
* AuthenticationProvider - 由ProviderManager用于执行特定类型的身份验证
*

Spring Security身份认证主要有两个目的:

1. 输入账号密码到AuthenticationManager, 以提供用户身份验证。
2. 表示当前经过身份验证的用户。当前身份验证可以从SecurityContext获取。

Authentication包含有:

* principal - 用户标识。使用 UserDetails 来替换验证后的 username 和 password.
* credentials - 通用密码
* authorities - 权限集合

# AuthenticationManager

AuthenticationManager 是一个API，它定义了Spring Security的过滤器如何执行认证，即授予主体的授权。
然后返回的身份认证被调用的控制器(即Spring Security的过滤器)设置在SecurityContextHolder上。