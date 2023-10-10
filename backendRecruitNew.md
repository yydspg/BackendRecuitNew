## 后端-万豪-22-本

#### 2.3&&2.3.1.1

设计思路是创建`add(int,T)` 和`remove(int,T)` 方法,衍生的上层方法调用时,自行选择是否处理`index`范围,抽取公共的`maintain`方法,暴力反射监听,便于横向扩展,再加入一个~~~~用处不大~~的Gc方法,对于公共方法`findNode`自身优化和调用时优化,实现迭代器,便于遍历

```java
package com.pg.backend.algorithm.linkedList;

import java.lang.reflect.Method;
import java.util.Iterator;


public class DoublyLinkedListSentinel<T> implements Iterable<T> {

    private final static String addMethodName = "add";
    private final static String removeMethodName = "remove";

    static class Node<T>{
        Node prev;
        T data;
        Node next;

        public Node(Node prev, T data, Node next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }

    private Node<T> head;
    private Node<T> tail;

    private int size = 0;



    public DoublyLinkedListSentinel() {
        head = new Node<T>(null,null,null);
        tail = new Node<T>(null,null,null);
        head.next = tail;
        tail.prev = head;
    }

    /**
     * constructor an Util
     * @param index
     * @return
     */
    private Node<T> findNode(int index){
        if(index < (size-1) / 2){
            return findNodeFrontToBack(index);
        }else{
            return findNodeBackToFrom(index);
        }
    }

    /**
     * index start from -1,find node from front to back
     * @param index
     * @return
     */
    private Node<T> findNodeFrontToBack(int index){
        int i=-1;
        for (Node<T> p =head;p != tail;p = p.next,i++){
            if(i == index){
                return p;
            }
        }
        return null;
    }

    /**
     * index start from size,find node from back to front
     * @param index
     * @return
     */
    private Node<T> findNodeBackToFrom(int index){
        int i = size;
        for (Node<T> p = tail;p != head;p = p.prev,i--){
            if(i == index){
                return p;
            }
        }
        return null;
    }

    /**
     * notification!!! this function doesnt has the ability to check the index rationality
     * if u want to call this function,please check the index`s rationality by yourself
     * @param index
     */
    public void remove(int index) throws NoSuchMethodException {
//        this way has pool efficiency
//        Node<T> prefix = findNode(index - 1);
//        Node<T> suffix = findNode(index + 1);
        Node<T> prefix = findNode(index - 1);
        Node<T> suffix = prefix.next.next;
        prefix.next = suffix;
        suffix.prev = prefix;
        Class[] cArgs = new Class[1];
        cArgs[0] = int.class;
        maintain(prefix,suffix,this.getClass().getDeclaredMethod(removeMethodName,cArgs[0]));
    }

