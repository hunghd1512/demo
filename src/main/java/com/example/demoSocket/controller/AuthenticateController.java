package com.example.demoSocket.controller;

import com.example.demoSocket.dto.LoginRequest;
import com.example.demoSocket.dto.LoginResponse;
import com.example.demoSocket.sercurity.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthenticateController {


        private final AuthenticationManager authenticationManager;
        private final JwtUtils jwtUtils;

        public AuthenticateController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
            this.authenticationManager = authenticationManager;
            this.jwtUtils = jwtUtils;
        }

        @PostMapping("/login")
        public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
            try {
                // 1️⃣ Tạo authentication token từ username/password
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.getUsername(),
                                loginRequest.getPassword()
                        )
                );

                // 2️⃣ Gán auth vào context (không bắt buộc, nhưng tốt nếu cần securityContext ở controller)
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // 3️⃣ Tạo JWT từ user đã xác thực
                String jwt = jwtUtils.generateJwtToken(authentication);

                return ResponseEntity.ok(new LoginResponse(jwt));
            } catch (BadCredentialsException ex) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Username or password incorrect"));
            }
        }
    }


