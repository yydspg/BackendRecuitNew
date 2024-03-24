package com.pg.backend.common.web;

import com.alibaba.fastjson2.JSON;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author paul 2024/3/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiRes implements Serializable {
    /** 业务响应码 **/
    private Integer code;

    /** 业务响应信息 **/
    private String msg;

    /** 数据对象 **/
    private Object data;

    /** 签名值 **/
    private String sign;
    /** 输出json格式字符串 **/
    public String toJSONString(){
        return JSON.toJSONString(this);
    }

    /** 业务处理成功 **/
    public static ApiRes ok(){
        return ok(null);
    }

    /** 业务处理成功 **/
    public static  ApiRes ok(Object data){
        return new ApiRes(ApiCodeEnum.SUCCESS.getCode(), ApiCodeEnum.SUCCESS.getMsg(), data, null);
    }

}
