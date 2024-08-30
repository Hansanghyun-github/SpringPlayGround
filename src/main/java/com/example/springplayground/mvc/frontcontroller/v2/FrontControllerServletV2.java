package com.example.springplayground.mvc.frontcontroller.v2;

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
 * V2의 핵심
 * -> View를 분리한 것
 *
 * 기존 ControllerV1에서는
 * String viewPath = "/WEB-INF/views/new-form.jsp";
 * RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
 * dispatcher.forward(request, response);
 * 위 코드가 중복되어 있다.
 *
 * 위 코드 대신, MyView를 반환하도록 변경
 * 프론트 컨트롤러에서 뷰를 렌더링 하는 역할을 담당
 * */
@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {
    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2(MemberRepository memberRepository) {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2(memberRepository));
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2(memberRepository));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        ControllerV2 controller = controllerMap.get(requestURI);
        if (controller == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        MyView view = controller.process(req, resp);
        view.render(req, resp);
    }
}
