package com.example.adminApiWebGameBlog.jwt;

import com.example.adminApiWebGameBlog.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private String getTokenFromHeader(HttpServletRequest request){
        String strToken = request.getHeader("Authorization");
        if(StringUtils.hasText(strToken) && strToken.startsWith("Bearer ")){
            return strToken.substring(7);
        }else {
            return null;
        }
    }

    //token has 2 key type-token and email;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromHeader(request);
        try {
            if(token != null){
                if(jwtTokenHelper.validateToken(token)){
                    String tokenDecode = jwtTokenHelper.decodeToken(token);
                    Map<String,String> result = new ObjectMapper().readValue(tokenDecode, HashMap.class);
                    UserDetails userDetails = userDetailsService.loadUserByUsername(result.get("username"));
                    if(StringUtils.hasText(result.get("typeToken")) && !result.get("typeToken").equals(TypeToken.RESFESH_TOKEN.name())){
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails.getUsername(),"",userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContext sc = SecurityContextHolder.getContext();
                        sc.setAuthentication(authenticationToken);
                    }
                }
            }
        }catch (Exception e){
            System.out.println("error" + e.getMessage());
        }
        filterChain.doFilter(request,response);
    }
}
