package com.pg.backend.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private Integer code;
    private String msg;
    private  T data;

    public static <T> Result<T> success(){
        Result<T> tResult = new Result<>();
        tResult.code = 1;
        return tResult;
    }
    public static <T> Result<T> success(T object){
        Result<T> result = new Result<>();
        result.data = object;
        result.code = 1;
        return result;
    }
    public static <T> Result<T> error(String msg){
        Result<T> result = new Result<>();
        result.code = 0;
        result.msg = msg;
        return result;
    }
}
