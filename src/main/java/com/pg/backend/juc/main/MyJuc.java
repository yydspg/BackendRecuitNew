package com.pg.backend.juc.main;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;


/**
 * @author paul 2024/3/3
 */
/*
    关于Future接口
    1. new Thread() 对于接口只支持 Runnable ,但是 Runnable 无发返回结果
    2. Callable 接口返回结果但是无法构造

    关于RunnableFuture接口
    1. Java可以接口多继承,所以增强 Runnable 接口只需使用 RunnableFuture 接口继承 Runnable && Future

    关于 FutureTask 实现类
    1. 实现了 RunnableFuture接口, 相当于实现 Runnable ,Future接口,但是未实现 Callable 接口
    2. 如果需要异步返回结果,因此采用构造注入思想 传入Callable
    3.A FutureTask can be used to wrap a Callable or Runnable object.
    4. get() 方法阻塞
    5. isDone() 方法while(true) 持续轮询消耗 cpu 资源

    关于 CompletableFuture类
    1. 实现Future && CompletionStage
    2. 不推荐 new CompletableFuture()
    3. 静态构造方法:
        runAsync (无返回值)
            public static CompletableFuture<Void> runAsync(Runnable runnable)
            public static CompletableFuture<Void> runAsync(Runnable runnable,Executor executor)
        supplyAsync (有返回值)
            public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
            public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier,Executor executor)
        无指定 Executor的方法,默认使用ForkJoinPool.commonPool() 作为线程池执行异步处理代码
    4. 优点
        异步任务结束时,自动回调某对象的方法
        主线程设置回调后,不用注意异步任务执行,异步任务之间可以顺序执行
        异步任务出错后,自动回调某对象方法
    5. get()&&join()
        异常处理方面，join() 提供了一种更为宽松的方式，不需要显示地处理检查异常，而 get() 则要求开发者更加明确地处理可能出现的异常情况
 */
/*
    Java接口多继承:
    在Java中禁止了类之间多继承，即一个类不能直接继承多个父类
    此问题发生在多继承环境中，当一个类继承自两个具有相同方法签名的父类时，编译器无法确定应该调用哪个父类的方法，从而导致二义性

    Java接口可以多继承。这是因为接口本身不包含具体实现，只是定义了一组抽象方法和常量
    当一个接口继承多个接口时，是合并所有父接口中的方法签名，而无需解决具体的实现冲突问题
    实现接口的类必须提供所有接口中声明的抽象方法的实现，即使这些方法签名在多个接口中重复，也不会产生二义性，因为最终是由实现类来提供唯一的一个实现版本

    构造注入:
    Java中的构造注入是一种依赖注入（Dependency Injection，DI）的方式，它是指在创建一个对象实例时，通过构造函数传递所需的依赖项（即其他对象或服务），
    而不是在类内部自行创建或查找这些依赖。这种方式确保依赖关系在对象创建阶段就被明确指定和建立起来，降低类间耦合度

    Executor&&Executors:
    在Java编程中，`Executor` 和 `Executors` 主要区别如下：
    1.`java.util.concurrent.Executor` 是一个接口，它定义了一种通用的方式来执行异步任务。它只有一个核心方法 `execute(Runnable command)`，
   该方法接收实现了 `Runnable` 接口的任务，并将其提交给执行器去异步执行。通过实现这个接口，开发者可以自行控制任务的执行策略，比如创建新的线程来执行任务或者将任务放入线程池中。

    2. `java.util.concurrent.Executors` 是一个工具类，它提供了一系列静态工厂方法来帮助开发者便捷地创建各种类型的线程池以及相关的执行服务。实现 `Executor` 或其子接口 `ExecutorService` 的实例
     - `newFixedThreadPool(int nThreads)` 创建固定大小的线程池，可以重用指定数量的线程执行多个任务
     - `newCachedThreadPool()` 创建一个可缓存线程池，线程池根据需要创建新线程，空闲线程会被回收
     - `newSingleThreadExecutor()` 创建一个单线程的Executor，同一时间内只会顺序执行一个任务
    阿里规范
    阿里巴巴集团在其《阿里巴巴Java开发手册》中明确规定，建议开发者不使用 Executors 类提供的静态工厂方法，而是通过自定义 `ThreadPoolExecutor` 方式来创建线程池。Executors存在潜在问题
    1. 固定的大小与拒绝策略- `Executors.newFixedThreadPool` 创建的线程池大小固定，若任务队列已满且线程数达到上限时，会采用默认的拒绝策略（通常是 `AbortPolicy`），直接抛出 `RejectedExecutionException`，生产环境而言不够灵活且不够健壮。
    2. 无界队列- `Executors.newCachedThreadPool` 和 `Executors.newSingleThreadExecutor` 使用的是无界的 `SynchronousQueue` 作为工作队列，这意味着如果有大量任务提交过来而无法及时处理时， OOM（Out Of Memory）。
    3. 资源消耗- `Executors.newSingleThreadExecutor` 和 `Executors.newFixedThreadPool` 如果遇到线程池中的线程被阻塞，无法释放资源，可能导致线程资源无法利用，同时对于IO密集型任务，线程池大小不适合
    4. 缺乏细粒度控制- 使用 `Executors` 创建的线程池往往默认配置较为简单，难以针对具体场景（如CPU密集型、IO密集型、混合型任务）进行细致的参数调优，如线程池大小、队列容量、拒绝策略等。
    综上所述，通过直接自定义 `ThreadPoolExecutor` 可以更明确地设定线程池的初始化参数，例如设置合适的线程池大小、采用有界队列、自定义拒绝策略等，从而降低资源耗尽的风险，提高系统稳定性和性能表现。
 */
@Slf4j
public class MyJuc {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        demo2();
    }
    /*
        普通处理,如同Future处理相同
     */
    public static void demo1() throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "hello World", pool);
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {log.error("thread[{}] was interrupted",Thread.currentThread());}
            return "hello World";
        }, pool);
        log.info(future.get());
        log.info(future1.get());
        pool.shutdown();
    }
    public static void demo2() throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            CompletableFuture<String> future = CompletableFuture.supplyAsync(()-> {
                        try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {log.error("thread[{}] was interrupted",Thread.currentThread());}
                        return "hello";
                    },pool)
                    .whenComplete((v, e)-> log.info("[{}] complete!! content[{}]",Thread.currentThread(),v))
                    .exceptionally(e->{
                log.error("[{}] error",Thread.currentThread());
                return null;
            });

            log.info("main thread is doing right thing");
            TimeUnit.SECONDS.sleep(3);
        } finally {
            pool.shutdown();
        }
    }
}
