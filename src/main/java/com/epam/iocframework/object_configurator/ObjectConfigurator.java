package com.epam.iocframework.object_configurator;

import com.epam.iocframework.ApplicationContext;

/**
 * @author Evgeny Borisov
 */
public interface ObjectConfigurator {
    void configure(Object t, ApplicationContext context);
}
