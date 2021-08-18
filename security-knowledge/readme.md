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

# 4.Authentication（身份验证）

### 架构组件

| 类名 | 描述 |
|--- | --- |
| SecurityContextHolder | Spring Security 存储身份验证详细信息的地方 |
| SecurityContext | 从 SecurityContextHolder 中获得，包含当前经过身份验证的用户的身份验证 |
| Authentication | 身份验证，可以是AuthenticationManager的输入，以提供用户已提供用于身份验证的凭据，也可以是 SecurityContext中的当前用户 |
| GrantedAuthority | 在身份验证中授予主体的权限(即角色、范围等) |
| AuthenticationManager | 定义Spring Security filter如何执行身份认证的API |
| ProviderManager | AuthenticationManager最常见的实现 |
| AuthenticationProvider | 由ProviderManager用于执行特定类型的身份验证 |
| AbstractAuthenticationProcessingFilter | 用于身份验证的基本筛选器 |

### 4.1.SecurityContextHolder

Spring Security身份验证模型的核心是SecurityContextHolder。它包含SecurityContext

![SecurityContextHolder](https://docs.spring.io/spring-security/site/docs/current/reference/html5/images/servlet/authentication/architecture/securitycontextholder.png)

SecurityContextHolder是Spring Security存储身份验证的详细信息的地方。 Spring Security并不关心SecurityContextHolder是如何填充的。
如果它包含一个值，那么它将被用作当前经过身份验证的用户。

例子，设置SecurityContextHolder

```
SecurityContext context = SecurityContextHolder.createEmptyContext();
Authentication authentication= new TestingAuthenticationToken("username"， "password"， "ROLE_USER");
context.setAuthentication(authentication);
SecurityContextHolder.setContext(context);
```

代码描述:

1. 我们首先创建一个空的SecurityContext． 重要的是创建一个新的SecurityContext实例而不是使用SecurityContextHolder.getContext () .setAuthentication(
   身份验证)以避免多个线程之间的竞争条件。
2. 接下来，我们创建一个新的身份验证对象。Spring安全不关心什么类型身份验证的实现SecurityContext．
   在这里,我们使用TestingAuthenticationToken因为它很简单。更常见的生产场景是UsernamePasswordAuthenticationToken (userDetails,密码,当局)．
3. 最后，我们设置SecurityContext在SecurityContextHolder, Spring Security将使用Authentication授权。

#### 4.1.1.访问当前认证用户

用户认证授权好了，那么该如何获取授权用户信息呢？

获取认证用户信息代码如下：

```
SecurityContext context = SecurityContextHolder.getContext(); 
Authentication authentication = context.getAuthentication(); 
String username = authentication.getName(); 
Object principal = authentication.getPrincipal(); 
Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); 
```

默认情况下，SecurityContextHolder使用ThreadLocal来存储这些细节

### 4.2.SecurityContext

SecurityContext是从SecurityContextHolder获得的。SecurityContext包含一个Authentication对象。

### 4.3.Authentication

Spring Security身份认证主要有两个目的:

1. 输入账号密码到AuthenticationManager, 以提供用户身份验证。
2. 表示当前经过身份验证的用户。当前身份验证可以从SecurityContext获取。

Authentication包含有:

* principal - 用户标识。使用 UserDetails 来替换验证后的 username 和 password.
* credentials - 通用密码
* authorities - 权限集合

### 4.4.GrantedAuthority

GrantedAuthority 是授予用户的高级权限，比如角色或者范围。

可以从Authentication.getAuthorities()方法获得grantedauthorys。此方法提供了一个GrantedAuthority对象的集合。

### 4.5.AuthenticationManager

AuthenticationManager是定义Spring安全过滤器如何执行身份验证的API。然后由调用AuthenticationManager的控制器（Spring Security Filter） 在
SecurityContextHolder 上设置返回的身份验证。如果你没有集成Spring Security的过滤器，你可以直接设置SecurityContextHolder， 而不需要使用AuthenticationManager。

### 4.6.ProviderManager

ProviderManager 是 AuthenticationManager 最常用的实现。 将 ProviderManager 委托给 AuthenticationProviders 列表。 每个
AuthenticationProvider 都有机会指示身份验证应该是成功、失败的，或者指示它不能做出决定并允许下游的 AuthenticationProvider 来决定。

图示如下(图片来源官网):

![ProviderManager](https://docs.spring.io/spring-security/site/docs/current/reference/html5/images/servlet/authentication/architecture/providermanager.png)

实际上，每个AuthenticationProvider都知道如何执行特定类型的身份验证。例如，一个AuthenticationProvider可能能够验证用户名/密码，而另一个AuthenticationProvider可能能够验证SAML断言。
这允许每个AuthenticationProvider执行一种非常特定的身份验证类型，同时支持多种类型的身份验证，并且只公开一个AuthenticationManager bean。

ProviderManager 还允许配置一个可选的父级 AuthenticationManager，在没有 AuthenticationProvider 可以执行身份验证的情况下，会咨询该父级 AuthenticationManager。
父类可以是任何类型的AuthenticationManager，但它通常是ProviderManager的一个实例。

图示如下:

![AuthenticationManager](https://docs.spring.io/spring-security/site/docs/current/reference/html5/images/servlet/authentication/architecture/providermanager-parent.png)

事实上，多个 ProviderManager 实例可能共享同一个 AuthenticationManager. 这在有多个SecurityFilterChain实例的场景中是很常见的，这些实例有一些共同的身份验证(
共享的父AuthenticationManager)，但也有不同的身份验证机制(不同的ProviderManager实例)

图示如下:

![共享 AuthenticationManager](https://docs.spring.io/spring-security/site/docs/current/reference/html5/images/servlet/authentication/architecture/providermanagers-parent.png)

默认情况下，ProviderManager 将尝试从成功的身份验证请求返回的 Authentication 对象中，清除任何敏感凭据信息。 这是防止像密码这样的信息在 HttpSession 中保留的时间超过必要的时间。

例如，当您使用用户对象的缓存来提高无状态应用程序的性能时，这可能会导致问题。如果Authentication包含对缓存中的对象的引用(比如UserDetails实例)，并且删除了它的凭证，
那么它将不再能够根据缓存的值进行身份验证。如果使用缓存，则需要考虑到这一点。一个明显的解决方案是首先复制对象，要么在缓存实现中复制，要么在创建返回的Authentication对象的AuthenticationProvider中复制。
或者，您可以禁用ProviderManager上的eraseCredentialsAfterAuthentication属性。

### 4.7.AuthenticationProvider

可以将多个AuthenticationProviders注入到ProviderManager中。每个AuthenticationProvider执行特定类型的身份验证。
例如，DaoAuthenticationProvider支持基于用户名/密码的身份验证，而JwtAuthenticationProvider支持JWT令牌的身份验证。

### 4.8.请求凭证与AuthenticationEntryPoint

AuthenticationEntryPoint用于发送从客户端请求凭据的HTTP响应

### 4.9.AbstractAuthenticationProcessingFilter

AbstractAuthenticationProcessingFilter 用作对用户凭据进行身份验证的基本过滤器。在对凭据进行身份验证之前，Spring Security通常使用AuthenticationEntryPoint请求凭据。
接下来， AbstractAuthenticationProcessingFilter 可以验证提交给它的任何身份验证请求。

具体流程，如图示:

![AbstractAuthenticationProcessingFilter](https://docs.spring.io/spring-security/site/docs/current/reference/html5/images/servlet/authentication/architecture/abstractauthenticationprocessingfilter.png)

具体描述:

1. 当用户提交他们的凭证时，AbstractAuthenticationProcessingFilter 会创建一个 Authentication，从 HttpServletRequest 到
   Authentication。Authentication 创建以后，依赖AbstractAuthenticationProcessingFilter的子类。 例如 用户名和密码提交到
   HttpServletRequest后，UsernamePasswordAuthenticationFilter 创建一个 UsernamePasswordAuthenticationToken。
2. 接下来将 Authentication 传递给 AuthenticationManager 进行身份验证。
3. 如果身份验证失败，则失败。

* SecurityContextHolder 被清除
* RememberMeServices。loginFail被调用。如果记得我没有配置，这是一个no-op。
* AuthenticationFailureHandler被调用。

4. 如果身份验证成功，则成功。

* 通知SessionAuthenticationStrategy一个新的登录。
* 认证是在SecurityContextHolder上设置的。稍后SecurityContextPersistenceFilter将SecurityContext保存到HttpSession。
* RememberMeServices。loginSuccess被调用。如果记得我没有配置，这是一个no-op。
* ApplicationEventPublisher发布一个InteractiveAuthenticationSuccessEvent。
* AuthenticationSuccessHandler被调用。

# 5.用户密码身份认证

最常见的用户身份验证方法之一是验证用户名和密码。因此，Spring Security提供了对使用用户名和密码进行身份验证的全面支持。

### 读取用户名和密码

Spring Security提供了以下内置机制来从HttpServletRequest中读取用户名和密码:

* 表单登录
* 基本身份验证
* 摘要式身份验证
* 内存身份验证
* jdbc的身份验证
* LDAP身份验证

验证方式太多，这里只挑重要的叙述, 表单登录和jdbc的身份验证

### 存储机制

每个支持的读取用户名和密码的机制都可以利用任何支持的存储机制:

* 带有内存认证的简单存储
* 使用JDBC身份验证的关系数据库
* 使用UserDetailService自定义数据存储
* 使用LDAP认证的LDAP存储

## 5.1.表单登录

关于表单登录就不多说了，就一个登录界面输入用户名密码登录，需要探讨的是Spring Security实现登录的具体过程。

### SpringSecurityFilterChain 图:

![SpringSecurityFilterChain](https://docs.spring.io/spring-security/site/docs/current/reference/html5/images/servlet/architecture/securityfilterchain.png)

重定向到登录页面过程如下(图源于官网):

![表单登录](https://docs.spring.io/spring-security/site/docs/current/reference/html5/images/servlet/authentication/unpwd/loginurlauthenticationentrypoint.png)

文字描述:

1. 首先用户像资源发出***未经身份验证***的请求 /private 。
2. Spring Security 的 FilterSecurityInterceptor 检测到未认证，抛出 AccessDeniedException。
3. ExceptionTranslationFilter 捕捉到异常，并发送一个重定向到配置的登录页面 AuthenticationEntryPoint。
   在大多数情况下AuthenticationEntryPoint是LoginUrlAuthenticationEntryPoint．
4. 然后浏览器将请求它被重定向到登录页面。

提交用户名和密码后， ***UsernamePasswordAuthenticationFilter会验证用户名和密码***。
UsernamePasswordAuthenticationFilter扩展了AbstractAuthenticationProcessingFilter，所以这个图看起来应该非常相似。

验证用户名密码(图源于官网):

下图建立在 SpringSecurityFilterChain 图基础上。

![登录验证过程](https://docs.spring.io/spring-security/site/docs/current/reference/html5/images/servlet/authentication/unpwd/usernamepasswordauthenticationfilter.png)

文字描述:

1. 当用户提交用户名和密码后，UsernamePasswordAuthenticationFilter 通过从HttpServletRequest中提取用户名和密码,根据用户名和密码创建
   UsernamePasswordAuthenticationToken。
2. 接下来,将UsernamePasswordAuthenticationToken传递给AuthenticationManager进行身份验证。AuthenticationManager的类型取决于用户信息的存储方式。
3. 如果验证失败，那就失败。

* 清除 SecurityContextHolder
* 执行 RememberMeServices.loginFail。 如果记住功能未配置，则不执行。
* 执行 AuthenticationFailureHandler

4. 如果验证成功，那么成功

* SessionAuthenticationStrategy 提醒由新登录
* 将 Authentication 设置在SecurityContextHolder.
* 执行 RememberMeServices.loginSuccess。 如果记住功能未配置，则不执行。
* ApplicationEventPublisher 发布一个 InteractiveAuthenticationSuccessEvent 事件。
* 调用 AuthenticationSuccessHandler。

默认情况下，Spring Security表单登录是启用的。然而，只要提供了任何基于servlet的配置，就必须显式地提供表单登录。

例如:

```
protected void configure(HttpSecurity http) {http // ... .formLogin(withDefaults());｝
```

在这个配置中，Spring Security将呈现一个默认的登录页面。

### 自定义表单登录

```
http // ... .formLogin(form -> form .loginPage("/login") .permitAll());｝
```

当在Spring Security配置中指定登录页面时，您将负责呈现页面。下面是一个Thymeleaf模板，它生成一个HTML登录表单，遵循/login登录页面:

登录表单(login.html):

```html
<！DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org">

<head>
    <title>Please Log In</title>
</head>

<body>
<h1>Please Log In</h1>
<div th:if="${param}">无效的用户名和密码。</div>
<div th:如果=" ${参数}">您已注销。</div>
<form th:action="@{/login}" method="post">
    <div><input type="text" name="username" placeholder=" username"/></div>
    <div><input type="password" name="password" placeholder=" password"/></div>
    <input type="submit"
           value=" login "/>
</form>
</body>

</html>
```

关于默认的HTML表单有几个关键点:

* 表单应该执行一个post to /login
* 表单需要包含一个由Thymeleaf自动包含的CSRF令牌。
* 表单应该在名为username的参数中指定用户名
* 表单应该在名为password的参数中指定密码
* 如果发现HTTP参数错误，则表示用户未能提供有效的用户名/密码
* 如果能找到HTTP参数logout，则表示用户注销成功

### 后台控制器 (loginController)

```
@Controller class LoginController {
 @GetMapping("/login") 
 String login() {return "login";}
 }
```

## 5.2. JDBC的身份验证

Spring Security 的 JdbcDaoImpl 实现了 UserDetailService，用来查询数据库中用户名和密码以便验证。JdbcUserDetailsManager 继承了 JdbcDaoImpl, 通过
UserDetailsManager 接口类实现对用户信息的管理(UserDetails), SpringSecurity 被配置为接受用户名/密码身份验证时，它将使用 UserDetails 来做身份 验证。

接下来具体讨论:

* Spring Security JDBC 身份验证使用的默认模式
* 设置数据源
* JdbcUserDetailsManager bean
* UserDetailService
* PasswordEncoder
* DaoAuthenticationProvider

### 默认模式

Spring Security 为JDBC的身份认证提供了默认查询。

创建用户表SQL:

```sql
create table users
(
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(500) not null,
    enabled  boolean not null
);

create table authorities
(
    username  varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key (username) references users (username)
);
create unique index ix_auth_username on authorities (username, authority);
```

创建用户组织SQL:

```sql
create table groups
(
    id         bigint generated by default as identity (start with 0) primary key,
    group_name varchar_ignorecase(50) not null
);

create table group_authorities
(
    group_id  bigint      not null,
    authority varchar(50) not null,
    constraint fk_group_authorities_group foreign key (group_id) references groups (id)
);

create table group_members
(
    id       bigint generated by default as identity (start with 0) primary key,
    username varchar(50) not null,
    group_id bigint      not null,
    constraint fk_group_members_group foreign key (group_id) references groups (id)
);
```

### 设置数据源

在配置 JdbcUserDetailsManager 之前，必须创建一个数据源。

```
@Bean 
DataSource DataSource () {
return new EmbeddedDatabaseBuilder() 
           .setType(H2) 
           .addScript("classpath:org/springframework/security/core/userdetails/jdbc/users.ddl") 
           .build();
 }
```

这里使用官网嵌入式的数据源，在生产环境请设置您自己的外部数据库连接。

### UserDetailService

用户名验证

### PasswordEncoder

密码验证

### DaoAuthenticationProvider

DaoAuthenticationProvider 是一个 AuthenticationProvider 的实现，它使用 UserDetailService 和 PasswordEncoder 来验证用户名和密码。

DaoAuthenticationProvider 在 Spring Security 中的具体工作流程:

![DaoAuthenticationProvider](https://docs.spring.io/spring-security/site/docs/current/reference/html5/images/servlet/authentication/unpwd/daoauthenticationprovider.png)

1. Authentication filter 读取客户端传递的 username 和 password，将其封装到 UsernamePasswordAuthenticationToken 中，然后将 token 传递到
   ProviderManager 中， ProviderManager 是实现 AuthenticationManager 来的。
2. ProviderManager 配置使用 AuthenticationProvider 的类型是 DaoAuthenticationProvider。
3. DaoAuthenticationProvider 使用 UserDetailService 获取数据库中的用户信息并实现用户名的校验，使用 PasswordEncoder 实现对密码的校验。
4. 校验成功则返回 UserDetails 和 Authorities，然后通过过滤器（Filter）将 UserDetails、Authorities 设置在 SecurityContextHolder 中。