//    @Deprecated
//    public void add(int index,Node<T> tem){
//
//        Node<T> prefix = findNode(index - 1);
//        Node<T> suffix = prefix.next;
//        prefix.next = tem;
//        tem.prev = prefix;
//        suffix.prev = tem;
//        tem.next = suffix;
//    }

    /**
     * notification!!! this function doesnt has the ability to check the index rationality
     * if u want to call this function,please check the index`s rationality by yourself
     * @param index
     * @param object
     */
    private void add(int index,T object) throws NoSuchMethodException {

        Node<T> prefix = findNode(index - 1);
        Node<T> suffix = prefix.next;
        Node<T> tem = new Node<>(prefix,object, suffix);
        prefix.next = tem;
        suffix.prev = tem;

        Class[] cArgs = new Class[2];
        cArgs[0] = int.class;
        cArgs[1] = Object.class;
        maintain(prefix,suffix,this.getClass().getDeclaredMethod(addMethodName,cArgs));

    }
    public void addByIndex(int index,T object) throws NoSuchMethodException {
        isIndexIllegal(index);
        add(index,object);
    }
    public void removeByIndex(int index) throws NoSuchMethodException{
        isIndexIllegal(index);
        remove(index);
    }
    public void addFirst(T object) throws NoSuchMethodException{
        add(0,object);
    }
    public void removeFirst() throws  NoSuchMethodException{
        remove(0);
    }
    public void addLast(T object) throws NoSuchMethodException{
        add(size,object);
    }
    public void removeLast() throws NoSuchMethodException{
        remove(size - 1);
    }
    private void isIndexIllegal(int index){
        if(index < 0 && index >size){
            throw illegalIndex(index);
        }
    }
    private IllegalArgumentException illegalIndex(int index){
        return new IllegalArgumentException(String.format("index: [%d] is illegal",index));
    }

    private void quickGc(Node<T> pre,Node<T> suf){
        pre = null;
        suf = null;
    }
    private void maintain(Node<T> pre,Node<T> suf,Method method){
        quickGc(pre,suf);
        maintainSize(method);
    }

    private  void maintainSize(Method method){
        if (method.getName().equals(addMethodName)) {
            size ++;
        }
        if (method.getName().equals(removeMethodName)) {
            size --;
        }
    }    
    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node<T> tem = head.next;
            @Override
            public boolean hasNext() {
                return tem != tail;
            }

            @Override
            public T next() {
                T oTem = tem.data;
                tem = tem.next;
                return oTem;
            }
        };
    }
}
```

#### 2.3.1.3

双向链表有环,当环出现在一端时,`head.next`和`tail.prev` !=`null`,只需置为`null` 即可,当环出现在中间时,首先使用快慢指针找到`bug结点`,其次双端同时遍历找到主链上的`bug`相邻结点,把环上的链路打开连上主链即可(但是代码没测试过)

```java
public void eliminationLoop(){

    if (head.prev != null || tail.next != null) {
            head.prev = null;
            tail.next = null;
            return;
        }
        Node<T> bug = detectCycle();
        if (bug == null) {
            return;
        }

        Node<T> t = bug.prev;
        Node<T>[] q= getPreAndSuf(bug);
        t.next = q[1];
        q[1].prev = t;
        bug.prev = q[0];
    }
    private  Node<T> detectCycle(){
        Node<T> f = head;
        Node<T> s = head;
        while(f != null && f.next != null){
            f = f.next.next;
            s = s.next;
            if(s == f){
                s = head;
                while(true){
                    if(s == f)  return s;
                    s = s.next;
                    f = f.next;
                }
            }
        }
        return null;
    }

    private Node<T>[] getPreAndSuf(Node<T> bug){
        Node<T>[] res = new Node[2];
        Node<T> t = head,p = tail;
        while(t.next == bug && p.prev == bug){
            if(t.next != bug) t = t.next;
            if(p.prev != bug) p = p.prev;
        }
        res[0] = t;
        res[1] = p;
        return res;
    }
```

#### 2.4

多重背包,采用二进制优化

```java
package com.pg.backend.algorithm.dp;

import java.util.Scanner;

