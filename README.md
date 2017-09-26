# AkaBoot
Integration with Akka and springboot


#### 职责定义
  module starter是模块开发的核心，需要承担模块的业务边界。所有模块的依赖，都应该在module上组装；
  core主要定义框架层级的公用接口和管理对象；
  config是配置中心的代理，无论是server还是client都在此定义。但是config的web另起项目开发。
  remote负责module之间相互依赖（服务的注册及发现）的调用及处理方式（同步或异步）。

#### TODOs
 #####1. 支持手动组合配置参数，为关联配置中心配置项做准备：
  - 增加配置中心客户端
  - 收集并解析配置项
  - 构造akka所需的配置对象
  
 #####2. 支持spring配置，简化spring融合：
  - 支持注解配置
  - 支持spring注册标签，抽象标签定义（spring相关）；
  
   