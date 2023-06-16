package com.planner.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private ObjectMapper objectMapper = new ObjectMapper(); // JSON 변환을 처리할 ObjectMapper 객체 생성

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {
        // 로그인이 성공한 경우 success 값을 true로 설정하고 JSON 응답을 생성하여 반환합니다.
        Map<String, Object> data = new HashMap<>();
        data.put("success", true);

        // 상태 코드를 설정하고, 반환될 JSON 응답의 컨텐츠 유형과 인코딩을 설정합니다.
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 응답 스트림에 JSON 데이터를 쓰고 응답을 반환합니다.
        objectMapper.writeValue(response.getWriter(), data);
    }
}
