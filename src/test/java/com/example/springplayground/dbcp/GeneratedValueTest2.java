package com.example.springplayground.dbcp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

/**
 * have to use mysql database & connection pool size greater than 1
 * */
@SpringBootTest
@Transactional
@ActiveProfiles("cp-size-2")
public class GeneratedValueTest2 {
    @Autowired MemberAUTORepository memberAUTORepository;
    @Autowired MemberSEQUENCERepository memberSEQUENCERepository;
    @Autowired MemberIDENTITYRepository memberIDENTITYRepository;

    @Test
    void AUTO_strategy_success_to_save_new_entity() throws Exception {
        // given
        MemberAUTO memberAUTO = new MemberAUTO("memberAUTO");

        // when // then
        assertThatCode(() -> memberAUTORepository.save(memberAUTO))
                .doesNotThrowAnyException();
        assertThat(memberAUTORepository.findAll().get(0).getName())
                .isEqualTo("memberAUTO");
    }

    @Test
    void SEQUENCE_strategy_success_to_save_new_entity() throws Exception {
        // given
        MemberSEQUENCE memberSEQUENCE = new MemberSEQUENCE("memberSEQUENCE");

        // when // then
        assertThatCode(() -> memberSEQUENCERepository.save(memberSEQUENCE))
                .doesNotThrowAnyException();
        assertThat(memberSEQUENCERepository.findAll().get(0).getName())
                .isEqualTo("memberSEQUENCE");
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
