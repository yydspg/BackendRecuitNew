package com.pg.backend.ctrl.dev;

import com.alibaba.fastjson2.JSON;
import com.pg.backend.common.model.ApiRes;
import com.pg.backend.common.util.ReqKit;
import com.pg.backend.ctrl.common.AbstractCtrl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author paul 2024/3/24
 */
@Slf4j
@RestController
@RequestMapping("/los")
@Tag(name = "测试用例")
public class queryCtl extends AbstractCtrl {
    @Value(value = "${spring.datasource.username}")
    private String username;
//    @Value(value = "${mongodb.name}")
//    private String mongodb;
//    @Value(value = "${redis-server}")
//    private String redis;
    @Resource
    private Environment environment;



    @RequestMapping(value = "/test1/",method = RequestMethod.POST)
    @Operation(summary = "测试 reqKit")
    public Object test1() {
        String id1 = super.getValString("id");
        log.info("ok");
        return id1;
    }
//    @GetMapping("/test2")
//    @Operation(summary = "测试nacos通用配置")
//    public ApiRes test2() {
//        return ApiRes.success(username + mongodb + redis);
//    }
//    @GetMapping("/test3")
//    @Operation(summary = "测试nacos")
//    public String test3() {
//        return environment.getProperty("server.port");
//    }
}
