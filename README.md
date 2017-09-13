# AkaBoot
Integration with Akka and springboot


#### TODOs
1. 职责定义：
  module starter是模块开发的核心，需要承担模块的业务边界。所有模块的依赖，都应该在module上组装；
  core主要定义框架层级的公用接口和管理对象；
  config是配置中心的代理，无论是server还是client都在此定义。但是config的web另起项目开发。
  remote负责module之间相互依赖（注册）的调用及处理方式（同步或异步）。
2. 
  
   