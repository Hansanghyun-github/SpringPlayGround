package com.example.springplayground.beanDuplicate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(MyTestConfiguration.class)
public class RemoveDuplicateTest {
    @Autowired
    EmptyInterface emptyInterface;

    @Autowired
    ApplicationContext applicationContext;
    
    @Test
    void success_to_bean_create() throws Exception {
        // when // then
        emptyInterface.print();
    }
    
    @Test
    void duplicate_bean_problem_even_if_apply_TestConfiguration() throws Exception {
        // when // then
        Assertions.assertThatThrownBy(() -> applicationContext.getBean(EmptyInterface.class))
                .isInstanceOf(NoUniqueBeanDefinitionException.class);
    }
    
}
