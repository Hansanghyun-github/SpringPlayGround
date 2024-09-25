package com.example.springplayground.beanDuplicate;

import org.springframework.stereotype.Component;

@Component
public class ImplementClass implements EmptyInterface {

    @Override
    public void print() {
        System.out.println("ImplementClass is loaded");
    }

}
