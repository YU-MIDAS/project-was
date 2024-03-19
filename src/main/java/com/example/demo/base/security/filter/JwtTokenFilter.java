package com.example.demo.base.security.filter;

import com.example.demo.base.blacklist_token.BlacklistTokenService;
import com.example.demo.base.exception.CustomException;
import com.example.demo.base.exception.ExceptionCode;
import com.example.demo.base.exception.ExceptionResponse;
import com.example.demo.base.jwt.JwtProvider;
import com.example.demo.base.security.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final BlacklistTokenService blacklistTokenService;
    private final ObjectMapper objectMapper;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getServletPath().startsWith("/swagger-ui") ||
                request.getServletPath().startsWith("/api/auth") ||
                request.getServletPath().startsWith("/v3/api-docs");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String accessToken = jwtProvider.parseToken(request);
            blacklistTokenService.checkBlacklist(accessToken); //무효화된 토큰 검사
            UserDetails userDetails = userDetailsService.loadUserByUsername(jwtProvider.getAccountId(accessToken)); //토큰 계정 검사
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);
            SecurityContextHolder.getContext().setAuthentication(authentication); //인증정보 저장
            filterChain.doFilter(request, response);
        }catch (Exception e){
            jwtExceptionHandler(response, e);
        }
    }

    public void jwtExceptionHandler(HttpServletResponse response, Exception exception) throws IOException{
        ExceptionCode exceptionCode = null;
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(401);
        ExceptionResponse exceptionResponse;
        if (exception instanceof SignatureException) {
            exceptionCode = ExceptionCode.INVALID_TOKEN;
        } else if (exception instanceof MalformedJwtException) {
            exceptionCode = ExceptionCode.WRONG_TOKEN;
        } else if (exception instanceof ExpiredJwtException) {
            exceptionCode = ExceptionCode.EXPIRE_ACCESS_TOKEN;
        } else if (exception instanceof CustomException){
            exceptionCode = ((CustomException) exception).getExceptionCode();
        }
        if (exceptionCode != null){
            exceptionResponse = ExceptionResponse.of(exceptionCode);
        } else {
            exceptionResponse = ExceptionResponse.of(exception);
        }
        response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
    }
}
