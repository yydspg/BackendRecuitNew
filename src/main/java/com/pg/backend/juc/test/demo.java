package com.pg.backend.juc.test;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * @author paul 2024/3/3
 */

public class demo {
    /*
    在现代Java企业级应用开发中，虽然`Runnable`和`Callable`接口是最基本的并发编程构建块，用来定义任务（即待在线程中执行的操作），
    但`FutureTask`作为一个结合了`Runnable`和`Future`特性的类，在处理具有返回值和取消功能的异步任务时非常有用，尤其是在涉及线程池的情况下。

    `FutureTask`通常与`ExecutorService`一起使用，因为当你提交一个`Callable`任务到线程池时，
    线程池会返回一个`Future`对象，允许你查询任务是否完成，获取任务结果或取消任务。`FutureTask`恰好是`Future`接口和`Runnable`接口的实现，因此它可以作为任务提交给线程或线程池，并同时提供未来结果的控制能力。

    总结来说，在实际的企业应用中，尤其是需要异步处理和获取结果的情况下，`Callable`与`FutureTask`组合使用的场景更为常见，
    尤其是在利用`ThreadPoolExecutor`等高级并发组件时。而`Runnable`则常用于那些不需要返回值的任务，或是通过扩展Runnable来实现简单的线程逻辑。
     */
    public static void main(String[] args) {

    }
}
