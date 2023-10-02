package com.cos.jwt.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * MyFilter1
 * <pre>
 * Describe here
 * </pre>
 *
 * @version 1.0,
 */

@Slf4j
public class MyFilter1 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("### filter1 start");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
