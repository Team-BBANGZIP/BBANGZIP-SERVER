package org.sopt.jwt.auth.web;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.jwt.auth.authentication.UserAuthenticationFactory;
import org.sopt.jwt.core.JwtClaimsKeys;
import org.sopt.jwt.core.JwtTokenProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserAuthenticationFactory userAuthenticationFactory;

    private static final AntPathMatcher PM = new AntPathMatcher();
    private static final List<String> SKIP = List.of(
            "/actuator/**",
            "/callback",
            "/test/jwt/token/issue",
            "/api/v1/auth/signin",
            "/api/v1/auth/re-issue"
    );

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
        String path = request.getServletPath();
        return (SKIP.stream().anyMatch(p -> PM.match(p, path)));
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = jwtTokenProvider.getJwtFromRequest(request);

        if (StringUtils.hasText(token)) {
            Claims claims = jwtTokenProvider.parseAndVerify(token);
            String type = claims.get(JwtClaimsKeys.TYPE, String.class);

            if (JwtClaimsKeys.ACCESS.equals(type)) {
                if (log.isDebugEnabled()) {
                    log.debug("JWT parsed. sub={}, sid={}", claims.getSubject(), claims.get("sid"));
                }
                userAuthenticationFactory.authenticateUser(claims, request);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Skip authentication. tokenType={}", type);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}