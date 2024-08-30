package com.example.springplayground.mvc.frontcontroller.v4;

import com.example.springplayground.mvc.repository.Member;
import com.example.springplayground.mvc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class MemberSaveControllerV4 implements ControllerV4{
    private final MemberRepository memberRepository;
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.put("member", member);
        return "save-result";
    }
}