public class DpTest {
    public void ans(){
        Scanner in = new Scanner(System.in);
        int index = 0;
        int N = in.nextInt();
        int V = in.nextInt();
        int[] f = new int[V+1];
        int[] v = new int[100000];
        int[] w = new int[100000];
        int t1=0,t2=0,t3=0;
        for (int i = 0; i < N; i++) {
            String[] s = in.nextLine().split(" ");
            t1 = Integer.parseInt(s[0]);
            t2 = Integer.parseInt(s[1]);
            t3 = Integer.parseInt(s[2]);
            for (int j=0;j<=t3;j<<=1){
                v[++index] = j*t1;
                w[++index] = j*t2;
                t3 -= j;
            }
            if(t3>0){
                v[++index] = t3*t1;
                w[++index] = t3*t1;
            }
        }
        for (int i = 0; i < index; i++) {
            for (int j = V;j > v[i];j--){
                f[j] = Math.max(f[j-1],f[j-v[i]]+w[i]);
            }
        }
        System.out.println(f[V]);
    }
}
```

#### 2.5.1

快排$Olog(n)$ ,冒泡排序$O(n^2)$ ,插入排序$O(n^2)$ ,选择排序$O(n^2)$,堆排序$O(nlogn)$ ,计数排序$O(n+k)$ ,k是整数的范围,桶排序$O(n+k)$ ,k是桶的数量,希尔排序$O(nlogn)$

#### 2.5.2

1. **面向过程** 以过程为中心,问题分解成一系列步骤,每个过程都会对数据进行操作,常用应该主要是反射,链式编程,`lambda,stream`体现的较多;**面向对象** 以对象为中心,问题被分解成一系列互相交互的对象,每个对象都有的状态和行为事务的处理由对象内部完成

2. Boxing将基本数据类型转为包装类,UnBoxing反之,过程编译器自动完成,目的:1.包装类便于直接操作,无需`Utils` 2.基本数据类型利于传参

3. `String` 类被`final`修饰,不可被继承,成员方法无法被重写,字符串不可变,多线程安全,同一个字符串可被多个线程共享,用`String` 作为参数保证安全;不可变字符串保证了`hashCode`唯一同时由此特性实现字符串常量池(创建字符时,若已经存在,直接引用)

4. `Lambda`主要用于匿名内部类的简化书写(重写条件是:只含有一个抽象方法的接口),函数式编程注重解决事件的工具而非对象,所以忽略产生对象,注重行为执行,主要是使用`Lombok 的@Builder` `stream()` .etc 简洁的实现函数式编程   

```java
 @Test
    public void demo(){
        //part 1 lambda list
        List<String> te = new ArrayList<>();
        te.forEach(t->log.info("t"));
        te.removeIf(t -> t.length()> 1);
        te.replaceAll(t -> {
            if (t.contains("a")) return "ok";
            return "false";
        });
        te.sort((t,p)->t.length()-p.length());
        te.sort(Comparator.comparingInt(String::length));

        //part 2 lambda map
        Map<String,String > pt = new HashMap<>();
        pt.forEach((k,v)->log.info("{}:{}",k,v));
        pt.replaceAll((k,v)->v.toUpperCase());
        String newMsg = "testNewMsg";
        String key ="testKey";
        //map中的K无对应V时,newMsg关联至K
        pt.merge(key,newMsg,(v1,v2)->v1+v2);
        pt.compute(key,(k,v)->v ==null ? newMsg:v.concat(newMsg));
        pt.computeIfAbsent(key, v->newMsg);
        pt.computeIfPresent(key,(k,v)->newMsg);

        Runnable rnn = ()-> log.info("test");
        //函数式编程,stream,@Builder.etc
        //IntStream,LongStream,DoubleStream,Stream 继承 BaseStream,特性:无储存,函数式,可消费,对于Stream的操作分为:terminal.intermediate
        te.stream().forEach(t-> log.info(t));
        te.stream().filter(t->t.length() == 2).forEach(t->log.info(t));
        te.stream().distinct().forEach(t->log.info(t));
        te.stream().map(t->t.toUpperCase()).forEach(t->log.info(t));
        Stream<String> stream = Stream.of("1", "2", "3");
        List<String> list = stream.collect(Collectors.toList());
        Set<String> set = stream.collect(Collectors.toSet());
        List<String> list1 = set.stream().collect(Collectors.toList());
        HashSet<String> collect = stream.collect(Collectors.toCollection(HashSet::new));
        //字符串拼接
        String collect1 = stream.collect(Collectors.joining());

    }
