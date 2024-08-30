package com.example.springplayground.mvc.frontcontroller.v4;

import com.example.springplayground.mvc.repository.Member;
import com.example.springplayground.mvc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class MemberListControllerV4 implements ControllerV4{
    private final MemberRepository memberRepository;

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        List<Member> members = memberRepository.findAll();

        model.put("members", members);
        return "members";
    }
}
