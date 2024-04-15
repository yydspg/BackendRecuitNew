package com.pg.backend.common.abstractMethod;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * @author paul 2024/4/15
 */
@Slf4j
public abstract class AbstractBean {
    @PostConstruct
    protected void test() {
        log.error("[{}]",getName());
    }
    public abstract String getName();
}
