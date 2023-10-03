package com.cos.jwt.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.config.auth.PrincipalDetails;
import com.cos.jwt.model.User;
import com.cos.jwt.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JwtAuthorizationFilter
 * <pre>
 * 시큐리티가 필터를 가지고 있는데 그 필터 중에 BasicAuthenticationFilter 있음
 * 권한이나 인증이 필요한 특정 주소를 요청했을 때 위 필터를 무조건 타게 되어 있음
 * 만약 권한이나 인증이 필요한 주소가 아니라면 이 필터를 타지 않음
 * </pre>
 *
 * @version 1.0,
 */

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private UserRepository userRepository;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    // 인증이나 권한이 필요한 주소요청이 있을 때 해당 필터를 타게 됨
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("=== 인증이나 권한이 필요한 주소가 요청 ====");

        String jwtHeader = request.getHeader("Authorization");
        log.info("### jwtHeader = {} ", jwtHeader);

        // header 있는지 확인
        if (jwtHeader == null || !jwtHeader.startsWith("Bearer")) {
            chain.doFilter(request, response);
            return;
        }

        // JWT 토큰 검증을 해서 정상적인 사용자 인지 확인
        String token = jwtHeader.replace("Bearer ", "");
        String username = JWT.require(Algorithm.HMAC512("cos")).build().verify(token).getClaim("username").asString();

        // 서명이 정상적으로 됨
        if (username != null) {
            User userEntity = userRepository.findByUsername(username).orElseThrow();
            PrincipalDetails principalDetails = new PrincipalDetails(userEntity);

            // JWT 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어 줌
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());

            // 강제로 시큐리티의 세션에 접근하여 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
