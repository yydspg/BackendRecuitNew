package com.pg.backend.common.aop.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

/**
 * @author paul 2024/4/23
 */
@Slf4j
@Component
public class Interceptor implements AnnoInterceptor {
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        log.info("lock");
        return invocation.proceed();
    }
}
