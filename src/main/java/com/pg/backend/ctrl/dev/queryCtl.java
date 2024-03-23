package com.pg.backend.ctrl.dev;

import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
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
public class queryCtl {
    @GetMapping("/test1")
    public Object test1() {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("canc",":acdcv");
        Object json = JSON.toJSON(stringStringHashMap);
        log.info("ok");
        return json;
    }
}
