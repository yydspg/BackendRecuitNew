package com.pg.backend.service.impl;

import com.pg.backend.entity.Post;
import com.pg.backend.mapper.DemoMapper;
import com.pg.backend.service.DemoService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {
    @Resource
    private DemoMapper demoMapper;

    @Override
    public Post query(int id) {
        return demoMapper.query(id);
    }

    @Override
    public void delete(int id) {
        demoMapper.delete(id);
    }

    @Override
    public void save(Post post) {
        demoMapper.save(post);
    }

    @Override
    public void update(Post post) {
        demoMapper.update(post);
    }
}
