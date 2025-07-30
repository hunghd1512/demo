package com.example.demoSocket.sercurity;

import com.example.demoSocket.entity.User;
import org.apache.catalina.Group;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtUtils jwtUtils;
    private final UserDetailServiceIml userDetailsService;

    public JwtAuthenticationProvider(JwtUtils jwtUtils, UserDetailServiceIml userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String jwt = (String) authentication.getCredentials();
        String username = jwtUtils.extractUsername(jwt);

        if (username == null || !jwtUtils.validateToken(jwt)) {
            throw new BadCredentialsException("Invalid JWT token");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // chỉ xử lý token dạng UsernamePasswordAuthenticationToken, có credentials là JWT
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
