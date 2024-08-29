package com.example.springplayground.mvc.frontcontroller.v1;

import com.example.springplayground.mvc.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * v1의 핵심 - 프론트 컨트롤러의 도입
 * 프론트 컨트롤러 서블릿 하나로 세부 컨트롤러를 호출한다.
 * 여기서 세부 컨트롤러들은 HttpServlet을 의존하지 않는다.
 * (오직 프론트 컨트롤러만 HttpServlet을 의존한다)
 * */
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1(MemberRepository memberRepository) {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1(memberRepository));
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1(memberRepository));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");
        String requestURI = req.getRequestURI();
        ControllerV1 controller = controllerMap.get(requestURI);
        if (controller == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        controller.process(req, resp);
    }
}
