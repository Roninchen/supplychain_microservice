# 接口文档地址
http://172.16.100.33:8080/swagger-ui.html
结构图
![结构图](http://122.224.124.250:10080/fdfs/test/M00/00/2B/rBBkE1vhkYKADYDnAAFUjzw_L2w243.png)

# 通用微服务与组件
父类pom：`cn.33.supply:1.0-SNAPSHOT`，包含springboot、spring-cloud版本，以及去除原本的日志，使用log4j2日志

## 说明
#### 网关
服务的名称，也就是`spring.application.name`请尽量满足以下正则：  
`(?<name>^[a-z0-9]+)-(?<version>v[0-9]+)(-service)?$`  
网关会自动将此服务路由为：/${version}/${name}/**。例如：chain33-v1-service => /v1/chain33/\**  
另外，网关会忽略以`-base` 和以 `-config`结尾的服务，例如：sms-base，supply-config

#### 注解：
* `@MustBeBound`：使用该注解则必须是绑定的用户才可以访问被注解的接口，直接从本平台登录的用户即使没绑定过其他平台也算绑定用户

项目中在接口的Controller中使用注解`@RequestAttribute(CommonConst.USER) UserVO userVO`即可获取用户信息。
例如：
```
@GetMapping
public ResponseVO demo(@ApiIgnore @RequestAttribute(CommonConst.USER) UserVO userVO) {
    log.info("用户信息打印：{}", userVO)
    return new ResponseVO<>(CommonResponseEnum.success);
}
```

#### 必要的约定，**重要重要重要**
* 所有的`@RequestMapping`必须以`/api/**`开头
* 对于引用了组件的服务，启动类中必须添加`@ComponentScan("com.disanbo")`
* 无需设置跨域，网关已经有跨域，服务再设置会有问题

## 组件介绍 
#### common-component：基础组件
一些基础的配置，基本上每个服务都需要引用
* 通用pom依赖:`swagger`、`actuator`、`web`、`lombok`等，具体可以去pom.xml中查看
* 全局错误处理配置
* 跨域处理配置
* 国际化处理配置
* 消息转换器、线程配置
* 通用静态常量
* 通用ResponseEnum接口
* 通用响应类VO
* 通用工具类
* 通用配置文件

#### block-chain-component：区块链组件
* 支持多种算法：`ed25519`、`secp256k1`
* 公私钥生成、签名
* 使用轮询上链

引入后使用方法：
```
配置文件bootstrap.yml中：
block:
  chain:
    execer: 合约名
    urls: 支持多个url
```
```
// transactionInfo：发送信息包装类，包含公私钥
ChainResponse chainResponse = BlockChainClients.secp256k1().sendTransaction(transactionInfo);
if (chainResponse.isSuccess) {
    // 上链成功
    String hash = chainResponse.getHash();
    Long height = chainResponse.getHeight();
} else {
    // 上链失败
    String err = chainResponse.getError();
}
```

#### redis-component：缓存组件
* redis缓存依赖和配置

引入后使用构造器注入下列依赖即可：
```
private final RedisRepository redisRepository;
```

引入此组件，需要在配置文件bootstrap.yml中加入相应的配置
```
spring:
  #redis
  redis:
    database: 0
    host: 
    port: 
    password:
    timeout: 3000ms
    lettuce:
      pool:
        max-idle: 8
        max-active: 8
        max-wait: -1ms
        min-idle: 0
```

#### feign-component：Feign组件
应用此组件，项目的`目录`必须是`com.disanbo`
* feign相关依赖和配置

#### sms-component：短信组件
* 短信发送

引入后使用构造器注入下列依赖即可：
```
private final SmsFeign smsFeign;
```

#### file-component：文件组件
* 文件的上传
* PDF文件上传并转换为图片
* 相关查询接口

引入后使用构造器注入下列依赖即可：
```
// 上传
private final FileUploadFeign fileUploadFeign;
// 查询
private final FileQueryFeign fileQueryFeign;
```

## 如何使用
新建一个module，修改pom.xml文件的parent标签：
```
<parent>  
    <groupId>cn.33</groupId>  
    <artifactId>supply</artifactId>  
    <version>1.0-SNAPSHOT</version>  
</parent>
```   
项目中需要什么功能，直接引入相关组件即可，例如需要使用到缓存功能，则引入基础组件和缓存组件即可：
```
<!-- 通用组件 -->
<dependency>
    <groupId>com.disanbo</groupId>
    <artifactId>common-component</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
<!-- Redis组件 -->
<dependency>
    <groupId>com.disanbo</groupId>
    <artifactId>redis-component</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```
新的项目中，需要把application.properties或者application.yml修改为bootstrap.yml，并且必须添加以下代码：
```
eureka:
  instance:
    ip-address: 你的ip地址
    instance-id: ${eureka.instance.ip-address}:${server.port}
    prefer-ip-address: true
    lease-renewal-interval-in-seconds: 30
    lease-expiration-duration-in-seconds: 90
    metadata-map:
      ip.address: ${eureka.instance.ip-address}
      ip.port: ${server.port}
  client:
    service-url:
      defaultZone: http://admin:i3sNstGiSm2BG3HL@172.16.100.32:1111/eureka,http://admin:i3sNstGiSm2BG3HL@172.16.100.33:1111/eureka
```
如果项目中不需要使用spring-cloud-config，可以在基础组件中剔除：
```
<!-- 通用组件 -->
<dependency>
    <groupId>com.disanbo</groupId>
    <artifactId>common-component</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <exclusions>
        <exclusion>
            <artifactId>spring-cloud-starter-config</artifactId>
            <groupId>org.springframework.cloud</groupId>
        </exclusion>
    </exclusions>
</dependency>
```