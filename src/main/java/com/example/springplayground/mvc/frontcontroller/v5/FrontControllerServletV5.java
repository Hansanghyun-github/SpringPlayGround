package com.example.springplayground.mvc.frontcontroller.v5;

import com.example.springplayground.mvc.frontcontroller.v2.MyView;
import com.example.springplayground.mvc.frontcontroller.v3.MemberFormControllerV3;
import com.example.springplayground.mvc.frontcontroller.v3.MemberListControllerV3;
import com.example.springplayground.mvc.frontcontroller.v3.MemberSaveControllerV3;
import com.example.springplayground.mvc.frontcontroller.v3.ModelView;
import com.example.springplayground.mvc.frontcontroller.v4.MemberFormControllerV4;
import com.example.springplayground.mvc.frontcontroller.v4.MemberListControllerV4;
import com.example.springplayground.mvc.frontcontroller.v4.MemberSaveControllerV4;
import com.example.springplayground.mvc.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * V5를 통해 어댑터 패턴을 적용한 프론트 컨트롤러
 *
 * 기존 V3와 V4는 시그니처가 다르다.
 * 이를 V3Adaptor와 V4Adaptor를 통해 통일시킨다.
 * V5 프론트 컨트롤러는 어댑터만 보고 판단하면 된다.
 *
 * 나중에 새로운 컨트롤러가 추가되어도 어댑터만 추가하면 된다.
 * */
public class FrontControllerServletV5 extends HttpServlet {
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapter = new ArrayList<>();

    public FrontControllerServletV5(MemberRepository memberRepository) {
        initHandlerMappingMap(memberRepository);
        initHandlerAdapters(memberRepository);
    }

    private void initHandlerMappingMap(MemberRepository memberRepository) {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3(memberRepository));
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3(memberRepository));

        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4(memberRepository));
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4(memberRepository));
    }

    private void initHandlerAdapters(MemberRepository memberRepository) {
        handlerAdapter.add(new ControllerV3HandlerAdapter());
        handlerAdapter.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object handler = getHandler(req);
        if (handler == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        ModelView mv = adapter.handle(req, resp, handler);

        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), req, resp);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapter) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter not found. handler=" + handler);
    }

    private Object getHandler(HttpServletRequest req) {
        String requestURI = req.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }
}
