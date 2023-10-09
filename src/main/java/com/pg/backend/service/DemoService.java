package com.pg.backend.service;

import com.pg.backend.entity.Post;

public interface DemoService {
    Post query(int id);

    void delete(int id);

    void save(Post post);

    void update(Post post);
}
