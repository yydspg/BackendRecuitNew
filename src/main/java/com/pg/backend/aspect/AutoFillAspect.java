package com.pg.backend.aspect;

import com.pg.backend.annotation.AutoFill;
import com.pg.backend.constant.AutoFillConstant;
import com.pg.backend.constant.OperationType;
import com.pg.backend.utils.BaseContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
public class AutoFillAspect {
    /**
     * 切点表达式,并且注明注解
     */
    @Pointcut("execution(* com.pg.backend.mapper.*.*(..))&&@annotation(com.pg.backend.annotation.AutoFill)")

    /**
     *拦截前执行
     */
    public void autoFillPointCut(){}
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        //获取操作类型
        OperationType value = autoFill.value();
        //获取传入mapper->DB的方法参数 ->Post对象
        Object[] args = joinPoint.getArgs();
        if(args == null || args.length == 0){
            return ;
        }
        //获取实体
        Object entity = args[0];
        //准备数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();
        //暴力反射获取方法
        Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
        Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
        Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
        Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

        //反射赋值

        if(value == OperationType.INSERT){
            setCreateUser.invoke(entity,currentId);
            setCreateTime.invoke(entity,now);
        }
        setUpdateTime.invoke(entity,now);
        setUpdateUser.invoke(entity,currentId);
    }
}
