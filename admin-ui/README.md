# UIAMP管理后台前端项目

## 项目简介

这是统一身份认证与权限管理平台(UIAMP)的管理后台前端项目，基于Vue 3 + Vite + TypeScript技术栈开发，使用Element Plus作为UI组件库。

## 技术栈

- **Vue 3**: 渐进式JavaScript框架
- **Vite**: 新一代前端构建工具
- **TypeScript**: JavaScript的超集，提供静态类型检查
- **Element Plus**: Vue 3的桌面端组件库
- **Pinia**: Vue的轻量级状态管理库
- **Vue Router**: Vue.js的官方路由管理器
- **Axios**: 基于Promise的HTTP客户端
- **ECharts**: 开源可视化图表库

## 项目结构

```
admin-ui/
├── public/                 # 静态资源文件
├── src/                    # 源代码目录
│   ├── assets/            # 静态资源
│   ├── components/        # 公共组件
│   ├── views/             # 页面视图
│   ├── router/            # 路由配置
│   ├── store/             # 状态管理
│   ├── api/               # API接口封装
│   ├── utils/             # 工具函数
│   ├── styles/            # 全局样式
│   ├── App.vue            # 根组件
│   └── main.ts            # 入口文件
├── tests/                  # 测试文件
├── index.html              # HTML模板
├── package.json            # 项目配置文件
├── vite.config.ts          # Vite配置文件
├── tsconfig.json           # TypeScript配置文件
└── README.md               # 项目说明文档
```

## 开发环境搭建

### 环境要求

- Node.js >= 16.0.0
- npm >= 8.0.0 或 yarn >= 1.22.0

### 安装依赖

```bash
# 使用 npm
npm install

# 或使用 yarn
yarn install
```

### 启动开发服务器

```bash
# 使用 npm
npm run dev

# 或使用 yarn
yarn dev
```

启动后访问 http://localhost:3000

## 构建部署

### 构建生产版本

```bash
# 使用 npm
npm run build

# 或使用 yarn
yarn build
```

构建后的文件将生成在 `dist` 目录中。

### 本地预览生产构建

```bash
# 使用 npm
npm run preview

# 或使用 yarn
yarn preview
```

## 项目配置

### 环境变量

项目支持环境变量配置，在项目根目录创建以下文件：

- `.env` - 所有环境通用配置
- `.env.development` - 开发环境配置
- `.env.production` - 生产环境配置

示例：
```
VITE_API_BASE_URL=http://localhost:8080/api
```

### 代理配置

在 `vite.config.ts` 中配置了API代理：

```typescript
server: {
  proxy: {
    '/api': {
      target: 'http://localhost:8080',
      changeOrigin: true,
      rewrite: (path) => path.replace(/^\/api/, '')
    }
  }
}
```

## 代码规范

### ESLint配置

项目集成了ESLint进行代码检查，配置文件为 `.eslintrc.cjs`。

### 代码格式化

使用Prettier进行代码格式化，配置文件为 `.prettierrc`。

### Git提交规范

建议使用以下提交类型：
- feat: 新功能
- fix: 修复bug
- chore: 构建工具或辅助工具的变动
- docs: 文档更新
- style: 代码格式调整
- refactor: 代码重构
- test: 测试相关
- perf: 性能优化

## 部署方案

### Docker部署

1. 构建Docker镜像：
```bash
docker build -t uiamp-admin .
```

2. 运行容器：
```bash
docker run -d -p 8080:80 uiamp-admin
```

### Nginx部署

将构建后的 `dist` 目录内容复制到Nginx的html目录下，并配置Nginx：

```nginx
server {
    listen 80;
    server_name your-domain.com;
    
    location / {
        root /usr/share/nginx/html;
        index index.html;
        try_files $uri $uri/ /index.html;
    }
    
    # API代理配置
    location /api/ {
        proxy_pass http://backend-server:8080/;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
    }
}
```

## 功能模块

### 1. 登录模块
- 用户认证
- Token管理
- 权限验证

### 2. 仪表盘
- 数据统计展示
- 图表可视化
- 系统状态监控

### 3. 用户管理
- 用户列表查看
- 用户信息编辑
- 用户状态管理
- 用户搜索功能

### 4. 应用管理
- 应用注册
- 应用信息维护
- App Key/Secret管理

### 5. 角色管理
- 角色创建/编辑/删除
- 用户角色分配
- 权限点管理

### 6. 操作日志
- 操作记录查看
- 操作人员追踪
- 异常操作告警

## API接口

所有API接口通过Axios封装，在 `src/api/modules.ts` 中定义：

- 用户相关接口: `userApi`
- 应用相关接口: `appApi`
- 角色相关接口: `roleApi`
- 认证相关接口: `authApi`

## 状态管理

使用Pinia进行状态管理，主要存储：

- 用户信息
- 认证Token
- 全局配置

## 路由配置

使用Vue Router进行路由管理，配置文件位于 `src/router/index.ts`，包含：

- 登录路由
- 布局路由
- 各功能模块路由
- 路由守卫

## 测试

### 单元测试

使用Vitest进行单元测试，测试文件位于 `tests` 目录。

### 端到端测试

使用Cypress进行端到端测试。

## 性能优化

1. **代码分割**: 使用Vite的动态导入功能进行代码分割
2. **资源压缩**: 构建时自动压缩CSS和JavaScript
3. **图片优化**: 支持WebP格式图片
4. **缓存策略**: 合理设置HTTP缓存头
5. **懒加载**: 路由组件和图片懒加载

## 安全性

1. **XSS防护**: 使用Vue的模板语法自动转义
2. **CSRF防护**: 使用Token验证
3. **CSP策略**: 配置内容安全策略
4. **HTTPS**: 生产环境强制使用HTTPS

## 监控和日志

1. **错误监控**: 集成前端错误监控工具
2. **性能监控**: 页面加载性能监控
3. **用户行为分析**: 用户操作行为记录

## 后续规划

1. 实现完整的测试覆盖
2. 增加国际化支持
3. 实现主题切换功能
4. 增加更多图表展示
5. 实现移动端适配