package com.pg.backend.ctrl.dev;

import com.alibaba.fastjson2.JSON;
import com.pg.backend.common.web.ApiRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
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
    @GetMapping("/test1")
    public Object test1() {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("canc",":acdcv");
        Object json = JSON.toJSON(stringStringHashMap);
        log.info("ok");
        return json;
    }
    @GetMapping("/test2")
    public ApiRes test2() {
        return  ApiRes.ok();
    }
    @GetMapping("/test3")
    @Operation(summary = "测试nacos")
    public ApiRes test3() {return ApiRes.ok(username);}
}
