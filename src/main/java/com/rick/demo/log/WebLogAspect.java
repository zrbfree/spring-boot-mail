package com.rick.demo.log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;

/**
 * Desc :  实现Web层的日志切面
 * User : RICK
 * Time : 2017/8/28 15:52
  */
@Component
@Aspect
@Order(-5)
public class WebLogAspect {
    private  final Logger log = LoggerFactory.getLogger(this.getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    /**
     * 定义一个切入点.
     * 解释下：
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */
    @Pointcut("execution(public * com.rick.demo.controller..*(..))")
    public void webLog(){
    }


    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint){
        startTime.set(System.currentTimeMillis());
        log.info("WebLogAspect.doBefore");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        log.info("URL:" + request.getRequestURL().toString());
        log.info("HTTP_METHOD:" + request.getMethod());
        log.info("IP:" + request.getRemoteAddr());
        log.info("CLASS_METHOD:" + joinPoint.getSignature().getDeclaringTypeName()
        + "." + joinPoint.getSignature().getName());
        log.info("ARGS:" + Arrays.toString(joinPoint.getArgs()));

        //获取所有参数方法一：
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()){
            String paraName = (String)enumeration.nextElement();
            System.out.println(paraName + ":" + request.getParameter(paraName));
        }
    }


    @After("webLog()")
    public void doAfter(){
        log.info("WebLogAspect.doAfter");
        log.info("doAfter 耗时(毫秒)" + (System.currentTimeMillis() - startTime.get()));
    }

    /**
     * Desc :  错误异常时候调用
     * User : RICK
     * Time : 2017/8/28 16:28
      */
    @AfterThrowing("webLog()")
    public void doAfterThrowing(JoinPoint joinPoint){
        // 处理完请求，返回内容
        log.info("WebLogAspect.doAfterReturning()");
        log.info("doAfterThrowing 耗时(毫秒)" + (System.currentTimeMillis() - startTime.get()));
    }


}
