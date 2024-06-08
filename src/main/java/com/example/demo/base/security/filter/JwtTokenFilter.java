package com.example.demo.base.security.filter;

import com.example.demo.base.blacklist_token.BlacklistTokenService;
import com.example.demo.base.exception.ExceptionResponse;
import com.example.demo.base.exception.JwtExceptionProvider;
import com.example.demo.base.jwt.JwtProvider;
import com.example.demo.bounded_context.account.entity.Account;
import com.example.demo.bounded_context.account.entity.Role;
import com.example.demo.bounded_context.account.service.AccountService;
import com.example.demo.bounded_context.auth.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final BlacklistTokenService blacklistTokenService;
    private final ObjectMapper objectMapper;
    private final AccountService accountService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if(request.getMethod().equals("GET")){
            return request.getServletPath().startsWith("/api/wiki") ||
                    request.getServletPath().startsWith("/api/solution") ||
                    request.getServletPath().startsWith("/api/account") ||
                    request.getServletPath().startsWith("/api/questionBoard")
                    ;
        }else if(request.getMethod().equals("POST")){
            return request.getServletPath().startsWith("/api/s3") ||
                    request.getServletPath().startsWith("/api/auth");
        }else return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String accessToken = jwtProvider.parseToken(request);
            blacklistTokenService.checkBlacklist(accessToken); //무효화된 토큰 검사
            Long accountId = Long.parseLong(jwtProvider.getAccountId(accessToken)); //토큰 id 추출
            Account account = accountService.findByAccountId(accountId); //데이터베이스 검사

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_" + account.getRole().name()));

            Authentication authentication = new UsernamePasswordAuthenticationToken(User.of(account), null, authorities); //인증객체 생성
            SecurityContextHolder.getContext().setAuthentication(authentication); //인증정보 저장
            filterChain.doFilter(request, response);
        }catch (Exception e){
            jwtExceptionHandler(response, e);
        }
    }

    public void jwtExceptionHandler(HttpServletResponse response, Exception exception) throws IOException{
        ExceptionResponse exceptionResponse = JwtExceptionProvider.generatorExceptionResponse(exception);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(401);
        response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
    }
}
