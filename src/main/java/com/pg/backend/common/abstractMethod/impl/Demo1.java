package com.pg.backend.common.abstractMethod.impl;

import com.pg.backend.common.abstractMethod.AbstractBean;
import org.springframework.stereotype.Component;

/**
 * @author paul 2024/4/15
 */
@Component
// 如果不加component 的话,此Bean 是不交于 spring IOC 容器的,所以 @PostConstruct 无法生效
public class Demo1 extends AbstractBean {

    @Override
    public String getName() {
        return "demo1";
    }
}
