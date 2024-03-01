package com.pg.backend.redisDistributedLock.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author paul 2024/2/29
 */
@Slf4j
@RestController
@RequestMapping("/te")
public class TeCtrl {
    @Autowired QueryService queryService;
    @GetMapping("/query")
    public String queryTe() {
        queryService.queryTe();
        return "ok";
    }
}
