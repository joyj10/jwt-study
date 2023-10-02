package com.cos.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * CorsConfig
 * <pre>
 * Describe here
 * </pre>
 *
 * @version 1.0,
 */

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);   // 내 서버가 응답을 할 때 json을 자바스크립에서 처리할 수 있게 할지를 설정
        config.addAllowedOrigin("*");   // 모든 ip에 응답 허용
        config.addAllowedHeader("*");   // 모든 헤더 응답 허용
        config.addAllowedMethod("*");   // 모든 메서드 응답 허용(GET, POST, PUT, PATCH, DELETE)
        source.registerCorsConfiguration("/api/**", config);    // /api 로 들어오는 모든 주소는 config 를 따르게 등록
        return new CorsFilter(source);
    }
}
