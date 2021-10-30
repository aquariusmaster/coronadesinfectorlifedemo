package com.example.cache;

import com.epam.iocframework.proxy_configurator.ProxyConfigurator;
import net.sf.cglib.proxy.Enhancer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheMethodHandlerProxyConfigurator implements ProxyConfigurator {

    private final Map<String, Object> cache = new ConcurrentHashMap<>();

    @Override
    public Object replaceWithProxyIfNeeded(Object t, Class implClass) {
        for (Method objMethod : implClass.getDeclaredMethods()) {
            Cache annotation = objMethod.getAnnotation(Cache.class);
            if (annotation != null) {
                if (implClass.getInterfaces().length == 0) {
                    return Enhancer.create(implClass, (net.sf.cglib.proxy.InvocationHandler) (proxy, method, args) -> getInvocationHandlerLogic(method, args, t));
                }
                return Proxy.newProxyInstance(implClass.getClassLoader(), implClass.getInterfaces(), (proxy, method, args) -> getInvocationHandlerLogic(method, args, t));

            }
        }
        return t;
    }

    private Object getInvocationHandlerLogic(Method method, Object[] args, Object t) {
        System.out.printf("Cache: method: %s, args: %s, Object: %s\n", method, Arrays.toString(args), t);
        return cache.computeIfAbsent(method.getName(), key -> {
            System.out.println("Computing new cache value for " + key + " method...");
            try {
                return method.invoke(t, args);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
