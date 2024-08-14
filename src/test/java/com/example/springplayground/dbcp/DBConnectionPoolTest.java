package com.example.springplayground.dbcp;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import java.sql.SQLTimeoutException;
import java.sql.SQLTransientConnectionException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test-dbcp")
class DBConnectionPoolTest {
    @Autowired MemberRepository memberRepository;
    @Autowired
    HikariDataSource dataSource;

    @Test
    void current_DB_connection_pool_size_is_one() throws Exception {
        // when // then
        assertThat(dataSource.getMaximumPoolSize()).isOne();
    }

    @Test
    void new_connection_was_timeout_because_of_one_connection() throws Exception {
        // given
        dataSource.getConnection();

        // when // then
        assertThatThrownBy(() -> dataSource.getConnection())
                .isInstanceOf(SQLTransientConnectionException.class);

    }

}