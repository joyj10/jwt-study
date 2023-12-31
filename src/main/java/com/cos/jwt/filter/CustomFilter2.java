package com.cos.jwt.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * MyFilter1
 * <pre>
 * Describe here
 * </pre>
 *
 * @version 1.0,
 */

@Slf4j
public class CustomFilter2 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("### filter1 start");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
