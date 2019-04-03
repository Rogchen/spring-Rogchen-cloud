title: 使用springcloud做统一配置中心

tags: [springcloud,统一配置中心]

##前言
    如果开发过微服务系统就知道整个系统共用很多中间件或者其它相同配置的文件、 
    以及在集群部署的条件下修改同个配置操作不复杂，但是繁琐需要频繁重启服务
    造成系统的不稳定等等原因。本例子就是通过全局配置中心解决这些问题。如果说
    系统不是使用springcloud也是可以通过引用springcloud的指定包来做处理。
    
## 解决方案    
### 一、搭建统一配置中心服务端

1、引入pom文件
springcloud对rabbitmq/kafka集成很好，直接引用。利用springcloud-bus、mq来做全局动态刷新配置
``` 
        <dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-server</artifactId>
		</dependency>
		<dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bus-amqp</artifactId>
         </dependency>
```
<!--more-->
2、springboot是默认集成tomcat的，如果阅读过tomcat源码的话就很清楚bootstap的作用。这里不做详细介绍，
    bootstrap是在application启动之前启动的。在bootstrap.properties中配置信息
``` bootstrap.properties
#可以使用git/svn/native(本地文件)
spring.profiles.active=git
spring.cloud.config.server.git.uri=https://github.com/Rogchen/Test
spring.cloud.config.server.git.search-paths=config-test.properties
spring.cloud.config.label=master

#native
#spring.profiles.active=native
#spring.cloud.config.server.native.search-locations=file:e:\\config-test.properties

#svn
#spring.profiles.active=subversion
#spring.cloud.config.server.svn.uri=127.0.0.1/..../trunk
#spring.cloud.config.server.svn.username=####
#spring.cloud.config.server.svn.password=####
```
application.yml配置
``` application.yml
server.port: 8888
spring:
  application:
    name: cloud-config-rogchen
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
management:
  security:
     enabled: false
```
3、添加启动
在springboot启动类上添加启动服务配置

` @EnableConfigServer `

4、执行main方法启动服务端。
### 二、搭建客户端
1、引入客户端的pom
```pom.xml
  <!-- 客户端配置模块 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
         <!-- Rabbitmq模块 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-bus-amqp</artifactId>
        </dependency>
    
```

2、客户端的配置文件配置bootstrap.properties，也可以通过应用名称获取文件名称spring.application.name=config-test
```bootstrap.properties
spring.application.name=cloud-config-demo
spring.cloud.config.label=master
#配置文件名-默认是properties
spring.cloud.config.name=config-test
#服务端访问地址
spring.cloud.config.uri= http://localhost:8888/
```
3、搭建测试效果
新建一个控制器直接贴代码了
```TestAuthController.java
@RefreshScope       //非常重要
public class TestAuthController {
    @Value(value = "${rogchen.test}")
    private String test;

    @RequestMapping("test")
    public String getTest(){
        return "test"+test;
    }
}
```
使用注解RefreshScope实现动态刷新配置

## 测试
* 访问 http://localhost:8881/test
* 修改git上面信息文件
* 再次访问 http://localhost:8881/test
* 发现信息没有变化
* 执行客户端刷新 http://localhost:8881/refresh
* 再次访问 http://localhost:8881/test 出现变化了
* 再次修改git上面信息文件
* 访问 http://localhost:8888/bus/refresh
* 再次访问 http://localhost:8881/test 出现变化了
* 我们可以使用customers:port进行指定客户端刷新。

### 不足
    是否发现如果我们每次修改配置就需要访问服务端刷新接口很麻烦，那么我们是否能直接自动进行客户端刷新。
1、使用git仓库的WebHook来轻松实现配置的自动刷新-设置push就进行刷新
![webhook](https://raw.githubusercontent.com/Rogchen/rogchen-picture/master/blog-img/other/cloud-config-webhook.png)

### 扩展
    统一配置中心可不是只能进行文件的修改的，还可以进行正式环境和开发环境的切换。
 ```配置服务规则
 #配置服务规则
 #/{application}/{profile}[/{label}]
 #/{application}-{profile}.yml
 #/{label}/{application}-{profile}.yml
 #/{application}-{profile}.propertiesd
 #/{label}/{application}-{profile}.properties
```
客户端通过制定profile来进行筛选，其实我们前面也通过name属性来进行了。
```$xslt
spring.cloud.config.name=config-test
#spring.cloud.config.profile=config-test
```

### 再序
前言那时候说过如果不是springboot项目也是可以通过引入springboot的jar来进行的。可以参考
* [非springboot项目如何进行统一配置](https://ke.qq.com/webcourse/index.html#cid=131889&term_id=100147308&taid=2289896173732657&vid=h1421882od3)

## 项目git

* [github地址:https://github.com/Rogchen/spring-Rogchen-cloud](https://github.com/Rogchen/spring-Rogchen-cloud)
> cloud-config cloud-config-demo模块
