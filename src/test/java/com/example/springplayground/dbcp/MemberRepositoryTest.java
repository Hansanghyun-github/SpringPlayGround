package com.example.springplayground.dbcp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    void member_repository_test() throws Exception {
        // given
        Member member = new Member("test");
        memberRepository.save(member);

        // when
        Member findMember = memberRepository.findById(member.getId()).orElseThrow();

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
    }
}