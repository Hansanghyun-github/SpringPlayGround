package com.example.springplayground.packageprivate.example;

import org.springframework.stereotype.Component;

@Component
class DefaultClass {
    public void printDefault() {
        System.out.println("Hello from DefaultClass");
    }
}
