package com.pg.provider.ctrl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author paul 2024/4/6
 */
@RestController
@RequestMapping("/nacos")
@Tag(name = "nacos调用者")
public class TestController {

    @Autowired
    private RestTemplate restTemplate;
    @Operation(summary = "测试")
    @RequestMapping(value = "/test1/",method = RequestMethod.GET)
    public String test1(){
        return restTemplate.getForObject("http://nacos-config/los/test3",String.class);
    }
}
