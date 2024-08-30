package com.example.springplayground.mvc.frontcontroller.v3;

import com.example.springplayground.mvc.frontcontroller.v2.MyView;
import com.example.springplayground.mvc.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * V3의 핵심
 * ModelView 도입
 *
 * 기존 ControllerV2는 서블릿 종속성이 있음 (HttpServletRequest, HttpServletResponse)
 * 그리고 뷰 이름이 중복된다.
 *
 * ControllerV3는 ModelView 클래스를 반환하여
 * 논리적인 뷰이름과 모델을 한 번에 담아서 반환한다.
 *
 * 각각의 컨트롤러는 서블릿을 의존하지 않고
 * 모델을 통해 데이터를 전달한다.
 * */
public class FrontControllerServletV3 extends HttpServlet {
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3(MemberRepository memberRepository) {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3(memberRepository));
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3(memberRepository));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        ControllerV3 controller = controllerMap.get(requestURI);
        if (controller == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        Map<String, String> paramMap = createParamMap(req);
        ModelView mv = controller.process(paramMap);
        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);
        view.render(mv.getModel(), req, resp);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest req) {
        Map<String, String> paramMap = new HashMap<>();
        req.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, req.getParameter(paramName)));
        return paramMap;
    }
}
