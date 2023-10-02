package com.pg.backend.controller;

import com.pg.backend.common.Result;
import com.pg.backend.entity.Dto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/demo")
public class HttpRequest {
    @GetMapping
    public Result<String> combine(@PathVariable String name,String type){
        StringBuilder re = new StringBuilder();
        String s = re.append(name).append("+").append(type).toString();
        return Result.success(s);
    }
    @PostMapping
    public Result<String> combineExt2(@RequestBody Dto dto,@RequestHeader(value = "auth",required = true) String auth){
        if(auth == null ) return Result.error("Invalid User");
        return Result.success(dto.getName()+"-"+dto.getType());
    }
}
