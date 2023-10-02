package com.pg.backend.interceptor;

import com.pg.backend.constant.JwtClaimsConstant;
import com.pg.backend.property.JwtProperties;
import com.pg.backend.utils.BaseContext;
import com.pg.backend.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private JwtProperties jwtProperties;

    /**
     * verify jwt ,return true means discharging and false means blocking
     * @param request current HTTP request
     * @param response current HTTP response
     * @param handler chosen handler to execute, for type and/or instance evaluation
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        //judge what had been interceptor ,controller or other
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        //1.get token form header
        String token = request.getHeader(jwtProperties.getUserTokenName());

        //2.verify jwt token
        try {
            log.info("jwt verify {}",token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(),token);
            long empId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            log.info("current user id :{}",empId);
            //set ThreadLocal value
            BaseContext.setCurrentId(empId);
            //discharged
            return true;
        } catch (NumberFormatException e) {
            response.setStatus(401);
            return false;
        }
    }
}
