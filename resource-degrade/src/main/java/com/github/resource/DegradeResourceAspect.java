package com.github.resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author tangsong
 * @date 2021/4/17 23:41
 */
@Aspect
public class DegradeResourceAspect {


    @Around("@annotation(degradeResource)")
    public Object doAround(ProceedingJoinPoint pjp, DegradeResource degradeResource) throws Throwable {
        try {
            return pjp.proceed();
        } catch(Throwable e){
            // need to trace exception list
            Class<? extends Throwable>[] exceptions = degradeResource.exceptionHandle();
            if(exceptions.length > 0) {
                List<Class<? extends Throwable>> exceptionList = Arrays.asList(exceptions);
                // 判断是否为同一个个异常
                if (exceptionBelongTo(e, exceptionList)) {
                    return handleFallbackMethod(pjp, degradeResource, e);
                } else {
                    throw e;
                }
            }
            return handleFallbackMethod(pjp, degradeResource, e);
        }
    }

    /**
     * if the throw exception is belong to exception trace list
     *
     * @param e
     * @param exceptionList
     * @return
     */
    private boolean exceptionBelongTo(Throwable e, List<Class<? extends Throwable>> exceptionList) {
        for (Class<? extends Throwable> aClass : exceptionList) {
            if(aClass.isAssignableFrom(e.getClass())) {
                return true;
            }
        }
        return false;
    }

    /**
     * invoke fallback method
     *
     */
    private Object handleFallbackMethod(ProceedingJoinPoint pjp, DegradeResource degradeResource, Throwable e) throws Throwable {
        // fallback method
        String fallback = degradeResource.fallback();
        if(StringUtils.isEmpty(fallback)) {
            throw e;
        }
        // fallback class
        Class<?> clazz = degradeResource.fallbackClass().length > 0 ? degradeResource.fallbackClass()[0] : pjp.getTarget().getClass();

        // 获取当前执行的方法名称
        Method fallbackMethod = findFallbackMethod(pjp, clazz, fallback);
        if(Objects.isNull(fallbackMethod)) {
            throw e;
        }

        // fallback method args
        Object[] args;
        Object[] originArgs = pjp.getArgs();
        int paramCount = fallbackMethod.getParameterTypes().length;
        if(originArgs.length == paramCount) {
            args = originArgs;
        } else {
            // fill throwable to fallback method args
            args = Arrays.copyOf(originArgs, originArgs.length + 1);
            args[args.length - 1] = e;
        }

        // if static
        if(Modifier.isStatic(fallbackMethod.getModifiers())) {
            return fallbackMethod.invoke(null, args);
        }
        return fallbackMethod.invoke(clazz.newInstance(), args);
    }

    private Method findFallbackMethod(ProceedingJoinPoint pjp, Class<?> clazz, String fallbackName) {
        MethodSignature signers = (MethodSignature) pjp.getSignature();
        Class<?>[] originParams = signers.getParameterTypes();
        Class<?>[] paramsWithException = Arrays.copyOf(originParams, originParams.length + 1);
        paramsWithException[paramsWithException.length - 1] = Throwable.class;
        // find fallback method with origin params
        Method method = findMethod(clazz, originParams, fallbackName, signers.getReturnType());
        if(method == null) {
            // find fallback method with exception params
            method = findMethod(clazz, paramsWithException, fallbackName, signers.getReturnType());
        }
        return method;
    }

    private Method findMethod(Class<?> clazz, Class<?>[] paramsType, String fallbackName, Class<?> returnType) {
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if(method.getName().equals(fallbackName)
                && returnType.isAssignableFrom(method.getReturnType())
                && Arrays.equals(paramsType, method.getParameterTypes())) {
                return method;
            }
        }
        return null;
    }

}
