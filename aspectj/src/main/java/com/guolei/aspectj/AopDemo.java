package com.guolei.aspectj;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by Android Studio.
 * User: guolei
 * Email: 1120832563@qq.com
 * Date: 2019/4/9
 * Time: 11:03 PM
 * Desc:
 */
@Aspect
public class AopDemo {

    @Before("execution(* android.app.Activity.onCreate(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        Log.e("AopDemo", "beforeMethod: ");
    }

    @After("execution(* android.app.Activity.onCreate(..))")
    public void afterMethod(JoinPoint joinPoint) {

    }

    @Pointcut("execution(* com.guolei.bytecodetechnology.AspectJDemo.aopmethod())")
    public void onAopMethod() {

    }

    @Around("onAopMethod")
    public Object methodOnAopMethod(ProceedingJoinPoint proceedingJoinPoint) {

        return null;
    }
}