```

    5.如果是由同一个classLoader加载的,相同;否则不同

#### 2.5.3

1. ArrayList是基于动态数组,LinkedList是基于双向链表;(数据量较大时)元素访问上,AL优于LL;(数据量较大时)插入删除上,LL优于AL;LL比AL的数据节点更大

2. todo

#### 2.5.4

1.   **进程** 是操作系统分配资源的基本单位,启动进程需要向OS 请求资源,一个进程至少有一个主线程,用于执行代码:**线程** 是进程的一个执行单元,线程从属于进程,用于执行程序,CPU调度的基本单位;**协程** 主要用于用户态,完全由用户控制无内核切换开销,勇于提升程序并发性能

2. 并发是指同一时间内,多任务同时执行,实际任意时刻,单任务执行;保证线程安全有**1.** 
   
   1. **内核线程(KLT)** :直接由OS kernel 支持的线程,由内核(多线程内核)实现线程切换,程序中一般使用轻量级线程(由内核线程支持),缺点是用户态和内核态之间切换不易,消耗内核资源                                                                                                                                      **用户线程**: 完全建立在用户空间的线程库,系统内核无法感知,缺点是线程操作自行处理,难以解决阻塞等问题,                                                                                                               **用户线程和轻量级线程(LWP)混合使用**:LWP作为用户线程和KLT的桥梁,可使用Kernel中的线程调度...,保留了建立在用户空间的用户线程,支持大规模的线程并发

3. jdk21之前,创建的线程对象称为platform thread(linux) ,pthread在底层OS thread 上运行java 代码,在代码运行时占据OS thread 

        创建流程:

            1.封装类Thread对象调用start()方法 (start() native 修饰,c++编写)

            2.create OsThread (跨平台,抽象层)

            3.create pThread(linux) || create ~~windows Thread ~~~           

        jdk21之后,出现虚拟线程,类协程,类似于用户线程,上下文切换JVM自行处理,JVM内部管理和调度,多个虚拟线程可以在同一个 OS 线程上运行其 Java 代码，可以有效地共享该线程。

    4.synchronized 作用于主要用于线程同步

    使用方法:

        1.非static 方法

```java
 class Test{
    public synchronized void test(){

    }
}
//等价于
class Test{
    public  void test(){
    synchronized(Test.class){
//new thread() 传入Test对象时,产生不同的对象锁
         }
    }
}
```

        2.static 方法

```java
 class Test{
    public static  synchronized void test(){

    }
}
//等价于
class Test{
    public static void test(){
    synchronized(Test.class){
//虽然传入不同对象,但是类对象一致
         }
    }
}
```

        3.部分代码块加锁

    实现原理: 

        代码块加锁是在字节码加指令monitorenter monitorexit ,线程同步方法加锁,会在class文件方法 Access Falgs 打标记 ACC_SYNCHRONIZED

    

#### 2.5.5



1. debug时找错误;自定义异常处理,防止程序中断(ex:sql错误,文件错误...);使业务更加完整

2. 主要使用`throw` 来手动抛出异常,实际开发可以创建`exception` 包,然后创建`BaseException` 用于继承,创建`GlobalExceptionhandler` 用于处理(方法重载)异常; 对于捕获异常,可以向上抛,也可以`try catch`

3. ```java
   //牛客上刚好做过一道题
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
   ```
   
   执行foo(0)时,不满足try语句if语句,不会抛出异常,执行finally语句;执行foo(1),满足try语句if语句,抛出异常,但是catch语句内有return 但是finally 语句内必须执行,所以finally语句执行后return
   
   如果`finally`语句中也有`exception `,或者线程挂了,后面代码也不会执行

#### 2.6

1. 设计模式是前辈们总结理解后解决特定问题的一系列处理流程和解决思想,用于提升代码的复用性,可维护性.etc.主要分为3类: **创建者模式** 对类的实例化过程进行抽象(单例,工厂,建造者.etc)**结构型模式** 关注对象的组成以及对象之间的依赖关系(代理,装饰者,适配器.etc) **行为型模式** 关注对象的行为问题,对不同的对象划分责任和算法的抽象(迭代器,策略,模板方法.etc)

2. 单例模式，向外提供唯一可被访问的实例化的对象，无此对象时，使用类创建一个。多线程并发访问时，加上Synchronized上锁,让没有持有该对象的类处于等待状态。
   
   实现:
   
   ```java
   
   //类加载时实例化
   public class St {
       
       private static St te = new St();
       
       private St() {
       }
       
       public static St getTe() {
           return te;
       }
       
   }
   //加单锁
   public class St1 {
       private static St1 te;
       
       private static St1() {
       }
      //每次需要获取锁
       private static synchronized St1 getTe() {
           if (te == null) {
               te = new St1();
           }
           return te;
       }   
   }
   //进锁前检查
   public class St2 {
       private  static St2 te;
       
       private St2() {
       }
       
       public static St2 getTe() {    
           //先查后锁
           if (te == null) {
               synchronized (St2.class) {
                   if (te == null) {
                       te = new St2();
                   }
               }
           }
           return te;
       }  
   }
   //静态内部类
   public class St3 {
       
       private St3() {
       }
       
       private static class Sti {
           private static final St3 te = new St3();
       }
       
       public static St3 getTe() {
           return Sti.te;
       }
        
   }
   ```

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
        String s = re.append(name)
                     .append("+")
                     .append(type)
                     .toString();
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

###### ext

1,2,3均已实现

#### 3.3

crud时的公共数据,实现`aspect`切面类,填充`updateTime,createUser`.etc字段

```java
//controller 
package com.pg.backend.controller;

