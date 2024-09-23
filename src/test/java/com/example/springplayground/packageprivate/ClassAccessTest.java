package com.example.springplayground.packageprivate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;

@SpringBootTest
@Transactional
class ClassAccessTest {
    @Autowired
    BeanFactory beanFactory;

    @Test
    void public_class_can_get() throws Exception {
        // given
        PublicClass bean = beanFactory.getBean(PublicClass.class);
        // beanFactory.getBean(DefaultClass.class); // Error: Cannot resolve bean because DefaultClass is package-private

        // when // then
        bean.printPublic();
    }
    
}