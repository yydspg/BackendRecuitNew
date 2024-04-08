package com.pg.sec.ctrl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author paul 2024/4/6
 */
@RestController
@RequestMapping("/api/test")
public class QueryController {
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String test1() {
        return "hello";
    }
}
