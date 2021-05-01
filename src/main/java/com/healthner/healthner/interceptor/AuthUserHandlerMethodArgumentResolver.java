package com.healthner.healthner.interceptor;

import com.healthner.healthner.kakaologin.dto.UserDto;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
    // @AuthUser 어노테이션에 대하여 수행 할 클래스

    @Override
    public Object resolveArgument(
            MethodParameter param, ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        // 파라미터에 @AuthUser가 붙어 있는지, 타입이 UserDto.UserInfo 인지 확인
        if( supportsParameter(param) == false ) {
            // 내가 해석할 수 있는 파라미터가 아니다.
            return WebArgumentResolver.UNRESOLVED;
        }

        // @AuthUser가 붙어있고 타입이 UserDto.UserInfo 인 경우
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpSession session = request.getSession();
        if( session == null) {
            return null;
        }

        return session.getAttribute("userInfo");
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // @AuthUser가 붙어 있는지 확인
        AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);

        // @AuthUser가 안붙어 있는 경우
        if( authUser == null ) {
            return false;
        }

        // UserDto.UserInfo 타입이 아닌 경우
        if( parameter.getParameterType().equals(UserDto.UserInfo.class) == false) {
            return false;
        }

        return true;
    }
}