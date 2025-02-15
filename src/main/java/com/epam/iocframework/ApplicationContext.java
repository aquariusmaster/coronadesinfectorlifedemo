package com.epam.iocframework;

import com.epam.iocframework.annotation.Singleton;
import com.epam.iocframework.config.Config;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Evgeny Borisov
 */
public class ApplicationContext {
    @Setter
    private ObjectFactory factory;
    private final Map<Class, Object> cache = new ConcurrentHashMap<>();
    private ThreadLocal<Map<Class, Object>> threadLocalCache = ThreadLocal.withInitial(ConcurrentHashMap::new);

    @Getter
    private Config config;

    public ApplicationContext(Config config) {
        this.config = config;
    }

    public <T> T getObject(Class<T> type) {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }
        if (threadLocalCache.get().containsKey(type)) {
            return (T) threadLocalCache.get().get(type);
        }

        Class<? extends T> implClass = type;

        if (type.isInterface()) {
            implClass = config.getImplClass(type);
        }
        T t = factory.instantiate(implClass);
        threadLocalCache.get().put(type, t);
        t = factory.prepare(t, implClass);
        threadLocalCache.get().remove(type);
        if (implClass.isAnnotationPresent(Singleton.class)) {
            cache.put(type, t);
        }
        return t;
    }
}
