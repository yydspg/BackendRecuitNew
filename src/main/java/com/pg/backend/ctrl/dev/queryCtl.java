package com.pg.backend.ctrl.dev;

import com.alibaba.fastjson2.JSON;
import com.pg.backend.common.web.ApiRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author paul 2024/3/24
 */
@Slf4j
@RestController
@RequestMapping("/los")
@Tag(name = "测试用例")
@RefreshScope
public class queryCtl {
    @Value(value = "${spring.datasource.username}")
    private String username;
    @Value(value = "${mongodb.name}")
    private String mongodb;
    @Value(value = "${redis-server}")
    private String redis;
    @Resource
    private Environment environment;
    @GetMapping("/test1")
    public Object test1() {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("canc",":acdcv");
        Object json = JSON.toJSON(stringStringHashMap);
        log.info("ok");
        return json;
    }
    @GetMapping("/test2")
    @Operation(summary = "测试nacos通用配置")
    public ApiRes test2() {
        return ApiRes.ok(username + mongodb + redis);
    }
    @GetMapping("/test3")
    @Operation(summary = "测试nacos")
    public String test3() {
        return environment.getProperty("server.port");
    }
}
