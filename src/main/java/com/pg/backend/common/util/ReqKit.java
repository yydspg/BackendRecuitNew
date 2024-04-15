package com.pg.backend.common.util;

/**
 * @author paul 2024/4/9
 */

import com.alibaba.fastjson2.JSONObject;
import com.pg.backend.common.exception.BizException;
import com.pg.backend.common.model.ApiCodeEnum;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ReqKit {

    protected  HttpServletRequest request;
    @Autowired
    public ReqKit(HttpServletRequest request) {
        this.request = request;
    }
    private static final String REQ_CONTEXT_KEY_PARAM_JSON  = "REQ_CONTEXT_KEY_PARAM_JSON";


    public JSONObject getParamsInJSON() {
        JSONObject resJSON = JSONKit.build();
        //JSON
        if(isJSON()) {
            String body = "";
            try {
                body = request.getReader().lines().collect(Collectors.joining(""));
                if(StringKit.isEmpty(body)) {
                    return resJSON;
                }
                return JSONObject.parseObject(body);
            } catch (Exception e) {
                log.error("[{}]ConvertParamsError", body);
                throw new BizException(ApiCodeEnum.PARAMS_ERROR, "ConvertError");
            }
        }
        //NON-JSON
        Map<String, String[]> properties = request.getParameterMap();
        Iterator<Map.Entry<String, String[]>> entries = properties.entrySet().iterator();
        Map.Entry<String,String[]> entry;
        StringBuilder value = new StringBuilder();
        String name;
        while(entries.hasNext()) {
            entry = entries.next();
            name = entry.getKey();
            String[] valueObj = entry.getValue();
            if (null != valueObj) {
                for (String s : valueObj) {
                    value.append(s).append(",");
                }
                value.deleteCharAt(value.length()-1);
            }
            // normal json
            if(!name.contains("[")) {
                resJSON.put(name,value.toString());
                continue;
            }
            //special json example: {ps[abc] : 1}
            String mainKey = name.substring(0, name.indexOf("["));
            String subKey = name.substring(name.indexOf("[") + 1 , name.indexOf("]"));
            JSONObject subJson = new JSONObject();
            if(resJSON.get(mainKey) != null) {
                subJson = (JSONObject)resJSON.get(mainKey);
            }
            subJson.put(subKey, value);
            resJSON.put(mainKey, subJson);
        }
        return resJSON;
    }
    private boolean isJSON() {
        String contentType = request.getContentType();
        return contentType != null
                && contentType.toLowerCase().contains("application/json")
                && !"GET".equalsIgnoreCase(request.getMethod());
    }
    /*
  获取请求参数,功能是从请求上下文中获取请求参数的JSON对象,这个方法的作用是确保每次调用都能获取到请求参数的JSON表示，并且尽可能地避免重复解析请求参数的过程，提高程序性能
   */
    public JSONObject getReqParamJSON() {
           /*
        Spring框架提供的一种机制，用于在运行时获取当前HTTP请求的相关属性。
        通过调用getRequestAttributes()方法，可以获得一个RequestAttributes对象，该对象封装了与当前HTTP请求相关的信息。
        getRequestAttributes()可能为空
         */
        /*
        将转换好的reqParam JSON格式的对象保存在当前请求上下文对象中进行保存；
         注意1： springMVC的CTRL默认单例模式， 不可使用局部变量保存，会出现线程安全问题；
         注意2： springMVC的请求模式为线程池，如果采用ThreadLocal保存对象信息，可能会出现不清空或者被覆盖的问题-->线程复用
         */
        Object o = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()).getAttribute(REQ_CONTEXT_KEY_PARAM_JSON, RequestAttributes.SCOPE_REQUEST);
        if (null == o) {
            JSONObject reqParam = this.getParamsInJSON();
            RequestContextHolder.getRequestAttributes().setAttribute(REQ_CONTEXT_KEY_PARAM_JSON,reqParam,RequestAttributes.SCOPE_REQUEST);
            return reqParam;
        }
        return (JSONObject) o;
    }
    /* 获取客户端ip地址 **/
    /*
    这段Java代码是一个方法，用于获取客户端（用户）的真实IP地址。在Web应用中，由于可能存在反向代理、负载均衡器等中间设备，直接通过`request.getRemoteAddr()`可能无法获取到客户端的真实IP，所以需要从请求头中查找一些特定字段。

      1. 首先尝试从请求头`x-forwarded-for`中获取IP地址，这个字段通常由代理服务器添加，用于标识原始客户端的IP地址。

      2. 若`x-forwarded-for`未提供或者其值为"unknown"，则尝试从`Proxy-Client-IP`字段获取。

      3. 若上述两种方式都未成功，则尝试从`WL-Proxy-Client-IP`字段获取。

    4. 如果以上三种请求头均未提供有效的IP地址，则使用`request.getRemoteAddr()`来获取当前请求连接的IP地址，这个可能是直接连接到服务器的代理IP。

    5. 最后，如果获取到的IP地址长度大于15且包含逗号（即可能存在多个代理IP的情况），截取第一个IP地址作为客户端真实IP地址并返回。

总之，该方法主要目的是在有代理服务器的情况下，尽可能准确地获取发起HTTP请求的客户端的真实IP地址。
     */
    public String getClientIp() {
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }

        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }
}
