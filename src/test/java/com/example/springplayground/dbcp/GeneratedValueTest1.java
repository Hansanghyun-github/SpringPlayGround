package com.example.springplayground.dbcp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

/**
 * have to use mysql database & connection pool size 1
 * */
@SpringBootTest
@Transactional
@ActiveProfiles("cp-size-1")
public class GeneratedValueTest1 {
    @Autowired MemberAUTORepository memberAUTORepository;
    @Autowired MemberSEQUENCERepository memberSEQUENCERepository;
    @Autowired MemberIDENTITYRepository memberIDENTITYRepository;

    @Test
    void AUTO_strategy_fail_to_save_new_entity() throws Exception {
        // given
        MemberAUTO memberAUTO = new MemberAUTO("memberAUTO");

        // when // then
        assertThatThrownBy(() -> memberAUTORepository.save(memberAUTO))
                .isInstanceOf(DataAccessResourceFailureException.class);
    }

    @Test
    void SEQUENCE_strategy_fail_to_save_new_entity() throws Exception {
        // given
        MemberSEQUENCE memberSEQUENCE = new MemberSEQUENCE("memberSEQUENCE");

        // when // then
        assertThatThrownBy(() -> memberSEQUENCERepository.save(memberSEQUENCE))
                .isInstanceOf(DataAccessResourceFailureException.class);
    }

    @Test
    void IDENTITY_strategy_sueccess_to_save_new_entity() throws Exception {
        // given
        MemberIDENTITY memberIDENTITY = new MemberIDENTITY("memberIDENTITY");

        // when // then
        assertThatCode(() -> memberIDENTITYRepository.save(memberIDENTITY))
                .doesNotThrowAnyException();
        assertThat(memberIDENTITYRepository.findAll().get(0).getName())
                .isEqualTo("memberIDENTITY");
    }

}
