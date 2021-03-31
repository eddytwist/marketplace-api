package com.edik.car.api.web.controller.aspect;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ControllersAspect {

    @Pointcut("execution(* com.edik.car.api.web.controller.*.*(..))")
    public void selectAllMethodsAvaliable() {

    }