package com.pg.backend.controller;

import com.pg.backend.common.Result;
import com.pg.backend.entity.Post;
import com.pg.backend.entity.PostDto;
import com.pg.backend.service.DemoService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/post")
public class DemoController {
    @Resource
    private DemoService demoService;
    @PostMapping
    public Result<String> add(@RequestBody PostDto postDto){
        Post post = new Post();
        BeanUtils.copyProperties(postDto,post);
        demoService.save(post);
        return Result.success();
    }
    @PutMapping
    public Result update(@RequestBody PostDto postDto){
        Post post = new Post();
        BeanUtils.copyProperties(postDto,post);
        demoService.update(post);
        return Result.success();
    }
    @DeleteMapping
    public Result<String> delete(@PathVariable int id){
        demoService.delete(id);
        return Result.success();
    }
    @GetMapping
    public Result<PostDto> query(@PathVariable int id){
        Post post = demoService.query(id);
        PostDto postDto = new PostDto();
        BeanUtils.copyProperties(post,postDto);
        return Result.success(postDto);
    }
}
