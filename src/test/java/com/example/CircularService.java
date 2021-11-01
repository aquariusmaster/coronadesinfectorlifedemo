package com.example;

import com.epam.iocframework.annotation.InjectByType;
import com.epam.iocframework.annotation.Singleton;

@Singleton
public class CircularService {

    @InjectByType
    Recommendator recommendator;

    public void work() {
        System.out.println("CircularService work");
    }

    public void reply() {
        recommendator.recommend();
    }
}
