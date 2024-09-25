package com.example.springplayground.beanDuplicate;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("configuration-example") // if this profile, this bean will be registered
public class TestImplementClass implements EmptyInterface {

    public TestImplementClass() {
        System.out.println("TestImplementClass is created");
    }

    @Override
    public void print() {
        System.out.println("TestImplementClass is loaded");
    }

}
