package com.pg.backend.juc.main.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author paul 2024/3/12
 */
@Slf4j
public class demo {
    public static final Object o = new Object();
    public static void main(String[] args) {
        new Thread(()->{
            log.info("t1Start");
            synchronized (o) {
                try {
                    o.wait();
                } catch (InterruptedException e) {
                    log.info("t1wasNotified");
                }
                log.info("t1Ok");
            }
        },"t1").start();
        new Thread(()->{
            log.info("t2Start");
            synchronized (o) {
                try {
                    o.wait();
                } catch (InterruptedException e) {
                    log.info("t2wasNotified");
                }
                log.info("t2Ok");
            }
        },"t2").start();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        synchronized (o) {
            o.notifyAll();
        }
    }
}
