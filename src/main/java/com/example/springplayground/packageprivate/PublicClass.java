package com.example.springplayground.packageprivate;

import org.springframework.stereotype.Component;

@Component
public class PublicClass {
    public void printPublic() {
        System.out.println("Hello from PublicClass");
    }
}
