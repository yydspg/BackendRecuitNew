package com.pg.backend.common.beans;

import com.pg.backend.common.util.SpringContextKit;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author paul 2024/4/23
 */
@Component
public class Test implements ApplicationRunner {
    private Map<String, IService> bucketServiceMap;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        bucketServiceMap = SpringContextKit.getBeansOfType(IService.class);
        for (Map.Entry<String, IService> t : bucketServiceMap.entrySet()) {
            System.out.println(t.getKey() +"..." + t.getValue());
        }
    }
}
