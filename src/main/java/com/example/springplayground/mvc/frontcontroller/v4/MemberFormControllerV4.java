package com.example.springplayground.mvc.frontcontroller.v4;

import java.util.Map;

public class MemberFormControllerV4 implements ControllerV4{
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        return "new-form";
    }
}
