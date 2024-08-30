package com.example.springplayground.mvc.frontcontroller.v2;

import com.example.springplayground.mvc.repository.Member;
import com.example.springplayground.mvc.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class MemberListControllerV2 implements ControllerV2 {
    private final MemberRepository memberRepository;
    public MemberListControllerV2(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
        List<Member> members = memberRepository.findAll();
        request.setAttribute("members", members);
        return new MyView("/WEB-INF/views/members.jsp");
    }
}
