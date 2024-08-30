package com.example.springplayground.mvc.frontcontroller.v2;

import com.example.springplayground.mvc.repository.Member;
import com.example.springplayground.mvc.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class MemberSaveControllerV2 implements ControllerV2 {
    private final MemberRepository memberRepository;
    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws SecurityException, IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);
        request.setAttribute("member", member);
        return new MyView("/WEB-INF/views/save-result.jsp");
    }
}
