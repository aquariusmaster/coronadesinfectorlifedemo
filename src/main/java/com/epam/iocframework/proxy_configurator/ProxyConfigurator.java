package com.epam.iocframework.proxy_configurator;

/**
 * @author Evgeny Borisov
 */
public interface ProxyConfigurator {
    Object replaceWithProxyIfNeeded(Object t, Class implClass);
}
