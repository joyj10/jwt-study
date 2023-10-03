package com.cos.jwt.config.jwt;

import com.cos.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * JwtAuthenticationFilter
 * <pre>
 * 스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음
 * /login 요청해서 username, password 전송하면(post)
 * UsernamePasswordAuthenticationFilter 동작함
 *
 * 지금은 securityConfig 에서 formLogin().disable() 해서 위 로직이 작동하지 않음
 * 하여 별도로 securityConfig 에 필터 등록을 해야 함
 * </pre>
 *
 * @version 1.0,
 */

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행 되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("==== JwtAuthenticationFilter : 로그인 시도 중 ====");

        // 1. username, password 받아서
        try {
            ObjectMapper mapper = new ObjectMapper();
            User user = mapper.readValue(request.getInputStream(), User.class);
            log.info("### user = {} ", user);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            // 2.정상인지 로그인 시도를 함. authenticationManager로 로그인 시도를 하면 PrincipalDetailsService 호출 됨
            // > 그러면 loadUserByUsername 실행 됨
            // PrincipalDetailsService의 loadUserByUsername() 함수가 실행된 후 정상이면 authentication이 리턴됨
            // DB에 있는 username과 password가 일치, authentication에 로그인한 정보가 담김
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            // 3. PrincipalDetails 를 세션에 담고 (session 담지 않으면 권한 관리 안됨)
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            log.info("### principalDetails = {} ", principalDetails.getUser()); // 로그인이 정상적으로 되어서 데이터가 있는 것
            log.info("==== JwtAuthenticationFilter : 로그인 완료 ====");

            // authentication 객체가 session 영역에 저장을 해야하고 그 방법이 return을 해주면 됨
            // 리턴하는 이유는 권한 관리를 security가 대신 해주기 문에 편하려고 하는 것임
            // 굳이 JWT 토큰을 사용하면서 세션을 만들 이유가 없음. 단지 권한 처리 때문에 세션에 넣어 줌

            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    // attemptAuthentication 메서드 실행 후 인증이 정상적으로 되는 경우 실행되는 매서드
    // JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response 해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("==== 인증 완료 된 후 실행 되는 메서드 : successfulAuthentication ====");
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
