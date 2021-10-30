package com.example;

import com.epam.iocframework.Application;
import com.epam.iocframework.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Evgeny Borisov
 */
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = Application.run("com.example", new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class)));
        CoronaDesinfector desinfector = context.getObject(CoronaDesinfector.class);
        System.out.println(desinfector.getClass());
        desinfector.start(new Room());
    }
}
