package com.pg.backend.redisDistributedLock.service;

import cn.hutool.core.lang.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author paul 2024/2/29
 */
@Service
@Slf4j
public class QueryService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private ReentrantLock lock = new ReentrantLock();
//    public synchronized void queryTe() {
//
//        try {
//            String s = stringRedisTemplate.opsForValue().get(Constant.TE.getDes());
//            Integer parseInt = Integer.parseInt(Objects.requireNonNull(s));
//            if (parseInt >0) {
//                stringRedisTemplate.opsForValue().set(Constant.TE.getDes(), String.valueOf(--parseInt));
//            }else {
//                log.info("empty");
//            }
//            log.info("==> removeSuccessCurrentRemain[{}]", parseInt);
//        } finally {
//
//        }
//    }
    public void queryTe() {
        String v = UUID.fastUUID() + Thread.currentThread().getName();
        /* query the lock */
        /* 加入锁失效时间 ,防止死锁*/
        while (Boolean.FALSE.equals(stringRedisTemplate.opsForValue().setIfAbsent(Constant.LOCK.getDes(),v ,30L, TimeUnit.SECONDS))) {
            try {
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                log.error("Thread [{}] was interrupted ",Thread.currentThread());
            }
        }
        /* 分布式锁机制无需 jvm局部加锁 */
        try {
            String s = stringRedisTemplate.opsForValue().get(Constant.TE.getDes());
            int parseInt = Integer.parseInt(Objects.requireNonNull(s));
            if (parseInt >0) {
                stringRedisTemplate.opsForValue().set(Constant.TE.getDes(), String.valueOf(--parseInt));
            }
        } finally {
            /* 防止因为锁提前失效导致的锁被其他线程误删,存在原子性问题
            if(v.equalsIgnoreCase(stringRedisTemplate.opsForValue().get(Constant.LOCK.getDes()))) {
                stringRedisTemplate.delete(Constant.LOCK.getDes());
            }*/
            /* 使用 lua 脚本,保证删除op的原子性 */
            /* lua 0 表示 false 1 表示 true,内部书写 lua 时,使用单引号 */
            String script = "if redis.call(get,KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
            stringRedisTemplate.execute(new DefaultRedisScript<>(script, Boolean.class), Arrays.asList(Constant.LOCK.getDes()),v);
        }
    }

}
