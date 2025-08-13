package com.example.demoSocket.sercurity;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private AuthEntryPointJwt authenticationEntryPoint;

    @Autowired
    private UserDetailServiceIml userDetailServiceIml;
    //    @Autowired
//    private DaoAuthenticationProvider daoAuthenticationProvider;
    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> providers = new ArrayList<>();
        providers.add(jwtAuthenticationProvider);
        providers.add(daoAuthenticationProvider()); // nếu muốn
        return new ProviderManager(providers);
    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtils jwtUtils, AuthenticationManager authManager) {
        return new JwtAuthenticationFilter(jwtUtils, authManager);
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailServiceIml);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(req -> req.requestMatchers("/h2-console/**", "/auth/login", "/secured/room", "/index.html", "/main.css", "/js/**", "/images/**")
                        .permitAll()

                        //     .requestMatchers(HttpMethod.POST,"/api/**").permitAll()
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(jwtAuthenticationProvider) // đăng ký bean để các lớp sau gọi
                .authenticationProvider(daoAuthenticationProvider())
                .addFilterBefore(
                        jwtAuthenticationFilter, // => Nếu request đã có JWT hợp lệ → bỏ qua login, gán Authentication luôn.
                        UsernamePasswordAuthenticationFilter.class  // ✅ Thêm filter tùy chỉnh để kiểm tra JWT trước khi tới filter login mặc định (UsernamePasswordAuthenticationFilter)
                );

        return http.build();
    }



}
