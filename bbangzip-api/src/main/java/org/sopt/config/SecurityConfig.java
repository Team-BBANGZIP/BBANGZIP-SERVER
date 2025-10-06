package org.sopt.config;

import lombok.RequiredArgsConstructor;
import org.sopt.jwt.auth.web.ExceptionHandlerFilter;
import org.sopt.jwt.auth.web.JwtAuthenticationEntryPoint;
import org.sopt.jwt.auth.web.JwtAuthenticationFilter;
import org.sopt.jwt.support.AuthConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ExceptionHandlerFilter exceptionHandlerFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(jwtAuthenticationEntryPoint));

        // OAuth2 Login 활성화
        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/oauth2/authorization/kakao") // 카카오 로그인 진입점
                .defaultSuccessUrl("/api/v1/auth/signin/kakao/success", true) // 성공시 redirect
                .failureUrl("/api/v1/auth/signin/kakao/fail") // 실패시 redirect
        );

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(AuthConstants.AUTH_WHITE_LIST).permitAll()
                .anyRequest().authenticated()
        );

        http.addFilterBefore(exceptionHandlerFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}