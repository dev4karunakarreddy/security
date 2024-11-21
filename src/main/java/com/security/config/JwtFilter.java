package com.security.config;

import com.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext applicationContext;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization=request.getHeader("Authorization");
        String token=null;
        String useName=null;

        if(authorization!=null
                && authorization.startsWith("Bearer ")){

            token=authorization.substring(7);
            useName=jwtService.extractUserName(token);

        }


        if(useName!=null
                && SecurityContextHolder.getContext().getAuthentication()==null){

            UserDetails userDetails=applicationContext
                    .getBean(UserDetailsServiceImpl.class)
                    .loadUserByUsername(useName);

            if(jwtService.validateToken(token,userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,
                        null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            }

        }
        filterChain.doFilter(request,response);
    }

}
