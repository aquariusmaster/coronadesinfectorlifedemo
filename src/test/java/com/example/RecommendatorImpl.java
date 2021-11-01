package com.example;

import com.epam.iocframework.annotation.InjectByType;
import com.epam.iocframework.annotation.InjectProperty;
import com.epam.iocframework.annotation.Singleton;
import com.example.cache.Cache;

/**
 * @author Evgeny Borisov
 */
@Singleton
public class RecommendatorImpl implements Recommendator {
    @InjectProperty("wisky")
    private String alcohol;

    @InjectByType
    private CircularService circularService;

    public RecommendatorImpl() {
        System.out.println("recommendator was created");
    }

    @Override
//    @Cache
    public String recommend() {
        circularService.work();
        return "to protect from covid-2019, drink " + alcohol;
    }
}
