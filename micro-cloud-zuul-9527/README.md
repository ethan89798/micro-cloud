
#### 1.增加pom依赖
    由于Zuul要注册到Eureka服务顺,所以也要增加Eureka的依赖
#### 2.增加application.yml的配置
#### 3.使用一个启动类,增加@EnabledZuulProxy开启路由服务
#### 4.测试是否成功
    源来地址:http://localhost:8001/dept/get/2
    网关地址:http://gateway9527.com:9527/micro-cloud-dept/dept/get/1
#### 5.路由访问的映射规则
    zuul:
      routes:
        myDept.serviceId: micro-cloud-dept
        myDept.path: mydept/**
    使用mydept来映射到微服务名