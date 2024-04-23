package com.pg.backend.common.aop.service;

import com.pg.backend.common.aop.anno.Anno;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author paul 2024/4/23
 */
@Service
@Slf4j
public class TestService {
    @Anno
    public void test(){
        log.info("service-->test");
    }
}
