package com.cos.jwt.config;

import com.cos.jwt.filter.CustomFilter2;
import com.cos.jwt.filter.CustomFilter3;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * FilterConfig
 * <pre>
 * Describe here
 * </pre>
 *
 * @version 1.0,
 */

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<CustomFilter2> filter1() {
        FilterRegistrationBean<CustomFilter2> bean = new FilterRegistrationBean<>(new CustomFilter2());
        bean.addUrlPatterns("/*"); // 모든 요청에서 다 걸어라
        bean.setOrder(0); // 낮은 번호가 필터 중에서 가장 먼저 실행
        return bean;
    }

    @Bean
    public FilterRegistrationBean<CustomFilter3> filter2() {
        FilterRegistrationBean<CustomFilter3> bean = new FilterRegistrationBean<>(new CustomFilter3());
        bean.addUrlPatterns("/*"); // 모든 요청에서 다 걸어라
        bean.setOrder(1); // 낮은 번호가 필터 중에서 가장 먼저 실행
        return bean;
    }
}
