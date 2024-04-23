package com.pg.backend.common.web;

import com.pg.backend.common.aop.anno.Anno;
import com.pg.backend.common.aop.service.TestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author paul 2024/4/23
 */
@Tag(name = " 测试aop")
@RestController
@RequestMapping(value = "/aop")
public class Test3{
    @Resource private TestService testService;
    @Anno
    @RequestMapping(value = "/test1",method = RequestMethod.GET)
    public String test1(){
        testService.test();
        return "helloWorld";
    }
}
