package com.example.springplayground.eventListener;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.transaction.support.TransactionTemplate;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EmptyServiceTest {
    @SpyBean MyEventListener myEventListener;
    @Autowired EmptyService emptyService;

    @Autowired TransactionTemplate transactionTemplate;
    @Test
    void when_event_class_called_and_transaction_has_committed_event_listener_method_call() throws Exception {
        // when
        emptyService.createEvent(1);

        // then
        verify(myEventListener, times(1)).eventListenerMethod(any());
    }

    @Test
    void when_target_event_occurs_error_listener_method_does_not_execute_becaue_of_rollback() throws Exception {
        // when
        try {
            emptyService.errorEvent(1);
        } catch (Exception e) {
        }
        
        // then
        verify(myEventListener, never()).eventListenerMethod(any());
    }

    @Test
    void event_listener_method_execute_synchronously() throws Exception {
        // given
        long start = System.currentTimeMillis();

        // when
        emptyService.slowEvent();
        
        // then
        long end = System.currentTimeMillis();
        assertThat(end - start).isGreaterThan(2000);
    }

}