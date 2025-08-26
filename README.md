# 统一身份认证与权限管理平台 (UIAMP)

## 项目简介

统一身份认证与权限管理平台(UIAMP)是一个企业级的用户身份认证和权限管理系统，旨在解决公司产品矩阵扩张带来的用户管理体系分散、用户体验割裂、管理成本高昂和安全风险等问题。通过实现单点登录(SSO)和集中权限管控，提升用户体验、管理效率和安全水平。

## 核心功能

### 1. 单点登录(SSO)
- 采用OAuth 2.1/OpenID Connect标准协议
- 提供公司统一登录门户页面
- 支持一次登录，多方应用信任访问
- 支持全局登出，一处登出，全系统会话失效

### 2. 管理中心后台
- 用户管理：查看和搜索平台所有用户列表
- 应用管理：注册、管理接入的各个内部产品
- 权限与角色管理：用户权限分配和角色管理
- 操作审计：记录所有权限分配、修改等关键操作日志

### 3. 开放API接口
- 提供供其他产品后端调用的标准API
- 所有接口进行应用身份认证(App Key/Secret)
- 关键API包括权限查询、权限管理等

## 技术架构

### 核心技术栈
- **后端框架**：Spring Boot 2.7.x + Spring Cloud 2021.x
- **认证协议**：OAuth 2.1/OpenID Connect
- **安全框架**：Spring Security 5.7.x
- **数据库**：MySQL 8.0
- **缓存**：Redis 6.x
- **ORM框架**：MyBatis Plus 3.5.x
- **JWT**：JSON Web Tokens 0.11.5
- **前端框架**：Vue.js 3.x + Element Plus

### 微服务架构
```
uiamp-parent (Maven Parent POM)
├── uiamp-gateway              # API网关服务
├── uiamp-auth-service         # 认证中心服务
├── uiamp-user-service         # 用户管理服务
├── uiamp-app-service          # 应用管理服务
├── uiamp-permission-service   # 权限管理服务
└── uiamp-common               # 公共模块
```

### 核心服务介绍

#### 认证中心服务 (uiamp-auth-service)
- 实现OAuth2授权服务器
- OpenID Connect协议支持
- JWT令牌生成与验证
- 客户端管理功能

#### 用户管理服务 (uiamp-user-service)
- 用户注册、激活、禁用
- 用户信息管理
- 密码重置与修改
- 用户查询接口

#### 应用管理服务 (uiamp-app-service)
- 应用注册与管理
- App Key/Secret生成
- 应用状态管理
- 应用信息查询

#### 权限管理服务 (uiamp-permission-service)
- 角色管理
- 用户角色分配
- 权限点管理
- 用户权限查询

#### 网关服务 (uiamp-gateway)
- API路由转发
- 跨域配置
- 请求拦截与鉴权
- 负载均衡

## 数据库设计

### 核心数据表
- `oauth_client_details` - OAuth客户端信息表
- `sys_user` - 用户信息表
- `sys_app` - 应用信息表
- `sys_role` - 角色信息表
- `sys_user_app_role` - 用户应用角色关联表
- `sys_operation_log` - 操作日志表

## 安全设计

### 认证流程
1. 用户登录请求
2. 认证服务器验证用户凭证
3. 生成Access Token和Refresh Token
4. 返回JWT令牌给客户端
5. 客户端携带令牌访问受保护资源

### 授权模式支持
- Authorization Code - 授权码模式(标准)
- Resource Owner Password Credentials - 密码模式
- Client Credentials - 客户端模式

### 安全增强措施
- 密码加密存储(Bcrypt)
- 防暴力破解机制
- 会话管理
- Token黑名单机制
- CORS安全配置

## 部署说明

### 环境要求
- JDK 11+
- MySQL 8.0+
- Redis 6.x+
- Maven 3.6+

### 部署步骤
1. 创建数据库并执行docs/database.sql脚本
2. 修改各服务application.yml中的数据库配置
3. 执行mvn clean install编译项目
4. 启动各服务：gateway、auth-service、user-service、app-service、permission-service

## 对接方案

### 接入流程
1. 新产品在UIAMP管理后台完成注册，获取App Key和App Secret
2. 产品前端改造登录逻辑，重定向至UIAMP统一登录页
3. 产品后端提供回调接口，处理UIAMP返回的授权码
4. 产品后端建立本地用户与UIAMP user_id的关联

### 产品侧用户表设计建议
```sql
CREATE TABLE local_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id VARCHAR(128) NOT NULL UNIQUE COMMENT 'UIAMP平台用户唯一标识',
    app_role VARCHAR(50) COMMENT '应用内角色',
    username VARCHAR(50) COMMENT '用户名',
    email VARCHAR(100) COMMENT '邮箱',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

## API文档

### 认证相关接口
- POST /auth/oauth2/token - 获取访问令牌
- GET /auth/user - 获取当前用户信息

### 用户管理接口
- GET /user/users - 获取用户列表
- GET /user/users/{id} - 获取用户详情
- POST /user/users - 创建用户
- PUT /user/users/{id} - 更新用户
- DELETE /user/users/{id} - 删除用户

### 应用管理接口
- GET /app/apps - 获取应用列表
- GET /app/apps/{id} - 获取应用详情
- POST /app/apps - 创建应用
- PUT /app/apps/{id} - 更新应用
- DELETE /app/apps/{id} - 删除应用

### 权限管理接口
- GET /permission/roles - 获取角色列表
- GET /permission/roles/{id} - 获取角色详情
- POST /permission/roles - 创建角色
- PUT /permission/roles/{id} - 更新角色
- DELETE /permission/roles/{id} - 删除角色

## 成功标准

- 用户体验：用户关于"重复登录"的投诉下降90%以上
- 管理效率：权限配置操作耗时减少50%
- 开发效率：新产品接入身份认证系统的平均耗时降至0.5人/天以下
- 接入范围：3个月内完成核心主力产品的接入

## 后续规划

1. 实现管理后台前端界面
2. 增加多租户支持
3. 实现细粒度权限控制
4. 增加组织架构管理功能
5. 实现操作审计日志服务
6. 增加监控和告警机制