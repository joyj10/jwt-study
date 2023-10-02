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
public class MyFilter3 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
      log.info("### filter3 start");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 토큰 : id, pwd 정상적으로 들어와서 로그인이 완료 되면 토큰을 만들어 주고 그걸 응답해 주는 로직 추가
        // 요청할 때 마다 Header 에 Authorization에 value 값으로 토큰을 가지고 옴
        // 그때 토큰이 넘어오면 이 토큰이   내가 만든 토큰이 맞는지만 검증만 하면 됨(RSA, HS256)
        if ("POST".equals(request.getMethod())) {
            log.info("POST 요청");
            String headerAuth = request.getHeader("Authorization");
            log.info("### headerAuth = {} ", headerAuth);

            if ("key".equals(headerAuth)) {
                filterChain.doFilter(request, response);
            } else {
                PrintWriter out = response.getWriter();
                out.println("인증 안됨");
            }
        }
    }
}
