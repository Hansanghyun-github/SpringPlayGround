package com.example.springplayground.eventListener;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EmptyServiceTest {
    @SpyBean MyEventListener myEventListener;
    @Autowired EmptyService emptyService;

    @Autowired TransactionTemplate transactionTemplate;
    @Test
    void event_listener_test() throws Exception {
        // when
        emptyService.createEvent1(1);

        // then
        verify(myEventListener, times(1)).handleEvent1(any());
    }
    
    @Test
    void event_listener_method_execute_synchronously() throws Exception {
        // given
        long start = System.currentTimeMillis();

        // when
        emptyService.createEvent2();
        
        // then
        long end = System.currentTimeMillis();
        assertThat(end - start).isGreaterThan(2000);
    }

}