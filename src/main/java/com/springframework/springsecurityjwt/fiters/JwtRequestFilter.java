package com.springframework.springsecurityjwt.fiters;

import com.springframework.springsecurityjwt.services.MyUserDetailsService;
import com.springframework.springsecurityjwt.util.JwtUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtRequestFilter extends OncePerRequestFilter {

    MyUserDetailsService userDetailsService;

    JwtUtil jwtUtil;


    public JwtRequestFilter(MyUserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal
            (HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {

        //examine the incoming request for the jwt in the header and if it`s valid.
        final String authHeader = httpServletRequest.getHeader("Authorization");

        String userName = null;
        String jwt = null;

        //extract the userName
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7);
            userName = jwtUtil.exactUserName(jwt);
        }

        //put the token into the security context
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            MyUserDetailsService userDetails = this.userDetailsService;

        }


    }
}