import com.pg.backend.common.Result;
import com.pg.backend.entity.Post;
import com.pg.backend.entity.PostDto;
import com.pg.backend.service.DemoService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/post")
public class DemoController {
    @Resource
    private DemoService demoService;
    @PostMapping
    public Result<String> add(@RequestBody PostDto postDto){
        Post post = new Post();
        BeanUtils.copyProperties(postDto,post);
        demoService.save(post);
        return Result.success();
    }
    @PutMapping
    public Result update(@RequestBody PostDto postDto){
        Post post = new Post();
        BeanUtils.copyProperties(postDto,post);
        demoService.update(post);
        return Result.success();
    }
    @DeleteMapping
    public Result<String> delete(@PathVariable int id){
        demoService.delete(id);
        return Result.success();
    }
    @GetMapping
    public Result<PostDto> query(@PathVariable int id){
        Post post = demoService.query(id);
        PostDto postDto = new PostDto();
        BeanUtils.copyProperties(post,postDto);
        return Result.success(postDto);
    }
}
}
```

#### 3.4

1. Restful API用url表示资源,http动词(Post,Get,Put,Delete)表示资源操作,使的请求资源和操作分离,更易维护;并且无状态,易用

2. ip用于标记网络中的每一台pc的逻辑地址;域名简化i的记忆;端口有物理端口(实际pc使用的接口)和虚拟端口(docker或者虚拟机.etc).三者的关系是:如果通过域名查找,会经过dns解析出ip,数据包会先发至对应设备,通过对应`port`发至进程

3. Http是一种明文方式的网络传输协议且无状态,https是对http进行ssl/tls协议对数据进行加密,提高传输安全.在实际使用中:`http Port (default):80`  ` https Port(default) : 443`   `http Url:http:`  ` https Url: https:`

4. http Request 中主要有:请求行(method,uri),请求头(method,cookie.检验的token,etc),请求体(json形式的`DTO,VO`)

5. 全双工通信在建立连接和断开连接时要通知双方,断开连接时由于数据传输可能正在进行,所以需要双方均收到应答,C端,S端均请求一次,回答一次,就形成了四次挥手;三次握手中,C端发起连接请求,所以S端不用向C端发请求

6. WebSocket 协议是单个 TCP 连接上进行全双工通信的协议,主要解决双向通信机制.
   
   WebSocket API 中，C端和S端只需完成一次握手，两者创建持久性的连接，并进行双向数据传输,允许S端主动向C端推送数据,使用 TCP 作为传输层协议，支持在 TCP 上层引入 TLS 层，进行数据加密.

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

```sql
use backendTest;
alter table tb_student_teacher add index s(id_class,id_student,id_teacher);
insert into tb_student_teacher(id_student, id_teacher, id_class, update_time, update_user, create_user, create_time) VALUE (1,2,3,LOCALTIME,1,1,LOCALTIME);
explain select id_class, id_student,id_teacher from tb_student_teacher use index (s) where id_class = ?;
#实际整合数据可能置于业务层,写两条联查sql
select s.name,t.name from tb_student s,tb_teacher t where (s.id,t.id) = (select id_student,id_teacher from tb_student_teacher where id_class = ?);
#多表共性数据置于业务层,同时删除
delete from tb_student_teacher where id_class = ?;
update tb_student set basic_info = ?;
#如果对单表的多字段多次修改,可能的操作是保证原子性的时候,先删后加
```

###### ext

```sql
select (select name from tb_teacher where id = t.id_teacher) 'name',(select count(*) from tb_student_teacher e where e.id_teacher = t.id_teacher) 'number' from tb_student_teacher t order by 'number' desc 
#功能实现了,但是此sql效率应该很低
```

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

使用mybatis时,主要流程是实现service,mapper ,~~~~但是单表查询编写复杂,使用mybatis-plus可避免~~ 

```java
//service
package com.pg.backend.service.impl;

import com.pg.backend.entity.Post;
import com.pg.backend.mapper.DemoMapper;
import com.pg.backend.service.DemoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Resource
    private DemoMapper demoMapper;

    @Override
    public Post query(int id) {
        return demoMapper.query(id);
    }

    @Override
    public void delete(int id) {
        demoMapper.delete(id);
    }

    @Override
    public void save(Post post) {
        demoMapper.save(post);
    }

    @Override
    public void update(Post post) {
        demoMapper.update(post);
    }
}
//mapper
package com.pg.backend.mapper;

import com.pg.backend.annotation.AutoFill;
import com.pg.backend.constant.OperationType;
import com.pg.backend.entity.Post;
import org.apache.ibatis.annotations.*;

@Mapper
public interface DemoMapper {
    @AutoFill(OperationType.UPDATE)
    @Select("select *  from db where id = #{id}")
    public Post query(int id);
    @Delete("delete from db where id = #{id}")
    void delete(int id);
    @AutoFill(OperationType.INSERT)
    @Insert("insert into db(info,create_time, update_time, create_user, update_user) value"+
   " (#{info},#{createTime}, #{updateTime}, #{createUser}, #{updateUser}) ")
    void save(Post post);
    @AutoFill(OperationType.UPDATE)
    @Update("update db set info = #{info} where id  = #{id}")
    void update(Post post);
```

#### 5.2

使用JWT校验的流程:编写`ThreadLocal`工具类;编写JWTUtil 用于处理`token`的`create() && parse()` ;编写`JwtInterceptor` 实现 `HandleInterceptor` 并重写`PreHandle`方法;编写配置类注册拦截器

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
