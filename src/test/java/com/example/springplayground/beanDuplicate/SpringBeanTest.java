package com.example.springplayground.beanDuplicate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("configuration-example")
@SpringBootTest
class SpringBeanTest {
    @Autowired
    ApplicationContext applicationContext;
    @Test
    void duplicate_bean_problem() throws Exception {
        // when // then
        Assertions.assertThatThrownBy(() -> applicationContext.getBean(EmptyInterface.class))
                .isInstanceOf(NoUniqueBeanDefinitionException.class);
    }
}