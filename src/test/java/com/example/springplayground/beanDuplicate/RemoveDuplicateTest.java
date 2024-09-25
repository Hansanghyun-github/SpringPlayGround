package com.example.springplayground.beanDuplicate;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(MyTestConfiguration.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class RemoveDuplicateTest {
    @Autowired
    EmptyInterface emptyInterface;

    @Autowired
    ApplicationContext applicationContext;
    
    @Test
    void 우선순위로_인해_빈_주입에_성공한다() throws Exception {
        // when // then
        emptyInterface.print();
    }

    @Test
    void 스프링_빈은_2개_조회되서_실패한다() throws Exception {
        // when // then
        Assertions.assertThatThrownBy(() -> applicationContext.getBean(EmptyInterface.class))
                .isInstanceOf(NoUniqueBeanDefinitionException.class);
    }

    @Test
    void 중복되는_빈들_조회() throws Exception {
        // when // then
        applicationContext.getBeansOfType(EmptyInterface.class)
                .forEach((k, v) -> System.out.println(k + " : " + v));
    }

}
