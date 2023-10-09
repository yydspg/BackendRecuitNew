package com.pg.backend.entity;

import com.pg.backend.annotation.AutoFill;
import com.pg.backend.constant.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private int id;
    private String info;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private int createUser;
    private int updateUser;
}
