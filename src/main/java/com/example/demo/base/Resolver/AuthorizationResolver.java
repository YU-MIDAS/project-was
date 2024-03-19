package com.example.demo.base.Resolver;

import com.example.demo.base.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@RequiredArgsConstructor
@Component
public class AuthorizationResolver implements HandlerMethodArgumentResolver {

    private final JwtProvider jwtProvider;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthorizationHeader.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = jwtProvider.parseToken((HttpServletRequest) webRequest.getNativeRequest());
        Claims claims = jwtProvider.getValidToken(token);

        if (Long.class.equals(parameter.getParameterType())) {
            return Long.valueOf(claims.get("accountId").toString());
        }
        return AccessToken.builder()
                .token(token)
                .accountId(Long.valueOf(claims.get("accountId").toString()))
                .timeToLive(claims.getExpiration())
                .build();
    }
}

