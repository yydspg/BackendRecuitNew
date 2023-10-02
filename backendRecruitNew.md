## 后端-万豪-22-本

#### 2.3

todo

#### 2.4

todo

#### 2.5.1

快排$Olog(n)$ 

#### 2.5.2

1. **面向过程** 以过程为中心,问题分解成一系列步骤,每个过程都会对数据进行操作;**面向对象** 以对象为中心,问题被分解成一系列互相交互的对象,每个对象都有的状态和行为事务的处理由对象内部完成

2. Boxing将基本数据类型转为包装类,UnBoxing反之,过程编译器自动完成,目的:1.包装类便于直接操作,无需Utils2.基本数据类型利于传参

3. todo

4. todo

5. 如果是由同一个classLoader加载的,相同;否则不同

#### 2.5.3

1. ArrayList是基于动态数组,LinkedList是基于双向链表;(数据量较大时)元素访问上,AL优于LL;(数据量较大时)插入删除上,LL优于AL;LL比AL的数据节点更大

2. todo

#### 2.5.4

1.   **进程** 是操作系统分配资源的基本单位,启动进程需要向OS 请求资源,一个进程至少有一个主线程,用于执行代码:**线程** 是进程的一个执行单元,线程从属于进程,用于执行程序,CPU调度的基本单位;**协程** 主要用于用户态,完全由用户控制无内核切换开销,勇于提升程序并发性能

2. 并发是指同一时间内,多任务同时执行,实际任意时刻,单任务执行;保证线程安全有**1.**

#### 2.5.5

1. debug时找错误;自定义异常处理,防止程序中断(ex:sql错误,文件错误...);使业务更加完整

2. 主要使用`throw` 来手动抛出异常,实际开发可以创建`exception` 包,然后创建`BaseException` 用于继承,创建`GlobalExceptionhandler` 用于处理(方法重载)异常; 对于捕获异常,可以向上抛,也可以`try catch`

3.  ~~~牛客上刚好做过一道题~~~
   
   ```java
   //下面程序的输出是?
   public class TestDemo{
       public static String output = "";
       public static void foo(int i){
           try{
               if(i == 1){
                   throw new Exception();
               }
           }catch (Exception e){
               output += "2";
               return ;
           }finally{
               output += "3";
           }
           output += "4";
       }
       public static void main(String[] args){
           foo(0);
           foo(1);
           System.out.println(output);
       }
   }
   ```
   
   执行foo(0)时,不满足try语句if语句,不会抛出异常,执行finally语句;执行foo(1),满足try语句if语句,抛出异常,但是catch语句内有return 但是finally 语句内必须执行,所以finally语句执行后return

4. 如果`finally`语句中也有exception ,或者线程挂了,后面代码也不会执行

#### 2.6

1. 设计模式是前辈们总结理解后解决特定问题的一系列处理流程和解决思想,用于提升代码的复用性,可维护性.etc.主要分为3类: **创建者模式** 对类的实例化过程进行抽象(单例,工厂,建造者.etc)**结构型模式** 关注对象的组成以及对象之间的依赖关系(代理,装饰者,适配器.etc) **行为型模式** 关注对象的行为问题,对不同的对象划分责任和算法的抽象(迭代器,策略,模板方法.etc)

2. todo

3. 生产者消费者用于并发设计,处理生产者和消费者之间的协作问题,对数据传输解耦,producer && consumer 只关注自身数据,不需注意数据发送接受,对于中间过程由类似的中间件rabbitMQ自行维护, spring AMQP 可以简化开发

#### 3.2

```java
 package com.pg.backend.controller;

import com.pg.backend.common.Result;
import com.pg.backend.entity.Dto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/demo")
public class HttpRequest {
    @GetMapping
    public Result<String> combine(@PathVariable String name,String type){
        StringBuilder re = new StringBuilder();
        String s = re.append(name).append("+").append(type).toString();
        return Result.success(s);
    }
    @PostMapping
    public Result<String> combineExt2(@RequestBody Dto dto,@RequestHeader(value = "auth",required = true) String auth){
        if(auth == null ) return Result.error("Invalid User");
        return Result.success(dto.getName()+"-"+dto.getType());
    }
} 
 }
```

#### 3.3

todo

#### 3.4

1. Restful API用url表示资源,http动词(Post,Get,Put,Delete)表示资源操作,使的请求资源和操作分离,更易维护;并且无状态,易用

2. ip用于标记网络中的每一台pc的逻辑地址;域名简化i的记忆;端口有物理端口(实际pc使用的接口)和虚拟端口(docker或者虚拟机.etc).三者的关系是:如果通过域名查找,会经过dns解析出ip,数据包会先发至对应设备,通过对应`port`发至进程

3. Http是一种明文方式的网络传输协议且无状态,https是对http进行ssl/tls协议对数据进行加密,提高传输安全.在实际使用中:$http Port (default):80 $  $ https Port(default) : 443$    $http Url:http:$     $ https Url: https:$

4. http Request 中主要有:请求行(method,uri),请求头(method,cookie.检验的token,etc),请求体(json形式的`DTO,VO`)

5. 全双工通信在建立连接和断开连接时要通知双方,断开连接时由于数据传输可能正在进行,所以需要双方需要收到应答,C端,S端均请求一次,回答一次,就形成了四次挥手;三次握手中,C端发起连接请求,所以S端不用向C端发请求

