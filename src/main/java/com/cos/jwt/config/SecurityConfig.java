package com.cos.jwt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

/**
 * SecurityConfig
 * <pre>
 * Describe here
 * </pre>
 *
 * @version 1.0,
 */

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsFilter corsFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션을 사용하지 않는 설정
                .and()
                .addFilter(corsFilter)  // @CrossOrigin(인증 X), 시큐리티 필터에 등록 인증(O) | 모든 요청을 허용
                .formLogin().disable()  // jwt 서버로 되어 있어서 formLogin 사용 X
                .httpBasic().disable()
                .authorizeHttpRequests()
                .antMatchers("/api/v1/user/**").hasAnyRole("USER", "MANAGER,", "ADMIN")
                .antMatchers("/api/v1/manager/**").hasAnyRole("MANAGER,", "ADMIN")
                .antMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll(); // 위 설정 이위에는 권한 없이 접근 가능

        return http.build();
    }
}
