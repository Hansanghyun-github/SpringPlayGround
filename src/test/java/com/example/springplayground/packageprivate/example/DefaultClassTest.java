package com.example.springplayground.packageprivate.example;

import com.example.springplayground.packageprivate.PublicClass;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DefaultClassTest {
    @Autowired
    BeanFactory beanFactory;

    @Test
    void accessDefaultClassWithinSamePackage() throws Exception {
        // given
        DefaultClass bean = beanFactory.getBean(DefaultClass.class);

        // when // then
        bean.printDefault();
    }

    @Test
    void accessPublicClass() throws Exception {
        // given
        PublicClass bean = beanFactory.getBean(PublicClass.class);

        // when // then
        bean.printPublic();
    }

}