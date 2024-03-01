package com.pg.backend.redisDistributedLock.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
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
    public synchronized void queryTe() {

        try {
            String s = stringRedisTemplate.opsForValue().get(Constant.TE.getDes());
            Integer parseInt = Integer.parseInt(Objects.requireNonNull(s));
            if (parseInt >0) {

                stringRedisTemplate.opsForValue().set(Constant.TE.getDes(), String.valueOf(--parseInt));
            }else {
                log.info("empty");
            }
            log.info("==> removeSuccessCurrentRemain[{}]", parseInt);
        } finally {

        }

    }
}
