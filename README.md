# Getting Started

### 项目结构：

```plaintext
com
    └── exorcist
        ├── dto         // 接口逻辑Json对象
        ├── interceptor // JWT拦截器
        ├── exception   // 全局校验
        ├── controller  // 处理HTTP请求的Controller层
        ├── service     // 业务逻辑接口
        │   └── UserService.java
        ├── service.impl // 业务逻辑实现类
        │   └── UserServiceImpl.java
        ├── mapper       //数据层映射
        ├── pojo        // 实体类和DTO
        └── util        // 工具类
```

