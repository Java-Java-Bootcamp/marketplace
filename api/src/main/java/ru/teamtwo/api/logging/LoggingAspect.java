package ru.teamtwo.api.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.StringJoiner;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Before("within(ru.teamtwo.api..*)")
    public void logBefore(JoinPoint joinPoint) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Parameter[] parameters = method.getParameters();
        Object[] args = joinPoint.getArgs();
        Class<?> declaringClass = method.getDeclaringClass();
        String className = declaringClass.getSimpleName();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Вход  ");
        stringBuilder.append(className);
        stringBuilder.append(".");
        stringBuilder.append(joinPoint.getSignature().getName());

        StringJoiner stringJoiner = new StringJoiner(",","(",")");
        for (int i = 0; i < parameters.length; i++) {
            String argToString = "null";
            try {
                argToString = args[i].toString();
            } catch (Exception ignored){}
            stringJoiner.add(parameters[i].getName()+"="+argToString);
        }
        stringBuilder.append(stringJoiner);

        log.trace(stringBuilder.toString());
    }

    @AfterReturning(pointcut="within(ru.teamtwo.api..*)", returning="returnValue")
    public void logAfter(JoinPoint joinPoint, Object returnValue) {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Class<?> declaringClass = method.getDeclaringClass();
        String className = declaringClass.getSimpleName();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Выход ");
        stringBuilder.append(className);
        stringBuilder.append(".");
        stringBuilder.append(method.getName());
        if(!method.getReturnType().equals(Void.TYPE)){
            stringBuilder.append("=");
            stringBuilder.append(returnValue!=null?returnValue.toString():"null");
        }

        log.trace(stringBuilder.toString());
    }
}