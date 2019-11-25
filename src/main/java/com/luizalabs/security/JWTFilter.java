package com.luizalabs.security;

import com.luizalabs.util.JWTUtils;
import com.luizalabs.util.StringUtils;

import javax.inject.Singleton;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Singleton
public class JWTFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getRequestURI().startsWith("/api/auth")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String tokenHeader = request.getHeader(JWTUtils.TOKEN_HEADER);

        if (StringUtils.isNullOrEmpty(tokenHeader)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String token = tokenHeader.substring("Bearer".length()).trim();

        try {
            JWTUtils.decode(token);
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}