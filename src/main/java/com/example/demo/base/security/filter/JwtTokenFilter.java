package com.example.demo.base.security.filter;

import com.example.demo.base.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            Claims claims = jwtProvider.getValidToken(jwtProvider.parseToken(request));
            Authentication authentication = new UsernamePasswordAuthenticationToken(claims.get("userId"), null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication); //인증정보 저장
            filterChain.doFilter(request, response);
        }catch (Exception e){
            jwtExceptionHandler(response, e);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().startsWith("/api/auth");
    }

    public void jwtExceptionHandler(HttpServletResponse response, Exception exception) throws IOException{
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        if (exception instanceof SignatureException) {
            response.getWriter().write("토큰이 유효하지 않습니다.");
        } else if (exception instanceof MalformedJwtException) {
            response.getWriter().write("올바르지 않은 토큰입니다.");
        } else if (exception instanceof ExpiredJwtException) {
            response.getWriter().write("토큰이 만료되었습니다.");
        } else {
            response.getWriter().write("인증 에러 발생: " + exception.getMessage());
        }
    }
}
