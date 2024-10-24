package com.example.springplayground.beanProcessor;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BeanPostProcessorTest {

    @Test
    void 빈_후처리기에_의해_스프링_빈이_A에서_B로_변경된다() throws Exception {
        // given
        ApplicationContext context =
                new AnnotationConfigApplicationContext(TestConfig.class);

        // when
        assertThatThrownBy(() -> context.getBean(A.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
        B b = context.getBean("beanA", B.class);

        // then
        b.print();
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public A beanA() {
            return new A();
        }

        @Bean
        public AToBProcessor aToBProcessor() {
            return new AToBProcessor();
        }
    }

    static class AToBProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) {
            if (bean instanceof A) {
                return new B();
            }
            return bean;
        }
    }

}