6. todo

#### 3.5

```java
//1.ossUtil
package com.pg.backend.controller;

import com.pg.backend.common.Result;
import com.pg.backend.constant.FilePath;
import com.pg.backend.constant.FileSize;
import com.pg.backend.constant.FileType;
import com.pg.backend.utils.AliOssUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/oss")
public class Oss {
    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    public Result upload(MultipartFile multipartFile) throws IOException {
        if(FileSize.LV1 <multipartFile.getSize()){
            return Result.error("file is too lage");
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        if(FileType.IMG != ext){
            return Result.error("file type not satisfied");
        }
        String objectName = new StringBuilder()
                .append(FilePath.PATH1)
                .append(UUID.randomUUID())
                .append(ext)
                .toString();
        //使用aliOssUtils上云可能更好
        String filePath = aliOssUtil.upload(multipartFile.getBytes(), objectName);
        return Result.success(filePath);
    }
}
//fileSizeConstant
package com.pg.backend.constant;

public class FileSize {
    public static final Integer LV1 = 1024*1024
}

//fileTypeConstant
package com.pg.backend.constant;

public class FileType {
    public static final String IMG = "/img";
}
//filePathContstant
package com.pg.backend.constant;

public class FilePath {
    public static final String PATH1 = "/demo";
}


```

#### 4.2



![](C:\Users\dell\AppData\Roaming\marktext\images\2023-10-02-14-12-48-image.png)

数据库建表语句

```sql
create database backendTest;
use backendTest;


create table tb_teacher(
    id int primary key auto_increment,
    name varchar(10) not null ,
    sex tinyint comment '0 represent man 1 represent woman' ,
    basic_info varchar(50) ,
    update_time time not null,
    update_user int not null,
    create_user int not null,
    create_time time not null
);
create table tb_student(
    id int primary key auto_increment,
    name varchar(10) not null ,
    sex tinyint comment '0 represent man 1 represent woman' ,
    basic_info varchar(50) ,
    update_time time not null,
    update_user int not null,
    create_user int not null,
    create_time time not null
);
ALTER TABLE tb_student ADD COLUMN id_student int not null unique ;
ALTER TABLE tb_teacher ADD COLUMN id_teacher int not null unique ;
create table tb_student_teacher(
    id int primary key auto_increment,
    id_student int not null unique,
    id_teacher int not null unique,
    id_class int not null unique,
    update_time time not null,
    update_user int not null,
    create_user int not null,
    create_time time not null
);


```

#### 4.3

对于中间表,可以在`id_class,id_student,id_teacher`

加联合索引,查询时遵循最左原则和索引覆盖,避免回表,增加性能

#### 4.4

- Automicity原子性,指一个事务不可分割,如果执行报错,需rollback,DB也需恢复

- Consistency一致性,主要指多表之间操作时,数据保持一致

- Isolation隔离性,指一个事务执行时不会对另一事务干扰

- Durability持久性,事务提交后无法更改

#### 4.5

todo

- PBAC0定义支持PBAC概念的系统最低要求,适用于需要基本角色访问控制的场景

- PBAC1:在PBAC0增加角色分类,A可以从B中继承许可权

- PBAC2:在PBAC0增加在不同组件的限制,用于对角色进行约束的场景

- PBAC3:统一PBAC1&&PBAC2 ,适用于完整的角色访问控制

#### 5.1

使用mybatis时,主要流程是实现service,mapper ,但是单表查询编写复杂,使用mybatis-plus可避免

#### 5.2

使用JWT校验的流程:编写ThreadLocal工具类;编写JWTUtil 用于处理token的create() && parse() ;编写JwtInterceptor 实现 HandleInterceptor 并重写PreHandle方法;编写配置类注册拦截器

```java
//1.jwtProperties
 package com.pg.backend.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "backend.jwt")
public class JwtProperties {
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;
}  
 //2.JwtUtil
 package com.pg.backend.interceptor;

import com.pg.backend.constant.JwtClaimsConstant;
import com.pg.backend.property.JwtProperties;
import com.pg.backend.utils.BaseContext;
import com.pg.backend.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private JwtProperties jwtProperties;

    /**
     * verify jwt ,return true means discharging and false means blocking
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        //judge what had been interceptor ,controller or other
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //1.get token form header
        String token = request.getHeader(jwtProperties.getUserTokenName());

        //2.verify jwt token
        try {
            log.info("jwt verify {}",token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(),token);
            long empId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            log.info("current user id :{}",empId);
            //set ThreadLocal value
            BaseContext.setCurrentId(empId);
            //discharged
            return true;
        } catch (NumberFormatException e) {
            response.setStatus(401);
            return false;
        }
    }
}
//3.webMvcConfig
package com.pg.backend.config;

import com.pg.backend.interceptor.JwtInterceptor;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Slf4j
@Configuration
public class WebMvcConfig {
    @Resource
    private JwtInterceptor jwtInterceptor;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("Start registering  custom interceptor...");
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/em
 ployee/login");
    }
}
 //4.baseContext
package com.pg.backend.utils;

public class BaseContext {
    private  static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    public static void setCurrentId(Long id){threadLocal.set(id);}

    public static Long getCurrentId(){return threadLocal.get();}

    public static void removeCurrentId(){threadLocal.remove();}
}
 
```

#### 5.3

todo

#### 5.4

todo
