package com.healthner.healthner.interceptor;


import com.healthner.healthner.kakaologin.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        // handler 종류 확인 => HandlerMethod 타입인지 체크
        // return true이면,  Controller에 있는 메서드가 아니므로, 그대로 컨트롤러로 진행
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 형 변환 하기
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        // @Auth 받아오기
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

        // method에 @Auth가 없는 경우, 즉 인증이 필요 없는 요청
        if (auth == null) {
            return true;
        }

        // @Auth가 있는 경우이므로, 세션이 있는지 체크
        // 세션이 없으면 'home'으로
        HttpSession session = request.getSession();
        if (session == null) {
            response.sendRedirect("loginerror");
            return false;
        }

        // 세션이 존재하면 유효한 유저인지 확인
        UserDto.UserInfo userInfo = (UserDto.UserInfo) session.getAttribute("userInfo");
        if (userInfo == null) {
            response.sendRedirect("loginerror");
            return false;
        }

        // admin일 경우
        String role = auth.role().toString();
        if(role != null) {
            if ("ADMIN".equals(role)) {
                if (userInfo.getRole() != Role.ADMIN) {
                    response.sendRedirect("loginerror");
                    return false;
                }
            }
        }

        // 접근허가, 즉 메소드를 실행하도록 한다
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}